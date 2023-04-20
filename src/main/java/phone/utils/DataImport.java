package phone.utils;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
 */

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.io.Reader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.list.*;
import phone.model.*;

public class DataImport{

    boolean debug = true;
    String server_path=""; // "/srv/data/phones/docs/";
    static Logger logger = LogManager.getLogger(DataImport.class);
    static final long serialVersionUID = 220L;			
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    //
    Map<String, String> planMap = new HashMap<>(); // <rate_plan_name,plan id>
    Map<String, String> contractMap = new HashMap<>(); //<rate_plan_name, contract_id> 		
    Map<String, String> billMap = new HashMap<>();//<found_num, biil id>
    public DataImport(){

    }
    public String doImport(String fileName, String dept_id){
	//
	prepareMaps();
	Connection con = null;
	PreparedStatement pstmt = null, pstmt2=null, pstmt3=null,
	    pstmt4=null,pstmt0=null;
	ResultSet rs = null;
	String qq0 = "select LAST_INSERT_ID() ";
	String qq = "insert into cell_devices "+
	    " (id,"+
	    " make,"+
	    " model,"+
	    " wireless_num,"+
	    " device_imei,"+
						
	    " sim_num,"+
	    " service_type,"+
	    " device_effective_date,"+
	    " user_effective_date,"+
	    " user_name,"+
						
	    " user_type,"+
	    " department_id,"+
	    " division_id,"+
	    " billing_id,"+
	    " contract_start_date,"+
						
	    " contract_end_date) "+
						
	    " values("+
	    " 0,?,?,?,?, "+
	    " ?,?,?,?,?, "+
	    " ?,?,?,?,?, ?)";
	String qq2 = "insert into rate_plans (id,name) values(0,?)";

	String qq3 = "insert into billings (id,name,foundation_account_num,department_id,division_id,account_num,vendor_id) values(0,?,?,?,?, ?,?)";
	String qq4 = "insert into contracts (id,name,bill_id,start_date,end_date,notification_date,rate_plan_id) values(0,?,?,?,?, ?,?)";				
	String its_dept="1", vendor_id="1"; //AT&T
	/*
	  clean up for import
	  delete from cell_devices;delete from others;
	  //
	  // this is the excel header
	  //
	  Foundation Account Number, 1 
	  Billing Account Number, 2
	  Billing Account Name, 3
	  Wireless Number, 4
	  Wireless User Status, 5
	  Status Effective Date, 6
	  "Wireless User Full Name", 6
	  "Phone or Device Make", 7
	  "Phone or Device Model", 8
	  "Phone or Device ID (IMEI)", 9
	  Wireless User Activation Date, 10
	  Contract Start Date,       11
	  Contract End Date,         12
	  Phone or Device Effective Date, 13
	  Service Type,         14
	  Smart Chip (SIM) Number, 15
	  Rate Plan Name, 16  // need plan ID
				
	  "User Defined Label 1","User Defined Label 2","User Defined Label 3","User Defined Label 4",OSVersion,IMEISV,TechnologyType,,,,,,,,,,,,,,,,,,


	*/
	String back = "";
	Reader in = null;
	int jj=1; 
	try{
	    con = Helper.getConnection();
	    if(con == null){
		back = "Could not connect to DB";
		return back;
	    }
	    pstmt0 = con.prepareStatement(qq0);						
	    pstmt = con.prepareStatement(qq);
	    pstmt2 = con.prepareStatement(qq2);
	    pstmt3 = con.prepareStatement(qq3);
	    pstmt4 = con.prepareStatement(qq4);
	    fileName = server_path+fileName;
	    System.err.println("file "+fileName);
	    in = new FileReader(fileName);
	    //
	    // if you want to define the header 
	    // Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader("ID", "Last Name", "First Name").parse(in);
	    //
	    // header auto detection
	    // Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
	    Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
	    //
	    String its_dept_id="8", hand_dept_id="7", div_id="";
	    String contract_init="Wireless";// plus username
	    String contract_name="",bill_id="", plan_id="", contract_id="";
	    // 
	    for (CSVRecord record : records) {
		String id = ""+jj;
		// first column is emtpy
		if(jj == 1){
		    jj++;
		    continue;
		}
		String user_type="Employee";
		String found_num = record.get(1);// ("Foundation account num"); // 1
		if(found_num.equals("")) break; // end of rows
		String bill_acc_num = record.get(2);// ("bill account num"); 
		String bill_name = record.get(3);// ("bill account name"); 
		String wireless_num = record.get(4);
								
		String status = record.get(5);// 
		String status_date = record.get(6);// ("Date Signed"); 
		String user_name = record.get(7);// ("Date Recorded");
		contract_name = contract_init+" "+user_name;
		String make = record.get(8);
		String model = record.get(9);								
										
		String imei_num = record.get(10);
		String user_effective_date = record.get(11);
		if(user_effective_date.length() < 10)
		    user_effective_date = "";
		String contract_start_date = record.get(12);
		String contract_end_date = record.get(13);
		if(contract_start_date.length() < 10) contract_start_date = "";
		if(contract_end_date.length() < 10) contract_end_date = "";
		String device_effective_date = record.get(14);
		if(device_effective_date.length() < 10)
		    device_effective_date = "";
		String service_type = record.get(15);
		String sim_num = record.get(16);
								
		String rate_plan_name = record.get(17);
		if(dept_id.equals("")){
		    if(!bill_name.equals("") && bill_name.endsWith("HAND")){
			dept_id = hand_dept_id;
		    }
		    else{
			dept_id = its_dept_id;
		    }
		}
		if(user_name.indexOf("CITY") > -1 ||
		   user_name.indexOf("GARAGE")> -1 ||
		   user_name.indexOf("TEAM") > -1 ||
		   user_name.indexOf("MDT") > -1 ||
		   user_name.indexOf("BPD") > -1 ||
		   user_name.indexOf("COVERT") > -1 ||									 
		   user_name.indexOf("POLICE") > -1 ||
		   user_name.indexOf("STATION") > -1 ||
		   user_name.indexOf("CALL") > -1 ||
		   user_name.indexOf("ANIMAL") > -1){
		    user_type = "Other";
		}
		//
		// String recorder_notes = recorder_id;
		System.err.println(" found #, bill #, name: "+found_num+", "+bill_acc_num+", "+bill_name);
		System.err.println(" rate plan, user, num: "+rate_plan_name+", "+user_name+", "+wireless_num);
		System.err.println(" contract start, end: "+contract_start_date+", "+contract_end_date);								
		/*
		  System.err.println(" wireless num: "+wireless_num);
		  System.err.println(" status, status_date: "+status+", "+status_date);
		  System.err.println(" user: "+user_name);
		  System.err.println(" make, model: "+make+", "+model);
		  System.err.println(" imei, sim: "+imei_num+", "+sim_num);
		  System.err.println(" user date: "+user_effective_date);

		  System.err.println(" device eff date: "+device_effective_date);
		  System.err.println(" service type: "+service_type);

		*/
		/*
		  back = waiver.doSaveForImport(pstmt);
		  if(!back.equals("")){
		  break;
		  }
		*/

		if(billMap.containsKey(bill_acc_num)){
		    bill_id = billMap.get(bill_acc_num);
		    System.err.println(" found bill id "+bill_id);
		}
		else{
		    Billing bill = new Billing(debug, bill_name, found_num, dept_id,div_id, bill_acc_num,vendor_id);
		    bill.doSaveForImport(pstmt3);
		    bill_id = getId(pstmt0, rs);
		    bill.setId(bill_id);
		    billMap.put(bill_acc_num, bill_id);
		}
		CellDevice device = new CellDevice(debug, make, model,wireless_num, imei_num, sim_num, service_type, device_effective_date, user_effective_date, user_name, user_type, dept_id, div_id, bill_id,contract_start_date, contract_end_date);
		device.doSaveForImport(pstmt);
								
		jj++;								
		// if(jj > 25) break; 
	    }
	}
	catch(Exception ex){
	    System.err.println(ex);
	    back += ex;
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);									
	}
	return back;
    }
    String getId(PreparedStatement stmt, ResultSet rs){
	String id = "";
	try{
	    rs = stmt.executeQuery();
	    if(rs.next())
		id = rs.getString(1);
	}catch(Exception ex){
	    System.err.println(ex);
	}
	return id;
    }
    void prepareMaps(){
	TypeList pll = new TypeList(debug, null, "rate_plans");
	String back = pll.find();
	if(back.equals("")){
	    List<Type> types = pll.getTypes();
	    for(Type one:types){
		planMap.put(one.getName(), one.getId());
	    }
	}
	BillingList bl = new BillingList(debug);
	back = bl.find();
	if(back.equals("")){
	    List<Billing> list = bl.getBillings();
	    if(list != null && list.size() > 0){
		for(Billing one:list){
		    String str = one.getAccount_num();
		    if(str != null && !str.equals("")){
			billMap.put(str, one.getId());
		    }
		}
	    }
	}
    }

}
