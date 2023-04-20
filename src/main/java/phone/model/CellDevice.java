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

public class CellDevice extends CommonInc implements java.io.Serializable{

    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");		
    String  id="",
	make="",
	model="",   
	wireless_num="", 
	device_imei="", 
	sim_num="",
	service_type="", // ('B','C','D','V'),
	device_effective_date="",
	user_effective_date="",
	user_name="",
	user_type="",
	department_id="",
	division_id="",
	billing_id="",
	inactive="",
	contract_start_date="",
	contract_end_date="",
	notes="";
    //
    static final long serialVersionUID = 2810L;
    static Logger logger = LogManager.getLogger(CellDevice.class);
    Department department = null;
    Division division = null;
    List<Division> divisions = null;
    Billing billing = null;
    //
    public CellDevice(){
	super();
    }
    public CellDevice(boolean deb){
	//
	// initialize
	//
	super(deb);
    }
    public CellDevice(String val){
	//
	setId(val);
    }
    public CellDevice(boolean deb, String val){
	//
	// initialize
	//
	super(deb);
	setId(val);
    }
    public CellDevice(boolean deb, String val, String val2){
	//
	// initialize
	//
	debug = deb;
	setId(val);
	setWirelessNum(val2);
    }
    // for import
    public CellDevice(boolean deb,
		      String val,
		      String val2,
		      String val3,
		      String val4,
		      String val5,
		      String val6,
		      String val7,
		      String val8,
		      String val9,
		      String val10,
		      String val11,
		      String val12,
		      String val13,
		      String val14,
		      String val15											
		      ){
	debug = deb;
	setMake(val);
	setModel(val2);
	setWirelessNum(val3);
	setDeviceImei(val4);
	setSimNum(val5);

	setServiceType(val6);
	setDeviceEffectiveDate(val7);
	setUserEffectiveDate(val8);
	setUserName(val9);
	setUserType(val10);
				
	setDepartment_id(val11);
	setDivision_id(val12);
	setBilling_id(val13);
	setContractStartDate(val14);
	setContractEndDate(val15);
    }
    public CellDevice(boolean deb,
		      String val,
		      String val2,
		      String val3,
		      String val4,
		      String val5,
		      String val6,
		      String val7,
		      String val8,
		      String val9,
		      String val10,
		      String val11,
		      String val12,
		      String val13,
		      String val14,
		      String val15,
		      String val16,
		      String val17,
		      boolean val18
		      ){
	debug = deb;
	setVals(val, val2, val3, val4, val5, val6, val7, val8, val9, val10,
		val11, val12, val13, val14, val15, val16, val17, val18);
    }
    void setVals(
		 String val,
		 String val2,
		 String val3,
		 String val4,
		 String val5,
		 String val6,
		 String val7,
		 String val8,
		 String val9,
		 String val10,
		 String val11,
		 String val12,
		 String val13,
		 String val14,
		 String val15,
		 String val16,
		 String val17,								
		 boolean val18
		 ){
	//
	// initialize
	//

	setId(val);
	setMake(val2);
	setModel(val3);
	setWirelessNum(val4);
	setDeviceImei(val5);
	setSimNum(val6);
	setServiceType(val7);
	setDeviceEffectiveDate(val8);
	setUserEffectiveDate(val9);
	setUserName(val10);
	setUserType(val11);
	setDepartment_id(val12);
	setDivision_id(val13);
	setBilling_id(val14);
	setContractStartDate(val15);
	setContractEndDate(val16);
	setNotes(val17);
	setInactive(val18);
    }
		
    public String getId(){
	return id;
    }
    public String getServiceType(){
	return service_type;
    }		
    public String getWirelessNum(){
	return wireless_num;
    }
    public String getSimNum(){
	return sim_num;
    }		
    public String getUserType(){
	return user_type;
    }
    public String getModel(){
	return model;
    }
    public String getMake(){
	return make;
    }		
    public String getMakeAndModel(){
	String ret = make;
	if(!model.equals("")){
	    if(!ret.equals("")) ret += ", ";
	    ret += model;
	}
	return ret;
    }		
		
    public String getDeviceImei(){
	return device_imei;
    }
    public String getDepartment_id(){
	return department_id;
    }
    public String getDivision_id(){
	return division_id;
    }
    public String getBilling_id(){
	return billing_id;
    }		
    public String getDeviceEffectiveDate(){
	return device_effective_date;
    }
    public String getUserEffectiveDate(){
	return user_effective_date;
    }		
    public boolean getInactive(){
	return !inactive.equals("");
						
    }
    public String getContractStartDate(){
	return contract_start_date;
    }
    public String getContractEndDate(){
	return contract_end_date;
    }
    public String getContractStartEndDate(){
	String ret = contract_start_date;
	if(!contract_end_date.equals("")){
	    if(!ret.equals("")) ret += " - ";
	    ret += contract_end_date;
	}
	return ret;
    }		
    public String getNotes(){
	return notes;
    }
    public String getUserName(){ 
	return user_name;
    }		

