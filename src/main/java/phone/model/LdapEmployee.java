package phone.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.sql.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.list.*;
import phone.utils.*;

public class LdapEmployee extends Employee implements java.io.Serializable{

    String username="", email="", phone_number="", ext_number="",
	phone_number2 ="", pager="", division="",
	dept="", busCat="", jobTitle="",
	ext_number2 = "", mail_box="";
    static final long serialVersionUID = 3100L;
    static Logger logger = LogManager.getLogger(LdapEmployee.class);
    //
    public LdapEmployee(){
	super();
    }
    public LdapEmployee(boolean deb){
	//
	// initialize
	//
	super(deb);
    }
    public LdapEmployee(boolean deb,
			String val,
			String val2,
			String val3,
			String val4,
			String val5,
			String val6,
			String val7
			){
	super(deb, val, val2);
	setPhone_number(val3);
	setExt_number(val4);
	setMail_box(val5);
	setDept(val6);
	setDivision(val7);

    }

    public String getUsername(){
	return username;
    }		
    public String getPhone_number(){ // otherPhone
	return phone_number;
    }
		
    public String getPhone_number2(){ // otherPhone
	return phone_number2;
    }
    public String getExt_number(){ // otherPhone
	return ext_number;
    }
    public String getPager(){ 
	return pager;
    }		
    public String getDept(){
	return dept;
    }

    public String getMail_box(){
	return mail_box;
    }

    public String getBusCat(){
	return busCat;
    }
    public String getDivision(){
	return division;
    }		
    public String getJobTitle(){
	return jobTitle;
    }
    public String getEmail(){
	return email;
    }
    public boolean hasPhone_number(){
	return !phone_number.equals("");
    }
    public void setPhone_number2(String val){
	if(val != null){
	    if(phone_number.equals("")){
		setPhone_number(val);
	    }
	    else{
		phone_number2 = Helper.getPhoneNumberCleaned(val);
		if(phone_number2.equals(phone_number)){
		    phone_number2 = ""; // we ignore
		}
		if(Helper.isExtNumber(val)){
		    ext_number2 = Helper.getExtNumberCleaned(val);
		    if(!ext_number2.equals("")){
			if(ext_number.equals("")){
			    if(phone_number2.equals("")){
				ext_number = ext_number2;
				ext_number2 = ""; 
			    }
			}
		    }
		}
	    }
	}
    }

    public void setPager(String val){
	if(val != null)
	    pager = val;
    }
    public void setUsername(String val){
	if(val != null)
	    username = val;
    }
    public void setEmail(String val){
	if(val != null)
	    email = val;
    }		
    public void setDept(String val){
	if(val != null){
	    if(val.equals("Email")) return; // ignore
	    if(val.equals("T&D") ||
	       val.equals("Purchasing") ||
	       val.equals("Engineering") ||
	       val.equals("Administration") ||
	       val.equals("Communication") ||							 
	       val.equals("Customer Services") ||
	       val.equals("Dillman")){
		dept="Utilities";
		setDivision(val);
	    }
	    else if(val.startsWith("Parking")){
		dept="Police";
		setDivision(val);
	    }
	    else if(val.startsWith("Uniformed")){
		dept="Police";
		setDivision(val);
	    }						
	    else if(dept.equals("")) // if not set before
		dept = val; 
	    else if(!val.equals("Public Works")){
		setDivision(val);
	    }
	}
    }
    // we avoid Fire as division for now
    public void setDivision(String val){
	if(val != null && !val.equals("Fire") && !val.endsWith("Transit")){
	    division = val;
	}
    }
    public void setPhone_number(String val){
	if(val != null){
	    phone_number = Helper.getPhoneNumberCleaned(val);
	    if(Helper.isExtNumber(val))
		ext_number = Helper.getExtNumberCleaned(val);
	}
    }
    public void setExt_number(String val){
	if(val != null)
	    ext_number = val;
    }
    public void setMail_box(String val){
	if(val != null)
	    mail_box = val;
    }				
    public void setBusCat(String val){
	if(val != null)
	    busCat = val;
	if(!busCat.equals("City Hall")){
	    setDept(val);
	}
    }		
    public void setJobTitle(String val){
	if(val != null)
	    jobTitle = val;
    }
    //
    public boolean userExists(){
	return !fullname.equals("");
    }
    //
    public String toString(){
	return getFullnameClean();
    }
    public String getFullnameClean(){
	String ret = getFullname();
	if(fullname.indexOf(",") > -1){
	    ret = fullname.replace(",","");
	}
	return ret;
    }
		
