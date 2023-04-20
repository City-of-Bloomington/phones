package phone.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.list.*;
import phone.utils.*;

public class Payment extends CommonInc implements java.io.Serializable{

    String id="", start_period="", end_period=""; 
    double base_monthly=0, emergency=0,federal_univ=0, in_univ=0,
	in_util_receipt=0, telecom=0, add_charge=0, information=0,
	internet=0, local_toll=0, reports=0, yp=0, credits=0, total=0;
    //
    String vendor_id="1"; // default 1 for AT&T 
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    static final long serialVersionUID = 1000L;
    static Logger logger = LogManager.getLogger(Payment.class);
    List<FileUpload> fileUploads = null;
    Type vendor = null;
    //
    public Payment(){
	super();
    }
    public Payment(boolean deb){
	//
	// initialize
	//
	super(deb);
    }
    public Payment(String val){
	//
	setId(val);
    }
    public Payment(boolean deb, String val){
	//
	// initialize
	//
	super(deb);
	setId(val);
    }
    public Payment(boolean deb,
		   String val,
		   String val2,
		   String val3,
		   double val4,
		   double val5,
		   double val6,
		   double val7,
		   double val8,
		   double val9,
		   double val10,
		   double val11,
		   double val12,
		   double val13,
		   double val14,
		   double val15,
		   double val16,
		   double val17,
		   String val18
		   ){
	debug = deb;
	setVals(val, val2, val3, val4, val5, val6,
		val7, val8, val9, val10, val11,
		val12, val13, val14, val15, val16, val17, val18);
    }
    void setVals(
		 String val,
		 String val2,
		 String val3,
		 double val4,
		 double val5,
		 double val6,
		 double val7,
		 double val8,
		 double val9,
		 double val10,
		 double val11,
		 double val12,
		 double val13,
		 double val14,
		 double val15,
		 double val16,
		 double val17,
		 String val18
		 ){				
	//
	// initialize
	//

	setId(val);
	setStart_period(val2);
	setEnd_period(val3);
	setBase_monthly_n(val4);
	setEmergency_n(val5);
	setFederal_univ_n(val6);
	setIn_univ_n(val7);
	setIn_util_receipt_n(val8);
	setTelecom_n(val9);
	setAdd_charge_n(val10);
	setInformation_n(val11);
	setInternet_n(val12);
	setLocal_toll_n(val13);
	setReports_n(val14);				
	setYp_n(val15);
	setCredits_n(val16);
	setTotal(val17);
	setVendor_id(val18);
    }
		
    public String getId(){
	return id;
    }
    public String getVendor_id(){
	return vendor_id;
    }		
    public String getStart_period(){
	return start_period;
    }		
    public String getEnd_period(){
	return end_period;
    }
		
