package phone.action;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.model.*;
import phone.list.*;
import phone.utils.*;

public class PaymentAction extends TopAction{

    static final long serialVersionUID = 1100L;	
    static Logger logger = LogManager.getLogger(PaymentAction.class);
    Payment payment = null;
    List<Payment> payments = null;
    List<Type> vendors = null;
    String paymentsTitle = "Current payments";
    String[] totalArrStr = null;
    public String execute(){
	String ret = SUCCESS;
	String back = doPrepare();
	if(!back.equals("")){
	    try{
		HttpServletResponse res = ServletActionContext.getResponse();
		String str = url+"Login";
		res.sendRedirect(str);
		return super.execute();
	    }catch(Exception ex){
		System.err.println(ex);
	    }
	}
	if(action.equals("Save")){
	    back = payment.doSave();
	    if(!back.equals("")){
		addActionError(back);
								
	    }
	    else{
		addActionMessage("Added Successfully");
	    }
	}				
	else if(action.startsWith("Save")){
	    back = payment.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	}
	else{		
	    getPayment();
	    if(!id.equals("")){
		back = payment.doSelect();
		if(!back.equals("")){
		    addActionError(back);
		}
	    }
	}
	return ret;
    }
    public Payment getPayment(){ 
	if(payment == null){
	    payment = new Payment();
	    payment.setId(id);
	}		
	return payment;
    }

    @Override
    public String getId(){
	if(id.equals("") && payment != null){
	    id = payment.getId();
	}
	return id;
    }
    public void setPayment(Payment val){
	if(val != null){
	    payment = val;
	}
    }
    public String getPaymentsTitle(){
	return paymentsTitle;
    }
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public List<Payment> getPayments(){
	if(payments == null){
	    PaymentList tl = new PaymentList(debug);
	    String back = tl.find();
	    if(back.equals("")){
		List<Payment> ones = tl.getPayments();
		if(ones != null && ones.size() > 0){
		    payments = ones;
		    totalArrStr = tl.getTotalArrStr();
		}
	    }
	}
	return payments;
    }
    public List<Type> getVendors(){
	if(vendors == null){
	    TypeList tl = new TypeList(debug,null,"vendors");
	    String back = tl.find();
	    if(back.equals("")){
		List<Type> ones = tl.getTypes();
		if(ones != null && ones.size() > 0){
		    vendors = ones;
		}
	    }
	}
	return vendors;
    }		
    public boolean haveTotalArr(){
	return totalArrStr != null;
    }
    public String[] getTotalArrStr(){
	return totalArrStr;
    }
}





































