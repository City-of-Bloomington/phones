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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = {"/Logout"})
public class Logout extends TopServlet{

    static Logger logger = LogManager.getLogger(Logout.class);
    static final long serialVersionUID = 1700L;		

    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException{

	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	Enumeration values = req.getParameterNames();
	String name= "";
	String value = "";
	HttpSession session = null;
	session = req.getSession(false);
	if(session != null){
	    session.invalidate();
	}
	String str = url;
	res.sendRedirect(str);
	return;
    }

}






















