    public String getBase_monthly(){
	return formatter.format(base_monthly);
    }
    public String getEmergency(){
	return formatter.format(emergency);				
    }
    public String getFederal_univ(){
	return formatter.format(federal_univ);
    }
    public String getIn_univ(){
	return formatter.format(in_univ);
    }
    public String getIn_util_receipt(){
	return formatter.format(in_util_receipt);
    }
    public String getTelecom(){
	return formatter.format(telecom);
    }
    public String getAdd_charge(){
	return formatter.format(add_charge);
    }
    public String getInformation(){
	return formatter.format(information);
    }
    public String getInternet(){
	return formatter.format(internet);
    }
    public String getLocal_toll(){
	return formatter.format(local_toll);
    }
    public String getReports(){
	return formatter.format(reports);
    }		
    public String getYp(){
	return formatter.format(yp);
    }
    public String getCredits(){
	return formatter.format(credits);
    }
    public String getTotal(){
	return formatter.format(total);
    }
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setVendor_id(String val){
	if(val != null && !val.equals("-1"))
	    vendor_id = val;
    }		
    public void setStart_period(String val){
	if(val != null)
	    start_period = val;
    }
    public void setEnd_period(String val){
	if(val != null)
	    end_period = val;
    }
    public void setBase_monthly_n(double val){
	base_monthly = val;
    }
    public void setBase_monthly(String val){
	base_monthly = doCleanNumber(val);
    }
    public void setEmergency_n(double val){
	emergency = val;
    }		
    public void setEmergency(String val){
	emergency = doCleanNumber(val);;
    }
    public void setFederal_univ_n(double val){
	federal_univ = val;
    }		
    public void setFederal_univ(String val){
	federal_univ = doCleanNumber(val);
    }
    public void setIn_univ_n(double val){
	in_univ = val;
    }		
    public void setIn_univ(String val){
	in_univ = doCleanNumber(val);
    }
    public void setIn_util_receipt_n(double val){
	in_util_receipt = val;
    }		
    public void setIn_util_receipt(String val){
	in_util_receipt = doCleanNumber(val);
    }
    public void setTelecom_n(double val){
	telecom = val;
    }		
    public void setTelecom(String val){
	telecom = doCleanNumber(val);
    }
    public void setAdd_charge_n(double val){
	add_charge = val;
    }		
    public void setAdd_charge(String val){
	add_charge = doCleanNumber(val);
    }
    public void setInformation_n(double val){
	information = val;
    }		
    public void setInformation(String val){
	information = doCleanNumber(val);
    }
    public void setInternet_n(double val){
	internet = val;
    }		
    public void setInternet(String val){
	internet = doCleanNumber(val);
    }
    public void setLocal_toll_n(double val){
	local_toll= val;
    }		
    public void setLocal_toll(String val){
	local_toll= doCleanNumber(val);
    }
    public void setReports_n(double val){
	reports = val;
    }				
    public void setReports(String val){
	reports = doCleanNumber(val);
    }
    public void setYp_n(double val){
	yp = val;
    }		
    public void setYp(String val){
	yp = doCleanNumber(val);
    }
    public void setCredits_n(double val){
	credits = val;
    }		
    public void setCredits(String val){
	credits = doCleanNumber(val);
    }
    public void setTotal(double val){
	total = val;
    }
    public String getPeriod(){
	// if the same year
	if(start_period.substring(6).equals(end_period.substring(6))){
	    return start_period.substring(0,6)+"-"+end_period.substring(0,6);
	}
	return start_period+" - "+end_period;				
    }
    public String getPeriodWithYear(){
	return start_period+" - "+end_period;				
    }		
    //
    public String toString(){
	return getPeriod();
    }
    public boolean equals(Object obj){
	if(obj instanceof Payment){
	    Payment one =(Payment)obj;
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
    public List<FileUpload> getFileUploads(){
	if(fileUploads == null && !id.equals("")){
	    FileUploadList ful = new FileUploadList(debug, id);
	    ful.setObj_type("Payment");
	    String back = ful.find();
	    if(back.equals("")){
		List<FileUpload> ones = ful.getUploads();
		if(ones != null && ones.size() > 0)
		    fileUploads = ones;
	    }
	}
	return fileUploads;

    }
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
    public boolean hasVendor(){
	getVendor();
	return vendor != null;
    }
    /**
     *
     * Number nn = formatter.parse(val);
     * double v = nn.doubleValue();
     */
    double doCleanNumber(String val){

	String val2 = val;
	double ret = 0;
	if(val2.indexOf("$") == -1){
	    val2 = "$"+val;
	}
	try{
	    ret = formatter.parse(val2).doubleValue();
	}catch(Exception ex){System.err.println(ex);}
	return ret;
				
    }
    //
    public String doSelect(){
	String msg = "";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
	String qq = " select id,date_format(start_period,'%m/%d/%Y'),"+
	    " date_format(end_period, '%m/%d/%Y'),"+
	    " base_monthly,emergency,federal_univ, in_univ, in_util_receipt,"+
	    " telecom,add_charge,information,internet,local_toll,reports,yp,"+
	    " credits,total,vendor_id "+
	    " from payments "+
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
			rs.getDouble(4),
			rs.getDouble(5),
			rs.getDouble(6),
			rs.getDouble(7),
			rs.getDouble(8),
			rs.getDouble(9),
			rs.getDouble(10),
			rs.getDouble(11),
			rs.getDouble(12),
			rs.getDouble(13),
			rs.getDouble(14),
			rs.getDouble(15),
			rs.getDouble(16),
			rs.getDouble(17),
			rs.getString(18));
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
	PreparedStatement stmt = null, stmt2=null;
	ResultSet rs = null;		
		
	String str="", msg="";
	String qq = "";
	if(start_period.equals("") || end_period.equals("")){
	    msg = "start period and end period are required";
	    return msg;
	}
	qq = "insert into payments values(0,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?)";
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
		stmt2 = con.prepareStatement(qq);				
		rs = stmt2.executeQuery();
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
	    Helper.databaseDisconnect(con, stmt, stmt2, rs);
	}
	return msg; // success
    }
    private String setParams(PreparedStatement stmt){
	String back = "";
	int jj=1;
	java.util.Date date_tmp = null;
	if(credits > 0){
	    credits = -credits;
	}
	total = base_monthly+emergency+federal_univ+in_univ+in_util_receipt+
	    telecom+add_charge+information+internet+local_toll+reports+yp+credits;
	try{
	    date_tmp = dateFormat.parse(start_period);
	    stmt.setDate(jj++, new java.sql.Date(date_tmp.getTime()));
	    date_tmp = dateFormat.parse(end_period);
	    stmt.setDate(jj++, new java.sql.Date(date_tmp.getTime()));
	    stmt.setDouble(jj++, base_monthly);
	    stmt.setDouble(jj++, emergency);
	    stmt.setDouble(jj++, federal_univ);
	    stmt.setDouble(jj++, in_univ);
	    stmt.setDouble(jj++, in_util_receipt);
	    stmt.setDouble(jj++, telecom);
	    stmt.setDouble(jj++, add_charge);
	    stmt.setDouble(jj++, information);
	    stmt.setDouble(jj++, internet);
	    stmt.setDouble(jj++, local_toll);
	    stmt.setDouble(jj++, reports);						
	    stmt.setDouble(jj++, yp);
	    stmt.setDouble(jj++, credits);
	    stmt.setDouble(jj++, total);
	    if(vendor_id.equals(""))
		stmt.setNull(jj++, Types.INTEGER);
	    else
		stmt.setString(jj++, vendor_id);
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
	qq = "update payments set start_period=?,"+
	    " end_period=?,"+
	    " base_monthly=?,emergency=?,federal_univ=?, "+
	    " in_univ=?, in_util_receipt=?,"+
	    " telecom=?,add_charge=?,information=?,internet=?,"+
	    " local_toll=?,reports=?,yp=?,"+
	    " credits=?,total=?,vendor_id=? "+
	    " where id=?";
	//
	if(start_period.equals("") || end_period.equals("")){
	    msg = "payment start period and end period are required";
	    return msg;
	}
	if(id.equals("")){
	    msg = "payment id not set ";
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
		stmt.setString(18, id);
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
	return msg; 
    }		
    //
    String doDelete(){

	String qq = "",msg="";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;			
	qq = "delete from  payments where id=?";
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
