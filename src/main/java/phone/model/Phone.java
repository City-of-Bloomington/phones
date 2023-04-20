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

public class Phone extends CommonInc implements java.io.Serializable{

    String id="", phoneNumber="", type="Phone",  address_id="",
	signal_type="",model="", location="", otherUse="", 
	department_id="", division_id="", billing_id="", port="", active="",
	notes="", wallPlate="", wallPlateNum="";

    // for new service:name, new employee:fullname
    String name="", fullname = "", emp_ext_id="";
    /*
      String ldapPhone = "", deptName="", divisionName="", name="",// emp, service
      pline_type = "";
    */
    String ext_numbers = ""; // adding one or mutiple extensions
    //
    static final long serialVersionUID = 2800L;
    static Logger logger = LogManager.getLogger(Phone.class);
    Department department = null;
    Division division = null;
    List<Division> divisions = null;
    Billing billing = null;
    Type address = null;
    List<PhoneExt> phoneExts = null;
    List<PLine> plines = null;
    // pLine ids that we need to remove from this line
    String[] del_pline_ids = null;
    //
    public Phone(){
	super();
    }
    public Phone(boolean deb){
	//
	// initialize
	//
	super(deb);
    }
    public Phone(String val){
	//
	setId(val);
    }
    public Phone(boolean deb, String val){
	//
	// initialize
	//
	super(deb);
	setId(val);
    }
    public Phone(boolean deb, String val, String val2){
	//
	// initialize
	//
	debug = deb;
	setId(val);
	setPhoneNumber(val2);
    }
    public Phone(boolean deb,
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
		 boolean val12,
		 String val13,
		 String val14,
		 String val15,
		 String val16
		 ){
	debug = deb;
	setVals(val, val2, val3, val4, val5, val6, val7, val8, val9, val10,
		val11, val12, val13, val14, val15, val16);
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
		 boolean val12,
		 String val13,
		 String val14,
		 String val15,
		 String val16
		 ){
	//
	// initialize
	//

	setId(val);
	setPhoneNumber(val2);
	setLocation(val3);
	setOtherUse(val4);
	setSignal_type(val5);
	setModel(val6);
	setAddress_id(val7);
	setDepartment_id(val8);
	setDivision_id(val9);
	setBilling_id(val10);
	setPort(val11);
	setActive(val12);
	setNotes(val13);
	setWallPlate(val14);
	setWallPlateNum(val15);
	setType(val16);
    }
		
