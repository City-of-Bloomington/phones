package phone.model;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.*;
import javax.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.list.*;
import phone.utils.*;

public class Report{
	
    static Logger logger = LogManager.getLogger(Report.class);
    static final long serialVersionUID = 51L;
    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
    boolean type=false, signal_type=false, addr=false, bill=false, dept=false,
	status=false, vendor=false;
    String reportType="stats", // stats, phones
	year="",date_from="", date_to="";
    String title = "", billing_id="", department_id="";
    boolean debug = false, needTotal = false;
    List<ReportRow> rows = new ArrayList<>();
    List<List<ReportRow>> all = new ArrayList<>();
    ReportRow columnTitle = null;
    int totalIndex = 2; // DB index for row with 2 items
    Billing billing = null;
    Department department = null;
    public Report(){

    }
    public Report(boolean deb){
	debug = deb;
    }
    public void setType(boolean val){
	type = val;
    }
    public void setSignal_type(boolean val){
	signal_type = val;
    }
    public void setAddr(boolean val){
	addr = val;
    }
    public void setBill(boolean val){
	bill = val;
    }
    public void setVendor(boolean val){
	vendor = val;
    }		
    public void setDept(boolean val){
	dept = val;
    }
    public void setStatus(boolean val){
	status = val;
    }
    public void setDepartment_id(String val){
	if(val != null && !val.equals("-1"))
	    department_id = val;
    }
    public void setBilling_id(String val){
	if(val != null && !val.equals("-1"))
	    billing_id = val;
    }
    public void setYear(String val){
	if(val != null && !val.equals("-1"))
	    year = val;
    }
    public void setDate_from(String val){
	if(val != null)
	    date_from = val;
    }
    public void setDate_to(String val){
	if(val != null)
	    date_to = val;
    }
    public void setReportType(String val){
	if(val != null)
	    reportType = val;
    }				
    //
    // getters
    //
    public boolean getSignal_type(){
	return signal_type;
    }
    public boolean getType(){
	return type;
    }		
    public boolean getDept(){
	return dept ;
    }	
    public boolean getAddr(){
	return addr ;
    }
    public boolean getBill(){
	return bill ;
    }
    public boolean getStatus(){
	return status ;
    }
    public boolean getVendor(){
	return vendor ;
    }		
    public String getTitle(){
	return title;
    }
    public String getDepartment_id(){
	if(department_id.equals(""))
	    return "-1";
	return department_id;
    }
    public String getBilling_id(){
	if(billing_id.equals(""))
	    return "-1";
	return billing_id;
    }
    public String getYear(){
	if(year.equals(""))
	    return "-1";
	return year;
    }
    public String getDate_from(){
	return date_from;
    }
    public String getDate_to(){
	return date_to;
    }
    public String getReportType(){
	return reportType;
    }
    public List<ReportRow> getRows(){
	return rows;
    }
    public List<List<ReportRow>> getAll(){
	return all;
    }
    public ReportRow getColumnTitle(){
	return columnTitle;
    }
    public Billing getBilling(){
	if(!billing_id.equals("") && billing == null){ 
	    Billing one = new Billing(debug, billing_id);
	    String back = one.doSelect();
	    if(back.equals("")){
		billing = one;
	    }
	}
	return billing;
    }
    public Department getDepartment(){
	if(!department_id.equals("") && department == null){ 
	    Department one = new Department(debug, department_id);
	    String back = one.doSelect();
	    if(back.equals("")){
		department = one;
	    }
	}
	return department;
    }
		
