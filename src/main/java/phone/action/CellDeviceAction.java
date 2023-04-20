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

public class CellDeviceAction extends TopAction{

    static final long serialVersionUID = 1900L;	
    static Logger logger = LogManager.getLogger(CellDeviceAction.class);
    CellDevice cellDevice = null;
    List<CellDevice> cellDevices = null;
    List<Billing> billings = null;
    List<Department> departments = null;
    String cellDevicesTitle = "Current Cell Devices";
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
	    back = cellDevice.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Added Successfully");
	    }
	}				
	else if(action.startsWith("Save")){
	    back = cellDevice.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	}
	else if(action.startsWith("Delete")){
	    if(cellDevice == null){
		getCellDevice();
	    }
	    back = cellDevice.doDelete();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		cellDevice = new CellDevice(debug);
		addActionMessage("Deleted Successfully");
	    }
	}				
	else{		
	    getCellDevice();
	    if(!id.equals("")){
		back = cellDevice.doSelect();
		if(!back.equals("")){
		    addActionError(back);
		}
	    }
	}
	return ret;
    }
    public CellDevice getCellDevice(){ 
	if(cellDevice == null){
	    cellDevice = new CellDevice();
	    cellDevice.setId(id);
	}		
	return cellDevice;
    }

    @Override
    public String getId(){
	if(id.equals("") && cellDevice != null){
	    id = cellDevice.getId();
	}
	return id;
    }
				
    public void setCellDevice(CellDevice val){
	if(val != null){
	    cellDevice = val;
	}
    }
    public String getCellDevicesTitle(){
	return cellDevicesTitle;
    }
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public boolean hasCellDevices(){
	getCellDevices();
	return cellDevices != null && cellDevices.size() > 0;
    }
    public List<CellDevice> getCellDevices(){
	if(cellDevices == null){
	    CellDeviceList tl = new CellDeviceList(debug);
	    tl.setPageParam(pageParam);						
	    String back = tl.find();
	    if(back.equals("")){
		List<CellDevice> ones = tl.getCellDevices();
		if(ones != null && ones.size() > 0){
		    cellDevices = ones;
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
		cellDevicesTitle="Total "+total+" records";
	    }
	}
	return cellDevices;
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
	    ret = url+"cellDevice.action?pageParam="+pageParam+".pageNumber:"+nextPage;
	}
	return ret;
    }
    public String getPrevPage(){
	String ret ="";
	if(!prevPage.equals("")){
	    ret = url+"cellDevice.action?pageParam="+pageParam+".pageNumber:"+prevPage;
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





































