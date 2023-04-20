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

public class CircuitList extends CommonInc{

    static Logger logger = LogManager.getLogger(CircuitList.class);
    static final long serialVersionUID = 1600L;
    String name = "", id="",  billing_id="", model="",
	limit="", location="",address_id="",
	department_id="", division_id="";
    boolean active_only = false;
    List<Circuit> circuits = null;
    public CircuitList(){
	super();
    }
    public CircuitList(boolean deb){
	super(deb);
    }		
    public CircuitList(boolean deb, String val){
	super(deb);
	setName(val);
    }
    public List<Circuit> getCircuits(){
	return circuits;
    }
    public void setName(String val){
	if(val != null)
	    name = val;
    }
    public void setModel(String val){
	if(val != null)
	    model = val;
    }
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setDepartment_id(String val){
	if(val != null)
	    department_id = val;
    }
    public void setDivsion_id(String val){
	if(val != null)
	    division_id = val;
    }
    public void setBilling_id(String val){
	if(val != null)
	    billing_id = val;
    }		
    public void setAddress_id(String val){
	if(val != null && !val.equals("-1"))
	    address_id = val;
    }
    public String getId(){
	return id;
    }
    public String getName(){
	return name;
    }
    public String getModel(){
	return model;
    }		
    public String getDepartment_id(){
	return department_id;
    }
    public String getAddress_id(){
	return address_id;
    }
    public String getDivision_id(){
	return division_id;
    }		
    public String getBilling_id(){
	return billing_id;
    }
    public void setActiveOnly(){
	active_only = true;
    }
    public void setNoLimit(){
	limit = "";
    }
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = " select p.id,p.name,"+
	    " p.model,p.address_id,p.department_id,"+
	    "p.division_id,p.billing_id, p.location, "+
	    " p.active,p.notes "+
	    " from special_circuits p ";
	String qw = "";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	if(!id.equals("")){
	    qw += " p.id = ? ";
	}
	else{
	    if(!name.equals("")){
		qw += " p.name like ? ";
	    }
	    if(!model.equals("")){
		qw += " p.model like ? ";
	    }						
	    if(!department_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.department_id=? ";
	    }
	    if(!division_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.division_id=? ";
	    }
	    if(!billing_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.billing_id=? ";
	    }
	    if(active_only){
		if(!qw.equals("")) qw += " and ";
		qw += " p.active is not null ";
	    }
	}
	if(!qw.equals("")){
	    qq += " where "+qw;
	}
	qq += " order by p.name ";
	if(!limit.equals("")){
	    qq += limit;
	}
	try{
	    if(debug){
		logger.debug(qq);
	    }
	    //
	    // System.err.println(qq);
	    //
	    pstmt = con.prepareStatement(qq);
	    if(!id.equals("")){
		pstmt.setString(1, id);
	    }
	    else {
		int jj=1;
		if(!name.equals("")){
		    pstmt.setString(jj++,"%"+name+"%");
		}
		if(!model.equals("")){
		    pstmt.setString(jj++,"%"+model+"%");		
		}
		if(!department_id.equals("")){
		    pstmt.setString(jj++,department_id);		
		}
		if(!division_id.equals("")){
		    pstmt.setString(jj++,division_id);		
		}
		if(!billing_id.equals("")){
		    pstmt.setString(jj++,billing_id);		
		}								
	    }
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		if(circuits == null)
		    circuits = new ArrayList<>();
		Circuit one =
		    new Circuit(debug,
				rs.getString(1),
				rs.getString(2),
				rs.getString(3),
				rs.getString(4),
				rs.getString(5),
				rs.getString(6),
				rs.getString(7),
				rs.getString(8),
				rs.getString(9),
				rs.getString(10));
		if(!circuits.contains(one))
		    circuits.add(one);
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






















































