package phone.list;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;
import javax.naming.*;
import javax.naming.directory.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.model.*;
import phone.utils.*;

public class EmpList extends CommonInc{

    static Logger logger = LogManager.getLogger(EmpList.class);
    static final long serialVersionUID = 2200L;
    static EnvBean bean = null;
    String name = "", phoneNum="";
		
    List<LdapEmployee> emps = null;
    public EmpList(){
	super();
    }
    public EmpList(boolean deb, EnvBean val){
	super(deb);
	setEnvBean(val);
    }
    public EmpList(boolean deb, EnvBean val, String val2){
	super(deb);
	setEnvBean(val);
	setPhoneNum(val2);
    }		
    public List<LdapEmployee> getEmps(){
	return emps;
    }
    public void setName(String val){
	if(val != null)
	    name = val;
    }
    public void setPhoneNum(String val){
	if(val != null)
	    phoneNum = val;
    }		
    public void setEnvBean(EnvBean val){
	if(val != null)
	    bean = val;
    }		
    public String getName(){
	return name;
    }

    boolean connectToServer(Hashtable env){

	if(env != null && bean != null){
	    env.put(Context.INITIAL_CONTEXT_FACTORY, 
		    "com.sun.jndi.ldap.LdapCtxFactory");
	    env.put(Context.PROVIDER_URL, bean.getUrl());
	    env.put(Context.SECURITY_AUTHENTICATION, "simple"); 
	    env.put(Context.SECURITY_PRINCIPAL, bean.getPrinciple());
	    env.put(Context.SECURITY_CREDENTIALS, bean.getPassword());
	}
	else{
	    return false;
	}
	try {
	    DirContext ctx = new InitialDirContext(env);
	} catch (NamingException ex) {
	    // System.err.println(" ldap "+ex);
	    return false;
	}
	return true;
    }
    public String find(){
	Hashtable env = new Hashtable(11);
	String back = "", str="";
	LdapEmployee emp = null;
	if (!connectToServer(env)){
	    System.err.println("Unable to connect to ldap");
	    return null;
	}
	try{
	    //
	    DirContext ctx = new InitialDirContext(env);
	    SearchControls ctls = new SearchControls();
	    String[] attrIDs = {"givenName",
		"department",
		"telephoneNumber",
		"mail",
		"cn",
		"sn",
		"division",
		"displayName",
		"otherTelephone", //pager, mobil
		"businessCategory",
		"title"};
	    //
	    ctls.setReturningAttributes(attrIDs);
	    ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    String filter = "";
	    if(!name.equals(""))
		filter = "(&(objectCategory=person)(displayName=*"+name+"*))";
	    else if(!phoneNum.equals(""))
		filter = "(&(objectCategory=person)(telephoneNumber=*"+phoneNum+"*))";				
	    int cnt = 0;
	    NamingEnumeration answer = ctx.search("", filter, ctls);
	    while(answer.hasMore()){
		//
		String fullname="";
		emp = new LdapEmployee();
		SearchResult sr = (SearchResult)(answer.next());
		Attributes atts = sr.getAttributes();
		//
		Attribute cn = (Attribute)(atts.get("cn"));
		if (cn != null){
		    String userid = cn.get().toString();
		    if(userid.indexOf("*") > -1){
			cnt++;
			continue;		
		    }
		    emp.setUsername(userid);
		}
		Attribute givenname = (Attribute)(atts.get("givenName"));
		if (givenname != null){
		    str = givenname.get().toString();
		    fullname = str;
		}
		Attribute sn = (Attribute)(atts.get("sn"));
		if (sn != null){
		    str = sn.get().toString();
		    fullname = fullname+" "+str;
		}
		Attribute cat = (Attribute)(atts.get("businessCategory"));
		if (cat != null){
		    str = cat.get().toString();
		    emp.setBusCat(str);
		}
		Attribute department = 
		    (Attribute)(atts.get("department"));
		if (department != null){
		    str = department.get().toString();
		    emp.setDept(str);
		}
		Attribute tele = (Attribute)(atts.get("division"));
		if (tele != null){
		    str = tele.get().toString();
		    emp.setDivision(str);
		}								
		tele = (Attribute)(atts.get("telephoneNumber"));
		if (tele != null){
		    str = tele.get().toString();
		    emp.setPhone_number(str);
		}
		tele = (Attribute)(atts.get("otherTelephone"));
		if (tele != null){
		    str = tele.get().toString();
		    emp.setPhone_number2(str);
		}
		tele = (Attribute)(atts.get("displayName"));
		if (tele != null){
		    str = tele.get().toString();
		    emp.setFullname(str);
		}
		else{
		    emp.setFullname(fullname);
		}
		Attribute title = (Attribute)(atts.get("title")); //job title
		if (title != null){
		    str = title.get().toString();
		    emp.setJobTitle(str);
		}
								
		Attribute email = (Attribute)(atts.get("mail"));
		if (email != null){
		    str = email.get().toString();
		    emp.setEmail(str);
		}
		if(emps == null){
		    emps = new ArrayList<>();
		}
		emps.add(emp);
	    }
	    for(LdapEmployee one:emps){
		System.err.println(one);
	    }
	}
	catch(Exception ex){
	    logger.error(ex);
	}
	return back;
    }

