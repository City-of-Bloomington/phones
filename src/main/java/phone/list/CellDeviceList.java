package phone.list;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.io.*;
import javax.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.model.*;
import phone.utils.*;

public class CellDeviceList extends CommonInc{

    static Logger logger = LogManager.getLogger(CellDeviceList.class);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");		
    static final long serialVersionUID = 3100L;
    String name = "", id="",  wireless_num="", sim_num="",
	service_type="", user_type="", user_name="",
	make="", billing_id="",
	limit=" limit 50 ", active_status="", date_from="", date_to="",
	department_id="", division_id="", model="";

    String pageParam=""; // for pagination
    int pageNumber = 1, nextPage=0, prevPage=0;
    int totalCount = 0, pageSize=50;
		
    List<CellDevice> cellDevices = null;
    public CellDeviceList(){
	super();
    }
    public CellDeviceList(boolean deb){
	super(deb);
    }		
    public CellDeviceList(boolean deb, String val){
	super(deb);
	setWirelessNum(val);
    }
    public List<CellDevice> getCellDevices(){
	return cellDevices;
    }
    public void setWirelessNum(String val){
	if(val != null && !val.equals("")){
	    wireless_num = Helper.getPhoneNumberCleaned(val);
	    pageParam +=".wirelessNum:"+wireless_num;
	}
    }
    public void setSimNum(String val){
	if(val != null && !val.equals("")){
	    sim_num = val;
	    pageParam +=".simNum:"+sim_num;
	}
    }
    public void setDateFrom(String val){
	if(val != null && !val.equals("")){
	    date_from = val;
	    pageParam +=".dateFrom:"+date_from;
	}
    }
    public void setDateTo(String val){
	if(val != null && !val.equals("")){
	    date_to = val;
	    pageParam +=".dateTo:"+date_to;
	}
    }		
    public void setUserName(String val){
	if(val != null && !val.equals("")){
	    user_name = val;
	    pageParam +=".userName:"+user_name;
	}
    }		
    public void setPageParam(String val){
	if(val != null && !val.equals("")){
	    handlePageParam(val);
	}
    }
    public void setId(String val){
	if(val != null && !val.equals("")){
	    id = val;
	    pageParam += ".id:"+id;
	}
    }
    public void setDepartment_id(String val){
	if(val != null && !val.equals("-1")){
	    if(!val.equals("")){
		department_id = val;
		pageParam += ".department_id:"+department_id;
	    }
	}
    }
    public void setDivision_id(String val){
	if(val != null && !val.equals("-1")){
	    if(!val.equals("")){
		division_id = val;
		pageParam += ".division_id:"+division_id;
	    }
	}
    }		
    public void setBilling_id(String val){
	if(val != null && !val.equals("-1")){
	    if(!val.equals("")){
		billing_id = val;
		pageParam += ".billing_id:"+val;
	    }
	}
    }

    public void setServiceType(String val){
	if(val != null && !val.equals("-1")){
	    if(!val.equals("")){
		service_type = val;
		pageParam += ".serviceType:"+val;
	    }
	}
    }
    public void setUserType(String val){
	if(val != null && !val.equals("-1")){
	    if(!val.equals("")){
		user_type = val;
		pageParam += ".userType:"+val;
	    }
	}
    }		
    public void setModel(String val){
	if(val != null && !val.equals("")){
	    model = val.trim();
	    pageParam += ".model:"+model;
	}
    }
    public void setMake(String val){
	if(val != null && !val.equals("")){
	    make = val.trim();
	    pageParam += ".make:"+make;
	}
    }				
    public void setActiveStatus(String val){
	if(val != null && !val.equals("-1")){
	    if(!val.equals("")){
		active_status = val;
		pageParam += ".activeStatus:"+val;
	    }
	}
    }
		
    public String getId(){
	return id;
    }
    public String getWirlessNum(){
	return wireless_num;
    }
    public String getModel(){
	return model;
    }
    public String getMake(){
	return make;
    }
    public String getUserName(){
	return user_name;
    }
    public String getDateFrom(){
	return date_from;
    }
    public String getDateTo(){
	return date_to;
    }		
    public String getDepartment_id(){
	if(department_id.equals(""))
	    return "-1";
	else
	    return department_id;
    }
    public String getDivision_id(){
	if(division_id.equals(""))
	    return "-1";
	else
	    return division_id;
    }		

