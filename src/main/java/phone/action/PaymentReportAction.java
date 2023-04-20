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

public class PaymentReportAction extends TopAction{

    static final long serialVersionUID = 97L;	
   
    static Logger logger = LogManager.getLogger(ReportAction.class);
    PaymentReport report = null;
    String reportTitle = "Payment Report ";
    String chartTitle = "Payments classified by type";
    List<Billing> billings = null;
    List<Department> departments = null;
    List<Type> years = null;
    List<Payment> payments = null;
    String[] totalArrStr = null;
    double[] totalArr = null;
    public String execute(){
	String ret = INPUT;            
	if(!action.equals("")){
	    ret = SUCCESS;
	    String back = report.find();
	    if(!back.equals("")){
		addActionError(back);
		ret = INPUT;
	    }
	    else{
		reportTitle = report.getTitle();
		chartTitle = report.getChartTitle();
		payments = report.getPayments();
		totalArr = report.getTotalArr();
		totalArrStr = report.getTotalArrStr();
		if(action.equals("Charts")){
		    ret = "charts";
		}
	    }
	}
	return ret;
    }			 
    public PaymentReport getReport(){
	if(report == null){
	    report = new PaymentReport(debug);
	}
	return report;
    }
    public void setReport(PaymentReport val){
	if(val != null)
	    report = val;
    }
    public String getReportTitle(){
	return reportTitle;
    }
    public String getChartTitle(){
	return chartTitle;
    }		
    public List<Type> getYears(){
	int startYear = 2013;
	if(years == null){
	    years = new ArrayList<>();
	    int curYear = Helper.getCurrentYear();
	    for(int jj=startYear;jj<=curYear;jj++){
		Type one = new Type(debug, ""+jj, ""+jj);
		years.add(one);
	    }
	}
	return years;
    }
    public List<Payment> getPayments(){
	return payments;
    }
    public String[] getTotalArrStr(){
	return totalArrStr;
    }
    public double[] getTotalArr2(){
	return totalArr;
    }
		
    public int[] getTotalArr(){
	int[] intv = new int[totalArr.length-3];
	// skip the first two and the last total
	for(int j=2;j<totalArr.length-1;j++){
	    int i=j-2;
	    intv[i] = (int)totalArr[j];
	    if(intv[i] < 0) intv[i] *= -1; // for credits
	}
	return intv;
    }		

}





































