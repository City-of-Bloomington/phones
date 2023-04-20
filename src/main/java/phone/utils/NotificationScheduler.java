/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
 */
package phone.utils;
import org.quartz.TriggerBuilder;
import org.quartz.DateBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
// import org.quartz.CronScheduleBuilder.cronSchedule;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.text.SimpleDateFormat;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.list.*;
import phone.model.*;

public class NotificationScheduler{

    static boolean debug = false;
    SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");		
    static Logger logger = LogManager.getLogger(NotificationScheduler.class);
    static final long serialVersionUID = 2237L;
    static final String emailStr = "@bloomington.in.gov";
    String msg="";	
    boolean activeMail = false;
    Date startDateTime = null;
    Contract contract = null;
    List<User> users = null;
    String cc="", msgText="", from="",to="", bcc="", subject="";
    public NotificationScheduler(boolean deb, Contract val, boolean val2){
	debug = deb;
	contract = val;
	activeMail = val2;
				
	try{
	    // String testDate = "06/01/2017";
	    Calendar cal = new GregorianCalendar();
	    String date = contract.getNotification_date();
	    java.util.Date myDate = dateformat.parse(date);
	    cal.setTime(myDate);
	    cal.set(Calendar.HOUR_OF_DAY, 15);//to run at 7am of the specified day
	    cal.set(Calendar.MINUTE, 35);
	    startDateTime = cal.getTime();
	    composeMessage();
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
			
	    String jobName = "phone_notification_"+contract.getId();
	    JobDetail job = JobBuilder.newJob(NotificationJob.class)
		.withIdentity(jobName, "phone_group2")
		.build();
	    job.getJobDataMap().put("to",to);
	    job.getJobDataMap().put("from",from);
	    if(!cc.equals("")){
		job.getJobDataMap().put("cc",cc);
	    }
	    // job.getJobDataMap().put("to","sibow"+emailStr);
	    // job.getJobDataMap().put("from","sibow"+emailStr);
	    /*
	      if(!act.getAnotherUserid().equals("")){
	      job.getJobDataMap().put("cc", act.getAnotherUserid()+emailStr);
	      }
	    */
	    job.getJobDataMap().put("msgText",msgText);
	    // 
	    // Trigger will run at 7am on the speciified date
	    // cron date and time entries (year can be ignored)
	    // second minute hour day-of-month month week-day year
	    // you can use ? no specific value, 0/5 for incrment (every 5 seconds)
	    // * for any value (in minutes mean every minute
	    /*
	      Trigger trigger = newTrigger()
	      .withIdentity("trigger_"+month+"_"+day+"_"+year, "accrualGroup")
	      .startAt(startDate)
	      .withSchedule(cronSchedule("* * 8 0 0/2 * ,FRI")
	      // .withMisfireHandlingInstructionFireNow())
	      .withMisfireHandlingInstructionFireAndProceed())
	      .endAt(endDate)						  
	      // .withMisfireHandlingInstructionIgnoreMisfires())
	      .build();
	    */
	    Trigger trigger = TriggerBuilder.newTrigger()
		.withIdentity(jobName, "phone_group2")
		.startAt(startDateTime) // one time stuff
		.withSchedule(simpleSchedule()
			      // .withIntervalInMinutes(5)
			      // .withIntervalInHours(24) // 24 every hours
			      // .repeatForever()
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

	    /*
	      logger.info("------- Started Scheduler -----------------");
			  
	      // wait long enough so that the scheduler as an opportunity to 
	      // run the job!
	      logger.info("------- Waiting 65 seconds... -------------");
	      try {
	      // wait 65 seconds to show job
	      Thread.sleep(65L * 1000L); 
	      // executing...
	      } catch (Exception e) {
	      }
			  
	      // shut down the scheduler
	      logger.info("------- Shutting Down ---------------------");
	      sched.shutdown(true);
	      logger.info("------- Shutdown Complete -----------------");
	    */
	}catch(Exception ex){
	    logger.error(ex);
	}
    }
    String findUsers(){
	UserList ul = new UserList(debug);
	ul.setCanReceiveEmail();
	ul.setActiveOnly();
	String back = ul.find();
	if(back.equals("")){
	    users = ul.getUsers();
	}
	return back;
    }
    String composeMessage(){
	String ret = "";
	String name = "", vendor_name="";
	if(contract != null){
	    ret = findUsers();
	    if(users != null){
		for(User one:users){
		    if(from.equals("")){
			from = one.getUsername()+emailStr;
			to = one.getUsername()+emailStr;
		    }
		    else{
			if(!cc.equals(""))
			    cc +=",";
			cc += one.getUsername()+emailStr;
		    }
		}
	    }
	    ret += " Phones app notification about the contract "+name;
	    ret += "\n";
	    // ret += " with vendor "+vendor_name+".\n";
	    ret += " The contract will end on "+contract.getEnd_date();
	    ret += "\n";						
	    ret += " The contract started on "+contract.getStart_date();
	    ret += "\n";
	    msgText = ret;
	}
	return ret;
    }
	
}
