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

public class ScanLogList extends CommonInc{

    static Logger logger = LogManager.getLogger(ScanLogList.class);
    static final long serialVersionUID = 2800L;
    List<ScanLog> scans = null;
	
    public ScanLogList(){
    }
    public ScanLogList(boolean deb){
	super(deb);
    }

    public List<ScanLog> getScans(){
	return scans;
    }
		

    public String find(){
		
	String back = "";
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	Connection con = Helper.getConnection();
	String qq = "select id,date_format(time,'%m/%d/%Y %H:%i'),outcome,failure_reason "+
	    "from scan_logs ";
	if(con == null){
	    back = "Could not connect to DB";
	    addError(back);
	    return back;
	}
	String qw = "";
	try{
	    if(!qw.equals("")){
		qq += " where "+qw;
	    }
	    qq += " order by id desc ";
						
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();
	    if(scans == null)
		scans = new ArrayList<ScanLog>();
	    while(rs.next()){
		ScanLog one =
		    new ScanLog(debug,
				rs.getString(1),
				rs.getString(2),
				rs.getString(3),
				rs.getString(4));
		if(!scans.contains(one))
		    scans.add(one);
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






















