    public String getId(){
	return id;
    }
    public String getType(){
	return type;
    }		
    public String getPhoneNumber(){
	return phoneNumber;
    }
    public String getLocation(){
	return location;
    }
    public String getOtherUse(){
	return otherUse;
    }
    public String getSignal_type(){
	return signal_type;
    }
    public String getModel(){
	return model;
    }
    public String getAddress_id(){
	return address_id;
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
    public String getPort(){
	return port;
    }
    public boolean getActive(){
	return !active.equals("");
						
    }
    public String getNotes(){
	return notes;
    }
    public String getName(){ // for service name
	return "";
    }		
    public String getWallPlate(){
	return wallPlate;
    }
    public String getWallPlateNum(){
	return wallPlateNum;
    }		
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setExt_numbers(String val){
	if(val != null)
	    ext_numbers = val;
    }		
    public void setDel_pline_ids(String[] vals){
	if(vals != null)
	    del_pline_ids = vals;
    }
		
    public void setPhoneNumber(String val){
	if(val != null && !val.equals("")){
	    phoneNumber = Helper.getPhoneNumberCleaned(val);
	}
    }
    public void setLocation(String val){
	if(val != null)
	    location = val;
    }
    public void setOtherUse(String val){
	if(val != null)
	    otherUse = val;
    }
    public void setSignal_type(String val){
	if(val != null)
	    signal_type = val;
    }

    public void setModel(String val){
	if(val != null)
	    model = val;
    }
    public void setName(String val){ // new service name
	if(val != null)
	    name = val;
    }
    public void setFullname(String val){ // new service name
	if(val != null)
	    fullname = val;
    }		
    public void setAddress_id(String val){
	if(val != null && !val.equals("-1"))
	    address_id = val;
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
    public void setPort(String val){
	if(val != null)
	    port = val;
    }
    public void setActive(boolean val){
	if(val){
	    active = "y";
	}
    }
    public void setNotes(String val){
	if(val != null)
	    notes = val;
    }
    public void setWallPlate(String val){
	if(val != null)
	    wallPlate = val;
    }
    public void setWallPlateNum(String val){
	if(val != null)
	    wallPlateNum = val;
    }
    public void setType(String val){
	if(val != null)
	    type = val;
    }
    public void setEmp_ext_id(String val){
	if(val != null && !val.equals("-1"))
	    emp_ext_id = val;
    }		
    //
    public String toString(){
	return phoneNumber;
    }
    public boolean equals(Object obj){
	if(obj instanceof Phone){
	    Phone one =(Phone)obj;
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
	if(billing == null){
	    billing = new Billing();
	}
	return billing;
    }
    public boolean hasBilling(){
	return !billing_id.equals("");
    }
    public Type getAddress(){
	if(address == null && !address_id.equals("")){
	    Type one = new Type(debug, address_id, null, "addresses");
	    String back = one.doSelect();
	    if(back.equals("")){
		address = one;
	    }
	}
	if(address == null){
	    address = new Type(debug);
	}
	return address;
    }
    public boolean hasDivision(){
	return !division_id.equals("");

    }		
    public boolean hasDivisions(){
	getDivisions();
	return divisions != null && divisions.size() > 0;
    }
    // for more than one
    public boolean hasPhoneExts(){
	getPhoneExts();
	return phoneExts != null && phoneExts.size() > 0;
    }
    // for only one
    public boolean hasOnePhoneExt(){
	getPhoneExts();
	return phoneExts != null && phoneExts.size() == 1;
    }
    public boolean hasNewLineName(){
	return !name.equals("") || !fullname.equals("");
    }		
    public PhoneExt getPhoneExt(){
	if(hasPhoneExts()){
	    return phoneExts.get(0);
	}
	return null;
    }
    public List<PhoneExt> getPhoneExts(){
	if(!id.equals("") && phoneExts == null){
	    PhoneExtList pel = new PhoneExtList(debug, id);// phone_id
	    String back = pel.find();
	    if(back.equals("")){
		List<PhoneExt> ones = pel.getPhoneExts();
		if(ones != null && ones.size() > 0){
		    phoneExts = ones;
		}
	    }
	}
	return phoneExts;
    }
    public List<PLine> getPlines(){
	if(plines == null && !id.equals("")){
	    PLineList pll = new PLineList(debug);
	    pll.setPhone_id(id);
	    String back = pll.find();
	    if(back.equals("")){
		List<PLine> ones = pll.getPLines();
		if(ones != null && ones.size() > 0){
		    plines = ones;
		}
	    }
	}
	return plines;
    }
    private void addPhoneExts(){
	if(!id.equals("")){
	    if(ext_numbers != null && ext_numbers.length() > 2){
		if(ext_numbers.indexOf(" ") > 3){
		    String strArr[] = ext_numbers.split("\\s+");
		    if(strArr.length > 0){
			for(String str:strArr){
			    PhoneExt one = new PhoneExt(debug, null, str, str, id);
			    one.doSave();
			}
		    }
		}
		else{
		    PhoneExt one = new PhoneExt(debug, null, ext_numbers, ext_numbers, id);
		    one.doSave();										
		}
	    }
	}
    }
    private boolean hasNewExts(){
	return !ext_numbers.equals("");
    }
    public boolean hasPlines(){
	getPlines();
	return plines != null && plines.size() > 0;
    }
    public boolean canBeDeleted(){
	return !hasPlines();
    }
    //
    public String doSelect(){
	String msg = "";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
	String qq = " select id,phoneNumber,location,otherUse,signal_type,"+
	    " model,address_id,department_id,division_id,billing_id, port, "+
	    " active,notes, wallPlate, wallPlateNum, type from phones "+
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
			rs.getString(12) != null,
			rs.getString(13),
			rs.getString(14),
			rs.getString(15),
			rs.getString(16));
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
	active = "y";
	String str="", msg="";
	String qq = "";
	if(phoneNumber.equals("")){
	    msg = "phone number not set";
	    return msg;
	}
	qq = "insert into phones values(0,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?)";
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
	if(hasNewExts()){
	    addPhoneExts();
	}
	return msg; // success
    }
    private String setParams(PreparedStatement stmt){
	String back = "";
	int jj=1;
	try{
	    stmt.setString(jj++, phoneNumber);
	    if(location.equals(""))
		stmt.setNull(jj++, Types.VARCHAR);
	    else
		stmt.setString(jj++,location);
	    if(otherUse.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, otherUse);						
	    if(signal_type.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, signal_type);						
	    if(model.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, model);
	    if(address_id.equals(""))
		stmt.setNull(jj++,Types.INTEGER);
	    else
		stmt.setString(jj++, address_id);
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
	    if(port.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, port);
	    if(active.equals(""))
		stmt.setNull(jj++,Types.CHAR);
	    else
		stmt.setString(jj++, "y");
	    if(notes.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, notes);
	    if(wallPlate.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, wallPlate);
	    if(wallPlateNum.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, wallPlateNum);
	    if(type.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, type);
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
	qq = "update phones set phoneNumber=?,location=?, otherUse=?,"+
	    " signal_type=?, model=?, address_id=?, department_id=?,"+
	    " division_id=?, billing_id=?, port=?, active=?, notes=?,"+
	    " wallPlate=?, wallPlateNum=?, type=? "+
	    " where id=?";
	//
	if(id.equals("") || phoneNumber.equals("")){
	    msg = " id or phone number not set";
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
		stmt.setString(16, id);
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
	if(hasNewExts()){
	    addPhoneExts();
	}
	if(hasNewLineName()){
	    addLineName();
	}
	return msg; 
    }		
    //
    public String doDelete(){

	String qq = "",qq2="", msg="";
	Connection con = null;
	PreparedStatement stmt = null, stmt2=null;
	ResultSet rs = null;			
	qq = "delete from phone_exts where phone_id=?";
	qq2 = "delete from phones where id=?";
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
    public String removeLines(){
	String qq = "",qq2="", msg="";
	Connection con = null;
	PreparedStatement stmt = null, stmt2 = null;
	ResultSet rs = null;
	if(del_pline_ids == null){
	    msg = " no line user checked to be removed ";
	    return msg;
	}
	qq = "delete from pline_phones where line_id=? and phone_id=?";
	qq2 = "delete from plines where id=? ";
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    stmt = con.prepareStatement(qq);
	    stmt2 = con.prepareStatement(qq2);
	    for(String str:del_pline_ids){
		stmt.setString(1, str);
		stmt.setString(2, id);
		stmt.executeUpdate();
		stmt2.setString(1, str);
		stmt2.executeUpdate();
	    }
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
    String addLineName(){
	String back = "";
	if(name.equals("") && fullname.equals("")) return back;
	PLine pl = null;
	if(!name.equals("")){
	    pl = new PLine(debug, null, name, "Service");
	}
	else{
	    pl = new PLine(debug, null, fullname, "Employee");
	}
	back = pl.doSave();
	if(back.equals("")){
	    String line_id = pl.getId();
	    PLinePhone plp = new PLinePhone(debug, null, line_id, id, emp_ext_id);
	    back = plp.doSave();
	}
	return back;
    }
}
