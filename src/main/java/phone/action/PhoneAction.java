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

public class PhoneAction extends TopAction{

    static final long serialVersionUID = 1900L;	
    static Logger logger = LogManager.getLogger(PhoneAction.class);
    Phone phone = null;
    List<Phone> phones = null;
    List<Type> vendors = null;
    List<Type> addresses = null;
    List<Billing> billings = null;
    List<Department> departments = null;
    String phonesTitle = "Current phones";
    String nextPage = "", prevPage = "", pageNumber ="";
    String pageParam = "";
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
	    back = phone.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Added Successfully");
	    }
	}				
	else if(action.startsWith("Save")){
	    back = phone.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	}
	else if(action.startsWith("Remove")){
	    if(phone == null){
		getPhone();
	    }
	    back = phone.removeLines();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Employee removed from this phone");
	    }
	}
	else if(action.startsWith("Delete")){
	    if(phone == null){
		getPhone();
	    }
	    back = phone.doDelete();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		phone = new Phone(debug);
		addActionMessage("Deleted Successfully");
	    }
	}				
	else{		
	    getPhone();
	    if(!id.equals("")){
		back = phone.doSelect();
		if(!back.equals("")){
		    addActionError(back);
		}
	    }
	}
	return ret;
    }
    public Phone getPhone(){ 
	if(phone == null){
	    phone = new Phone();
	    phone.setId(id);
	}		
	return phone;
    }

    @Override
    public String getId(){
	if(id.equals("") && phone != null){
	    id = phone.getId();
	}
	return id;
    }
				
    public void setPhone(Phone val){
	if(val != null){
	    phone = val;
	}
    }
    public String getPhonesTitle(){
	return phonesTitle;
    }
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public boolean hasPhones(){
	getPhones();
	return phones != null && phones.size() > 0;
    }
    public List<Phone> getPhones(){
	if(phones == null){
	    PhoneList tl = new PhoneList(debug);
	    tl.setPageParam(pageParam);						
	    String back = tl.find();
	    if(back.equals("")){
		List<Phone> ones = tl.getPhones();
		if(ones != null && ones.size() > 0){
		    phones = ones;
		}
		tl.findPages();
		int cnt = tl.getNextPage();
		if(cnt > 0)
		    nextPage = ""+cnt;
		cnt = tl.getPrevPage();
		if(cnt > 0)
		    prevPage = ""+cnt;
		int total = tl.getTotalCount();
		pageParam = tl.getPageParam();
		pageNumber = tl.getPageNumber();
		pageParam = tl.getPageParam();
		phonesTitle="Total "+total+" records";
	    }
	}
	return phones;
    }
    public List<Type> getAddresses(){
	if(addresses == null){
	    TypeList tl = new TypeList(debug, null,"addresses");
	    String back = tl.find();
	    if(back.equals("")){
		List<Type> ones = tl.getTypes();
		if(ones != null && ones.size() > 0){
		    addresses = ones;
		}
	    }
	}
	return addresses;
    }
    public boolean hasAddresses(){
	getAddresses();
	return addresses != null && addresses.size() > 0;
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
    public boolean hasBillings(){
	getBillings();
	return billings != null && billings.size() > 0;
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
    public boolean hasDepartments(){
	getDepartments();
	return departments != null && departments.size() > 0;
    }
    public void setPageParam(String val){
	if(val != null && !val.equals(""))		
	    pageParam = val;
    }				
    public String getNextPage(){
	String ret = "";
	if(!nextPage.equals("")){
	    ret = url+"phone.action?pageParam="+pageParam+".pageNumber:"+nextPage;
	}
	return ret;
    }
    public String getPrevPage(){
	String ret ="";
	if(!prevPage.equals("")){
	    ret = url+"phone.action?pageParam="+pageParam+".pageNumber:"+prevPage;
	}
	return ret;
    }
    public String getPageNumber(){
	return pageNumber;
    }
    public boolean hasNextPage(){
	return !nextPage.equals("");
    }
    public boolean hasPrevPage(){
	return !prevPage.equals("");
    }
		
}





































