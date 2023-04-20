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
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.model.*;
import phone.utils.*;

public class ContractList extends CommonInc{

    static Logger logger = LogManager.getLogger(ContractList.class);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    static final long serialVersionUID = 1300L;
    String name = "", id="", limit=" limit 30 ",
	vendor_id="", date_from="", date_to="", whichDate="c.start_date", 
	expire_status="", billing_id="";
    List<Contract> contracts = null;
    public ContractList(){
	super();
    }
    public ContractList(boolean deb){
	super(deb);
    }		
    public List<Contract> getContracts(){
	return contracts;
    }

    public void setVendor_id(String val){
	if(val != null && !val.equals("-1"))
	    vendor_id = val;
    }
    public void setBilling_id(String val){
	if(val != null && !val.equals("-1"))
	    billing_id = val;
    }		

    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setDate_from(String val){
	if(val != null)
	    date_from = val;
    }
    public void setDate_to(String val){
	if(val != null)
	    date_to = val;
    }
    public void setWhichDate(String val){
	if(val != null)
	    whichDate = val;
    }
    public void setExpire_status(String val){
	if(val != null && !val.equals("all"))
	    expire_status = val;
    }		
    public String getId(){
	return id;
    }
    public String getVendor_id(){
	if(vendor_id.equals(""))
	    return "-1";
	return vendor_id;
    }
    public String getBilling_id(){
	if(billing_id.equals(""))
	    return "-1";
	return billing_id;
    }		
    public String getDate_from(){
	return date_from;
    }
    public String getDate_to(){
	return date_to;
    }
    public String getWhichDate(){
	return whichDate;
    }
    public String getExpire_status(){
	if(expire_status.equals("")){
	    return "all";
	}
	return expire_status;
    }		

    public void setNoLimit(){
	limit = "";
    }
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = " select c.id,c.name,c.billing_id,"+
	    " date_format(c.start_date,'%m/%d/%Y'),"+
	    " date_format(c.end_date,'%m/%d/%Y'),"+
	    " date_format(c.notification_date,'%m/%d/%Y'),"+
	    " c.notes,datediff(c.end_date, now()) "+
	    " from contracts c left join billings b on c.billing_id=b.id ";
	String qw = "";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	if(!id.equals("")){
	    qw += " c.id = ? ";
	}
	else if(!billing_id.equals("")){
	    qw += " c.billing_id = ? ";
	}
	else{
	    if(!name.equals("")){
		qw += " c.name like ? ";
	    }						
	    if(!date_from.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += whichDate+" >= ? ";
	    }
	    if(!date_to.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += whichDate+" <= ? ";
	    }
	    if(!vendor_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " b.vendor_id = ? ";								
	    }
	    if(expire_status.equals("active")){
		if(!qw.equals("")) qw += " and ";								
		qw += " c.end_date > now() ";
	    }
	    else if(expire_status.equals("expired")){
		if(!qw.equals("")) qw += " and ";
		qw += " c.end_date < now() ";								
	    }
	}
	if(!qw.equals("")){
	    qq += " where "+qw;
	}
	qq += " order by c.id desc ";
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
	    else if(!billing_id.equals("")){
		pstmt.setString(1, billing_id);
	    }
	    else {
		int jj=1;
		if(!name.equals("")){
		    pstmt.setString(jj++,"%"+name+"%");
		}
		if(!date_from.equals("")){
		    java.util.Date date_tmp = dateFormat.parse(date_from);
		    pstmt.setDate(jj++, new java.sql.Date(date_tmp.getTime()));
		}
		if(!date_to.equals("")){
		    java.util.Date date_tmp = dateFormat.parse(date_to);
		    pstmt.setDate(jj++, new java.sql.Date(date_tmp.getTime()));
		}
		if(!vendor_id.equals("")){
		    pstmt.setString(jj++,vendor_id);		
		}								
	    }
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		if(contracts == null)
		    contracts = new ArrayList<>();
		int days = 0;
		String str = rs.getString(8);
		if(str != null){
		    days = rs.getInt(8);
		}
		Contract one =
		    new Contract(debug,
				 rs.getString(1),
				 rs.getString(2),
				 rs.getString(3),
				 rs.getString(4),
				 rs.getString(5),
				 rs.getString(6),
				 rs.getString(7),
				 days);
		if(!contracts.contains(one))
		    contracts.add(one);
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






















































