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

public class BillingList extends CommonInc{

    static Logger logger = LogManager.getLogger(BillingList.class);
    static final long serialVersionUID = 1300L;
    String name = "", id="",  account_num="",  limit=" limit 30 ",
	vendor_id="", address="", city="", state="", zip="",
	department_id="", division_id="";
		
    List<Billing> billings = null;
    public BillingList(){
	super();
    }
    public BillingList(boolean deb){
	super(deb);
    }		
    public BillingList(boolean deb, String val){
	super(deb);
	setVendor_id(val);
    }
    public List<Billing> getBillings(){
	return billings;
    }
    public void setVendor_id(String val){
	if(val != null && !val.equals("-1"))
	    vendor_id = val;
    }

    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setAccount_num(String val){
	if(val != null)
	    account_num = val;
    }		
    public void setDepartment_id(String val){
	if(val != null)
	    department_id = val;
    }
    public void setDivsion_id(String val){
	if(val != null)
	    division_id = val;
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
    public String getId(){
	return id;
    }
    public String getVendor_id(){
	return vendor_id;
    }		
    public String getDepartment_id(){
	return department_id;
    }
    public String getDivision_id(){
	return division_id;
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
    public void setNoLimit(){
	limit = "";
    }
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = " select p.id,p.name,p.department_id,p.division_id,"+
	    " p.account_num,p.vendor_id,p.address,p.city,p.state,p.zip, "+
	    " p.foundation_account_num "+
	    " from billings p ";
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
	    if(!account_num.equals("")){
		qw += " p.account_num like ? ";
	    }
	    if(!department_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.department_id=? ";
	    }
	    if(!division_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.division_id=? ";
	    }
	    if(!vendor_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.vendor_id=? ";
	    }
	    if(!address.equals("")){
		qw += " p.address like ? ";
	    }
	    if(!city.equals("")){
		qw += " p.city like ? ";
	    }
	    if(!state.equals("")){
		qw += " p.state like ? ";
	    }
	    if(!zip.equals("")){
		qw += " p.zip like ? ";
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
		if(!account_num.equals("")){
		    pstmt.setString(jj++,"%"+account_num+"%");		
		}
		if(!department_id.equals("")){
		    pstmt.setString(jj++,department_id);		
		}
		if(!division_id.equals("")){
		    pstmt.setString(jj++,division_id);		
		}
		if(!vendor_id.equals("")){
		    pstmt.setString(jj++,vendor_id);		
		}
		if(!address.equals("")){
		    pstmt.setString(jj++,"%"+address+"%");		
		}
		if(!city.equals("")){
		    pstmt.setString(jj++,"%"+city+"%");		
		}
		if(!state.equals("")){
		    pstmt.setString(jj++,state);		
		}
		if(!zip.equals("")){
		    pstmt.setString(jj++,zip+"%");		
		}								
	    }
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		if(billings == null)
		    billings = new ArrayList<>();
		Billing one =
		    new Billing(debug,
				rs.getString(1),
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
		if(!billings.contains(one))
		    billings.add(one);
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






















































