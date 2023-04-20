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

public class CircuitAction extends TopAction{

    static final long serialVersionUID = 1500L;	
    static Logger logger = LogManager.getLogger(CircuitAction.class);
    Circuit circuit = null;
    List<Circuit> circuits = null;
    List<Type> vendors = null;
    List<Type> addresses = null;
    List<Billing> billings = null;
    List<Department> departments = null;
    String circuitsTitle = "Current circuits";
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
	    back = circuit.doSave();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Added Successfully");
	    }
	}				
	else if(action.startsWith("Save")){
	    back = circuit.doUpdate();
	    if(!back.equals("")){
		addActionError(back);
	    }
	    else{
		addActionMessage("Updated Successfully");
	    }
	}
	else{		
	    getCircuit();
	    if(!id.equals("")){
		back = circuit.doSelect();
		if(!back.equals("")){
		    addActionError(back);
		}
	    }
	}
	return ret;
    }
    public Circuit getCircuit(){ 
	if(circuit == null){
	    circuit = new Circuit();
	    circuit.setId(id);
	}		
	return circuit;
    }

    @Override
    public String getId(){
	if(id.equals("") && circuit != null){
	    id = circuit.getId();
	}
	return id;
    }
				
    public void setCircuit(Circuit val){
	if(val != null){
	    circuit = val;
	}
    }

    public String getCircuitsTitle(){
	return circuitsTitle;
    }
    public void setAction2(String val){
	if(val != null && !val.equals(""))		
	    action = val;
    }
    public boolean hasCircuits(){
	getCircuits();
	return circuits != null && circuits.size() > 0;
    }
    public List<Circuit> getCircuits(){
	if(circuits == null){
	    CircuitList tl = new CircuitList(debug);
	    String back = tl.find();
	    if(back.equals("")){
		List<Circuit> ones = tl.getCircuits();
		if(ones != null && ones.size() > 0){
		    circuits = ones;
		}
	    }
	}
	return circuits;
    }
    public List<Type> getAddresses(){
	if(addresses == null){
	    TypeList tl = new TypeList(debug, null,"addresses");
	    String back = tl.find();
	    if(back.equals("")){
		List<Type> ones = tl.getTypes();
		if(ones != null && ones.size() > 0){
		    addresses = ones;
		}
	    }
	}
	return addresses;
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
	}
	return departments;
    }		
    public boolean hasAddresses(){
	getAddresses();
	return addresses != null && addresses.size() > 0;
    }
    public boolean hasBillings(){
	getBillings();
	return billings != null && billings.size() > 0;
    }		
    public boolean hasDepartments(){
	getDepartments();
	return departments != null && departments.size() > 0;
    }
}





































