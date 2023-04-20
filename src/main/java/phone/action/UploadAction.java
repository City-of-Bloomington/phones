/**
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package phone.action;

import java.io.File;
import java.util.*;
import java.nio.file.*;
import org.apache.commons.io.FileUtils;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import phone.model.*;
import phone.list.*;
import phone.utils.*;

public class UploadAction extends TopAction{

    static final long serialVersionUID = 20L;	
    String obj_id="", notes="", obj_type="";
    static Logger logger = LogManager.getLogger(UploadAction.class);	
    private File file;
    private String contentType, saveDir="";
    private String filename;
    private String url = "";
    private List<FileUpload> uploads = null;
		
    public void setUpload(File file) {
	this.file = file;
    }
    public void setSaveDir(String str) {
	if(str != null)
	    saveDir = str;
    }		
	
    public void setUploadContentType(String contentType) {
	this.contentType = contentType;
    }
	
    public void setUploadFileName(String filename) {
	this.filename = filename;
    }
    public void setAction(String val){
	action = val;
    }	
    public String execute() {
	String ret = INPUT;		
	String back = doPrepare();
	if(!back.equals("")){
	    try{
		HttpServletResponse res = ServletActionContext.getResponse();
		String str = url+"Login";
		res.sendRedirect(str);
		return super.execute();
	    }catch(Exception ex){
		System.err.println(ex);
	    }
	}		
	if(!action.equals("")){
	    if(file != null){
		try{
		    String file_ext = Helper.getFileExtension(filename, file);
		    FileUpload upload = new FileUpload();
		    upload.setObj_id(obj_id);
		    upload.setObj_type(obj_type);										
		    upload.setNotes(notes);
		    String new_file_name = upload.genNewFileName(file_ext);
		    upload.setOld_file_name(filename);
		    upload.setUser_id(user.getId());
		    // String filePath = ctx.getRealPath("/") +"WEB-INF"+File.separator+"files"+File.separator;
		    File new_file = new File(server_path, new_file_name);
		    FileUtils.copyFile(file, new_file);
		    back = upload.doSave();
		    if(back.equals("")){
			ret = SUCCESS;
			addActionMessage("Save successfully");
		    }
		    else{
			addActionError(back);
		    }
		}catch(Exception ex){
		    logger.error(ex);
		    addActionError(""+ex);
		}
	    }
	}
	return ret;
    }
    public void setNotes(String val){
	if(val != null)
	    notes = val;
    }
    public void setObj_id(String val){
	if(val != null)
	    obj_id = val;
    }
    public void setObj_type(String val){
	if(val != null)
	    obj_type = val;
    }		
    public String getNotes(){
	return notes;
    }
    public String getObj_id(){
	return obj_id;
    }
    public String getObj_type(){
	return obj_type;
    }		
    public List<FileUpload> getUploads(){
	if(uploads == null){
	    FileUploadList fl = new FileUploadList();
	    if(!obj_id.equals(""))
		fl.setObj_id(obj_id);						
	    String back = fl.find();
	    if(back.equals("")){
		List<FileUpload> list = fl.getUploads();
		if(list != null && list.size() > 0){
		    uploads = list;
		}
	    }
	}
	return uploads;
    }
    /*
      public Request getRequest(){
      if(request == null && !request_id.equals("")){
      Request one = new Request(false, request_id);
      String back = one.doSelect();
      if(back.equals("")){
      request = one;
      }
      }
      return request;
      }
    */
}





































