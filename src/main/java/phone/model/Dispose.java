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

public class Dispose extends CommonInc implements java.io.Serializable{

    String id="", name="", line_id="", date_time="",
	agree="", decision_user_id="";
    // SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    static final long serialVersionUID = 1000L;
    static Logger logger = LogManager.getLogger(Dispose.class);
    PLine pLine = null;
    User user = null;
    //
    public Dispose(){
	super();
    }
    public Dispose(boolean deb){
	//
	// initialize
	//
	super(deb);
    }
    public Dispose(String val){
	//
	setId(val);
    }
    public Dispose(boolean deb, String val){
	//
	// initialize
	//
	super(deb);
	setId(val);
    }
    public Dispose(boolean deb,
		   String val,
		   String val2){
	setLine_id(val);
	setName(val2);
				
    }
    public Dispose(boolean deb,
		   String val,
		   String val2,
		   String val3,
		   String val4,
		   String val5,
		   String val6
		   ){
	debug = deb;
	setVals(val, val2, val3, val4, val5, val6);
    }
    void setVals(
		 String val,
		 String val2,
		 String val3,
		 String val4,
		 String val5,
		 String val6
		 ){				
	//
	// initialize
	//

	setId(val);
	setLine_id(val2);
	setName(val3);
	setDate_time(val4);
	setAgree(val5 != null);
	setDecision_user_id(val6);
    }
		
    public String getId(){
	return id;
    }
    public String getLine_id(){
	return line_id;
    }		
    public String getName(){
	return name;
    }
    public String getDate_time(){
	return date_time;
    }
    public boolean getAgree(){
	return !agree.equals("");
    }		
    public boolean isAgree(){
	return !agree.equals("");
    }
    public String getDecision_user_id(){
	return decision_user_id;
    }

    public void setId(String val){
	if(val != null)
	    id = val;
    }
		
    public void setLine_id(String val){
	if(val != null)
	    line_id = val;
    }		
    public void setName(String val){
	if(val != null)
	    name = val;
    }
    public void setDate_time(String val){
	if(val != null)
	    date_time = val;
    }
    public void setAgree(boolean val){
	if(val)
	    agree = "y";
    }
    public void setDecision_user_id(String val){
	if(val != null)
	    decision_user_id = val;
    }
    //
    public String toString(){
	return id;
    }
    public boolean equals(Object obj){
	if(obj instanceof Dispose){
	    Dispose one =(Dispose)obj;
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
    public PLine getPLine(){
	String back = "";
	if(!line_id.equals("") && pLine == null){
	    PLine one = new PLine(debug, line_id);
	    back = one.doSelect();
	    if(back.equals("")){
		pLine = one;
	    }
	}
	return pLine;
    }
    public User getUser(){
	String back = "";
	if(!decision_user_id.equals("") && user == null){
	    User one = new User(debug, decision_user_id);
	    back = one.doSelect();
	    if(back.equals("")){
		user = one;
	    }
	}
	return user;
    }		
    // todo date_format
    public String doSelect(){
	String msg = "";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
	String qq = " select id,line_id,name,"+
	    " type,date_format(date_time,'%m/%d/%Y %H:%i'),agree,decision_user_id "+
	    " from disposed_lines "+
	    " where id=?";
	if(id.equals("")){
	    msg = " id not set";
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
	    stmt.setString(1, id);
	    rs = stmt.executeQuery();
	    if(rs.next()){
		setVals(rs.getString(1),
			rs.getString(2),
			rs.getString(3),
			rs.getString(4),
			rs.getString(5),
			rs.getString(6));
	    }
	    else{
		msg = " No match found ";
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
	PreparedStatement stmt = null;
	ResultSet rs = null;		
		
	String str="", msg="";
	String qq = "";
	if(line_id.equals("")){
	    msg = "line id not set";
	    return msg;
	}
	qq = "insert into disposed_lines values(0,?,?,now(), null, null)";
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
	    msg = setParams(stmt);
	    if(msg.equals("")){
		stmt.executeUpdate();
		qq = "select LAST_INSERT_ID() ";
		if(debug){
		    logger.debug(qq);
		}
		stmt = con.prepareStatement(qq);				
		rs = stmt.executeQuery();
		if(rs.next())
		    id = rs.getString(1);
	    }
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
    private String setParams(PreparedStatement stmt){
	String back = "";
	try{
	    stmt.setString(1, line_id);
	    if(name.equals(""))
		stmt.setNull(2,Types.VARCHAR);
	    else
		stmt.setString(2, name);
	}
	catch(Exception ex){
	    back += ex;
	}
	return back;
    }
    public String doUpdate(){
		
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
		
	String str="", msg="";
	String qq = "";
	qq = "update disposed_lines set agree=?,"+
	    " decision_user_id=? "+
	    " where id=?";
	//
	if(id.equals("")){
	    msg = " id not set";
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
	    if(agree.equals(""))
		stmt.setNull(1,Types.CHAR);
	    else
		stmt.setString(1, "y");						
	    if(decision_user_id.equals(""))
		stmt.setNull(2,Types.INTEGER);
	    else
		stmt.setString(2, decision_user_id);						
	    stmt.setString(3, id);
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
	if(msg.equals("") && !agree.equals("")){
	    msg = doDeleteObj();
	}
	if(msg.equals("")){
	    doSelect();
	}
	return msg; 
    }
    String doDeleteObj(){
	String back = "";
	getPLine();
	if(pLine != null){
	    back = pLine.doDelete();
	}
	return back;
    }
    //
    String doDelete(){

	String qq = "",msg="";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;			
	qq = "delete from  disposed_lines where id=?";
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
