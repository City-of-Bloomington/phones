package phone.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.*;
import javax.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.list.*;
import phone.utils.*;

public class PaymentReport{
	
    static Logger logger = LogManager.getLogger(PaymentReport.class);
    static final long serialVersionUID = 51L;
    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
    boolean type=false, signal_type=false, addr=false, bill=false, dept=false,
	status=false, vendor=false;
    String year="",date_from="", date_to="";
    String title = "", chartTitle ="", billing_id="", department_id="";
    boolean debug = false, needTotal = false;
    List<Payment> payments = null;
    String[] totalArrStr = null;
    double[] totalArr = null;
    Billing billing = null;
    Department department = null;
    public PaymentReport(){

    }
    public PaymentReport(boolean deb){
	debug = deb;
    }
    public void setType(boolean val){
	type = val;
    }
    public void setBill(boolean val){
	bill = val;
    }
    public void setVendor(boolean val){
	vendor = val;
    }		
    public void setDept(boolean val){
	dept = val;
    }
    public void setStatus(boolean val){
	status = val;
    }
    public void setDepartment_id(String val){
	if(val != null && !val.equals("-1"))
	    department_id = val;
    }
    public void setBilling_id(String val){
	if(val != null && !val.equals("-1"))
	    billing_id = val;
    }
    public void setYear(String val){
	if(val != null && !val.equals("-1"))
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
    //
    // getters
    //
    public boolean getType(){
	return type;
    }		
    public boolean getDept(){
	return dept ;
    }	
    public boolean getAddr(){
	return addr ;
    }
    public boolean getBill(){
	return bill ;
    }
    public boolean getStatus(){
	return status ;
    }
    public boolean getVendor(){
	return vendor ;
    }		
    public String getTitle(){
	return title;
    }
    public String getChartTitle(){
	return chartTitle;
    }		
    public String getDepartment_id(){
	if(department_id.equals(""))
	    return "-1";
	return department_id;
    }
    public String getBilling_id(){
	if(billing_id.equals(""))
	    return "-1";
	return billing_id;
    }
    public String getYear(){
	if(year.equals(""))
	    return "-1";
	return year;
    }
    public String getDate_from(){
	return date_from;
    }
    public String getDate_to(){
	return date_to;
    }

    public List<Payment> getPayments(){
	return payments;
    }
    public double[] getTotalArr(){
	return totalArr;
    }
    public String[] getTotalArrStr(){
	return totalArrStr;
    }		

    public Billing getBilling(){
	if(!billing_id.equals("") && billing == null){ 
	    Billing one = new Billing(debug, billing_id);
	    String back = one.doSelect();
	    if(back.equals("")){
		billing = one;
	    }
	}
	return billing;
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
    public String find(){
	return findPayments();

    }
    public String findPayments(){
	PaymentList pl = new PaymentList(debug);
	chartTitle = "Total Payments by Type ";
	if(!year.equals("")){
	    pl.setYear(year);
	    title = "Payments year "+year;
	    chartTitle += "("+year+")";
	}
	else{
	    title ="Payments ";
	    if(!date_from.equals("")){
		pl.setDate_from(date_from);
		title = title+" "+date_from;
		chartTitle += date_from;
	    }
	    if(!date_to.equals("")){
		pl.setDate_to(date_to);
		title = title+" - "+date_to;
		chartTitle += " - "+date_to;
	    }
	}
	String back = pl.find();
	if(back.equals("")){
	    List<Payment> ones = pl.getPayments();
	    if(ones != null && ones.size() > 0){
		payments = ones;
		totalArrStr = pl.getTotalArrStr();
		totalArr = pl.getTotalArr();								
	    }
	}
	return back;
    }

}






















































