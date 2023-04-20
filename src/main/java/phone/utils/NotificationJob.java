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
import javax.mail.*;
import javax.mail.Address;
import javax.mail.internet.*;
import javax.activation.*;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobDataMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.list.*;
import phone.model.*;

public class NotificationJob implements Job{

    boolean debug = true;
    Logger logger = LogManager.getLogger(NotificationJob.class);
    long serialVersionUID = 2227L;
    String emailStr = "@bloomington.in.gov";
    JobDataMap dataMap = null;
    boolean activeMail = false;
    String from = "", to="", msgText="", cc="", bcc="", subject="";
		
    //
    // will send emails
    public NotificationJob(){

    }
    public void execute(JobExecutionContext context)
        throws JobExecutionException {
	try{
	    JobDataMap dataMap = context.getJobDetail().getJobDataMap();
	    logger.debug("map = " + dataMap.entrySet());
	    logger.debug("dataMap = " + context.getJobDetail().getJobDataMap());
	    to = (String)dataMap.get("to");
	    from = (String)dataMap.get("from");
	    String str = (String)dataMap.get("cc");
	    if(str != null){
		cc = str;
	    }
	    str = (String)dataMap.get("activeMail");
	    if(str != null && str.equals("true")){
		activeMail = true;
	    }
	    str = (String)dataMap.get("msgText");
	    if(str != null){
		msgText = str;
	    }
	    doInit();
	    sendEmail(from, to, cc, bcc, msgText);
	    doDestroy();
	}
	catch(Exception ex){
	    logger.error(ex);
	    System.err.println(ex);
	}
    }
    public void doInit(){

	//
	// good idea to clear old data from quartz tables
	//

    }
    public void doDestroy() {

    }	    
    public String sendEmail(String from, String to, String cc, String bcc, String msgText){
	String message= "";
	String host = "localhost"; // whoville.city. ...
	String subject = "Phones contract expire notification";
	try {
	    //
	    // create some properties and get the default Session
	    //
	    Properties props = new Properties();
	    props.put("mail.smtp.host", host);
	    if (debug) props.put("mail.debug", "true");
			
	    Session session = Session.getDefaultInstance(props, null);
	    session.setDebug(debug);
	    //
	    // create a message
	    //
	    Message msg = new MimeMessage(session);
	    msg.setFrom(new InternetAddress(from));
	    if(to != null && !to.equals("")){
		InternetAddress[] address = {new InternetAddress(to)};
		msg.setRecipients(Message.RecipientType.TO, address);
	    }
	    if(cc != null && !cc.equals("")){
		InternetAddress[] address2 = javax.mail.internet.InternetAddress.parse(cc);
		msg.addRecipients(Message.RecipientType.CC, address2);
	    }
	    if(bcc != null && !bcc.equals("")){
		InternetAddress[] address3 = javax.mail.internet.InternetAddress.parse(bcc);
		msg.setRecipients(Message.RecipientType.BCC, address3);
	    }
	    msg.setSubject(subject);
	    msg.setSentDate(new Date());

	    // If the desired charset is known, you can use
	    // setText(text, charset)
	    msg.setText(msgText);
	    //
	    Transport.send(msg);
	} catch (MessagingException mex){

	    logger.debug("\n--Exception handling in MailHandle.java");
	    //   mex.printStackTrace();
	    message += " Exception in MailHandle "+mex;
	    Exception ex = mex;
	    do {
		if (ex instanceof SendFailedException) {
		    SendFailedException sfex = (SendFailedException)ex;
		    Address[] invalid = sfex.getInvalidAddresses();
		    if (invalid != null) {
			System.out.println("    ** Invalid Addresses");
			if (invalid != null) {
			    for (int i = 0; i < invalid.length; i++) 
				message += "         " + invalid[i];
			}
		    }
		    Address[] validUnsent = sfex.getValidUnsentAddresses();
		    if (validUnsent != null) {
			System.out.println("    ** ValidUnsent Addresses");
			if (validUnsent != null) {
			    for (int i = 0; i < validUnsent.length; i++) 
				message += "         "+validUnsent[i];
			}
		    }
		    Address[] validSent = sfex.getValidSentAddresses();
		    if (validSent != null) {
			System.out.println("    ** ValidSent Addresses");
			if (validSent != null) {
			    for (int i = 0; i < validSent.length; i++) 
				message += "         "+validSent[i];
			}
		    }
		}
		System.out.println();
		if (ex instanceof MessagingException){
		    ex = ((MessagingException)ex).getNextException();
		}
		else{
		    ex = null;
		}
	    } while (ex != null);
	    logger.error(message);
	}
	return message;
    }
}






















































