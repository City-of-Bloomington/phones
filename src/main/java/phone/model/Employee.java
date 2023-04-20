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

public class Employee extends CommonInc implements java.io.Serializable{

    String id="";
    String fullname = "";
    static final long serialVersionUID = 2300L;
    static Logger logger = LogManager.getLogger(Employee.class);
    // List<EmployeePhone> employeePhones = null; 
    //
    public Employee(){
	super();
    }
    public Employee(boolean deb){
	//
	// initialize
	//
	super(deb);
    }
    public Employee(String val){
	//
	setId(val);
    }
    public Employee(boolean deb, String val){
	//
	// initialize
	//
	super(deb);
	setId(val);
    }
    public Employee(boolean deb, String val, String val2){
	//
	// initialize
	//
	debug = deb;
	setId(val);
	setFullname(val2);
    }

    public String getId(){
	return id;
    }

    public String getFullname(){
	return fullname;
    }
    // needed for service
    public void setFullname(String val){
	if(val != null)
	    fullname = val;
    }
    /*
      public List<EmployeePhone> getEmployeePhones(){
      if(employeePhones == null && !id.equals("")){
      EmployeePhoneList ep = new EmployeePhoneList(debug, id);
      String back = ep.find();
      if(back.equals("")){
      List<EmployeePhone> ones = ep.getEmployeePhones();
      if(ones != null && ones.size() > 0){
      employeePhones = ones;
      }
      }
      }
      return employeePhones;
      }
		
      public boolean hasPhone(){
      getEmployeePhones();
      return employeePhones != null && employeePhones.size() > 0;
      }
    */
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    //
    public String toString(){
	return fullname;
    }
    public boolean equals(Object obj){
	if(obj instanceof Employee){
	    Employee one =(Employee)obj;
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
    /*
      public String doSelect(){
      String msg = "";
      Connection con = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;		
      String qq = " select e.id,e.fullname from employees e where  e.id=? ";
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
      setId(rs.getString(1));
      setFullname(rs.getString(2));
      }
      else{
      msg = " No such user";
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

      public String doSave(){
		
      Connection con = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;		
		
      String str="", msg="";
      String qq = "";
      if(fullname.equals("")){
      msg = "employee name not set";
      return msg;
      }
      qq = "insert into employees values(0,?)";
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
      stmt.setString(1, fullname);
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
      public String doUpdate(){
		
      Connection con = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;		
		
      String str="", msg="";
      String qq = "";
      qq = "update employees set fullname=? where id=?";
      //
      if(id.equals("") || fullname.equals("")){
      msg = " id or name not set";
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
      stmt.setString(1, fullname);
      stmt.setString(2, id);
      stmt.executeUpdate();
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
      //
      String doDelete(){

      String qq = "",msg="";
      Connection con = null;
      PreparedStatement stmt = null;
      ResultSet rs = null;			
      qq = "delete from employees where id=?";
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
    */

}