    public String findAll(){
	Hashtable env = new Hashtable(11);
	String back = "", str="";
	LdapEmployee emp = null;
	if (!connectToServer(env)){
	    System.err.println("Unable to connect to ldap");
	    return null;
	}
	try{
	    //
	    DirContext ctx = new InitialDirContext(env);
	    SearchControls ctls = new SearchControls();
	    String[] attrIDs = {"givenName",
		"department",
		"telephoneNumber",
		"mail",
		"cn",
		"sn",
		"ou",
		"division",
		"displayname",
		"othertelephone",
		"businessCategory",
		"title"};
	    //
	    ctls.setReturningAttributes(attrIDs);
	    ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	    String filter = "";
	    filter = "(&(objectCategory=person)(telephoneNumber=*))";
						
	    int cnt = 0, cnt2=0;
	    NamingEnumeration answer = ctx.search("", filter, ctls);
	    while(answer.hasMore()){
		//
		boolean fire = false;
		String fullname="";
		emp = new LdapEmployee();
		SearchResult sr = (SearchResult)(answer.next());
		Attributes atts = sr.getAttributes();
		//
		Attribute cn = (Attribute)(atts.get("cn"));
		if (cn != null){
		    String userid = cn.get().toString();
		    //
		    // avoid those that starts with *
		    //
		    if(userid.indexOf("*") > -1){ 
			cnt++;
			continue;		
		    }
		    emp.setUsername(userid);
		}
		Attribute givenname = (Attribute)(atts.get("givenName"));
		if (givenname != null){
		    str = givenname.get().toString();
		    fullname = str;
		}
		Attribute sn = (Attribute)(atts.get("sn"));
		if (sn != null){
		    str = sn.get().toString();
		    fullname = fullname+" "+str;
		}
		Attribute cat = (Attribute)(atts.get("businessCategory"));
		if (cat != null){
		    str = cat.get().toString();
		    emp.setBusCat(str);
		}
		Attribute department = 
		    (Attribute)(atts.get("department"));
		if (department != null){
		    str = department.get().toString();
		    emp.setDept(str);
		}
		Attribute ou = 
		    (Attribute)(atts.get("ou"));
		if (ou != null){
		    str = ou.get().toString();
		    // System.err.println(" ou "+str);
		}								
		Attribute tele = (Attribute)(atts.get("division"));
		if (tele != null){
		    str = tele.get().toString();
		    emp.setDivision(str);
		}
		tele = (Attribute)(atts.get("ou"));
		if (tele != null){
		    str = tele.get().toString();
		    // System.err.println(" ou "+str);
		    emp.setDivision(str);
		}											
		tele = (Attribute)(atts.get("telephoneNumber"));
		if (tele != null){
		    str = tele.get().toString();
		    emp.setPhone_number(str);
		}
		tele = (Attribute)(atts.get("othertelephone"));
		if (tele != null){
		    str = tele.get().toString();
		    emp.setPhone_number2(str);
		}
		Attribute title = (Attribute)(atts.get("title")); //job title
		if (title != null){
		    str = title.get().toString();
		    emp.setJobTitle(str);
		}
		Attribute email = (Attribute)(atts.get("mail"));
		if (email != null){
		    str = email.get().toString();
		    emp.setEmail(str);
		}
		Attribute home = (Attribute)(atts.get("displayname"));
		if (home != null){
		    str = home.get().toString();
		    emp.setFullname(str);
		    // System.err.println(" fullname "+str);
		}
		else{
		    emp.setFullname(fullname);
		}
		if(emps == null){
		    emps = new ArrayList<>();
		}
		emps.add(emp);
	    }
	}
	catch(Exception ex){
	    logger.error(ex);
	}
	return back;
    }

}






















































