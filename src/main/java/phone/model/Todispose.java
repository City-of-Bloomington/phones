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

public class Todispose extends CommonInc implements java.io.Serializable{

    String decision_user_id="";
    static final long serialVersionUID = 1000L;
    static Logger logger = LogManager.getLogger(Todispose.class);
    String[] dispose_ids = null;
    //
    public Todispose(){
	super();
    }
    public Todispose(boolean deb){
	//
	// initialize
	//
	super(deb);
    }

    public String getDecision_user_id(){
	return decision_user_id;
    }

    public void setDispose_ids(String[] vals){
	if(vals != null)
	    dispose_ids = vals;
    }
    public void setDecision_user_id(String val){
	if(val != null)
	    decision_user_id = val;
    }
    //
    public String doDispose(){
	Connection con = null;
	PreparedStatement stmt = null, stmt2 = null;
	ResultSet rs = null;		
		
	String msg="";
	String qq = "", qq2 = "";
	if(dispose_ids == null){
	    msg = "no use line selected to be disposed ";
	    return msg;
	}
	qq = "update disposed_lines set agree='y', decision_user_id=? where id=? ";
	qq2 = "delete from pline_phones where line_id in (select line_id from disposed_lines  where id = ? ) ";
	//
	if(debug){
	    logger.debug(qq);
	}
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to DB";
	    addError(msg);
	    return msg;
	}			
	try{
	    if(debug){
		logger.debug(qq);
	    }
	    stmt = con.prepareStatement(qq);
	    stmt2 = con.prepareStatement(qq2);
	    for(String str:dispose_ids){
		stmt.setString(1, decision_user_id);
		stmt.setString(2, str);
		stmt.executeUpdate();
		stmt2.setString(1, str);
		stmt2.executeUpdate();								
	    }
	}
	catch(Exception ex){
	    msg = ex+": "+qq;
	    logger.error(msg);
	    addError(msg);
	    return msg;
	}
	finally{
	    Helper.databaseDisconnect(con, stmt, stmt2, rs);
	}
	return msg; // success
    }
	 

}