    @Override
    public boolean equals(Object obj){
	if(obj instanceof LdapEmployee){
	    LdapEmployee one =(LdapEmployee)obj;
	    return getFullnameClean().equals(one.getFullnameClean());
	}
	return false;				
    }
    @Override
    public int hashCode(){
	int seed = 43;
	if(!getFullname().equals("")){
	    seed += getFullname().hashCode();
	}
	return seed;
    }		
    public String addNewEmployee(){
	String msg = "", back = "";
	//
	// check if the phone number already exists
	//
	Phone phone = null, phone2 = null;
	PhoneExt phoneExt = null, phoneExt2 = null;
	List<PhoneExt> phoneExts = null;
	Department department = null;
	Division division2 = null;
	PLine pLine = null;
	// PLinePhone pLinePhone = null;
	if(!phone_number2.equals("")){
	    // System.err.println(" two phones "+phone_number+" "+phone_number2);
	}
	if(!fullname.equals("")){
	    pLine = new PLine(debug, null, fullname);
	    back = pLine.doSave();
	    if(back.equals("")){
		msg += back;
	    }
	}
	if(!dept.equals("")){
	    DepartmentList dl = new DepartmentList(debug);
	    dl.setName(dept);
	    back = dl.find();
	    if(back.equals("")){
		List<Department> ones = dl.getDepartments();
		if(ones != null && ones.size() > 0){
		    department = ones.get(0);
		}
	    }
	    else {
		msg += back;
	    }
	    if(department == null){
		department = new Department(debug, null, dept, dept, null);
		back = department.doSave();
		if(!back.equals("")){
		    msg += back;
		}
	    }
	}
	if(!division.equals("")){
	    DivisionList dl = new DivisionList(debug);
	    dl.setName(division);
	    dl.setDepartment_id(department.getId());
	    back = dl.find();
	    if(back.equals("")){
		List<Division> ones = dl.getDivisions();
		if(ones != null && ones.size() > 0){
		    division2 = ones.get(0);
		}
	    }
	    else {
		msg += back;
	    }
	    if(division2 == null){
		division2 = new Division(debug, null, division, division, department.getId(), null);
		back = division2.doSave();
	    }
	}
	if(!phone_number.equals("")){
	    PhoneList pl = new PhoneList(debug);
	    pl.setPhoneNumber(phone_number);
	    back = pl.find();
	    if(back.equals("")){
		List<Phone> ones = pl.getPhones();
		if(ones != null && ones.size() > 0){
		    phone = ones.get(0);
		}
	    }
	    else{
		msg += back;
	    }
	    if(phone == null){
		// create new phone record
		phone = new Phone(debug, null, phone_number);
		phone.setType("Phone");
		if(department != null){
		    phone.setDepartment_id(department.getId());
		}
		if(division2 != null){
		    phone.setDivision_id(division2.getId());
		}
		back = phone.doSave();
	    }
	}
	if(!phone_number2.equals("")){
	    PhoneList pl = new PhoneList(debug);
	    pl.setPhoneNumber(phone_number2);
	    back = pl.find();
	    if(back.equals("")){
		List<Phone> ones = pl.getPhones();
		if(ones != null && ones.size() > 0){
		    phone2 = ones.get(0);
		}
	    }
	    else{
		msg += back;
	    }
	    if(phone2 == null){
		// create new phone record
		phone2 = new Phone(debug, null, phone_number2);
		phone2.setType("Phone");
		if(department != null){
		    phone2.setDepartment_id(department.getId());
		}
		if(division2 != null){
		    phone2.setDivision_id(division2.getId());
		}
		back = phone2.doSave();
	    }
	}				
	if(!ext_number.equals("")){
	    PhoneExtList pel = new PhoneExtList(debug);
	    pel.setExt_number(ext_number);
	    back = pel.find();
	    if(back.equals("")){
		List<PhoneExt> ones = pel.getPhoneExts();
		if(ones != null && ones.size() > 0){
		    phoneExts = ones;
		    phoneExt = ones.get(0);
		    // System.err.println("ext: "+ext_number+" found");
		}
	    }
	    else{
		msg += back;
	    }
	    if(phoneExt == null){
		// we create a new one
		// System.err.println("adding phone ext: "+ext_number);
		phoneExt = new PhoneExt(debug, null, ext_number, ext_number, phone.getId());
		back = phoneExt.doSave();
		if(!back.equals("")){
		    msg += back;
		}								
	    }
	}
	if(!ext_number2.equals("")){
	    PhoneExtList pel = new PhoneExtList(debug);
	    pel.setExt_number(ext_number2);
	    back = pel.find();
	    if(back.equals("")){
		List<PhoneExt> ones = pel.getPhoneExts();
		if(ones != null && ones.size() > 0){
		    phoneExt2 = ones.get(0);
		}
	    }
	    else{
		msg += back;
	    }
	    if(phoneExt2 == null){
		// we create a new one
		// System.err.println("adding 2 phone ext: "+ext_number2);
		phoneExt2 = new PhoneExt(debug, null, ext_number2, ext_number2, phone2.getId());
		back = phoneExt2.doSave();
		if(!back.equals("")){
		    msg += back;
		}								
	    }
	}				
	//
	// check if PlinePhone exists
	//
	if(pLine != null){ //we have new employee
	    if(phone != null){
		// System.err.println("adding phone line ");								
		PLinePhone pLinePhone = new PLinePhone(debug);
		pLinePhone.setPhone_id(phone.getId());
		pLinePhone.setLine_id(pLine.getId());
		if(phoneExt != null){
		    pLinePhone.setExt_id(phoneExt.getId());
		}
		back = pLinePhone.doSave();
		if(!back.equals("")){
		    msg += back;
		}
	    }
	    if(phone2 != null){
		// System.err.println(" addomg 2nd phone");
		PLinePhone pLinePhone = new PLinePhone(debug);
		pLinePhone.setPhone_id(phone2.getId());
		pLinePhone.setLine_id(pLine.getId());
		if(phoneExt2 != null){
		    pLinePhone.setExt_id(phoneExt2.getId());
		}
		back = pLinePhone.doSave();
		if(!back.equals("")){
		    msg += back;
		}
	    }						
	}
	return msg;
    }
		
}
