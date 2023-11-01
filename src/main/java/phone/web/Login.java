package phone.web;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import javax.naming.*;
import javax.naming.directory.*;
import javax.sql.*;
import java.net.URL;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.model.*;

@WebServlet(urlPatterns = {"/CasLogin"})
public class Login extends TopServlet{

    static Logger logger = LogManager.getLogger(Login.class);
    static final long serialVersionUID = 1600L;		

    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException {
	String message="";
	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	String name, value, id="", username="";
	AttributePrincipal principal = null;				
	if (req.getUserPrincipal() != null) {
	    principal = (AttributePrincipal) req.getUserPrincipal();
	    username = principal.getName();
	}
	String host_forward = req.getHeader("X-Forwarded-Host");
	String host = req.getHeader("host");	
								
	if(host_forward != null){
	    System.err.println(" host_forward "+host_forward);
	    url = "https://"+host_forward+"/phones/";
	}
	else if(host != null){
	    /**
	    if(host.indexOf("phones") > -1){
		url = host;
	    }
	    else{
		url = host+"/phones/";
	    }
	    */
	}
	Enumeration<String> values = req.getParameterNames();				
	while (values.hasMoreElements()) {
	    name = ((String)values.nextElement()).trim();
	    value = (req.getParameter(name)).trim();
	    if (name.equals("id")) {
		id = value;
	    }
	}
	HttpSession session = null;
	if(principal != null){
	    final Map attributes = principal.getAttributes();
	    Iterator attributeNames = attributes.keySet().iterator();
	    if (attributeNames.hasNext()) {
		for (; attributeNames.hasNext(); ) {
                    String attributeName = (String) attributeNames.next();
                    // System.err.println(" name "+attributeName);
                    final Object attributeValue = attributes.get(attributeName);
		    if (attributeValue instanceof List) {
                        final List vals = (List) attributeValue;
                        System.err.println("Multi-valued attribute: " + vals.size());
			int jj=1;
                        for (Object val : vals) {
                            System.err.println(jj+" "+val);
			    jj++;
                        }
                    } else {
                        System.err.println(" value "+attributeValue);
                    }
		}
	    }
	}
	if(username != null && !username.equals("")){
	    session = req.getSession(false);			
	    User user = getUser(username);
	    if(user != null && session != null){
		session.setAttribute("user",user);
		String action = url+"welcome.action";
		// we need this when we send a url to a user
		// when assigned to him/her
		if(!id.equals("")){
		    action = url+"phone.action?id="+id;
		}
		out.println("<head><title></title><META HTTP-EQUIV=\""+
			    "refresh\" CONTENT=\"0; URL=" + action+
			    "\"></head>");
		out.println("<body>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		return;								
	    }
	}
	message += " You can not access this system, check with IT or try again later";
	out.println("<head><title></title><body>");
	out.println("<p><font color=red>");
	out.println(message);
	out.println("</p>");
	out.println("</body>");
	out.println("</html>");
	out.flush();	
    }
    User getUser(String username){

	User user = null;
	String message="";
	User one = new User(debug, null, username);
	String back = one.doSelect();
	if(!back.equals("")){
	    message += back;
	    logger.error(back);
	}
	else{
	    user = one;
	}
	return user;
    }
}






















































