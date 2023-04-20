/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package phone.list;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.model.*;
import phone.utils.*;

public class FileUploadList extends CommonInc{

    String id="", user_id="", date="", date_from="", date_to="";

    String obj_id="", obj_type="";
    String limit = " limit 10 ";
    static final long serialVersionUID = 2900L;		
    static Logger logger = LogManager.getLogger(FileUploadList.class);
    List<FileUpload> uploads = null;
    public FileUploadList(){
		
    }
    public FileUploadList(boolean deb, String val){
	debug = deb;
	setObj_id(val);
    }				
    //
    // getters
    //
    public String getId(){
	return id;
    }	
    public String getUser_id(){
	return user_id;
    }
    public String getDate_from(){
	return date_from;
    }
    public String getDate_to(){
	return date_to;
    }
    public String getObj_id(){
	return obj_id;
    }
    public String getObj_type(){
	return obj_type;
    }		
    public void setDate_from(String val){
	if(val != null)
	    date_from = val;
    }
    public void setDate_to(String val){
	if(val != null)
	    date_to = val;
    }
    public void setObj_id(String val){
	if(val != null)
	    obj_id = val;
    }
    public void setObj_type(String val){
	if(val != null)
	    obj_type = val;
    }		
    public void setNoLimit(){
	limit = "";
    }
    public List<FileUpload> getUploads(){
	return uploads;
    }
    public FileUpload getLastUpload(){
	if(uploads == null){
	    String back = find();
	}
	if(uploads != null){
	    return uploads.get(0);
	}
	return null;
    }
    //
    // setters
    //
    public void setId(String val){
	if(val != null)
	    id = val;
    }	
    public void setUser_id (String val){
	if(val != null)
	    user_id = val;
    }
	
    public String find(){
	String msg="";
	PreparedStatement pstmt = null;
	Connection con = null;
	ResultSet rs = null;		
	String qq = "select id,file_name,old_file_name,obj_id,obj_type,date_format(date,'%m/%d/%Y'),user_id,notes from media ";
	logger.debug(qq);
	con = Helper.getConnection();
	if(con == null){
	    msg += " could not connect to database";
	    return msg;
	}		
	try{
	    String qw = "";
	    if(!id.equals("")){
		qw += " id = ? ";
	    }
	    else{
		if(!obj_id.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " obj_id = ? ";
		}
		if(!obj_type.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " obj_type = ? ";
		}								
		if(!date_from.equals("")){
		    if(!qw.equals("")) qw += " and ";								
		    qw += " date >= str_to_date('"+date_from+"','%m/%d/%Y')";
		}
		if(!date_to.equals("")){
		    if(!qw.equals("")) qw += " and ";
		    qw += " date <= str_to_date('"+date_to+"','%m/%d/%Y')";								
		}
	    }
	    if(!qw.equals("")){
		qq += " where "+qw;
	    }
	    qq += " order by id desc "+limit;
	    // System.err.println(qq);
	    pstmt = con.prepareStatement(qq);
	    int jj=1;
	    if(!id.equals("")){
		pstmt.setString(jj++, id);
	    }
	    else{
		if(!obj_id.equals("")){
		    pstmt.setString(jj++, obj_id);
		}
		if(!obj_type.equals("")){
		    pstmt.setString(jj++, obj_type);
		}								
	    }
	    rs = pstmt.executeQuery();
	    uploads = new ArrayList<FileUpload>();
	    while(rs.next()){
		FileUpload one = new FileUpload(rs.getString(1),
						rs.getString(2),
						rs.getString(3),
						rs.getString(4),
						rs.getString(5),
						rs.getString(6),
						rs.getString(7),
						rs.getString(8));
		uploads.add(one);
	    }
	}
	catch(Exception ex){
	    msg += " "+ex;
	    logger.error(ex+":"+qq);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }
	
}
