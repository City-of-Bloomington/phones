package phone.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.list.*;
import phone.utils.*;

public class Contract extends CommonInc implements java.io.Serializable{

    String id="", name="", start_date="", end_date="", notification_date="",
	notes="", rate_plan_id="",
	billing_id=""; // we do not need vendor_id we have in billings
    int days_before_expire = 0;
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    static final long serialVersionUID = 1000L;
    // changed from 3 months to 1 only
    static final int defaultNotificationateMonthsBeforeEndDate = -1;		
    static Logger logger = LogManager.getLogger(Contract.class);
    Department department = null;
    Type ratePlan = null;
    Type vendor = null;

    List<FileUpload> fileUploads = null;
    //
    public Contract(){
	super();
    }
    public Contract(boolean deb){
	//
	// initialize
	//
	super(deb);
    }
    public Contract(String val){
	//
	setId(val);
    }
    public Contract(boolean deb, String val){
	//
	// initialize
	//
	super(deb);
	setId(val);
    }
    public Contract(boolean deb, String val, String val2){
	//
	// initialize
	//
	debug = deb;
	setId(val);
	setName(val2);
    }
    public Contract(boolean deb,
		    String val,
		    String val2,
		    String val3,
		    String val4,
		    String val5,
		    String val6,
		    String val7,
		    int val8
		    ){
	debug = deb;
	setVals(val, val2, val3, val4, val5, val6, val7, val8);
    }
    // 
    public Contract(boolean deb, String val, String val2, String val3, String val4){
	debug = deb;
	setName(val);
	setBilling_id(val2);
	setStart_date(val3);
	setEnd_date(val4);
    }
    void setVals(
		 String val,
		 String val2,
		 String val3,
		 String val4,
		 String val5,
		 String val6,
		 String val7,
		 int val8
		 ){				
	//
	// initialize
	//

	setId(val);
	setName(val2);				
	setBilling_id(val3);
	setStart_date(val4);
	setEnd_date(val5);
	setNotification_date(val6);
	setNotes(val7);
	setDays_before_expire(val8 );

    }
		
    public String getId(){
	return id;
    }
    public String getName(){
	return name;
    }		
    public String getRate_plan_id(){
	return rate_plan_id;
    }
    public String getBilling_id(){
	return billing_id;
    }		
    public String getStart_date(){
	return start_date;
    }
    public String getEnd_date(){
	return end_date;
    }
    public String getNotification_date(){
	return notification_date;
    }
    public String getNotes(){
	return notes;
    }
    public String getDays_before_expire(){
	return ""+days_before_expire;
    }						
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setBilling_id(String val){
	if(val != null && !val.equals("-1"))
	    billing_id = val;
    }		
		
    public void setName(String val){
	if(val != null)
	    name = val;
    }
    // we ignore negative values
    public void setDays_before_expire(int val){
	if(val > 0)
	    days_before_expire = val;
    }				

