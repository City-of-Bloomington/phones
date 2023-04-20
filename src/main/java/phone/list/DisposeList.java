package phone.action;
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

public class DisposeList extends CommonInc{

    static Logger logger = LogManager.getLogger(DisposeList.class);
    static final long serialVersionUID = 1900L;
    String name = ""; // for service
    String id = "";
    boolean decided = false, undecided = false;
    List<Dispose> disposes = null;
	
    public DisposeList(){
    }
    public DisposeList(boolean deb){
	super(deb);
    }
    public List<Dispose> getDisposes(){
	return disposes;
    }
		
    public void setName(String val){
	if(val != null)
	    name = val;
    }
    public void setDecidedOnly(){
	decided = true;
    }
    public void setUndecidedOnly(){
	undecided = true;
    }		
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = "select d.id,d.line_id,d.name,date_format(d.date_time,'%m/%d/%Y %H:%i'),d.agree,d.decision_user_id from disposed_lines d ";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	String qw = "";

	try{
	    if(!name.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " d.name like ? ";
	    }
	    if(!id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " d.id = ? ";
	    }
	    if(decided){
		if(!qw.equals("")) qw += " and ";
		qw += " d.agree is not null";
	    }
	    else if(undecided){
		if(!qw.equals("")) qw += " and ";
		qw += " d.agree is null";
	    }
	    if(!qw.equals("")){
		qq += " where "+qw;
	    }
	    qq += " order by d.name ";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    int jj=1;
	    if(!name.equals("")){
		pstmt.setString(jj++,"%"+name+"%");
	    }
	    if(!id.equals("")){
		pstmt.setString(jj++, id);
	    }
	    rs = pstmt.executeQuery();
	    if(disposes == null)
		disposes = new ArrayList<Dispose>();
	    while(rs.next()){
		Dispose one =
		    new Dispose(debug,
				rs.getString(1),
				rs.getString(2),
				rs.getString(3),
				rs.getString(4),
				rs.getString(5),
				rs.getString(6));
		if(!disposes.contains(one))
		    disposes.add(one);
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






















































