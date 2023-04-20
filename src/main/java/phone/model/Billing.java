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

public class Billing extends CommonInc implements java.io.Serializable{

    String id="", name="", account_num="", foundation_account_num="",
	department_id="", division_id="", vendor_id="";
    String address="", city="", state="", zip="";
    static final long serialVersionUID = 1000L;
    static Logger logger = LogManager.getLogger(Billing.class);
    Department department = null;
    Division division = null;
    Type vendor = null;
    List<Division> divisions = null;
    List<FileUpload> fileUploads = null;
    List<Contract> contracts = null;
    //
    public Billing(){
	super();
    }
    public Billing(boolean deb){
	//
	// initialize
	//
	super(deb);
    }
    public Billing(String val){
	//
	setId(val);
    }
    public Billing(boolean deb, String val){
	//
	// initialize
	//
	super(deb);
	setId(val);
    }
    public Billing(boolean deb, String val, String val2){
	//
	// initialize
	//
	debug = deb;
	setId(val);
	setName(val2);
    }
    public Billing(boolean deb,
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
		   String val11
		   ){
	debug = deb;
	setVals(val, val2, val3, val4, val5, val6,
		val7, val8, val9, val10, val11);
    }
    // for data import
    public Billing(boolean deb,
		   String val,
		   String val2,
		   String val3,
		   String val4,
		   String val5,
									 
		   String val6
		   ){
	debug = deb;
	setName(val);
	setFoundation_account_num(val2);
	setDepartment_id(val3);
	setDivision_id(val4);
	setAccount_num(val5);
	setVendor_id(val6);
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
		 String val11
		 ){				
	//
	// initialize
	//

	setId(val);
	setName(val2);
	setDepartment_id(val3);
	setDivision_id(val4);
	setAccount_num(val5);
	setVendor_id(val6);
	setAddress(val7);
	setCity(val8);
	setState(val9);
	setZip(val10);
	setFoundation_account_num(val11);
    }
		
    public String getId(){
	return id;
    }
    public String getName(){
	return name;
    }		
    public String getDepartment_id(){
	return department_id;
    }
    public String getDivision_id(){
	return division_id;
    }
    public String getVendor_id(){
	return vendor_id;
    }
    public String getAccount_num(){
	return account_num;
    }
    public String getInfo(){
	String ret = name;
	if(!account_num.equals("")){
	    if(!ret.equals("")) ret += " ";
	    ret += account_num;
	}
	return ret;
    }
    public String getFoundation_account_num(){
	return foundation_account_num;
    }		
    public String getAddress(){
	return address;
    }
    public String getCity(){
	return city;
    }
    public String getState(){
	return state;
    }
    public String getZip(){
	return zip;
    }
    public String getFullAddress(){
	String ret = address;
	if(!city.equals("")){
	    if(!ret.equals("")) ret += " "; 
	    ret += city;
	}
	if(!state.equals("")){
	    if(!ret.equals("")) ret += ", "; 
	    ret += state;
	}
	if(!zip.equals("")){
	    if(!ret.equals("")) ret += " "; 
	    ret += zip;
	}
	return ret;
    }

    public void setId(String val){
	if(val != null)
	    id = val;
    }
		
    public void setName(String val){
	if(val != null)
	    name = val;
    }
		
    public void setAddress(String val){
	if(val != null)
	    address = val;
    }
    public void setCity(String val){
	if(val != null)
	    city = val;
    }
    public void setState(String val){
	if(val != null)
	    state = val;
    }
    public void setZip(String val){
	if(val != null)
	    zip = val;
    }
		
