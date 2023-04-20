package phone.web;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.List;
import phone.model.*;
import phone.list.*;
import phone.utils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = {"/EmployeeService"})
public class EmployeeService extends TopServlet{

    static EnvBean envBean = null; // move to TopServlet
    static final long serialVersionUID = 2500L;
    static Logger logger = LogManager.getLogger(EmployeeService.class);

    public void doGet(HttpServletRequest req,
		      HttpServletResponse res)
	throws ServletException, IOException {
	doPost(req,res);
    }

    /**
     * Generates the Group form and processes view, add, update and delete operations.
     *
     * @param req The request input stream
     * @param res The response output stream
     */
    public void doPost(HttpServletRequest req,
		       HttpServletResponse res)
	throws ServletException, IOException {

	String id = "";

	//
	String message="", action="";
	res.setContentType("application/json");
	PrintWriter out = res.getWriter();
	String name, value;
	String term ="", type="";
	boolean success = true;
	HttpSession session = null;
	/**
	if(url.equals("")){
	    url    = getServletContext().getInitParameter("url");
	    //
	    String str = getServletContext().getInitParameter("debug");
	    if(str.equals("true")) debug = true;
	    if(envBean == null){
		envBean = new EnvBean();
		str = getServletContext().getInitParameter("ldap_url");
		envBean.setUrl(str);
		str = getServletContext().getInitParameter("ldap_principle");
		envBean.setPrinciple(str);
		str = getServletContext().getInitParameter("ldap_password");
		envBean.setPassword(str);
	    }
	}
	*/
	Enumeration<String> values = req.getParameterNames();
	String [] vals = null;
	while (values.hasMoreElements()){
	    name = values.nextElement().trim();
	    vals = req.getParameterValues(name);
	    value = vals[vals.length-1].trim();
	    if (name.equals("term")) { // this is what jquery sends
		term = value;
	    }
	    else if (name.equals("type")) {
		type = value;
	    }
	    else if (name.equals("action")){
		action = value;
	    }
	    else{
	    }
	}
	//	System.err.println("emp service called "+term);
				
	EmpList empList =  null;
	List<LdapEmployee> emps = null;
	if(term.length() > 2){
	    //
	    empList = new EmpList(debug, envBean);
	    if(term.matches("\\d+")){ // decimal
		empList.setPhoneNum(term);
	    }
	    else{
		empList.setName(term);
	    }
	    String back = empList.find();
	    if(back.equals("")){
		emps = empList.getEmps();
	    }
	}
	if(emps != null && emps.size() > 0){
	    String json = writeJson(emps, type);
	    out.println(json);
	}
	out.flush();
	out.close();
    }

    /**
     * Creates a JSON array string for a list of users
     *
     * @param users The users
     * @param type unused
     * @return The json string
     */
    String writeJson(List<LdapEmployee> emps, String type){
	String json="";
	for(LdapEmployee one:emps){
	    if(!json.equals("")) json += ",";
	    json += "{\"id\":\""+one.getFullname()+"\",\"value\":\""+one.getFullname()+"\",\"dept\":\""+one.getDept()+"\",\"phone\":\""+one.getPhone_number()+"\",\"busCat\":\""+one.getBusCat()+"\",\"fullname\":\""+one.getFullname()+"\",\"division\":\""+one.getDivision()+"\"}";
	}
	json = "["+json+"]";
	// System.err.println("json "+json);
	return json;
    }
}
