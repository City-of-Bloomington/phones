package phone.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import javax.naming.*;
import javax.naming.directory.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.list.*;
import phone.utils.*;

public class ScanLog extends CommonInc{

    String id="", time="", outcome="", failure_reason="";
    static final long serialVersionUID = 2700L;	
    static Logger logger = LogManager.getLogger(ScanLog.class);
    //
    public ScanLog(){
	super();
    }	
    public ScanLog(boolean deb, String val){
	//
	// initialize
	//
	super(deb);
	setId(val);
    }
    public ScanLog(boolean deb, String val, String val2, String val3, String val4){
	//
	// initialize
	//
	super(deb);
	setId(val);
	setTime(val2);
	setOutcome(val3);
	setFailure_reason(val4);
    }		
		
    //
    public ScanLog(boolean deb){
	//
	// initialize
	//
	super(deb);
    }
    public boolean equals(Object obj){
	if(obj instanceof ScanLog){
	    ScanLog one =(ScanLog)obj;
	    return id.equals(one.getId());
	}
	return false;				
    }
    public int hashCode(){
	int seed = 17;
	if(!id.equals("")){
	    try{
		seed += Integer.parseInt(id);
	    }catch(Exception ex){
	    }
	}
	return seed;
    }
    //
    // getters
    //
    public String getId(){
	return id;
    }
    public String getTime(){
	return time;
    }
    public String getOutcome(){
	return outcome;
    }
    public String getFailure_reason(){
	return failure_reason;
    }		
    //
    // setters
    //
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setTime(String val){
	if(val != null)
	    time = val;
    }
    public void setOutcome(String val){
	if(val != null)
	    outcome = val;
    }
    public void setFailure_reason(String val){
	if(val != null)
	    failure_reason = val;
    }		
    public String toString(){
	return id+" "+time;
    }
    //
    public String doSelect(){
	String back = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "select id,date_format(time,'%m/%d/%Y %H:%i'),outcome,failure_reason "+
	    "from scan_logs where id=?";
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
		setTime(rs.getString(2));
		setOutcome(rs.getString(3));
		setFailure_reason(rs.getString(4));
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
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "insert into scan_logs values(0,now(),?,?)";
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
	    pstmt.setString(1,outcome);
	    if(failure_reason.equals("")){
		pstmt.setNull(2, Types.VARCHAR);
	    }
	    else
		pstmt.setString(2, failure_reason);
	    pstmt.executeUpdate();
	    //
	    // get the id of the new record
	    //
	    qq = "select LAST_INSERT_ID() ";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);				
	    rs = pstmt.executeQuery();
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
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return back;

    }

}
