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

public class PhoneExtAction extends TopAction{

    static final long serialVersionUID = 1900L;	
    static Logger logger = LogManager.getLogger(PhoneExtAction.class);
    PhoneExt phoneExt = null;
    List<PhoneExt> phoneExts = null;
    String phone_id = "";
    String phoneExtsTitle = "Current phone extentions";
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
	    back = phoneExt.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Added Successfully");
	    }
	}				
	else if(action.startsWith("Save")){
	    back = phoneExt.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	}
	else if(action.startsWith("Remove")){

	}				
	else{		
	    getPhoneExt();
	    if(!id.equals("")){
		back = phoneExt.doSelect();
		if(!back.equals("")){
		    addActionError(back);
		}
	    }
	}
	return ret;
    }
    public PhoneExt getPhoneExt(){ 
	if(phoneExt == null){
	    phoneExt = new PhoneExt();
	    if(!phone_id.equals("")){
		phoneExt.setPhone_id(phone_id);
	    }
	    phoneExt.setId(id);
	}		
	return phoneExt;
    }

    @Override
    public String getId(){
	if(id.equals("") && phoneExt != null){
	    id = phoneExt.getId();
	}
	return id;
    }
    public String getPhone_id(){
	if(phone_id.equals("") && phoneExt != null){
	    phone_id = phoneExt.getPhone_id();
	}
	return phone_id;
    }
    public void setPhone_id(String val){
	if(val != null){
	    phone_id = val;
	}
    }		
    public void setPhoneExt(PhoneExt val){
	if(val != null){
	    phoneExt = val;
	}
    }
    public String getPhoneExtsTitle(){
	return phoneExtsTitle;
    }
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public List<PhoneExt> getPhoneExts(){
	if(phoneExts == null){
	    PhoneExtList tl = new PhoneExtList(debug);
	    String back = tl.find();
	    if(back.equals("")){
		List<PhoneExt> ones = tl.getPhoneExts();
		if(ones != null && ones.size() > 0){
		    phoneExts = ones;
		}
	    }
	}
	return phoneExts;
    }
		
}





































