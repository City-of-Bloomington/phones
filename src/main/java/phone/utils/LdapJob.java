/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
 */
package phone.utils;

import java.util.*;
import java.io.*;
import java.text.*;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobDataMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.list.*;
import phone.model.*;

public class LdapJob implements Job{

    boolean debug = true;
    Logger logger = LogManager.getLogger(LdapJob.class);
    long serialVersionUID = 2227L;
    static EnvBean bean = null;
    //
    public LdapJob(){

    }
    public void execute(JobExecutionContext context)
        throws JobExecutionException {
	try{
						
	    doInit(context);
	    doWork();
	    doDestroy();
	}
	catch(Exception ex){
	    logger.error(ex);
	    System.err.println(ex);
	}
    }
    public void doInit(JobExecutionContext context){
	JobDataMap dataMap = context.getJobDetail().getJobDataMap();
	bean = new EnvBean();				
	String val = (String)dataMap.get("ldap_url");
	if(val != null){
	    bean.setUrl(val);
	}
	val = (String)dataMap.get("ldap_principle");
	if(val != null){
	    bean.setPrinciple(val);
	}
	val = (String)dataMap.get("ldap_password");
	if(val != null){
	    bean.setPassword(val);
	}
    }
    public void doDestroy() {

    }	    
    /**
     *  work function
     */
    public void doWork(){

	String msg = "";
	if(bean != null && !bean.isNotReady()){
	    ManagePhones mp = new ManagePhones(debug, bean);
	    msg = mp.doProcess();
	    if(!msg.equals("")){
		logger.error(msg);
	    }
	}
	else{
	    System.err.println("Phones: ldap ENV bean null or component not set ");
	}
    }

}






















































