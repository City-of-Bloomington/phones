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

public class ManagePhonesAction extends TopAction{

    static final long serialVersionUID = 1100L;	
    static Logger logger = LogManager.getLogger(ManagePhonesAction.class);
    ManagePhones manage = null;
    String managePhonesTitle = "Latest runs ";
    String scansTitle = "Most recent directory scans";
    List<ScanLog> scans = null;
		
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
	if(action.equals("Start")){
	    if(manage == null){
		getManage();
	    }
	    back = manage.doProcess();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Processed Successfully");
	    }
	}				
	else{
	    getManage();
	}
	return ret;
    }
    public ManagePhones getManage(){ 
	if(manage == null){
	    manage = new ManagePhones(debug, bean);
	}		
	return manage;
    }
    public void setManage(ManagePhones val){
	if(val != null){
	    manage = val;
	}
    }

    public String getManagePhonesTitle(){
	return managePhonesTitle;
    }
    public String getScansTitle(){
	return scansTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public boolean hasScans(){
	getScans();
	return scans != null;
    }
    public List<ScanLog> getScans(){
	if(scans == null){
	    ScanLogList tl = new ScanLogList(debug);
	    String back = tl.find();
	    if(back.equals("")){
		List<ScanLog> ones = tl.getScans();
		if(ones != null && ones.size() > 0){
		    scans = ones;
		}
	    }
	}
	return scans;
    }


		
}





