    public String getBilling_id(){
	if(billing_id.equals(""))
	    return "-1";
	else
	    return billing_id;
    }		
    public String getServiceType(){
	if(service_type.equals("")){
	    return "-1";
	}
	else
	    return service_type;
    }
    public String getUserType(){
	if(service_type.equals("")){
	    return "-1";
	}
	else
	    return user_type;
    }		
    public String getActiveStatus(){
	if(active_status.equals(""))
	    return "-1";
	else
	    return active_status;
    }		

    public void setNoLimit(){
	limit = "";
    }
    public void setPageNumber(String val){
	if(val != null && !val.equals("-1")){
	    try{
		pageNumber = Integer.parseInt(val);
	    }catch(Exception ex){
								
	    }
	}
    }				
    public void findPages(){
	int pageCount = 1;
	if(totalCount > 0 && pageSize > 0){
	    pageCount = totalCount / pageSize;
	    if(pageCount <= 0) pageCount = 1;
	    if(pageCount*pageSize < totalCount){ // integer division
		pageCount += 1;
	    }
	}
	if(pageCount > 1){
	    if(pageNumber < pageCount){
		nextPage = pageNumber+1;
	    }
	    if(pageNumber > 1){
		prevPage = pageNumber-1;
	    }
	}
    }
    public int getNextPage(){
	return nextPage;
    }
    public int getPrevPage(){
	return prevPage;
    }
    public int getTotalCount(){
	return totalCount;
    }
    public String getPageParam(){
	return pageParam;
    }
    public String getPageNumber(){
	return ""+pageNumber;
    }
    private void handlePageParam(String vals){
	if(vals != null && vals.indexOf(".") > -1){
	    String paramArr[] = vals.split("\\."); // . is special character
	    if(paramArr != null && paramArr.length > 0){
		for(String strParam:paramArr){
		    if(strParam.indexOf(":") > -1){
			String keyVal[] = strParam.split(":");
			if(keyVal.length > 1){
			    String key=keyVal[0];
			    String val=keyVal[1];
			    setKeyVal(key, val);
			}
		    }
		}
	    }
	}
    }
    private void setKeyVal(String key, String val){
	if(key != null && !key.equals("") && val != null && !val.equals("")){
	    switch(key){
	    case "wirelessNum":
		setWirelessNum(val);
		break;
	    case "id":
		setId(val);
		break;
	    case "department_id":
		setDepartment_id(val);
		break;
	    case "division_id":
		setDivision_id(val);
		break;
	    case "billing_id":
		setBilling_id(val);
		break;								
	    case "serviceType":
		setServiceType(val);
		break;
	    case "userType":
		setUserType(val);
		break;
	    case "userName":
		setUserName(val);
		break;								
	    case "model":
		setModel(val);
		break;
	    case "make":
		setMake(val);
		break;
	    case "dateFrom":
		setDateFrom(val);
		break;
	    case "dateTo":
		setDateTo(val);
		break;								
	    case "activeStatus":
		setActiveStatus(val);
		break;
	    case "pageNumber":
		setPageNumber(val);
		break;
	    default:
		System.err.println(" unknown key "+key+" val "+val);
	    }
	}
				
    }
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null, pstmt2 = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qc = "select count(*) from cell_devices p ";
	String qq = " select "+
	    " p.id,p.make,p.model,p.wireless_num,p.device_imei,"+
	    " p.sim_num,p.service_type,"+
	    " date_format(p.device_effective_date,'%m/%d/%Y'),"+
	    " date_format(p.user_effective_date,'%m/%d/%Y'), "+
	    " p.user_name,p.user_type,"+
	    " p.department_id,p.division_id,p.billing_id,"+
	    " date_format(p.contract_start_date,'%m/%d/%Y'), "+
	    " date_format(p.contract_end_date,'%m/%d/%Y'), "+
	    " p.notes, "+
	    " p.inactive from cell_devices p ";
	String qw = "";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	if(!id.equals("")){
	    qw += " p.id = ? ";
	}
	else{
	    if(!make.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.make like ? ";
	    }
	    if(!model.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.model like ? ";
	    }
	    if(!user_name.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.user_name like ? ";
	    }						
	    if(!wireless_num.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.wireless_num like ? ";
	    }
	    if(!sim_num.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.sim_num like ? ";
	    }						
	    if(!service_type.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.service_type = ? ";
	    }
	    if(!user_type.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.user_type = ? ";
	    }
	    if(!department_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.department_id=? ";
	    }
	    if(!division_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.division_id=? ";
	    }
	    if(!billing_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.billing_id=? ";
	    }
	    if(!date_from.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.contract_end_date >= ? ";
	    }
	    if(!date_to.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.contract_end_date <= ? ";
	    }						
	    if(!active_status.equals("")){
		if(!qw.equals("")) qw += " and ";
		if(active_status.equals("Active")){
		    qw += " p.inactive is null ";
		}
		else{
		    qw += " p.inacitve is not null ";
		}
	    }
	}
	if(!qw.equals("")){
	    qw = " where "+qw;
	}
	qc += qw;
	qq += qw +" order by p.wireless_num ";
	if(pageNumber > 1){
	    // since first row is 0 not one
	    limit = " limit "+((pageNumber-1)*pageSize)+","+pageSize;
	}
	else
	    limit = " limit "+pageSize;						
	qq += limit;
	try{
	    // System.err.println(qq);
	    if(debug){
		logger.debug(qq);
	    }
	    //
	    pstmt = con.prepareStatement(qc);
	    pstmt2 = con.prepareStatement(qq);						
	    if(!id.equals("")){
		pstmt.setString(1, id);
		pstmt2.setString(1, id);
								
	    }
	    else {
		int jj=1;
		if(!make.equals("")){
		    pstmt.setString(jj, "%"+make+"%");
		    pstmt2.setString(jj++, "%"+make+"%");										
		}
		if(!model.equals("")){
		    pstmt.setString(jj, "%"+model+"%");
		    pstmt2.setString(jj++, "%"+model+"%");										
		}
		if(!user_name.equals("")){
		    pstmt.setString(jj, "%"+user_name+"%");
		    pstmt2.setString(jj++, "%"+user_name+"%");
		}								
		if(!wireless_num.equals("")){
		    pstmt.setString(jj,"%"+wireless_num);
		    pstmt2.setString(jj++,"%"+wireless_num);										
		}
		if(!sim_num.equals("")){
		    pstmt.setString(jj,"%"+sim_num);
		    pstmt2.setString(jj++,"%"+sim_num);										
		}								
		if(!service_type.equals("")){
		    pstmt.setString(jj,service_type);
		    pstmt2.setString(jj++,service_type);
		}
		if(!user_type.equals("")){
		    pstmt.setString(jj,user_type);
		    pstmt2.setString(jj++,user_type);
		}
		if(!department_id.equals("")){
		    pstmt.setString(jj,department_id);
		    pstmt2.setString(jj++,department_id);
		}
		if(!division_id.equals("")){
		    pstmt.setString(jj,division_id);
		    pstmt2.setString(jj++,division_id);
		}
		if(!billing_id.equals("")){
		    pstmt.setString(jj,billing_id);		
		    pstmt2.setString(jj++,billing_id);		
		}
		if(!date_from.equals("")){
		    java.util.Date date_tmp = dateFormat.parse(date_from);
		    java.sql.Date date = new java.sql.Date(date_tmp.getTime());
		    pstmt.setDate(jj, date);
		    pstmt2.setDate(jj++,date);
		}
		if(!date_to.equals("")){
		    java.util.Date date_tmp = dateFormat.parse(date_to);
		    java.sql.Date date = new java.sql.Date(date_tmp.getTime());
		    pstmt.setDate(jj, date);
		    pstmt2.setDate(jj++,date);
		}								
	    }
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		totalCount = rs.getInt(1);
	    }
	    rs = pstmt2.executeQuery();								
	    while(rs.next()){
		if(cellDevices == null)
		    cellDevices = new ArrayList<>();
		CellDevice one =
		    new CellDevice(debug,
				   rs.getString(1),
				   rs.getString(2),
				   rs.getString(3),
				   rs.getString(4),
				   rs.getString(5),
				   rs.getString(6),
				   rs.getString(7),
				   rs.getString(8),
				   rs.getString(9),
				   rs.getString(10),
				   rs.getString(11),
				   rs.getString(12),
				   rs.getString(13),
				   rs.getString(14),
				   rs.getString(15),
				   rs.getString(16),
				   rs.getString(17),
				   rs.getString(18)!=null);
		if(!cellDevices.contains(one))
		    cellDevices.add(one);
	    }
	}
	catch(Exception ex){
	    back += ex+" : "+qq;
	    logger.error(back);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return back;
    }
}






















































