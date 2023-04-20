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

public class SearchAction extends TopAction{

    static final long serialVersionUID = 1900L;	
    static Logger logger = LogManager.getLogger(SearchAction.class);
    Phone phone = null;
    PhoneList phonelst = null;
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
	if(!action.equals("")){
	    if(phonelst == null){
		getPhonelst();
	    }
	    phonelst.setPageParam(pageParam);
	    back = phonelst.find();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		phonelst.findPages();
		int cnt = phonelst.getNextPage();
		if(cnt > 0)
		    nextPage = ""+cnt;
		cnt = phonelst.getPrevPage();
		if(cnt > 0)
		    prevPage = ""+cnt;
		int total = phonelst.getTotalCount();
		pageParam = phonelst.getPageParam();
		pageNumber = phonelst.getPageNumber();
		pageParam = phonelst.getPageParam();
		List<Phone> ones = phonelst.getPhones();
		if(ones == null || ones.size() == 0){
		    addActionMessage("No match found");
		}
		else if(ones.size() == 1){
		    Phone phone = ones.get(0);
		    try{
			HttpServletResponse res = ServletActionContext.getResponse();
			String str = url+"phone.action?id="+phone.getId();
			res.sendRedirect(str);
			return super.execute();
		    }catch(Exception ex){
			System.err.println(ex);
		    }										
		}
		else{
		    phones = ones;
		    phonesTitle="Found "+total+" records";
		    addActionMessage("Found "+total+" records");
		}
	    }
	}				
	else{		
	    getPhonelst();
	}
	return ret;
    }
    public PhoneList getPhonelst(){ 
	if(phonelst == null){
	    phonelst = new PhoneList(debug);
	}		
	return phonelst;
    }

    @Override
    public String getId(){
	if(id.equals("") && phone != null){
	    id = phone.getId();
	}
	return id;
    }
				
    public void setPhonelst(PhoneList val){
	if(val != null){
	    phonelst = val;
	}
    }
    public String getPhonesTitle(){
	return phonesTitle;
    }
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public void setPageParam(String val){
	if(val != null && !val.equals(""))		
	    pageParam = val;
    }		
    public List<Phone> getPhones(){
	return phones;
    }
    public boolean hasPhones(){
	getPhones();
	return phones != null && phones.size() > 0;
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
    public String getNextPage(){
	String ret = "";
	if(!nextPage.equals("")){
	    ret = url+"search.action?action=find&pageParam="+pageParam+".pageNumber:"+nextPage;
	}
	return ret;
    }
    public String getPrevPage(){
	String ret ="";
	if(!prevPage.equals("")){
	    ret = url+"search.action?action=find&pageParam="+pageParam+".pageNumber:"+prevPage;
	}
	return ret;
    }
    public boolean hasDepartments(){
	getDepartments();
	return departments != null && departments.size() > 0;
    }
    public boolean hasBillings(){
	getBillings();
	return billings != null && billings.size() > 0;
    }
    public boolean hasAddresses(){
	getAddresses();
	return addresses != null && addresses.size() > 0;
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





































