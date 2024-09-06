package phone.list;
/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.model.*;
import phone.utils.*;

public class PhoneList extends CommonInc{

    static Logger logger = LogManager.getLogger(PhoneList.class);
    static final long serialVersionUID = 3100L;
    String name = "", id="",  phoneNumber="", type="", billing_id="",
	limit=" limit 50 ", mailBox = "", signal_type="", address_id="",
	department_id="", division_id="", wallPlate="", wallPlateNum="",
	ext_number="",activeStatus = "", model="",
	line_name="";//employee name or service
    String pageParam=""; // for pagination
    int pageNumber = 1, nextPage=0, prevPage=0;
    int totalCount = 0, pageSize=50;
		
    List<Phone> phones = null;
    public PhoneList(){
	super();
    }
    public PhoneList(boolean deb){
	super(deb);
    }		
    public PhoneList(boolean deb, String val){
	super(deb);
	setPhoneNumber(val);
    }
    public List<Phone> getPhones(){
	return phones;
    }
    public void setPhoneNumber(String val){
	if(val != null && !val.equals("")){
	    phoneNumber = Helper.getPhoneNumberCleaned(val);
	    pageParam +=".phoneNumber:"+phoneNumber;
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
    public void setDivsion_id(String val){
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
    public void setType(String val){
	if(val != null && !val.equals("-1")){
	    if(!val.equals("")){
		type = val;
		pageParam += ".type:"+val;
	    }
	}
    }
    public void setSignal_type(String val){
	if(val != null && !val.equals("-1")){
	    if(!val.equals("")){
		signal_type = val;
		pageParam += ".signal_type:"+val;
	    }
	}
    }		
    public void setAddress_id(String val){
	if(val != null && !val.equals("-1")){
	    if(!val.equals("")){
		address_id = val;
		pageParam += ".address_id:"+val;
	    }
	}
    }
    public void setWallPlate(String val){
	if(val != null && !val.equals("-1")){
	    if(!val.equals("")){
		wallPlate = val;
		pageParam += ".wallPlate:"+val;
	    }
	}
    }
    public void setWallPlateNum(String val){
	if(val != null && !val.equals("")){
	    wallPlateNum = val;
	    pageParam += ".wallPlateNum:"+val;						
	}
    }
    public void setModel(String val){
	if(val != null && !val.equals("")){
	    model = val.trim();
	    pageParam += ".model:"+model;
	}
    }		
    public void setActiveStatus(String val){
	if(val != null && !val.equals("-1")){
	    if(!val.equals("")){
		activeStatus = val;
		pageParam += ".activeStatus:"+val;
	    }
	}
    }
    public void setLine_name(String val){
	if(val != null && !val.equals("")){
	    line_name = val.trim();
	    pageParam += ".line_name:"+line_name;
	}
    }
    public void setExt_number(String val){
	if(val != null && !val.equals("")){
	    ext_number = val.trim();
	    pageParam += ".ext_number:"+ext_number;
	}
    }
		
    public String getId(){
	return id;
    }
    public String getPhoneNumber(){
	return phoneNumber;
    }
    public String getModel(){
	return model;
    }		
    public String getDepartment_id(){
	if(department_id.equals(""))
	    return "-1";
	else
	    return department_id;
    }
    public String getAddress_id(){
	if(address_id.equals(""))
	    return "-1";
	else				
	    return address_id;
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
    public String getType(){
	if(type.equals("")){
	    return "-1";
	}
	else
	    return type;
    }
    public String getActiveStatus(){
	if(activeStatus.equals(""))
	    return "-1";
	else
	    return activeStatus;
    }		
    public String getSignal_type(){
	if(signal_type.equals(""))
	    return "-1";
	else
	    return signal_type;
    }		
    public String getWallPlate(){
	if(wallPlate.equals(""))
	    return "-1";				
	return wallPlate;
    }
    public String getWallPlateNum(){
	return wallPlateNum;
    }
    public String getLine_name(){
	return line_name;
    }
    public String getExt_number(){
	return ext_number;
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
	    case "phoneNumber":
		setPhoneNumber(val);
		break;
	    case "id":
		setId(val);
		break;
	    case "department_id":
		setDepartment_id(val);
		break;
	    case "division_id":
		setDivsion_id(val);
		break;
	    case "billing_id":
		setBilling_id(val);
		break;
	    case "type":
		setType(val);
		break;
	    case "signal_type":
		setSignal_type(val);
		break;
	    case "address_id":
		setAddress_id(val);
		break;
	    case "wallPlate":
		setWallPlate(val);
		break;
	    case "wallPlateNum":
		setWallPlateNum(val);
		break;
	    case "model":
		setModel(val);
		break;
	    case "activeStatus":
		setActiveStatus(val);
		break;
	    case "line_name":
		setLine_name(val);
		break;
	    case "ext_number":
		setExt_number(val);
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
	String qc = "select count(*) from phones p ";
	String qq = " select p.id,p.phoneNumber,p.location,"+
	    " p.otherUse,"+
	    " p.signal_type,p.model,p.address_id,p.department_id,"+
	    " p.division_id,p.billing_id, p.port, "+
	    " p.active,p.notes,p.wallPlate,p.wallPlateNum,p.type "+
	    " from phones p ";
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
	    if(!line_name.equals("") || !ext_number.equals("")){
		qc +=" left join pline_phones pl on p.id=pl.phone_id ";
		qq +=" left join pline_phones pl on p.id=pl.phone_id ";
	    }
	    if(!line_name.equals("")){						
		qc += " left join plines l on l.id=pl.line_id ";
		qq += " left join plines l on l.id=pl.line_id ";
		if(!qw.equals("")) qw += " and ";
		qw += " l.name like ? ";
	    }
	    if(!ext_number.equals("")){
		qc += " left join phone_exts pe on pe.id=pl.ext_id ";
		qq += " left join phone_exts pe on pe.id=pl.ext_id ";
		if(!qw.equals("")) qw += " and ";
		qw += " pe.ext_number = ? ";
	    }				
	    if(!phoneNumber.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.phoneNumber like ? ";
	    }
	    if(!address_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.address_id = ? ";
	    }						
	    if(!type.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.type = ? ";
	    }
	    if(!signal_type.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.signal_type = ? ";
	    }
	    if(!wallPlate.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.wallPlate = ? ";
	    }
	    if(!wallPlateNum.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " p.wallPlateNum = ? ";
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
	    if(!activeStatus.equals("")){
		if(!qw.equals("")) qw += " and ";
		if(activeStatus.equals("Active")){
		    qw += " p.id in (select phone_id from pline_phones) ";
		}
		else{
		    qw += " p.id not in (select phone_id from pline_phones) ";
		}
	    }
	}
	if(!qw.equals("")){
	    qw = " where "+qw;
	}
	qc += qw;
	qq += qw +" order by p.phoneNumber ";
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
		if(!line_name.equals("")){
		    pstmt.setString(jj,"%"+line_name+"%");
		    pstmt2.setString(jj++,"%"+line_name+"%");
		}
		if(!ext_number.equals("")){
		    pstmt.setString(jj,ext_number);
		    pstmt2.setString(jj++,ext_number);										
		}								
		if(!phoneNumber.equals("")){
		    pstmt.setString(jj,"%"+phoneNumber);
		    pstmt2.setString(jj++,"%"+phoneNumber);
		}
		if(!address_id.equals("")){
		    pstmt.setString(jj,address_id);
		    pstmt2.setString(jj++,address_id);		
		}								
		if(!type.equals("")){
		    pstmt.setString(jj,type);
		    pstmt2.setString(jj++,type);
		}
		if(!signal_type.equals("")){
		    pstmt.setString(jj,signal_type);
		    pstmt2.setString(jj++,signal_type);
		}
		if(!wallPlate.equals("")){
		    pstmt.setString(jj,wallPlate);
		    pstmt2.setString(jj++,wallPlate);
		}
		if(!wallPlateNum.equals("")){
		    pstmt.setString(jj,wallPlateNum);
		    pstmt2.setString(jj++,wallPlateNum);
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
	    }
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		totalCount = rs.getInt(1);
	    }
	    rs = pstmt2.executeQuery();								
	    while(rs.next()){
		if(phones == null)
		    phones = new ArrayList<>();
		Phone one =
		    new Phone(debug,
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
			      rs.getString(12) != null,
			      rs.getString(13),
			      rs.getString(14),
			      rs.getString(15),
			      rs.getString(16));
		if(!phones.contains(one))
		    phones.add(one);
	    }
	}
	catch(Exception ex){
	    back += ex+" : "+qq;
	    logger.error(back);
	    addError(back);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, pstmt2, rs);
	}
	return back;
    }
    /**
	select p.phoneNumber,
	   p.location,
	   p.signal_type,
	   p.type,
	   l.name,
	   l.type,
	   pe.ext_number,
	   dp.name Dept,
	   dv.name Division
	   from phones p
	   left join departments dp on p.department_id=dp.id
	   left join divisions dv on p.division_id=dv.id
	   left join pline_phones pl on p.id=pl.phone_id 	     
	   left join plines l on l.id=pl.line_id
	   left join phone_exts pe on pe.id=pl.ext_id
	   INTO OUTFILE '/var/lib/mysql-files/phone_data.csv'
	   FIELDS TERMINATED BY ','
	   LINES TERMINATED BY '\n';
	   

     */
}






















































