package phone.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */

import java.sql.*;
import java.io.*;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.list.*;
import phone.utils.*;

public class Department extends Type{

    static final long serialVersionUID = 1800L;			
    static Logger logger = LogManager.getLogger(Department.class);		
    String alias="", longDistanceCode="";
    List<Division> divisions = null;
    public Department(){
	setTable_name("departments");
    }
    public Department(boolean deb, String val){
	super(deb, val);
	setTable_name("departments");
    }				
    public Department(boolean deb, String val, String val2){
	super(deb, val, val2);
	setTable_name("departments");
    }		
    public Department(boolean deb, String val, String val2, String val3, String val4){
	super(deb, val, val2);
	setTable_name("departments");
	setAlias(val3);
	setLongDistanceCode(val4);
    }
		
    public void setAlias(String val){
	if(val != null){
	    if(val.length() > 30){
		alias = val.substring(0,30);
	    }
	    else{
		alias = val;
	    }
	}
    }
    public void setLongDistanceCode(String val){
	if(val != null)
	    longDistanceCode = val;
    }		
    public String getAlias(){
	return alias;
    }
    public String getLongDistanceCode(){
	return longDistanceCode;
    }
    public boolean hasDivisions(){
	getDivisions();
	return divisions != null;
    }
    public List<Division> getDivisions(){
	if(divisions == null && !id.equals("")){
	    DivisionList dl = new DivisionList(debug, id);
	    String back = dl.find();
	    if(back.equals("")){
		List<Division> ones = dl.getDivisions();
		if(ones != null && ones.size() > 0){
		    divisions = ones;
		}
	    }
	}
	return divisions;
    }
    @Override
    public String doSelect(){
	String back = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "select id,name,alias,longDistanceCode "+
	    "from departments where id=?";
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
		setName(rs.getString(2));
		setAlias(rs.getString(3));
		setLongDistanceCode(rs.getString(4));
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
    @Override
    public String doSave(){
		
	String back = "";
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "insert into departments values(0,?,?,?)";
	if(name.equals("")){
	    back = "department name not set ";
	    logger.error(back);
	    addError(back);
	    return back;
	}
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
	    pstmt.setString(1,name);
	    if(alias.equals(""))
		pstmt.setNull(2, Types.VARCHAR);
	    else
		pstmt.setString(2, alias);						
	    if(longDistanceCode.equals(""))
		pstmt.setNull(3, Types.VARCHAR);
	    else
		pstmt.setString(3, longDistanceCode);								
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
    @Override
    public String doUpdate(){
		
	String back = "";
	if(name.equals("")){
	    back = " name not set ";
	    logger.error(back);
	    addError(back);
	    return back;
	}
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String str="";
	String qq = "";
		
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	try{
	    qq = "update departments set name=?,alias=?,longDistanceCode=? "+
		"where id=?";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1,name);
	    if(alias.equals(""))
		pstmt.setNull(2, Types.VARCHAR);
	    else
		pstmt.setString(2, alias);						
	    if(longDistanceCode.equals(""))
		pstmt.setNull(3, Types.VARCHAR);
	    else
		pstmt.setString(3, longDistanceCode);									

	    pstmt.setString(4,id);
	    pstmt.executeUpdate();
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
    @Override
    public String doDelete(){
		
	String back = "", qq = "";
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
		
	con = Helper.getConnection();
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	try{
	    qq = "delete from departments where id=?";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1,id);
	    pstmt.executeUpdate();
	    message="Deleted Successfully";
	    id="";
	    name="";
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

}
