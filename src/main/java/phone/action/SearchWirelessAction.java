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

public class SearchWirelessAction extends TopAction{

    static final long serialVersionUID = 1900L;	
    static Logger logger = LogManager.getLogger(SearchWirelessAction.class);
    CellDeviceList cellst = null;
    List<CellDevice> cellDevices = null;
    List<Type> vendors = null;
    List<Billing> billings = null;
    List<Department> departments = null;
    String cellsTitle = "Cell Devices";
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
	    if(cellst == null){
		getCellst();
	    }
	    cellst.setPageParam(pageParam);
	    back = cellst.find();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		cellst.findPages();
		int cnt = cellst.getNextPage();
		if(cnt > 0)
		    nextPage = ""+cnt;
		cnt = cellst.getPrevPage();
		if(cnt > 0)
		    prevPage = ""+cnt;
		int total = cellst.getTotalCount();
		pageParam = cellst.getPageParam();
		pageNumber = cellst.getPageNumber();
		pageParam = cellst.getPageParam();
		List<CellDevice> ones = cellst.getCellDevices();
		if(ones == null || ones.size() == 0){
		    addActionMessage("No match found");
		}
		else{
		    cellDevices = ones;
		    cellsTitle="Found "+total+" records";
		    addActionMessage("Found "+total+" records");
		}
	    }
	}				
	else{		
	    getCellst();
	}
	return ret;
    }
    public CellDeviceList getCellst(){ 
	if(cellst == null){
	    cellst = new CellDeviceList(debug);
	}		
	return cellst;
    }

				
    public void setCellst(CellDeviceList val){
	if(val != null){
	    cellst = val;
	}
    }
    public String getCellsTitle(){
	return cellsTitle;
    }
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public void setPageParam(String val){
	if(val != null && !val.equals(""))		
	    pageParam = val;
    }		
    public List<CellDevice> getCellDevices(){
	return cellDevices;
    }
    public boolean hasCellDevices(){
	getCellDevices();
	return cellDevices != null && cellDevices.size() > 0;
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
	    ret = url+"wirelessSearch.action?action=find&pageParam="+pageParam+".pageNumber:"+nextPage;
	}
	return ret;
    }
    public String getPrevPage(){
	String ret ="";
	if(!prevPage.equals("")){
	    ret = url+"wirelessSearch.action?action=find&pageParam="+pageParam+".pageNumber:"+prevPage;
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





