    public String find(){
	if(reportType.equals("stats"))
	    return findStats();
	else
	    return findPhoneRecords();
    }
    /*
      select d.name department,p.phoneNumber phone,pl.name name from phones p         left join departments d on p.department_id=d.id                                 left join pline_phones pp on pp.phone_id=p.id                                   left join plines pl on pl.id = pp.line_id                                       order by department, phone limit 50;

    */
    public String findPhoneRecords(){
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String msg = "";
	String which_date = "";
	String qq = "", qw="", qo="";
	//
	// qw is shared in all sqls
	//
	if(!department_id.equals("")){
	    if(!qw.equals("")) qw += " and ";
	    qw += " p.department_id=? ";
	}
	qq = " select d.name department,p.phoneNumber phone,pl.name name from phones p "+
	    " left join departments d on p.department_id=d.id "+
	    " left join pline_phones pp on pp.phone_id=p.id  "+
	    " left join plines pl on pl.id =pp.line_id ";
	qo = " order by department, phone, name ";
	if(!qw.equals("")){
	    qq += " where "+qw;
	}
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect ";
	    return msg;
	}				
	qq += qo;
	all = new ArrayList<>();
	try{
	    pstmt = con.prepareStatement(qq);
	    int jj=1;
	    if(!department_id.equals("")){
		pstmt.setString(jj++, department_id);
	    }				
	    rs = pstmt.executeQuery();						
	    while(rs.next()){
		if(rows == null)
		    rows = new ArrayList<>();										
		String str = rs.getString(1);
		if(str == null || str.equals("")) str = "Unknown";
		String str2 = rs.getString(2);
		String str3 = rs.getString(3);
		if(str3 == null || str3.equals("")) str3 = "Unknown";
		ReportRow one = new ReportRow(debug, 3);
		one.setRow(str, str2, str3);
		rows.add(one);
	    }
	}catch(Exception e){
	    msg += e+":"+qq;
	    logger.error(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}		
	return msg;						
    }
    public String findStats(){
		
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String msg = "";
	String which_date = "";
	String qq = "", qw="", qg="";
	//
	// qw is shared in all sqls
	//
	if(!billing_id.equals("")){
	    qw += " p.billing_id=? ";
	}
	if(!department_id.equals("")){
	    if(!qw.equals("")) qw += " and ";
	    qw += " p.department_id=? ";
	}								
	all = new ArrayList<>();
	try{
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect ";
		return msg;
	    }
	    if(signal_type){
		rows = new ArrayList<>();
		ReportRow one = new ReportRow(debug);
		// totalIndex = 2;
		// columnTitle.setSize(2);
		one.setTitle("Phones Classified by Signal Types");
		one.setRow("Signal Type","Count");
		rows.add(one);
		qq = " select p.signal_type name,count(*) cnt from phones p ";
		qg = " group by name ";								
		if(!qw.equals("")){
		    qq += " where "+qw;
		}
		qq += qg;
		if(debug)
		    logger.debug(qq);
		pstmt = con.prepareStatement(qq);
								
		int jj=1;
		if(!billing_id.equals("")){
		    pstmt.setString(jj++, billing_id);
		}
		if(!department_id.equals("")){
		    pstmt.setString(jj++, department_id);
		}				
		rs = pstmt.executeQuery();
		int total = 0;
		while(rs.next()){
		    String str = rs.getString(1);
		    if(str == null){
			str = "Unspecified";
		    }
		    one = new ReportRow(debug, 2);
		    one.setRow(str,
			       rs.getString(2));
		    rows.add(one);
		    total += rs.getInt(2);
		}
		one = new ReportRow(debug, 2);
		one.setRow("Total",""+total);
		rows.add(one);
		all.add(rows);
	    }
	    if(type){
		rows = new ArrayList<>();
		ReportRow one = new ReportRow(debug);
		one.setTitle("Phones Classified by Use Types");
		one.setRow("Use Type","Count");
		rows.add(one);
		qq = " select p.type name,count(*) cnt from phones p ";
		qg = " group by name ";								
		if(!qw.equals("")){
		    qq += " where "+qw;
		}
		qq += qg;
		if(debug)
		    logger.debug(qq);
		pstmt = con.prepareStatement(qq);
		int jj=1;
		if(!billing_id.equals("")){
		    pstmt.setString(jj++, billing_id);
		}
		if(!department_id.equals("")){
		    pstmt.setString(jj++, department_id);
		}												
		rs = pstmt.executeQuery();
		int total = 0;
		while(rs.next()){
		    String str = rs.getString(1);
		    if(str == null){
			str = "Unspecified";
		    }
		    one = new ReportRow(debug, 2);
		    one.setRow(str,
			       rs.getString(2));
		    rows.add(one);
		    total += rs.getInt(2);
		}
		one = new ReportRow(debug, 2);
		one.setRow("Total",""+total);
		rows.add(one);
		all.add(rows);
	    }
	    if(dept){
		rows = new ArrayList<>();
		ReportRow one = new ReportRow(debug);
		// columnTitle = new ReportRow(debug);			
		totalIndex = 2;
		// columnTitle.setSize(2);
		one.setTitle("Phones Classified by Departments");
		one.setRow("Department","Count");
		rows.add(one);
		qq = " select d.name name,count(*) cnt from phones p left join departments d on d.id=p.department_id  ";
		qg = " group by name ";								
		if(!qw.equals("")){
		    qq += " where "+qw;
		}
		qq += qg;
		if(debug)
		    logger.debug(qq);
		pstmt = con.prepareStatement(qq);
		int jj=1;
		if(!billing_id.equals("")){
		    pstmt.setString(jj++, billing_id);
		}
		if(!department_id.equals("")){
		    pstmt.setString(jj++, department_id);
		}												
		rs = pstmt.executeQuery();
		int total = 0;
		while(rs.next()){
		    String str = rs.getString(1);
		    if(str == null){
			str = "Unspecified";
		    }
		    one = new ReportRow(debug, 2);
		    one.setRow(str,
			       rs.getString(2));
		    rows.add(one);
		    total += rs.getInt(2);
		}
		one = new ReportRow(debug, 2);
		one.setRow("Total",""+total);
		rows.add(one);
		all.add(rows);
	    }
	    if(addr){
		rows = new ArrayList<>();								
		ReportRow one = new ReportRow(debug);			
		totalIndex = 2;
		// columnTitle.setSize(2);
		one.setTitle("Phones Classified by Address");
		one.setRow("Address","Count");
		rows.add(one);
		qq = " select a.name name,count(*) cnt from phones p left join addresses a on a.id=p.address_id ";
		qg = " group by name ";								
		if(!qw.equals("")){
		    qq += " where "+qw;
		}
		qq += qg;
		if(debug)
		    logger.debug(qq);
		pstmt = con.prepareStatement(qq);
		int jj=1;
		if(!billing_id.equals("")){
		    pstmt.setString(jj++, billing_id);
		}
		if(!department_id.equals("")){
		    pstmt.setString(jj++, department_id);
		}																				
		rs = pstmt.executeQuery();
		int total = 0;
		while(rs.next()){
		    String str = rs.getString(1);
		    if(str == null){
			str = "Unspecified";
		    }
		    one = new ReportRow(debug, 2);
		    one.setRow(str,
			       rs.getString(2));
		    rows.add(one);
		    total += rs.getInt(2);
		}
		one = new ReportRow(debug, 2);
		one.setRow("Total",""+total);
		rows.add(one);
		all.add(rows);
	    }
	    if(bill){
		rows = new ArrayList<>();								
		ReportRow one = new ReportRow(debug);			
		totalIndex = 2;
		// columnTitle.setSize(2);
		one.setTitle("Phones Classified by Billings");
		one.setRow("Billing","Count");
		rows.add(one);
		qq = " select b.name name,count(*) cnt from phones p left join billings b on b.id=p.billing_id ";
		qg = " group by name ";								
		if(!qw.equals("")){
		    qq += " where "+qw;
		}
		qq += qg;
		if(debug)
		    logger.debug(qq);
		pstmt = con.prepareStatement(qq);
		int jj=1;
		if(!billing_id.equals("")){
		    pstmt.setString(jj++, billing_id);
		}
		if(!department_id.equals("")){
		    pstmt.setString(jj++, department_id);
		}																				
		rs = pstmt.executeQuery();
		int total = 0;
		while(rs.next()){
		    String str = rs.getString(1);
		    if(str == null){
			str = "Unspecified";
		    }
		    one = new ReportRow(debug, 2);
		    one.setRow(str,
			       rs.getString(2));
		    rows.add(one);
		    total += rs.getInt(2);
		}
		one = new ReportRow(debug, 2);
		one.setRow("Total",""+total);
		rows.add(one);
		all.add(rows);
	    }
	    if(vendor){
		rows = new ArrayList<>();								
		ReportRow one = new ReportRow(debug);			
		totalIndex = 2;
		// columnTitle.setSize(2);
		one.setTitle("Phones Classified by Vendor");
		one.setRow("Vendor","Count");
		rows.add(one);
		qq = " select v.name name,count(*) cnt from phones p left join billings b on b.id=p.billing_id left join vendors v on v.id=b.vendor_id ";
		qg = " group by name ";								
		if(!qw.equals("")){
		    qq += " where "+qw;
		}
		qq += qg;
		if(debug)
		    logger.debug(qq);
		pstmt = con.prepareStatement(qq);
		int jj=1;
		if(!billing_id.equals("")){
		    pstmt.setString(jj++, billing_id);
		}
		if(!department_id.equals("")){
		    pstmt.setString(jj++, department_id);
		}																				
		rs = pstmt.executeQuery();
		int total = 0;
		while(rs.next()){
		    String str = rs.getString(1);
		    if(str == null){
			str = "Unspecified";
		    }
		    one = new ReportRow(debug, 2);
		    one.setRow(str,
			       rs.getString(2));
		    rows.add(one);
		    total += rs.getInt(2);
		}
		one = new ReportRow(debug, 2);
		one.setRow("Total",""+total);
		rows.add(one);
		all.add(rows);
	    }												
	    if(status){
		rows = new ArrayList<>();
		ReportRow one = new ReportRow(debug);			
		totalIndex = 2;
		// columnTitle.setSize(2);
		one.setTitle("Phones Classified by Active Status");
		one.setRow("Status","Count");
		rows.add(one);
		qq = " select 'Active', count(*) from phones p where p.id in (select phone_id from pline_phones) ";
		if(!qw.equals("")){
		    qq += " and "+qw;
		}
		qq += "union select 'Inactive', count(*) from phones p2 where p2.id not in(select phone_id from pline_phones) ";
		if(!qw.equals("")){
		    qq += " and "+qw.replace("p.","p2.");
		}								
		if(debug)
		    logger.debug(qq);
		pstmt = con.prepareStatement(qq);
		int jj=1;
		if(!billing_id.equals("")){
		    pstmt.setString(jj++, billing_id);
		}
		if(!department_id.equals("")){
		    pstmt.setString(jj++, department_id);
		}
		if(!billing_id.equals("")){
		    pstmt.setString(jj++, billing_id);
		}
		if(!department_id.equals("")){
		    pstmt.setString(jj++, department_id);
		}
		rs = pstmt.executeQuery();
		int total = 0;
		while(rs.next()){
		    String str = rs.getString(1);
		    if(str == null){
			str = "Unspecified";
		    }
		    one = new ReportRow(debug, 2);
		    one.setRow(str,
			       rs.getString(2));
		    rows.add(one);
		    total += rs.getInt(2);
		}
		one = new ReportRow(debug, 2);
		one.setRow("Total",""+total);
		rows.add(one);
		all.add(rows);
	    }												
	}catch(Exception e){
	    msg += e+":"+qq;
	    logger.error(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}		
	return msg;
    }		
}






















































