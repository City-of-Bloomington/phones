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

public class LdapEmployeeList extends CommonInc{

    static Logger logger = LogManager.getLogger(LdapEmployeeList.class);
    static final long serialVersionUID = 3100L;
    String name = "", id="",  phone_id="",
	limit="", mailBox = "",
	phone_number="";
		
    List<LdapEmployee> employees = null;
    public LdapEmployeeList(){
	super();
    }
    public LdapEmployeeList(boolean deb){
	super(deb);
    }		
    public LdapEmployeeList(boolean deb, String val){
	super(deb);
	setName(val);
    }
    public List<LdapEmployee> getEmployees(){
	return employees;
    }
    public void setName(String val){
	if(val != null)
	    name = val;
    }
    public void setMailBox(String val){
	if(val != null)
	    mailBox = val;
    }		
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setPhone_number(String val){
	phone_number = val;
    }
    public void setPhone_id(String val){
	phone_id = val;
    }		
    public String getId(){
	return id;
    }
    public String getPhone_number(){
	return phone_number;
    }
    public String getPhone_id(){
	return phone_id;
    }		
    public String getName(){
	return name;
    }
    public String getMailBox(){
	return mailBox;
    }		
    public void setNoLimit(){
	limit = "";
    }
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = " select e.id,e.name, "+
	    "p.phoneNumber,i.ext_number,i.mail_box,"+
	    "d.name department,v.name division "+
	    "from plines e inner join pline_phones ep on e.id=ep.line_id inner join phones p on p.id = ep.phone_id "+
	    "left join phone_exts i on i.id=ep.ext_id "+
	    "left join departments d on p.department_id=d.id "+
	    "left join divisions v on p.division_id=d.id "+
	    "where e.type='Employee' and e.id not in (select line_id from disposed_lines)";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	try{
	    if(debug){
		logger.debug(qq);
	    }
	    //
	    // System.err.println(qq);
	    //
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		if(employees == null)
		    employees = new ArrayList<>();
		LdapEmployee one =
		    new LdapEmployee(debug,
				     rs.getString(1),
				     rs.getString(2),
				     rs.getString(3),
				     rs.getString(4),
				     rs.getString(5),
				     rs.getString(6),
				     rs.getString(7)
				     );
		if(!employees.contains(one))
		    employees.add(one);
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






















