    public void setId(String val){
	if(val != null)
	    id = val;
    }
		
    public void setWirelessNum(String val){
	if(val != null && !val.equals("")){
	    wireless_num = Helper.getPhoneNumberCleaned(val);
	}
    }
    public void setModel(String val){
	if(val != null)
	    model = val;
    }
    public void setMake(String val){ 
	if(val != null)
	    make = val;
    }
    public void setUserType(String val){ 
	if(val != null)
	    user_type = val;
    }		
    public void setSimNum(String val){ 
	if(val != null)
	    sim_num = val;
    }		
    public void setUserName(String val){ 
	if(val != null)
	    user_name = val;
    }
    public void setDeviceImei(String val){
	if(val != null)
	    device_imei = val;
    }
    public void setDeviceEffectiveDate(String val){ 
	if(val != null)
	    device_effective_date = val;
    }
    public void setUserEffectiveDate(String val){ 
	if(val != null)
	    user_effective_date = val;
    }
    public void setContractStartDate(String val){
	if(val != null)
	    contract_start_date = val;
    }
    public void setContractEndDate(String val){
	if(val != null)
	    contract_end_date = val;
    }		
    public void setDepartment_id(String val){
	if(val != null && !val.equals("-1"))
	    department_id = val;
    }
    public void setDivision_id(String val){
	if(val != null && !val.equals("-1"))
	    division_id = val;
    }
    public void setBilling_id(String val){
	if(val != null && !val.equals("-1"))						
	    billing_id = val;
    }		
    public void setInactive(boolean val){
	if(val){
	    inactive = "y";
	}
    }
    public void setNotes(String val){
	if(val != null)
	    notes = val;
    }
    public void setServiceType(String val){
	if(val != null)
	    service_type = val;
    }
		
