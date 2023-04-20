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

public class PLineAction extends TopAction{

    static final long serialVersionUID = 1900L;	
    static Logger logger = LogManager.getLogger(PLineAction.class);
    PLine pline = null;
    List<PLine> plines = null;
    String pLinesTitle = "Current phone line users";
    String nextPage = "", prevPage = "", pageNumber ="";
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
	    back = pline.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Added Successfully");
	    }
	}				
	else if(action.startsWith("Save")){
	    back = pline.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	}
	else if(action.startsWith("Remove")){
	    back = pline.removeExtension();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Removed Successfully");
	    }
	}				
	else{		
	    getPline();
	    if(!id.equals("")){
		back = pline.doSelect();
		if(!back.equals("")){
		    addActionError(back);
		}
	    }
	}
	return ret;
    }
    public PLine getPline(){ 
	if(pline == null){
	    pline = new PLine();
	    pline.setId(id);
	}		
	return pline;
    }

    @Override
    public String getId(){
	if(id.equals("") && pline != null){
	    id = pline.getId();
	}
	return id;
    }
				
    public void setPline(PLine val){
	if(val != null){
	    pline = val;
	}
    }
    public String getPlinesTitle(){
	return pLinesTitle;
    }
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public void setPageNumber(String val){
	if(val != null && !val.equals(""))		
	    pageNumber = val;
    }		
    public boolean hasPlines(){
	getPlines();
	return plines != null && plines.size() > 0;
    }
    public List<PLine> getPlines(){
	if(plines == null){
	    PLineList tl = new PLineList(debug);
	    tl.setPageNumber(pageNumber);
	    String back = tl.find();
	    if(back.equals("")){
		List<PLine> ones = tl.getPLines();
		if(ones != null && ones.size() > 0){
		    plines = ones;
		}
		tl.findPages();
		int cnt = tl.getNextPage();
		if(cnt > 0)
		    nextPage = ""+cnt;
		cnt = tl.getPrevPage();
		if(cnt > 0)
		    prevPage = ""+cnt;
		int total = tl.getTotalCount();
		pLinesTitle = "Total line users "+total;
	    }
	}
	return plines;
    }
    public String getNextPage(){
	String ret = "";
	if(!nextPage.equals("")){
	    ret = ""+url+"pline.action?pageNumber="+nextPage;
	}
	return ret;
    }
    public String getPrevPage(){
	String ret ="";
	if(!prevPage.equals("")){
	    ret = ""+url+"pline.action?pageNumber="+prevPage;
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





































