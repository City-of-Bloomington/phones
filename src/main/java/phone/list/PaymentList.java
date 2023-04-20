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
import java.text.NumberFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.model.*;
import phone.utils.*;

public class PaymentList extends CommonInc{

    static Logger logger = LogManager.getLogger(PaymentList.class);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    static final long serialVersionUID = 1300L;
    String id="", limit="", vendor_id="",
	date_from="", date_to="", whichDate="start_period", 
	year="";
    double totalArr[] = {0,0,0,0,0, 0,0,0,0,0, 0,0,0,0,0, 0};
    String[] totalStr = {"Total","","0","0", "0","0","0","0","0", "0","0","0","0","0", "0","0"};
    List<Payment> payments = null;
    public PaymentList(){
	super();
    }
    public PaymentList(boolean deb){
	super(deb);
    }		
    public PaymentList(boolean deb, String val){
	super(deb);
	setId(val);
    }
    public List<Payment> getPayments(){
	return payments;
    }

    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setVendor_id(String val){
	if(val != null)
	    vendor_id = val;
    }		
    public void setYear(String val){
	if(val != null)
	    year = val;
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

    public String getId(){
	return id;
    }
    public String getVendor_id(){
	return vendor_id;
    }		
    public String getYear(){
	return year;
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
    public String[] getTotalArrStr(){
	for(int j=2;j<16;j++){
	    totalStr[j] = formatter.format(totalArr[j]);
	}
	return totalStr;
    }
    public double[] getTotalArr(){
	return totalArr;
    }		
    public void setNoLimit(){
	limit = "";
    }
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = " select id,date_format(start_period,'%m/%d/%Y'),"+
	    " date_format(end_period, '%m/%d/%Y'),"+
	    " base_monthly,emergency,federal_univ, in_univ, in_util_receipt,"+
	    " telecom,add_charge,information,internet,local_toll,reports,yp,"+
	    " credits,total,vendor_id "+
	    " from payments ";
	String qw = "";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	if(!id.equals("")){
	    qw += " id = ? ";
	}
	else{
	    if(!year.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " year(end_period) = ? ";
	    }
	    else{
		if(!date_from.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += whichDate+" >= ? ";
		}
		if(!date_to.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += whichDate+" <= ? ";
		}						
	    }
	}
	if(!qw.equals("")){
	    qq += " where "+qw;
	}
	qq += " order by start_period ";
	if(!limit.equals("")){
	    qq += limit;
	}
	try{
	    if(debug){
		logger.debug(qq);
	    }
	    //
	    pstmt = con.prepareStatement(qq);
	    if(!id.equals("")){
		pstmt.setString(1, id);
	    }
	    else {
		int jj=1;
		if(!year.equals("")){
		    pstmt.setString(jj++,year);
		}
		else{
		    if(!date_from.equals("")){
			java.util.Date date_tmp = dateFormat.parse(date_from);
			pstmt.setDate(jj++, new java.sql.Date(date_tmp.getTime()));
		    }
		    if(!date_to.equals("")){
			java.util.Date date_tmp = dateFormat.parse(date_to);
			pstmt.setDate(jj++, new java.sql.Date(date_tmp.getTime()));
		    }
		}
	    }
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		if(payments == null)
		    payments = new ArrayList<>();
		Payment one =
		    new Payment(debug,
				rs.getString(1),
				rs.getString(2),
				rs.getString(3),
				rs.getDouble(4),
				rs.getDouble(5),
				rs.getDouble(6),
				rs.getDouble(7),
				rs.getDouble(8),
				rs.getDouble(9),
				rs.getDouble(10),
				rs.getDouble(11),
				rs.getDouble(12),
				rs.getDouble(13),
				rs.getDouble(14),
				rs.getDouble(15),
				rs.getDouble(16),
				rs.getDouble(17),
				rs.getString(18));
		if(!payments.contains(one))
		    payments.add(one);
		for(int j=4;j<18;j++){
		    totalArr[j-2] += rs.getDouble(j);
		}
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






















