    public void setDepartment_id(String val){
	if(val != null && !val.equals("-1"))
	    department_id = val;
    }
    public void setDivision_id(String val){
	if(val != null && !val.equals("-1"))
	    division_id = val;
    }
    public void setVendor_id(String val){
	if(val != null && !val.equals("-1"))
	    vendor_id = val;
    }
    public void setAccount_num(String val){
	if(val != null)
	    account_num = val;
    }
    public void setFoundation_account_num(String val){
	if(val != null)
	    foundation_account_num = val;
    }		
    //
    public String toString(){
	return getInfo2();
    }
    public String getInfo2(){
	String ret = name;
	String str = getDepartmentInfo();
	if(str != null && !str.equals("")){
	    if(!ret.equals("")) ret += ", ";
	    ret += str;
	}
	return ret;
    }
    public boolean equals(Object obj){
	if(obj instanceof Billing){
	    Billing one =(Billing)obj;
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
    public boolean hasDivision(){
	return !division_id.equals("");

    }
    public boolean hasDivisions(){
	getDepartment();
	return department != null && department.hasDivisions();
    }
    public boolean hasAttachments(){
	getFileUploads();
	return fileUploads != null && fileUploads.size() > 0;

    }
    public List<FileUpload> getFileUploads(){
	if(fileUploads == null && !id.equals("")){
	    FileUploadList ful = new FileUploadList(debug, id);
	    ful.setObj_type("Billing");
	    String back = ful.find();
	    if(back.equals("")){
		List<FileUpload> ones = ful.getUploads();
		if(ones != null && ones.size() > 0)
		    fileUploads = ones;
	    }
	}
	return fileUploads;

    }
    public List<Contract> getContracts(){
	if(!id.equals("") && contracts == null){ 
	    ContractList cll = new ContractList(debug);
	    cll.setBilling_id(id);
	    String back = cll.find();
	    if(back.equals("")){
		List<Contract> ones = cll.getContracts();
		if(ones != null)
		    contracts = ones;
	    }
	}
	return contracts;
    }
    public boolean hasContracts(){
	getContracts();
	return contracts != null && contracts.size() > 0;
    }
    public Contract getLatestContract(){
	if(hasContracts()){
	    return contracts.get(0); // top one is the latest becuase of id desc
	}
	return null;
    }
    public Type getVendor(){
	if(!vendor_id.equals("") && vendor == null){ 
	    Type one = new Type(debug, vendor_id, null, "vendors");
	    String back = one.doSelect();
	    if(back.equals("")){
		vendor = one;
	    }
	}
	return vendor;
    }		
    //
    public String doSelect(){
	String msg = "";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
	String qq = " select id,name,"+
	    " department_id,division_id,account_num,vendor_id, "+
	    " address,city,state,zip,foundation_account_num "+
	    " from billings "+
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
			rs.getString(11)
			);
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
	if(name.equals("")){
	    msg = "billing name not set";
	    return msg;
	}
	qq = "insert into billings values(0,?,?,?,?, ?,?,?,?,?, ?)";
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
    public String doSaveForImport(PreparedStatement stmt){
	String back = "";
	try{
	    stmt.setString(1, name);
	    if(foundation_account_num.equals(""))
		stmt.setNull(2,Types.VARCHAR);
	    else
		stmt.setString(2, foundation_account_num);								
	    if(department_id.equals(""))
		stmt.setNull(3,Types.INTEGER);
	    else
		stmt.setString(3, department_id);						
	    if(division_id.equals(""))
		stmt.setNull(4,Types.INTEGER);
	    else
		stmt.setString(4, division_id);
	    if(account_num.equals(""))
		stmt.setNull(5,Types.VARCHAR);
	    else
		stmt.setString(5, account_num);						
	    if(vendor_id.equals(""))
		stmt.setNull(6,Types.INTEGER);
	    else
		stmt.setString(6, vendor_id);
	    stmt.executeUpdate();
	}
	catch(Exception ex){
	    back += ex;
	}
	return back;

    }
    private String setParams(PreparedStatement stmt){
	String back = "";
	try{
	    stmt.setString(1, name);
	    if(foundation_account_num.equals(""))
		stmt.setNull(2,Types.VARCHAR);
	    else
		stmt.setString(2, foundation_account_num);								
	    if(department_id.equals(""))
		stmt.setNull(3,Types.INTEGER);
	    else
		stmt.setString(3, department_id);						
	    if(division_id.equals(""))
		stmt.setNull(4,Types.INTEGER);
	    else
		stmt.setString(4, division_id);
	    if(account_num.equals(""))
		stmt.setNull(5,Types.VARCHAR);
	    else
		stmt.setString(5, account_num);						
	    if(vendor_id.equals(""))
		stmt.setNull(6,Types.INTEGER);
	    else
		stmt.setString(6, vendor_id);
	    if(address.equals(""))
		stmt.setNull(7,Types.VARCHAR);
	    else
		stmt.setString(7, address);
	    if(city.equals(""))
		stmt.setNull(8,Types.VARCHAR);
	    else
		stmt.setString(8, city);
	    if(state.equals(""))
		stmt.setNull(9,Types.VARCHAR);
	    else
		stmt.setString(9, state);
	    if(zip.equals(""))
		stmt.setNull(10,Types.VARCHAR);
	    else
		stmt.setString(10, zip);
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
	qq = "update billings set name=?,"+
	    "foundation_account_num=?,"+
	    " department_id=?,"+
	    " division_id=?, account_num=?, vendor_id=?, "+
	    " address=?, city=?, state=?, zip=? "+
	    " where id=?";
	//
	if(id.equals("") || name.equals("")){
	    msg = " id or billing name not set";
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
		stmt.setString(11, id);
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

	String qq = "",msg="";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;			
	qq = "delete from  billings where id=?";
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
