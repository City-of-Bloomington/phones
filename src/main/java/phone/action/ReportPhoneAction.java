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

public class ReportPhoneAction extends TopAction{

    static final long serialVersionUID = 97L;	
   
    static Logger logger = LogManager.getLogger(ReportPhoneAction.class);
    Report report = null;
    List<Department> departments = null;
    String reportTitle = "Phone Records Report ";
    String outputType = ""; // html, csv
    public String execute(){
	String ret = INPUT;            
	if(!action.equals("")){
	    ret = SUCCESS;
	    report.setReportType("phones");
	    String back = report.find();
	    if(!back.equals("")){
		addActionError(back);
		ret = INPUT;
	    }
	    else{
		if(!report.getDepartment_id().equals("-1")){
		    reportTitle += " (Department "+report.getDepartment()+")";
		}
		if(!outputType.equals("")){
		    ret = "csv";
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
    public void setOutputType(boolean val){
	if(val)
	    outputType = "csv";
    }
    public boolean getOutputType(){
	if(!outputType.equals("")) // csv
	    return true;
	return false;
    }
		
    public boolean hasDepartments(){
	getDepartments();
	return departments != null && departments.size() > 0;
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





































