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

public class ContractAction extends TopAction{

    static final long serialVersionUID = 1100L;	
    static Logger logger = LogManager.getLogger(ContractAction.class);
    Contract contract = null;
    List<Contract> contracts = null;
    List<Type> ratePlans = null;
    List<Type> vendors = null;
    List<Billing> billings = null;
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
	if(action.equals("Save")){
	    back = contract.doSave();
	    if(!back.equals("")){
		addActionError(back);
								
	    }
	    else{
		new NotificationScheduler(debug, contract, activeMail);
		addActionMessage("Added Successfully");
	    }
	}				
	else if(action.startsWith("Save")){
	    back = contract.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	}
	else{		
	    getContract();
	    if(!id.equals("")){
		back = contract.doSelect();
		if(!back.equals("")){
		    addActionError(back);
		}
	    }
	}
	return ret;
    }
    public Contract getContract(){ 
	if(contract == null){
	    contract = new Contract();
	    contract.setId(id);
	}		
	return contract;
    }
    public List<Type> getRatePlans(){
	if(ratePlans == null){
	    TypeList tl = new TypeList(debug, null,"rate_plans");
	    String back = tl.find();
	    if(back.equals("")){
		List<Type> ones = tl.getTypes();
		if(ones != null && ones.size() > 0){
		    ratePlans = ones;
		}
	    }
	}
	return ratePlans;
    }
    public List<Type> getVendors(){
	if(vendors == null){
	    TypeList tl = new TypeList(debug, null,"vendors");
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
    @Override
    public String getId(){
	if(id.equals("") && contract != null){
	    id = contract.getId();
	}
	return id;
    }
    public void setContract(Contract val){
	if(val != null){
	    contract = val;
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
	if(contracts == null){
	    ContractList tl = new ContractList(debug);
	    String back = tl.find();
	    if(back.equals("")){
		List<Contract> ones = tl.getContracts();
		if(ones != null && ones.size() > 0){
		    contracts = ones;
		}
	    }
	}
	return contracts;
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
    public boolean hasRatePlans(){
	getRatePlans();
	return ratePlans != null && ratePlans.size() > 0;
    }
    public boolean hasVendors(){
	getVendors();
	return vendors != null && vendors.size() > 0;
    }
    public boolean hasContracts(){
	getContracts();
	return contracts != null && contracts.size() > 0;
    }
    public boolean hasBillings(){
	getBillings();
	return billings != null && billings.size() > 0;
    }		
}





































