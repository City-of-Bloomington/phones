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

public class PLinePhone extends CommonInc implements java.io.Serializable{

    String id="", mail_box="",  ext_id = "", phone_id="", line_id="";
    static final long serialVersionUID = 2300L;
    static Logger logger = LogManager.getLogger(PLinePhone.class);
    Phone phone = null;
    PhoneExt phoneExt = null;
    //
    public PLinePhone(){
	super();
    }
    public PLinePhone(boolean deb){
	//
	// initialize
	//
	super(deb);
    }
    public PLinePhone(String val){
	//
	setId(val);
    }
    public PLinePhone(boolean deb, String val){
	//
	// initialize
	//
	super(deb);
	setId(val);
    }
    public PLinePhone(boolean deb, String val, String val2, String val3, String val4){
	//
	debug = deb;
	setValues(val,val2, val3, val4);
    }
    public PLinePhone(boolean deb, String val, String val2, String val3, String val4, String val5, String val6){
	//
	debug = deb;
	setValues(val,val2, val3, val4);
	setPhoneExt(val5, val6);
    }		
    public void setValues(String val, String val2, String val3, String val4){
	setId(val);
	setLine_id(val2);				
	setPhone_id(val3);
	setExt_id(val4);
    }		
    private void setPhoneExt(String val2, String val3){
	if(val2 != null || val3 != null){
	    phoneExt = new PhoneExt(debug, ext_id, val2, val3, phone_id);
	}
    }
		
    public String getId(){
	return id;
    }
    public String getPhone_id(){
	return phone_id;
    }
    public String getExt_id(){
	return ext_id;
    }		
    public String getLine_id(){
	return line_id;
    }
    public boolean hasPhone(){
	return !phone_id.equals("");
    }
    public Phone getPhone(){
	if(phone == null && !phone_id.equals("")){
	    Phone one = new Phone(debug, phone_id);
	    String back = one.doSelect();
	    if(back.equals("")){
		phone = one;
	    }
	}
	return phone;
    }
    public boolean hasPhoneExt(){
	return !ext_id.equals("");
    }
    public PhoneExt getPhoneExt(){
	if(phoneExt == null && !ext_id.equals("")){
	    PhoneExt one = new PhoneExt(debug, ext_id);
	    String back = one.doSelect();
	    if(back.equals("")){
		phoneExt = one;
	    }
	}
	return phoneExt;
    }		
    public void setId(String val){
	if(val != null)
	    id = val;
    }

    public void setPhone_id(String val){
	if(val != null)
	    phone_id = val;
    }
    public void setExt_id(String val){
	if(val != null)
	    ext_id = val.trim();
    }
    public void setLine_id(String val){
	if(val != null)
	    line_id = val;
    }		
    //
    public String toString(){
	return getId();
    }
    public boolean equals(Object obj){
	if(obj instanceof PLinePhone){
	    PLinePhone one =(PLinePhone)obj;
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
	String qq = " select e.id,e.line_id,e.phone_id,e.ext_id,i.ext_number,i.mail_box from pline_phones e left join phone_exts i on i.id=e.ext_id where e.id=? ";
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
		setValues(rs.getString(1),
			  rs.getString(2),
			  rs.getString(3),
			  rs.getString(4));
		setPhoneExt(rs.getString(5),
			    rs.getString(6));
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
	if(phone_id.equals("") || line_id.equals("")){
	    msg = "line id and/or phone id  not set";
	    return msg;
	}
	qq = "insert into pline_phones values(0,?,?,?)";
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
	    stmt.setString(1, line_id);
	    stmt.setString(2, phone_id);
	    if(ext_id.equals(""))
		stmt.setNull(3,Types.INTEGER);
	    else
		stmt.setString(3, ext_id);						
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
	qq = "update pline_phones set ext_id=?,line_id=?,phone_id=? where id=?";
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
	    if(ext_id.equals(""))
		stmt.setNull(1,Types.INTEGER);
	    else
		stmt.setString(1, ext_id);
	    if(line_id.equals(""))
		stmt.setNull(2,Types.INTEGER);
	    else
		stmt.setString(2, line_id);
	    if(phone_id.equals(""))
		stmt.setNull(3,Types.INTEGER);
	    else
		stmt.setString(3, phone_id);						
	    stmt.setString(4, id);
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
	doSelect();
	return msg; // success
    }
    public String doDelete(){
		
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
		
	String str="", msg="";
	String qq = "";
	qq = "delete from pline_phones where id=?";
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
	    stmt.setString(1, id);
	    stmt.executeUpdate();
	    id=""; ext_id="";
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
	return msg; 
    }				



}
