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

public class BillingAction extends TopAction{

    static final long serialVersionUID = 1100L;	
    static Logger logger = LogManager.getLogger(BillingAction.class);
    Billing billing = null;
    List<Billing> billings = null;
    List<Type> vendors = null;
    List<Department> departments = null;
    String billingsTitle = "Current billings";
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
	    back = billing.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Added Successfully");
	    }
	}				
	else if(action.startsWith("Save")){
	    back = billing.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	}
	else{		
	    getBilling();
	    if(!id.equals("")){
		back = billing.doSelect();
		if(!back.equals("")){
		    addActionError(back);
		}
	    }
	}
	return ret;
    }
    public Billing getBilling(){ 
	if(billing == null){
	    billing = new Billing();
	    billing.setId(id);
	}		
	return billing;
    }

    @Override
    public String getId(){
	if(id.equals("") && billing != null){
	    id = billing.getId();
	}
	return id;
    }
				
    public void setBilling(Billing val){
	if(val != null){
	    billing = val;
	}
    }
    public String getBillingsTitle(){
	return billingsTitle;
    }
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public boolean hasBillings(){
	getBillings();
	return billings != null && billings.size() > 0;
    }		
    public boolean hasDepartments(){
	getDepartments();
	return departments != null && departments.size() > 0;
    }
    public boolean hasVendors(){
	getVendors();
	return vendors != null && vendors.size() > 0;
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
	}
	return departments;
    }		
    public List<Type> getVendors(){
	if(vendors == null){
	    TypeList tl = new TypeList(debug, null, "vendors");
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
		
}





































