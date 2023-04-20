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

public class PLinePhoneList extends CommonInc{

    static Logger logger = LogManager.getLogger(PLinePhoneList.class);
    static final long serialVersionUID = 2400L;
    String id="", name = "", line_id="",  phone_id="", limit="";
		
    List<PLinePhone> pLinePhones = null;
    public PLinePhoneList(){
	super();
    }
    public PLinePhoneList(boolean deb){
	super(deb);
    }		
    public PLinePhoneList(boolean deb, String val){
	super(deb);
	setLine_id(val);
    }
    public List<PLinePhone> getPLinePhones(){
	return pLinePhones;
    }
    public void setLine_id(String val){
	if(val != null)
	    line_id = val;
    }
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setPhone_id(String val){
	phone_id = val;
    }		
    public String getId(){
	return id;
    }

    public String getPhone_id(){
	return phone_id;
    }		
    public void setNoLimit(){
	limit = "";
    }
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = " select e.id,e.line_id,e.phone_id,e.ext_id,i.ext_number,i.mail_box from pline_phones e left join phone_exts i on i.id=e.ext_id  ";
	String qw="";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	if(!id.equals("")){
	    qw += " e.id = ? ";
	}
	else{
	    if(!line_id.equals("")){
		qw += " e.line_id = ? ";
	    }
	    if(!phone_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " e.phone_id=? ";
	    }
	}
	if(!qw.equals("")){
	    qq += " where "+qw;
	}
	qq += " order by e.id ";
	if(!limit.equals("")){
	    qq += limit;
	}
	try{
	    if(debug){
		logger.debug(qq);
	    }
	    // System.err.println(qq);
	    //
	    pstmt = con.prepareStatement(qq);
	    if(!id.equals("")){
		pstmt.setString(1, id);
	    }
	    else {
		int jj=1;
		if(!line_id.equals("")){
		    pstmt.setString(jj++,line_id);
		}
		if(!phone_id.equals("")){
		    pstmt.setString(jj++,phone_id);		
		}
	    }
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		if(pLinePhones == null)
		    pLinePhones = new ArrayList<>();
		PLinePhone one =
		    new PLinePhone(debug,
				   rs.getString(1),
				   rs.getString(2),
				   rs.getString(3),
				   rs.getString(4),
				   rs.getString(5),
				   rs.getString(6)
				   );
		if(!pLinePhones.contains(one))
		    pLinePhones.add(one);
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






















































