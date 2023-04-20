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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.model.*;
import phone.utils.*;

public class PhoneExtList extends CommonInc{

    static Logger logger = LogManager.getLogger(PhoneExtList.class);
    static final long serialVersionUID = 3150L;
    String id="",  ext_number="", mail_box="", phone_id="", limit=" limit 30" ;
    boolean active_only = false;
    List<PhoneExt> phoneExts = null;
    public PhoneExtList(){
	super();
    }
    public PhoneExtList(boolean deb){
	super(deb);
    }		
    public PhoneExtList(boolean deb, String val){
	super(deb);
	setPhone_id(val);
    }
    public List<PhoneExt> getPhoneExts(){
	return phoneExts;
    }
    public void setExt_number(String val){
	if(val != null)
	    ext_number = val;
    }
    public void setMail_box(String val){
	if(val != null)
	    mail_box = val;
    }
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setPhone_id(String val){
	if(val != null)
	    phone_id = val;
    }
    public String getId(){
	return id;
    }
    public String getExt_number(){
	return ext_number;
    }
    public String getMail_box(){
	return mail_box;
    }		
    public String getPhone_id(){
	return phone_id;
    }
    public void setNoLimit(){
	limit = "";
    }
    public void setActiveOnly(){
	active_only = true;
    }		
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = " select i.id,i.ext_number,i.mail_box,i.phone_id "+
	    " from phone_exts i,phones p  ";
	String qw = " p.id=i.phone_id ";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	if(!id.equals("")){
	    if(!qw.equals("")) qw += " and ";
	    qw += " i.id = ? ";
	}
	else{
	    if(!ext_number.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " i.ext_number = ? ";
	    }
	    if(!mail_box.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " i.mail_box = ? ";
	    }						
	    if(!phone_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " i.phone_id=? ";
	    }
	    if(active_only){
		if(!qw.equals("")) qw += " and ";
		qw += " p.active is not null ";
	    }
	}
	if(!qw.equals("")){
	    qq += " where "+qw;
	}
	qq += " order by i.ext_number ";
	if(!limit.equals("")){
	    qq += limit;
	}
	try{
	    if(debug){
		logger.debug(qq);
	    }
	    //
	    // System.err.println(qq);
	    //
	    pstmt = con.prepareStatement(qq);
	    if(!id.equals("")){
		pstmt.setString(1, id);
	    }
	    else {
		int jj=1;
		if(!ext_number.equals("")){
		    pstmt.setString(jj++,ext_number);
		}
		if(!mail_box.equals("")){
		    pstmt.setString(jj++,mail_box);
		}								
		if(!phone_id.equals("")){
		    pstmt.setString(jj++,phone_id);		
		}
	    }
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		if(phoneExts == null)
		    phoneExts = new ArrayList<>();
		PhoneExt one =
		    new PhoneExt(debug,
				 rs.getString(1),
				 rs.getString(2),
				 rs.getString(3),
				 rs.getString(4));
		if(!phoneExts.contains(one))
		    phoneExts.add(one);
	    }
	}
	catch(Exception ex){
	    back += ex+" : "+qq;
	    logger.error(back);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return back;
    }
}






















































