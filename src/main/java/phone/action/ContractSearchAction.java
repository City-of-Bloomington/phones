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

public class ContractSearchAction extends TopAction{

    static final long serialVersionUID = 1900L;	
    static Logger logger = LogManager.getLogger(ContractSearchAction.class);
    List<Billing> billings = null;
    List<Type> vendors = null;
    List<Contract> contracts = null;
    ContractList contralst = null;
    String contractsTitle = "Current contracts";
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
	    contralst.setNoLimit();
	    back = contralst.find();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		List<Contract> ones = contralst.getContracts();
		if(ones == null || ones.size() == 0){
		    addActionMessage("No match found");
		}
		else{
		    contracts = ones;
		    contractsTitle="Found "+ones.size()+" records";
		    addActionMessage("Found "+ones.size()+" records");
		}
	    }
	}				
	else{		
	    getContralst();
	}
	return ret;
    }
    public ContractList getContralst(){ 
	if(contralst == null){
	    contralst = new ContractList(debug);
	}		
	return contralst;
    }

				
    public void setContralst(ContractList val){
	if(val != null){
	    contralst = val;
	}
    }
    public String getContractsTitle(){
	return contractsTitle;
    }
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public List<Contract> getContracts(){
	if(contracts == null && contralst == null){
	    getContralst();
	    String back = contralst.find();
	    if(back.equals("")){
		List<Contract> ones = contralst.getContracts();
		if(ones != null && ones.size() > 0){
		    contracts = ones;
		}
	    }
	}
	return contracts;
    }
    public boolean hasContracts(){
	getContracts();
	return contracts != null && contracts.size() > 0;
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
    public List<Type> getVendors(){
	if(vendors == null){
	    TypeList tl = new TypeList(debug, null, "vendors");
	    String back = tl.find();
	    if(back.equals("")){
		List<Type> ones = tl.getTypes();
		if(ones != null && ones.size() > 0){
		    vendors = ones;
		}
	    }
	}
	return vendors;
    }				
}





































