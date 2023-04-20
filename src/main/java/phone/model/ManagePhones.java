package phone.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */

import java.sql.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Hashtable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.list.*;
import phone.utils.*;

public class ManagePhones extends CommonInc{

    static final long serialVersionUID = 500L;			
    static Logger logger = LogManager.getLogger(ManagePhones.class);		
    static EnvBean bean = null;
    List<LdapEmployee> ldapEmps = null;
    List<LdapEmployee> dbEmps = null;
    List<LdapEmployee> discardEmps = null;
    List<LdapEmployee> newEmps = null;
		
    public ManagePhones(){

    }
    public ManagePhones(boolean deb){
	super(deb);				
    }				
    public ManagePhones(boolean deb, EnvBean val){
	super(deb);
	setEnvBean(val);
    }
    public void setEnvBean(EnvBean val){
	if(val != null)
	    bean = val;
    }
    public List<LdapEmployee> getLdapEmps(){
	return ldapEmps;
    }
    public List<LdapEmployee> getDbEmps(){
	return dbEmps;
    }
    public List<LdapEmployee> getDiscardEmps(){
	return discardEmps;
    }
    public List<LdapEmployee> getNewEmps(){
	return newEmps;
    }
    public boolean hasLdapEmps(){
	return ldapEmps != null && ldapEmps.size() > 0;
    }
    public boolean hasDbEmps(){
	return dbEmps != null && dbEmps.size() > 0;
    }
    public boolean hasDiscardEmps(){
	return discardEmps != null && discardEmps.size() > 0;
    }
    public boolean hasNewEmps(){
	return newEmps != null && newEmps.size() > 0;
    }		
    /**
     * get the list of current employee and their phones from Ldap (directory)
     */
    String getEmpListFromLdap(){
	String back = "";
	EmpList empl = new EmpList(debug, bean);
	back = empl.findAll();
	if(back.equals("")){
	    ldapEmps = empl.getEmps();
	}
	return back;
    }
    String getEmplistFromDb(){
	String back = "";
	LdapEmployeeList lel = new LdapEmployeeList(debug);
	back = lel.find();
	if(back.equals("")){
	    dbEmps = lel.getEmployees();
	}
	return back;
    }

    public String doProcess(){
	String back = "";
	if(bean == null || bean.isNotReady()){
	    back =" bean is null or component not set";
	    logger.error(back);
	    return back;
	}
	back = getEmpListFromLdap();
	if(!back.equals("")){
	    System.err.println(back);
	}
	back = getEmplistFromDb();
	//
	// find all emps who are not in Ldap any more to be removed
	//
	discardEmps = new ArrayList<>();
	newEmps = new ArrayList<>();
	for(LdapEmployee dbOne:dbEmps){
	    if(!ldapEmps.contains(dbOne)){
		discardEmps.add(dbOne);
		Dispose one = new Dispose(debug, dbOne.getId(), dbOne.getFullname());
		back += one.doSave();
	    }
	}
	//
	// find all new ones in ldap to be added to DB
	//
	for(LdapEmployee ldapOne:ldapEmps){
	    if(ldapOne.hasPhone_number()){
		if(!dbEmps.contains(ldapOne)){
		    newEmps.add(ldapOne);
		}
	    }
	}
	//	System.err.println(" new emps "+newEmps.size());				
	if(newEmps != null && newEmps.size() > 0){
	    for(LdapEmployee one:newEmps){
		back += one.addNewEmployee();
	    }
	}
	String outcome = back.equals("")?"Success":"Failure";
	ScanLog scan = new ScanLog(debug, null, null, outcome, back);
	back = scan.doSave();
	return back;
    }

}
