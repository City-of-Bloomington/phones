package phone.action;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.model.*;
import phone.list.*;
import phone.utils.*;

public class ReportAction extends TopAction{

    static final long serialVersionUID = 97L;	
   
    static Logger logger = LogManager.getLogger(ReportAction.class);
    Report report = null;
    String reportTitle = "Phones Report ";
    List<Billing> billings = null;
    List<Department> departments = null;
    public String execute(){
	String ret = INPUT;            
	if(action.equals("Submit")){
	    ret = SUCCESS;
	    String back = report.find();
	    if(!back.equals("")){
		addActionError(back);
		ret = INPUT;
	    }
	    else{
		if(!report.getBilling_id().equals("-1")){
		    reportTitle += " (Billing "+report.getBilling()+")";
		}
		if(!report.getDepartment_id().equals("-1")){
		    reportTitle += " (Department "+report.getDepartment()+")";
		}
	    }
	}
	return ret;
    }			 
    public Report getReport(){
	if(report == null){
	    report = new Report(debug);
	}
	return report;
    }
    public void setReport(Report val){
	if(val != null)
	    report = val;
    }
    public boolean hasDepartments(){
	getDepartments();
	return departments != null && departments.size() > 0;
    }
    public boolean hasBillings(){
	getBillings();
	return billings != null && billings.size() > 0;
    }		
    public List<Billing> getBillings(){
	if(billings == null){
	    BillingList tl = new BillingList(debug);
	    String back = tl.find();
	    if(back.equals("")){
		List<Billing> ones = tl.getBillings();
		if(ones != null && ones.size() > 0){
		    billings = ones;
		}
	    }
	}
	return billings;
    }		
    public List<Department> getDepartments(){
	if(departments == null){
	    DepartmentList tl = new DepartmentList(debug);
	    String back = tl.find();
	    if(back.equals("")){
		List<Department> ones = tl.getDepartments();
		if(ones != null && ones.size() > 0){
		    departments = ones;
		}
	    }
	    else{
		System.err.println(back);
	    }
	}
	return departments;
    }
    public String getReportTitle(){
	return reportTitle;
    }

}





































