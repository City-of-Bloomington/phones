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

public class VendorAction extends TopAction{

    static final long serialVersionUID = 3000L;	
    static Logger logger = LogManager.getLogger(VendorAction.class);
    //
    Type vendor = null;
    List<Type> vendors = null;
    String vendorsTitle = "Current Vendors";
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
	    back = vendor.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = vendor.getId();
		addActionMessage("Saved Successfully");
	    }
	}				
	else if(action.equals("Save Changes")){ 
	    back = vendor.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
		id = vendor.getId();
	    }
	}
	else if(action.equals("Delete")){ 
	    back = vendor.doDelete();
	    if(!back.equals("")){
		// back to the same page 
		addActionError(back);
	    }
	    else{
		vendor = new Type(debug, null,null,"vendors");
		addActionMessage("Deleted Successfully");
		id="";
	    }
	}
	else if(!id.equals("")){ 
	    getVendor();
	    back = vendor.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}
	else{		
	    getVendor();
	}
	return ret;
    }
    public Type getVendor(){ 
	if(vendor == null){
	    if(!id.equals("")){
		vendor = new Type(debug, id,null,"vendors");
	    }
	    else{
		vendor = new Type(debug, null,null,"vendors");
	    }
	}		
	return vendor;
    }

    public void setVendor(Type val){
	if(val != null){
	    vendor = val;
	    if(!id.equals("")){
		vendor.setId(id);
	    }
	}
    }

    public String getVendorsTitle(){
	return vendorsTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }

    public String getId(){
	if(id.equals("") && vendor != null){
	    id = vendor.getId();
	}
	return id;
    }
    // most recent
    public List<Type> getVendors(){ 
	if(vendors == null){
	    TypeList dl = new TypeList(debug, null, "vendors");
	    String back = dl.find();
	    vendors = dl.getTypes();
	}		
	return vendors;
    }

}





































