package phone.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.list.*;
import phone.utils.*;

public class User extends CommonInc implements java.io.Serializable{

    String id="", username="", role="", firstname="", lastname="", 
	dept_id="9", mail_notification="", inactive=""; // ITS
				
    String fullname="";
    static final long serialVersionUID = 2900L;
    static Logger logger = LogManager.getLogger(User.class);
    Department department = null;
    //
    public User(){
	super();
    }
    public User(boolean deb){
	//
	// initialize
	//
	super(deb);
    }
    public User(String val){
	//
	setId(val);
    }
    public User(boolean deb, String val){
	//
	// initialize
	//
	super(deb);
	setId(val);
    }
    public User(boolean deb, String val, String val2){
	//
	// initialize
	//
	debug = deb;
	setId(val);
	setUsername(val2);
    }
    public User(boolean deb, String val, String val2, String val3, String val4){
	//
	// initialize
	//
	debug = deb;
	setId(val);
	setUsername(val2);
	setFirstname(val3);
	setLastname(val4);
    }		
		
    public User(boolean deb,
		String val,
		String val2,
		String val3,
		String val4,
		String val5,
		String val6,
		String val7,
		String val8
		){
	//
	// initialize
	//
	debug = deb;
	setId(val);
	setUsername(val2);
	setFirstname(val3);
	setLastname(val4);
	setDept_id(val5);
	setRole(val6);
	setMailNotification(val7 != null);
	setInactive(val8 != null);
    }
    public String getId(){
	return id;
    }
    public String getUsername(){
	return username;
    }
    public String getFirstname(){
	return firstname;
    }
    public String getLastname(){
	return lastname;
    }		
    public String getRole(){
	return role;
    }
    public String getDept_id(){
	return dept_id;
    }
    public boolean getMailNotification(){
	return !mail_notification.equals("");
    }
    public boolean getInactive(){
	return !inactive.equals("");
    }
    public boolean isActive(){
	return inactive.equals("");
    }
    public String getFullname(){
	if(fullname.equals("")){
	    if(!firstname.equals("")) fullname = firstname;
	    if(!lastname.equals("")){
		if(!fullname.equals("")) fullname += " ";
		fullname += lastname;
	    }
	}
	return fullname;
    }
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setUsername(String val){
	if(val != null)
	    username = val;
    }		
    public void setRole(String val){
	if(val != null)
	    role = val;
    }
    public void setDept_id(String val){
	if(val != null && !val.equals("-1"))
	    dept_id = val;
    }
    public void setFirstname(String val){
	if(val != null)
	    firstname = val.trim();
    }
    public void setLastname(String val){
	if(val != null)
	    lastname = val.trim();
    }
    public void setMailNotification(boolean val){
	if(val)
	    mail_notification = "y";
    }
    public void setInactive(boolean val){
	if(val)
	    inactive = "y";
    }		
    //
    public boolean userExists(){
	return !lastname.equals("");
    }
    //
    public boolean hasRole(String val){
	return role != null && role.indexOf(val) > -1;
    }
    public boolean canEdit(){
	return hasRole("Edit");
    }
    public boolean isAdmin(){
	return hasRole("Admin");
    }
    //
    public String toString(){
	return getFullname();
    }
    public boolean equals(Object obj){
	if(obj instanceof User){
	    User one =(User)obj;
	    return id.equals(one.getId());
	}
	return false;				
    }
    public int hashCode(){
	int seed = 37;
	if(!id.equals("")){
	    try{
		seed += Integer.parseInt(id);
	    }catch(Exception ex){
	    }
	}
	return seed;
    }
    //
    public String doSelect(){
	String msg = "";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
	String qq = " select id,username,firstname,lastname,dept_id,role,mail_notification,inactive from users where  ";
	if(!id.equals("")){
	    qq += " id = ? ";
	}
	else if(!username.equals("")){ // for login
	    qq += " username = ? ";
	}
	else {
	    msg = " User id or username not set";
	    addError(msg);
	    return msg;
	}
	if(debug){
	    logger.debug(qq);
	}
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    stmt = con.prepareStatement(qq);
	    if(!id.equals(""))
		stmt.setString(1, id);
	    else
		stmt.setString(1, username);								
	    rs = stmt.executeQuery();
	    if(rs.next()){
		setId(rs.getString(1));
		setUsername(rs.getString(2));
		setFirstname(rs.getString(3));
		setLastname(rs.getString(4));
		setDept_id(rs.getString(5));								
		setRole(rs.getString(6));
		setMailNotification(rs.getString(7) != null);
		setInactive(rs.getString(8) != null);
	    }
	    else{
		msg = " No such user";
		message = msg;
	    }
	}catch(Exception ex){
	    logger.error(ex+" : "+qq);
	    msg += " "+ex;
	    addError(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, stmt, rs);
	}
	return msg;
    }
    //
    public String doSave(){
		
	Connection con = null;
	PreparedStatement stmt = null, stmt2=null;
	ResultSet rs = null;		
		
	String str="", msg="";
	String qq = "";
	if(username.equals("") || lastname.equals("")){
	    msg = "username or  last name not set";
	    return msg;
	}
	qq = "insert into users values(0,?,?,?,?,?, ?, null)";
	//
	if(debug){
	    logger.debug(qq);
	}
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    stmt = con.prepareStatement(qq);
	    stmt.setString(1, username);
	    if(firstname.equals(""))
		stmt.setNull(2, Types.VARCHAR);
	    else
		stmt.setString(2,firstname);
	    stmt.setString(3,lastname);
	    if(dept_id.equals(""))
		stmt.setNull(4,Types.INTEGER);
	    else
		stmt.setString(4, dept_id);
	    if(role.equals(""))
		stmt.setNull(5,Types.INTEGER);
	    else
		stmt.setString(5, role);
	    if(mail_notification.equals(""))
		stmt.setNull(6, Types.CHAR);
	    else
		stmt.setString(6,mail_notification);						
	    stmt.executeUpdate();
	    qq = "select LAST_INSERT_ID() ";
	    if(debug){
		logger.debug(qq);
	    }
	    stmt2 = con.prepareStatement(qq);				
	    rs = stmt2.executeQuery();
	    if(rs.next())
		id = rs.getString(1);
	}
	catch(Exception ex){
	    msg = ex+": "+qq;
	    logger.error(msg);
	    addError(msg);
	    return msg;
	}
	finally{
	    Helper.databaseDisconnect(con, stmt, stmt2, rs);
	}
	return msg; // success
    }
    public String doUpdate(){
		
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
		
	String str="", msg="";
	String qq = "";
	qq = "update users set username=?,firstname=?,lastname=?, dept_id=?,role=?,mail_notification=?,inactive=? where id=?";
	//
	if(id.equals("") || username.equals("") || lastname.equals("")){
	    msg = "User id, username or last name not set";
	    return msg;
	}				
	if(debug){
	    logger.debug(qq);
	}
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    stmt = con.prepareStatement(qq);
	    stmt.setString(1, username);
	    if(firstname.equals(""))
		stmt.setNull(2,Types.VARCHAR);
	    else
		stmt.setString(2, firstname);
	    stmt.setString(3, lastname);
	    if(dept_id.equals(""))
		stmt.setNull(4,Types.INTEGER);
	    else
		stmt.setString(4, dept_id);						
	    if(role.equals(""))
		stmt.setNull(5,Types.VARCHAR);
	    else
		stmt.setString(5, role);
	    if(mail_notification.equals(""))
		stmt.setNull(6,Types.CHAR);
	    else
		stmt.setString(6, "y");
	    if(inactive.equals(""))
		stmt.setNull(7,Types.CHAR);
	    else
		stmt.setString(7, "y");						
	    stmt.setString(8, id);
	    stmt.executeUpdate();
	}
	catch(Exception ex){
	    msg = ex+": "+qq;
	    logger.error(msg);
	    addError(msg);
	    return msg;
	}
	finally{
	    Helper.databaseDisconnect(con, stmt, rs);
	}
	return msg; // success
    }		
    //
    public String doDelete(){

	String qq = "",msg="";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;			
	qq = "delete from  users where id=?";
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    stmt = con.prepareStatement(qq);
	    stmt.setString(1,id);
	    stmt.executeUpdate();
	    message="Deleted Successfully";
	    //
	}
	catch(Exception ex){
	    msg = ex+" : "+qq;
	    logger.error(msg);
	    addError(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, stmt, rs);
	}			
	return msg; 
    }


}
