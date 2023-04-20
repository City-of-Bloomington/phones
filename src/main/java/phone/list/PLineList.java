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

public class PLineList extends CommonInc{

    static Logger logger = LogManager.getLogger(PLineList.class);
    static final long serialVersionUID = 3100L;
    String name = "", id="",  phone_id="", 
	limit=" limit 50 ", mail_box = "",
	phone="";
    int pageNumber = 1, nextPage=0, prevPage=0;
    int  totalCount = 0, pageSize=50;
    List<PLine> pLines = null;
    public PLineList(){
	super();
    }
    public PLineList(boolean deb){
	super(deb);
    }		
    public PLineList(boolean deb, String val){
	super(deb);
	setName(val);
    }
    public List<PLine> getPLines(){
	return pLines;
    }
    public void setName(String val){
	if(val != null)
	    name = val;
    }
    public void setMail_box(String val){
	if(val != null)
	    mail_box = val;
    }		
    public void setId(String val){
	if(val != null)
	    id = val;
    }
    public void setPhone(String val){
	phone = val;
    }
    public void setPhone_id(String val){
	phone_id = val;
    }		
    public String getId(){
	return id;
    }
    public String getPhone(){
	return phone;
    }
    public String getPhone_id(){
	return phone_id;
    }		
    public String getName(){
	return name;
    }
    public String getMail_box(){
	return mail_box;
    }
    public void setPageNumber(String val){
	if(val != null && !val.equals("-1")){
	    try{
		pageNumber = Integer.parseInt(val);
	    }catch(Exception ex){
								
	    }
	}
    }		
    public void setNoLimit(){
	limit = "";
    }
    public int getTotalCount(){
	return totalCount;
    }
    public void findPages(){
	int pageCount = 1;
	if(totalCount > 0 && pageSize > 0){
	    pageCount = totalCount / pageSize;
	    if(pageCount <= 0) pageCount = 1;
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
    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null, pstmt2 = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qc = "select count(*) from plines p left join pline_phones lp on lp.line_id=p.id left join phones pp on pp.id=lp.phone_id  left join phone_exts pe on pe.id = lp.ext_id ", qw ="";
	String qq = "select p.id,p.name,p.type from plines p left join pline_phones lp on lp.line_id=p.id left join phones pp on pp.id=lp.phone_id  left join phone_exts pe on pe.id = lp.ext_id ";				
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	if(!id.equals("")){
	    qw += " p.id = ? ";
	}
	else{
	    if(!name.equals("")){
		qw += " p.name like ? ";
	    }
	    if(!mail_box.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " pe.mail_box like ? ";
	    }
	    if(!phone.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " pp.phoneNumber like ? ";
	    }
	    if(!phone_id.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " lp.phone_id=? ";
	    }
	}
	if(!qw.equals("")){
	    qc += " where "+qw;
	    qq += " where "+qw;
	}
	qq += " order by p.name ";
	if(pageNumber > 1){
	    limit = " limit "+((pageNumber-1)*pageSize)+","+pageSize;
	}
	else
	    limit = " limit "+pageSize;						
	qq += limit;
	try{
	    if(debug){
		logger.debug(qq);
	    }
	    //
	    pstmt = con.prepareStatement(qc);
	    pstmt2 = con.prepareStatement(qq);
	    if(!id.equals("")){
		pstmt.setString(1, id);
	    }
	    else {
		int jj=1;
		if(!name.equals("")){
		    pstmt.setString(jj,"%"+name+"%");
		    pstmt2.setString(jj++,"%"+name+"%");
		}
		if(!mail_box.equals("")){
		    pstmt.setString(jj,mail_box);												
		    pstmt2.setString(jj++,mail_box);		
		}
		if(!phone.equals("")){
		    pstmt.setString(jj,"%"+phone);
		    pstmt2.setString(jj++,"%"+phone);
		}
		if(!phone_id.equals("")){
		    pstmt.setString(jj,phone_id);		
		    pstmt2.setString(jj++,phone_id);		
		}
	    }
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		totalCount = rs.getInt(1);
	    }
	    rs = pstmt2.executeQuery();						
	    while(rs.next()){
		if(pLines == null)
		    pLines = new ArrayList<>();
		PLine one =
		    new PLine(debug,
			      rs.getString(1),
			      rs.getString(2),
			      rs.getString(3));
		if(!pLines.contains(one))
		    pLines.add(one);
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
}






















































