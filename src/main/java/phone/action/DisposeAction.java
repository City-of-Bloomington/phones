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

public class DisposeAction extends TopAction{

    static final long serialVersionUID = 1900L;	
    static Logger logger = LogManager.getLogger(DisposeAction.class);
    String phone_id = "";
    Todispose todispose = null;
    List<Dispose> todisposes = null;
    List<Dispose> disposeds = null;
    String toBeDisposedTitle = "User lines can be disposed";
    String disposedTitle = "Disposed user lines";
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
	if(action.equals("Dispose")){
	    todispose.setDecision_user_id(user.getId());
	    back = todispose.doDispose();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Disposed Successfully");
	    }
	}				
	else{		
	    getTodispose();
	}
	return ret;
    }
    public Todispose getTodispose(){ 
	if(todispose == null){
	    todispose = new Todispose(debug);						
	}		
	return todispose;
    }

    public void setTodispose(Todispose val){
	if(val != null){
	    todispose = val;
	}
    }

    public String getToBeDisposedTitle(){
	return toBeDisposedTitle;
    }
    public String getDisposedTitle(){
	return disposedTitle;
    }		
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public boolean canDispose(){
	getTodisposes();
	return todisposes != null;
    }
    public List<Dispose> getTodisposes(){
	if(todisposes == null){
	    DisposeList tl = new DisposeList(debug);
	    tl.setUndecidedOnly();
	    String back = tl.find();
	    if(back.equals("")){
		List<Dispose> ones = tl.getDisposes();
		if(ones != null && ones.size() > 0){
		    todisposes = ones;
		    toBeDisposedTitle = " "+ones.size()+" records can be disposed";
		}
	    }
	    if(todisposes == null || todisposes.size() == 0){
		toBeDisposedTitle = "no records to be disposed ";
	    }
	}
	return todisposes;
    }
    public boolean haveDisposeds(){
	getDisposeds();
	return disposeds != null;
    }
    public List<Dispose> getDisposeds(){
	if(disposeds == null){
	    DisposeList tl = new DisposeList(debug);
	    tl.setDecidedOnly();
	    String back = tl.find();
	    if(back.equals("")){
		List<Dispose> ones = tl.getDisposes();
		if(ones != null && ones.size() > 0){
		    disposeds = ones;
		    disposedTitle = " "+ones.size()+" line disposed ";
		}
	    }
	    if(disposeds == null || disposeds.size() == 0){
		disposedTitle = " No records disposed yet";
	    }
	}
	return disposeds;
    }
		
}





































