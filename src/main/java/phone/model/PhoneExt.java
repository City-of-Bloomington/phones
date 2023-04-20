package phone.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */

import java.sql.*;
import java.io.*;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.list.*;
import phone.utils.*;

public class PhoneExt extends CommonInc{

    static final long serialVersionUID = 1800L;			
    static Logger logger = LogManager.getLogger(PhoneExt.class);		
    String id = "", ext_number="", mail_box="", phone_id="";
    public PhoneExt(){

    }
    public PhoneExt(boolean deb, String val){
	super(deb);
	setId(val);

    }				
    public PhoneExt(boolean deb, String val, String val2, String val3, String val4){
	super(deb);
	setId(val);
	setExt_number(val2);
	setMail_box(val3);
	setPhone_id(val4);
    }		
    public String getId(){
	return id;
    }
    public String getPhone_id(){
	return phone_id;
    }		
    public String getExt_number(){
	return ext_number;
    }
    public String getMail_box(){
	return mail_box;
    }		
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setPhone_id(String val){
	if(val != null)
	    phone_id = val;
    }		
    public void setExt_number(String val){
	if(val != null)
	    ext_number = val;
    }
    public void setMail_box(String val){
	if(val != null)
	    mail_box = val;
    }		
    public boolean equals(Object obj){
	if(obj instanceof PhoneExt){
	    PhoneExt one =(PhoneExt)obj;
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
    public String toString(){
	String ret = ext_number;
	if(ret.equals("")){
	    ret = mail_box;
	}
	return ret;
    }
		
    public String doSelect(){
	String back = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "select id,ext_number,mail_box,phone_id "+
	    "from phone_exts where id=?";
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	try{
	    if(debug){
		logger.debug(qq);
	    }				
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1,id);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		setExt_number(rs.getString(2));
		setMail_box(rs.getString(3));
		setPhone_id(rs.getString(4));
	    }
	    else{
		back ="Record "+id+" Not found";
		message = back;
	    }
	}
	catch(Exception ex){
	    back += ex+":"+qq;
	    logger.error(back);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);			
	}
	return back;
    }
    public String doSave(){
		
	String back = "";
		
	Connection con = null;
	PreparedStatement pstmt = null, pstmt2=null;
	ResultSet rs = null;
	String qq = "insert into phone_exts values(0,?,?,?)";
	if(ext_number.equals("") && mail_box.equals("")){
	    back = "ext number, or mail box not set ";
	    logger.error(back);
	    addError(back);
	    return back;
	}
	if(phone_id.equals("")){
	    back = "phone id not set ";
	    logger.error(back);
	    addError(back);
	    return back;
	}				
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	try{
	    pstmt = con.prepareStatement(qq);
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt.setString(1, ext_number);
	    if(mail_box.equals(""))
		pstmt.setNull(2, Types.INTEGER);
	    else
		pstmt.setString(2, mail_box);								
	    pstmt.setString(3, phone_id);
	    pstmt.executeUpdate();
	    //
	    // get the id of the new record
	    //
	    qq = "select LAST_INSERT_ID() ";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt2 = con.prepareStatement(qq);				
	    rs = pstmt2.executeQuery();
	    if(rs.next()){
		id = rs.getString(1);
	    }
	}
	catch(Exception ex){
	    back += ex;
	    logger.error(back);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, pstmt2, rs);
	}
	return back;

    }
    public String doUpdate(){
		
	String back = "";
	if(ext_number.equals("")){
	    back = " ext number not set ";
	    logger.error(back);
	    addError(back);
	    return back;
	}
	if(phone_id.equals("")){
	    back = " phone_id not set ";
	    logger.error(back);
	    addError(back);
	    return back;
	}				
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String str="";
	String qq = "";
		
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	try{
	    qq = "update phone_exts set ext_number=?,mail_box=?,phone_id=? "+
		"where id=?";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1,ext_number);
	    if(mail_box.equals(""))
		pstmt.setNull(2, Types.INTEGER);
	    else
		pstmt.setString(2, mail_box);									
	    pstmt.setString(3,phone_id);
	    pstmt.setString(4,id);
	    pstmt.executeUpdate();
	}
	catch(Exception ex){
	    back += ex+":"+qq;
	    logger.error(back);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return back;

    }
    public String doDelete(){
		
	String back = "", qq = "";
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
		
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	try{
	    qq = "delete from phone_exts where id=?";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1,id);
	    pstmt.executeUpdate();
	    message="Deleted Successfully";
	    id="";
	    ext_number="";
	    phone_id="";
	}
	catch(Exception ex){
	    back += ex+":"+qq;
	    logger.error(back);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return back;
    }

}
