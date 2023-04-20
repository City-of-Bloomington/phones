package phone.list;
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

public class DepartmentList extends CommonInc{

    static Logger logger = LogManager.getLogger(DepartmentList.class);
    static final long serialVersionUID = 1900L;
    String name = ""; // for service
    String id = "", exclude_dept_id="";
    List<Department> departments = null;
	
    public DepartmentList(){
    }
    public DepartmentList(boolean deb){
	super(deb);
    }
    public List<Department> getDepartments(){
	return departments;
    }
		
    public void setName(String val){
	if(val != null)
	    name = val;
    }
    public void setExclued_dept_id(String val){
	if(val != null)
	    exclude_dept_id = val;
    }
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = "select d.id,d.name,d.alias,d.longDistanceCode from departments d ";
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
		qw += " d.id = ? ";
	    }
	    else if(!exclude_dept_id.equals("")){
		qw += " d.id <> ? ";
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
	    else if(!exclude_dept_id.equals("")){
		pstmt.setString(jj++, exclude_dept_id);
	    }						
	    rs = pstmt.executeQuery();
	    if(departments == null)
		departments = new ArrayList<Department>();
	    while(rs.next()){
		Department one =
		    new Department(debug,
				   rs.getString(1),
				   rs.getString(2),
				   rs.getString(3),
				   rs.getString(4));
		if(!departments.contains(one))
		    departments.add(one);
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






















































