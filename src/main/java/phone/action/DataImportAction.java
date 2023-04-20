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

public class DataImportAction extends TopAction{

    static final long serialVersionUID = 290L;	
    static Logger logger = LogManager.getLogger(DataImportAction.class);
    String server_path="/srv/data/phones/docs/";
    //
    //
    String[] fileList = { 
	"Fire-Wireless.csv",
	"Garage-Wireless.csv",
	"HR-Wireless.csv",
	"ITS-Wireless.csv",
	"Parks-WSCA-Wireless.csv",
	"Planning-Wireless.csv",
	"Police-Wireless.csv",
	"PW-Wireless.csv",
	"Util-Wireless.csv"};

    // String file_name = "c:\\webapps\\phones\\docs\\ITS-Wireless.csv";
    String dept_id = "-1", file_name="";
    List<Department> departments = null;
    DataImport dimport = null;
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
	if(action.equals("Import")){
	    if(dept_id.equals("-1")){
		back = "you need to pick a department";
		addActionError(back);								

	    }
	    else{
		getDimport();
		back = dimport.doImport(file_name, dept_id);
		if(!back.equals("")){
		    addActionError(back);
		}
		else{
		    addActionMessage("Save Successfully");
		}
	    }
	}				
	else{		
	    getDimport();
	}
	return ret;
    }
    public DataImport getDimport(){ 
	if(dimport == null){
	    dimport = new DataImport();
	}		
	return dimport;
    }

    public void setDataImport(DataImport val){
	if(val != null){
	    dimport = val;
	}
    }
    public String getFile_name(){
	return file_name;
    }
    public String getDept_id(){
	return dept_id;
    }		
    public void setDept_id(String val){
	if(val != null && !val.equals("-1"))
	    dept_id = val;
    }
    public void setFile_name(String val){
	if(val != null && !val.equals("-1"))
	    file_name = val;
    }		
    public List<Department> getDepartments(){
	if(departments == null){
	    DepartmentList dl = new DepartmentList(debug);
	    String back = dl.find();
	    if(back.equals("")){
		List<Department> ones = dl.getDepartments();
		if(ones != null && ones.size() > 0){
		    departments = ones;
		}
	    }
	}
	return departments;
    }
    public String[] getFileList(){
	return fileList;
    }
}





