    //
    public String toString(){
	return wireless_num;
    }
    public boolean equals(Object obj){
	if(obj instanceof CellDevice){
	    CellDevice one =(CellDevice)obj;
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
    public Department getDepartment(){
	if(!department_id.equals("") && department == null){ 
	    Department one = new Department(debug, department_id);
	    String back = one.doSelect();
	    if(back.equals("")){
		department = one;
	    }
	}
	return department;
    }

    public List<Division> getDivisions(){
	if(department == null){
	    getDepartment();
	}
	if(department != null){
	    if(department.hasDivisions()){
		divisions = department.getDivisions();
	    }
	}
	return divisions;
    }
    public Division getDivision(){
	if(!division_id.equals("") && division == null){ 
	    Division one = new Division(debug, division_id);
	    String back = one.doSelect();
	    if(back.equals("")){
		division = one;
	    }
	}
	return division;
    }		
    public String getDepartmentInfo(){
	String ret = "";
	if(department == null){
	    getDepartment();
	    getDivision();
	}
	if(department != null){
	    ret = department.getName();
	}
	if(division != null){
	    if(!ret.equals("")) ret += ", ";
	    ret += division.getName();
	}
	return ret;
    }		
    public Billing getBilling(){
	if(!billing_id.equals("") && billing == null){ 
	    Billing one = new Billing(debug, billing_id);
	    String back = one.doSelect();
	    if(back.equals("")){
		billing = one;
	    }
	}
	return billing;
    }		

    public boolean hasDivision(){
	return !division_id.equals("");

    }		
    public boolean hasDivisions(){
	getDivisions();
	return divisions != null && divisions.size() > 0;
    }
    //
    public String doSelect(){
	String msg = "";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
	String qq = " select id,make,model,wireless_num,device_imei,"+
	    " sim_num,service_type,"+
	    " date_format(device_effective_date,'%m/%d/%Y'),"+
	    " date_format(user_effective_date,'%m/%d/%Y'), "+
	    " user_name,user_type,"+
	    " department_id,division_id,billing_id, "+
	    " date_format(contract_start_date,'%m/%d/%Y'),"+
	    " date_format(contract_end_date,'%m/%d/%Y'), "+
	    " notes,"+
	    " inactive from cell_devices "+
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
			rs.getString(6),
			rs.getString(7),
			rs.getString(8),
			rs.getString(9),
			rs.getString(10),
			rs.getString(11),
			rs.getString(12), 
			rs.getString(13),
			rs.getString(14),
			rs.getString(15),
			rs.getString(16),
			rs.getString(17),
			rs.getString(18) != null);
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
	inactive = "";
	String str="", msg="";
	String qq = "";
	if(wireless_num.equals("")){
	    msg = "wirelees number not set";
	    return msg;
	}
	qq = "insert into cell_devices values(0,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?)";
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
	try{
	    if(make.equals(""))
		stmt.setNull(jj++, Types.VARCHAR);
	    else
		stmt.setString(jj++,make);
	    if(model.equals(""))
		stmt.setNull(jj++, Types.VARCHAR);
	    else
		stmt.setString(jj++,model);
	    //
	    stmt.setString(jj++,wireless_num);
	    if(device_imei.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, device_imei);						
	    if(sim_num.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, sim_num);						
	    if(service_type.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, service_type);
	    if(device_effective_date.equals(""))
		stmt.setNull(jj++,Types.DATE);
	    else{
		java.util.Date date_tmp = dateFormat.parse(device_effective_date);
		stmt.setDate(jj++, new java.sql.Date(date_tmp.getTime()));
	    }
	    if(user_effective_date.equals(""))
		stmt.setNull(jj++,Types.DATE);
	    else{
		java.util.Date date_tmp = dateFormat.parse(user_effective_date);
		stmt.setDate(jj++, new java.sql.Date(date_tmp.getTime()));
	    }
	    if(user_name.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, user_name);
	    if(user_type.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, user_type);						
	    if(department_id.equals(""))
		stmt.setNull(jj++,Types.INTEGER);
	    else
		stmt.setString(jj++, department_id);						
	    if(division_id.equals(""))
		stmt.setNull(jj++,Types.INTEGER);
	    else
		stmt.setString(jj++, division_id);
	    if(billing_id.equals(""))
		stmt.setNull(jj++,Types.INTEGER);
	    else
		stmt.setString(jj++, billing_id);						
	    if(contract_start_date.equals(""))
		stmt.setNull(jj++,Types.DATE);
	    else{
		java.util.Date date_tmp = dateFormat.parse(contract_start_date);
		stmt.setDate(jj++, new java.sql.Date(date_tmp.getTime()));
	    }
	    if(contract_end_date.equals(""))
		stmt.setNull(jj++,Types.DATE);
	    else{
		java.util.Date date_tmp = dateFormat.parse(contract_end_date);
		stmt.setDate(jj++, new java.sql.Date(date_tmp.getTime()));
	    }
	    if(notes.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, notes);
	    if(inactive.equals(""))
		stmt.setNull(jj++,Types.CHAR);
	    else
		stmt.setString(jj++, "y");
	}
	catch(Exception ex){
	    back += ex;
	}
	return back;
    }
    public String doSaveForImport(PreparedStatement stmt){
	String back = "";
	int jj=1;
	try{
	    if(make.equals(""))
		stmt.setNull(jj++, Types.VARCHAR);
	    else
		stmt.setString(jj++,make);
	    if(model.equals(""))
		stmt.setNull(jj++, Types.VARCHAR);
	    else
		stmt.setString(jj++,model);
	    //
	    stmt.setString(jj++,wireless_num);
	    if(device_imei.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, device_imei);						
	    if(sim_num.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, sim_num);						
	    if(service_type.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, service_type);
	    if(device_effective_date.equals(""))
		stmt.setNull(jj++,Types.DATE);
	    else{
		stmt.setString(jj++, device_effective_date);
	    }
	    if(user_effective_date.equals(""))
		stmt.setNull(jj++,Types.DATE);
	    else{
		stmt.setString(jj++, user_effective_date);
	    }
	    if(user_name.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, user_name);
	    if(user_type.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, user_type);						
	    if(department_id.equals(""))
		stmt.setNull(jj++,Types.INTEGER);
	    else
		stmt.setString(jj++, department_id);						
	    if(division_id.equals(""))
		stmt.setNull(jj++,Types.INTEGER);
	    else
		stmt.setString(jj++, division_id);
	    if(billing_id.equals(""))
		stmt.setNull(jj++,Types.INTEGER);
	    else
		stmt.setString(jj++, billing_id);
	    if(contract_start_date.equals(""))
		stmt.setNull(jj++,Types.DATE);
	    else{
		stmt.setString(jj++, contract_start_date);
	    }
	    if(contract_end_date.equals(""))
		stmt.setNull(jj++,Types.DATE);
	    else{
		stmt.setString(jj++, contract_end_date);
	    }						
	    stmt.executeUpdate();
	}
	catch(Exception ex){
	    back += ex;
	    System.err.println(" cell device "+ex);
	}
	return back;
    }		
    public String doUpdate(){
		
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
		
	String str="", msg="";
	String qq = "";
	qq = "update cell_devices set "+
	    " make=?,"+
	    " model=?,"+
	    " wireless_num=?,"+
	    " device_imei=?,"+
	    " sim_num=?, "+
						
	    " service_type=?,"+
	    " device_effective_date=?, "+
	    " user_effective_date=?,"+
	    " user_name=?, "+
	    " user_type=?, "+
						
	    " department_id=?,"+
	    " division_id=?, "+
	    " billing_id=?,"+
	    " contract_start_date=?,"+
	    " contract_end_date=?,"+
						
	    " notes=?, "+
	    " inactive=?"+
	    " where id=?";
	//
	if(id.equals("") || wireless_num.equals("")){
	    msg = " id or wireless number not set";
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
    public String doDelete(){

	String qq = "",qq2="", msg="";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;			
	qq = "delete from cell_devices where id=?";
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
