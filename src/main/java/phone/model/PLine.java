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
/**
 * could represents service line, fax, interns, help line
 */
public class PLine extends CommonInc implements java.io.Serializable{

    String id="", name="",  type="Employee";
    // for adding a new phone
    String phone_number="", phone_id="", del_ext_id="";
    static final long serialVersionUID = 2800L;
    static Logger logger = LogManager.getLogger(PLine.class);
    Phone phone = null;
    PhoneExt phoneExt = null;
    //
    public PLine(){
	super();
    }
    public PLine(boolean deb){
	//
	// initialize
	//
	super(deb);
    }
    public PLine(String val){
	//
	setId(val);
    }
    public PLine(boolean deb, String val){
	//
	// initialize
	//
	super(deb);
	setId(val);
    }
    public PLine(boolean deb, String val, String val2){
	//
	// initialize
	//
	debug = deb;
	setId(val);
	setName(val2);
    }
    public PLine(boolean deb, String val, String val2, String val3){
	//
	// initialize
	//
	debug = deb;
	setId(val);
	setName(val2);
	setType(val3);
    }
    public String getId(){
	return id;
    }
    public String getName(){
	return name;
    }
    public String getType(){
	return type;
    }
    public boolean hasPhone(){
	getPhone();
	return phone != null;
    }
    public Phone getPhone(){

	if(phone == null && !id.equals("")){
	    PLinePhoneList ppl = new PLinePhoneList(debug, id);
	    String back = ppl.find();
	    if(back.equals("")){
		List<PLinePhone> ones = ppl.getPLinePhones();
		if(ones != null && ones.size() > 0){
		    PLinePhone one = ones.get(0);
		    Phone pp = one.getPhone();
		    if(pp != null){
			phone = pp;
		    }
		    PhoneExt pe = one.getPhoneExt();
		    if(pe != null){
			phoneExt = pe;
		    }
		}
	    }
	}
	return phone;
    }
    public String getInfo(){
	String ret = name;
	if(hasPhoneExt()){
	    ret += " (Ext:"+phoneExt.getExt_number()+") ";
	}
	return ret;
    }
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setName(String val){
	if(val != null)
	    name = val.trim();
    }
    public void setType(String val){
	if(val != null)
	    type = val;
    }
    public void setPhone_number(String val){
	if(val != null)
	    phone_number = val;
    }
    public void setPhone_id(String val){
	if(val != null)
	    phone_id = val;
    }
    public void setDel_ext_id(String val){
	if(val != null)
	    del_ext_id = val;
    }
		
    //
    public String toString(){
	return name;
    }
    public boolean equals(Object obj){
	if(obj instanceof PLine){
	    PLine one =(PLine)obj;
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
    public PhoneExt getPhoneExt(){
	if(phoneExt == null && !id.equals("")){
	    getPhone(); // this does both
	}
	return phoneExt;
    }
    public boolean hasPhoneExt(){
	getPhoneExt();
	return phoneExt != null;
    }
    //
    public String doSelect(){
	String msg = "";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
	String qq = " select id,name,type from plines where id = ? ";
				
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
		setId(rs.getString(1));
		setName(rs.getString(2));
		setType(rs.getString(3));
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
	if(name.equals("")){
	    msg = "name not set";
	    return msg;
	}
	if(!phone_number.equals("")){
	    msg = addPhone();
	}				
	qq = "insert into plines values(0,?,?)";
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
	    stmt.setString(1, name);
	    stmt.setString(2, type);						
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
	qq = "update plines set name=?,type=? where id=?";
	//
	if(id.equals("") || name.equals("")){
	    msg = " id or name not set";
	    return msg;
	}
	if(!phone_number.equals("")){
	    msg = addPhone();
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
	    stmt.setString(1, name);
	    stmt.setString(2, type);
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
	return msg; // success
    }
    public String addPhone(){
	String back = "";
	if(!phone_number.equals("")){
	    Phone pp = new Phone(debug, null, phone_number);
	    back = pp.doSave();
	    if(back.equals("")){
		phone_id = pp.getId();
	    }
	    if(phone_number.indexOf("ext") > -1){
		String val = phone_number.substring(phone_number.indexOf("ext") +3);
		PhoneExt pe = new PhoneExt(debug, null, val, val, phone_id);
		back = pe.doSave();
		if(back.equals("")){
		    phoneExt = pe;
		}
	    }
	}
	return back;
    }
    //
    public String doDelete(){

	String qq = "",qq2="", msg="";
	Connection con = null;
	PreparedStatement stmt = null, stmt2=null;
	ResultSet rs = null;			
	qq  = "delete from pline_phones where line_id=?";
	qq2 = "delete from plines where id=?";
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
	    stmt2 = con.prepareStatement(qq2);
	    stmt2.setString(1,id);
	    stmt2.executeUpdate();						
	    message="Deleted Successfully";
	    //
	}
	catch(Exception ex){
	    msg = ex+" : "+qq;
	    logger.error(msg);
	    addError(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, stmt, stmt2, rs);
	}			
	return msg; 
    }
    public String removeExtension(){

	String qq = "", msg="";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	if(del_ext_id.equals("")){
	    msg = "No extension selected ";
	    return msg;
	}
	qq  = "update pline_phones set ext_id=null where line_id=? and ext_id=?";
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    stmt = con.prepareStatement(qq);
	    stmt.setString(1,id);
	    stmt.setString(2,del_ext_id);
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
