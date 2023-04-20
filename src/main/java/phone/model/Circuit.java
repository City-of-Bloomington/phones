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

public class Circuit extends CommonInc implements java.io.Serializable{

    String id="", name="",  address_id="",
	model="", location="", 
	department_id="", division_id="", billing_id="", active="y",
	notes="";
    Department department = null;
    Division division = null;
    List<Division> divisions = null;
    static final long serialVersionUID = 1400L;
    static Logger logger = LogManager.getLogger(Circuit.class);
    //
    public Circuit(){
	super();
    }
    public Circuit(boolean deb){
	//
	// initialize
	//
	super(deb);
    }
    public Circuit(String val){
	//
	setId(val);
    }
    public Circuit(boolean deb, String val){
	//
	// initialize
	//
	super(deb);
	setId(val);
    }
    public Circuit(boolean deb, String val, String val2){
	//
	// initialize
	//
	debug = deb;
	setId(val);
	setName(val2);
    }
    public Circuit(boolean deb,
		   String val,
		   String val2,
		   String val3,
		   String val4,
		   String val5,
		   String val6,
		   String val7,
		   String val8,
		   String val9,
		   String val10
		   ){
	debug = deb;
	setVals(val, val2, val3, val4, val5, val6, val7, val8, val9, val10);

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
		 String val10
		 ){				
	//
	// initialize
	//

	setId(val);
	setName(val2);
	setModel(val3);
	setAddress_id(val4);
	setDepartment_id(val5);
	setDivision_id(val6);
	setBilling_id(val7);
	setLocation(val8);				
	setActive(val9 != null);
	setNotes(val10);
    }
		
    public String getId(){
	return id;
    }
    public String getName(){
	return name;
    }		
    public String getLocation(){
	return location;
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

    public boolean isActive(){
	return !active.equals("");
						
    }
    public String getNotes(){
	return notes;
    }
    public void setId(String val){
	if(val != null)
	    id = val;
    }
		
    public void setName(String val){
	if(val != null)
	    name = val;
    }		
    public void setLocation(String val){
	if(val != null)
	    location = val;
    }
    public void setModel(String val){
	if(val != null)
	    model = val;
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
    public void setActive(boolean val){
	if(val)
	    active = "y";
    }
    public void setNotes(String val){
	if(val != null)
	    notes = val;
    }
    //
    public String toString(){
	return name;
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
		
    public boolean equals(Object obj){
	if(obj instanceof Circuit){
	    Circuit one =(Circuit)obj;
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
    //
    public String doSelect(){
	String msg = "";
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
	String qq = " select id,name,"+
	    " model,address_id,department_id,division_id,billing_id,location,"+
	    " active,notes from special_circuits "+
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
			rs.getString(10));
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
	    msg = "circuit name not set";
	    return msg;
	}
	qq = "insert into special_circuits values(0,?,?,?,?, ?,?,?,?,?)";
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
	return msg; // success
    }
    private String setParams(PreparedStatement stmt){
	String back = "";
	int jj=1;
	try{
	    stmt.setString(jj++, name);
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
	    if(location.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, location);
	    if(active.equals(""))
		stmt.setNull(jj++,Types.CHAR);
	    else
		stmt.setString(jj++, "y");
	    if(notes.equals(""))
		stmt.setNull(jj++,Types.VARCHAR);
	    else
		stmt.setString(jj++, notes);
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
	qq = "update special_circuits set name=?,"+
	    " model=?, address_id=?, department_id=?,"+
	    " division_id=?, billing_id=?, location=?, active=?, notes=? "+
	    " where id=?";
	//
	if(id.equals("") || name.equals("")){
	    msg = " id or circuit name not set";
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
		stmt.setString(10, id);
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
	qq = "delete from  special_circuits where id=?";
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
