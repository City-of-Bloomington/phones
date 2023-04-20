/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
 */
package phone.utils;
import org.quartz.TriggerBuilder;
import org.quartz.DateBuilder.*;
import java.sql.*;
import static org.quartz.SimpleScheduleBuilder.*;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.struts2.util.ServletContextAware;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.list.*;
import phone.model.*;

public class LdapScheduler implements ServletContextListener{

    static boolean debug = false;
    static Logger logger = LogManager.getLogger(LdapScheduler.class);
    static final long serialVersionUID = 2237L;
    static final String emailStr = "@bloomington.in.gov";
    int month = 6, day = 1, year=2017; // Start of a Wednesday
    ServletContext ctx;
    static String ldap_url = "";
    static String ldap_principle = "";
    static String ldap_password = "";
    boolean activeMail = false;
    Date startDate = null;
    public void contextDestroyed(ServletContextEvent crtxe) {
	//
    };
    public void contextInitialized(ServletContextEvent crtxe){
	//
	// if we have old records we need to remove first before
	// the system will add a new one
	//
	cleanOldLdapRecord();
	ctx = crtxe.getServletContext();
	try{
	    Calendar cal = new GregorianCalendar();
	    cal.set(Calendar.HOUR_OF_DAY, 7);//to run at 7am of the specified day
	    // cal.set(Calendar.MINUTE, 25);
	    startDate = cal.getTime();
	    year = cal.get(Calendar.YEAR);
	    month = cal.get(Calendar.MONTH)+1;
	    day = cal.get(Calendar.DATE);
	    if(ctx != null){
		String val = ctx.getInitParameter("ldap_url");
		if(val != null){
		    ldap_url = val;
		}
		val = ctx.getInitParameter("ldap_principle");
		if(val != null){
		    ldap_principle = val;
		}
		val = ctx.getInitParameter("ldap_password");
		if(val != null){
		    ldap_password = val;
		}
	    }
	    run();
	}
	catch(Exception ex){
	    logger.error(ex);
	}
    }
	
    public void run() throws Exception {
	//
	try{
	    String msg = "";
	    logger.debug("------- Initializing ----------------------");
			
	    // First we must get a reference to a scheduler
	    // SchedulerFactory sf = new StdSchedulerFactory();
	    // Scheduler sched = sf.getScheduler();
			
	    logger.debug("------- Initialization Complete -----------");
			
	    // computer a time that is on the next round minute
	    //  Date runTime = evenMinuteDate(new Date());
			
	    logger.debug("------- Scheduling Job  -------------------");
			
	    // define the job and tie it to our Job class
			
	    String jobName = "job_ldap_"+year+"_"+month+"_"+day;
	    //System.err.println(" before job");
	    JobDetail job = JobBuilder.newJob(LdapJob.class)
		.withIdentity(jobName, "phone_group")
		.build();
	    job.getJobDataMap().put("ldap_url",ldap_url);
	    job.getJobDataMap().put("ldap_principle",ldap_principle);
	    job.getJobDataMap().put("ldap_password",ldap_password);
	    //
	    Trigger trigger = TriggerBuilder.newTrigger()
		.withIdentity(jobName, "phone_group")
		.startAt(startDate)
		.withSchedule(simpleSchedule()
			      // .withIntervalInMinutes(5)
			      .withIntervalInHours(24) // 24 every weeks
			      .repeatForever()
			      // .withRepeatCount(2) 
			      // .withMisfireHandlingInstructionFireNow())
			      .withMisfireHandlingInstructionIgnoreMisfires())
		// .endAt(endDate)						  
		.build();
			
	    // Tell quartz to schedule the job using our trigger
	    Scheduler sched = new StdSchedulerFactory().getScheduler();
	    sched.start();
	    sched.scheduleJob(job, trigger);
	    // System.err.println(" after schedule ");
	    //  logger.info(job.getKey() + " will run at: " + runTime);  
			
	    // Start up the scheduler (nothing can actually run until the 
	    // scheduler has been started)
	    //	sched.start();

	}catch(Exception ex){
	    logger.error(ex);
	}
    }
    void cleanOldLdapRecord(){
	Connection con = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;		
	String str="", msg="";
	String qq = "delete from qrtz_FIRED_TRIGGERS where job_name like ?";
	String qq2 = "delete from qrtz_SIMPLE_TRIGGERS where trigger_name like ?";
	String qq3 = "delete from qrtz_TRIGGERS where trigger_name like ?";
	String qq4 = "delete from qrtz_JOB_DETAILS where job_name like ?";
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to DB";
	    logger.error(msg);
	}			
	try{
	    stmt = con.prepareStatement(qq);
	    stmt.setString(1, "job_ldap_%");
	    stmt.executeUpdate();
	    stmt = con.prepareStatement(qq2);
	    stmt.setString(1, "job_ldap_%");
	    stmt.executeUpdate();
	    stmt = con.prepareStatement(qq3);
	    stmt.setString(1, "job_ldap_%");
	    stmt.executeUpdate();
	    stmt = con.prepareStatement(qq4);
	    stmt.setString(1, "job_ldap_%");
	    stmt.executeUpdate();
	}
	catch(Exception ex){
	    msg = ex+": "+qq;
	    logger.error(msg);
	}
	finally{
	    Helper.databaseDisconnect(con, stmt, rs);
	}
						
    }

}
