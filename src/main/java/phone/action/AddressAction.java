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

public class AddressAction extends TopAction{

    static final long serialVersionUID = 3000L;	
    static Logger logger = LogManager.getLogger(AddressAction.class);
    //
    Type address = null;
    List<Type> addresses = null;
    String addressesTitle = "Current Addresses";
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
	    back = address.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		id = address.getId();
		addActionMessage("Saved Successfully");
	    }
	}				
	else if(action.equals("Save Changes")){ 
	    back = address.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
		id = address.getId();
	    }
	}
	else if(action.equals("Delete")){ 
	    back = address.doDelete();
	    if(!back.equals("")){
		// back to the same page 
		addActionError(back);
	    }
	    else{
		address = new Type(debug, null,null,"addresses");
		addActionMessage("Deleted Successfully");
		id="";
	    }
	}
	else if(!id.equals("")){ 
	    getAddress();
	    back = address.doSelect();
	    if(!back.equals("")){
		addActionError(back);
	    }
	}
	else{		
	    getAddress();
	}
	return ret;
    }
    public Type getAddress(){ 
	if(address == null){
	    if(!id.equals("")){
		address = new Type(debug, id,null,"addresses");
	    }
	    else{
		address = new Type(debug, null,null,"addresses");
	    }
	}		
	return address;
    }

    public void setAddress(Type val){
	if(val != null){
	    address = val;
	    if(!id.equals("")){
		address.setId(id);
	    }
	}
    }

    public String getAddressesTitle(){
	return addressesTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }

    public String getId(){
	if(id.equals("") && address != null){
	    id = address.getId();
	}
	return id;
    }
    // most recent
    public List<Type> getAddresses(){ 
	if(addresses == null){
	    TypeList dl = new TypeList(debug, null, "addresses");
	    String back = dl.find();
	    addresses = dl.getTypes();
	}		
	return addresses;
    }

}





