    public void setStart_date(String val){
	if(val != null){
	    if(val.indexOf("-") > -1){
		start_date = changeDate(val);
	    }
	    else{
		start_date = val;
	    }
	}
    }
    public void setEnd_date(String val){
	if(val != null){
	    if(val.indexOf("-") > -1){
		end_date = changeDate(val);
	    }
	    else{
		end_date = val;
	    }
	}
    }
    public void setNotification_date(String val){
	if(val != null)
	    notification_date = val;
    }		
    public void setNotes(String val){
	if(val != null)
	    notes = val;
    }
    // change date from yyyy-mm-dd to mm/dd/yyyy
    private String changeDate(String val){
	String new_date = val;
	if(val != null && val.indexOf("-") > -1){
	    String[] vals = val.split("-");
	    if(vals != null && vals.length == 3){
		new_date = vals[1]+"/"+vals[2]+"/"+vals[0];
	    }
	}
	return new_date;
    }
    //
    public String toString(){
	return name;
    }
    public boolean equals(Object obj){
	if(obj instanceof Contract){
	    Contract one =(Contract)obj;
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
    public boolean hasAttachments(){
	getFileUploads();
	return fileUploads != null && fileUploads.size() > 0;

    }

    /*
      public Type getVendor(){
      if(vendor == null && !vendor_id.equals("")){
      Type one = new Type(debug, vendor_id, null, "vendors");
      String back = one.doSelect();
      if(back.equals("")){
      vendor = one;
      }
      }
      return vendor;
      }
    */
    public List<FileUpload> getFileUploads(){
	if(fileUploads == null && !id.equals("")){
	    FileUploadList ful = new FileUploadList(debug, id);
	    ful.setObj_type("Contract");
	    String back = ful.find();
	    if(back.equals("")){
		List<FileUpload> ones = ful.getUploads();
		if(ones != null && ones.size() > 0)
		    fileUploads = ones;
	    }
	}
	return fileUploads;

    }
    void findNotificationDate(){
	if(notification_date.equals("") && !end_date.equals("")){
	    // 3 months before the end date
	    String date = Helper.getDateFrom(end_date,"Month",defaultNotificationateMonthsBeforeEndDate);
	    if(date != null){
		// if the new date is less than start_date,
		// then we use start_date
		// else we use the new date for notification
		if(!start_date.equals("")){
		    if(Helper.isDateLess(date, start_date)){
			notification_date = start_date;
		    }
		    else{
			notification_date = date;
		    }
		}
		else{
		    notification_date = date;
		}
	    }
	}
    }
    //
    public String doSelect(){
	String msg = "";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
	String qq = " select id,name,billing_id,"+
	    " date_format(start_date,'%m/%d/%Y'),date_format(end_date,'%m/%d/%Y'),date_format(notification_date,'%m/%d/%Y'),notes, "+
	    " datediff(end_date,now()) "+
	    " from contracts "+
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
		int days = 0;
		String str = rs.getString(8);
		if(str != null){
		    days = rs.getInt(8);
		}
		setVals(rs.getString(1),
			rs.getString(2),
			rs.getString(3),
			rs.getString(4),
			rs.getString(5),
			rs.getString(6),
			rs.getString(7),
			days);
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
	if(name.equals("")){
	    msg = "contract name not set";
	    return msg;
	}
	findNotificationDate();
	qq = "insert into contracts values(0,?,?,?,?,  ?,?)";
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
	if(msg.equals("")){
	    msg = doSelect();
	}
	return msg; // success
    }
    private String setParams(PreparedStatement stmt){
	String back = "";
	java.util.Date date_tmp = null;
	try{
	    stmt.setString(1, name);
	    if(billing_id.equals("")){
		stmt.setNull(2, Types.INTEGER);
	    }
	    else{
		stmt.setString(2, billing_id);
	    }
	    if(start_date.equals("")){
		stmt.setNull(3,Types.DATE);
	    }
	    else{
		date_tmp = dateFormat.parse(start_date);
		stmt.setDate(3, new java.sql.Date(date_tmp.getTime()));
	    }
	    if(end_date.equals("")){
		stmt.setNull(4,Types.DATE);
	    }
	    else{
		date_tmp = dateFormat.parse(end_date);
		stmt.setDate(4, new java.sql.Date(date_tmp.getTime()));
	    }
	    if(notification_date.equals(""))
		stmt.setNull(5,Types.DATE);
	    else{
		date_tmp = dateFormat.parse(notification_date);
		stmt.setDate(5, new java.sql.Date(date_tmp.getTime()));
	    }
	    if(notes.equals(""))
		stmt.setNull(6,Types.VARCHAR);
	    else
		stmt.setString(6, notes);
	}
	catch(Exception ex){
	    back += ex;
	}
	return back;
    }
    String doSaveForImport(PreparedStatement stmt){
	String back = "";
	findNotificationDate();
	java.util.Date date_tmp = null;
	try{
	    stmt.setString(1, name);
	    if(billing_id.equals("")){
		stmt.setNull(2, Types.INTEGER);
	    }
	    else{
		stmt.setString(2, billing_id);
	    }
	    if(start_date.equals("")){
		stmt.setNull(3,Types.DATE);
	    }
	    else{
		date_tmp = dateFormat.parse(start_date);
		stmt.setDate(3, new java.sql.Date(date_tmp.getTime()));
	    }
	    if(end_date.equals("")){
		stmt.setNull(4, Types.DATE);
	    }
	    else{
		date_tmp = dateFormat.parse(end_date);
		stmt.setDate(4, new java.sql.Date(date_tmp.getTime()));
	    }
	    if(notification_date.equals(""))
		stmt.setNull(5,Types.DATE);
	    else{
		date_tmp = dateFormat.parse(notification_date);
		stmt.setDate(5, new java.sql.Date(date_tmp.getTime()));
	    }
	    stmt.executeUpdate();
	}
	catch(Exception ex){
	    back += ex;
	    System.err.println(" contract "+ex);
	}
	return back;
    }		
    public String doUpdate(){
		
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
		
	String str="", msg="";
	String qq = "";
	qq = "update contracts set name=?,billing_id=?,"+
	    " start_date=?,end_date=?,notification_date=?,"+
	    " notes=? "+
	    " where id=?";
	//
	if(id.equals("") || name.equals("")){
	    msg = " id or contract name not set";
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
	    msg = setParams(stmt);
	    if(msg.equals("")){
		stmt.setString(7, id);
		stmt.executeUpdate();
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
	if(msg.equals("")){
	    msg = doSelect();
	}
	return msg; 
    }		
    //
    String doDelete(){

	String qq = "",msg="";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;			
	qq = "delete from  contracts where id=?";
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
