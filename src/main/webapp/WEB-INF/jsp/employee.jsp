<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="employee" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<s:hidden name="employee.phone_id" value="%{employee.phone_id}" />	
	<s:if test="id == ''">
		<h1>New Employee</h1>
		<s:hidden name="employee.lastname" value="%{employee.lastname}" id="emp_last" />
		<s:hidden name="employee.firstname" value="%{employee.firstname}" id="emp_first" />		
	</s:if>
	<s:else>
		<h1>Edit Employee</h1>
		<s:hidden id="employee_id" name="employee.id" value="%{employee.id}" />
		<s:hidden name="employee.ext_id" value="%{employee.ext_id}" />
	</s:else>
  <s:if test="hasActionErrors()">
		<div class="errors">
      <s:actionerror/>
		</div>
  </s:if>
  <s:elseif test="hasActionMessages()">
		<div class="welcome">
      <s:actionmessage/>
		</div>
  </s:elseif>
  <p>* Required field <br />
		<s:if test="id != ''">
			If you make any change, please hit the 'Save Changes' button
			<s:if test="employee.phone.hasOnePhoneExt()">
				** check this box to delete phone extension <br />
			</s:if>
		</s:if>
		<s:else>


			You must hit 'Save' button to save data.
		</s:else>
	</p>
	<div class="tt-row-container">
		<s:if test="employee.id != ''">		
			<dl class="fn1-output-field">
				<dt>ID </dt>
				<dd><s:property value="employee.id" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Last Name </dt>
				<dd><s:textfield name="employee.lastname" value="%{employee.lastname}" size="20" maxlength="60" required="true" id="emp_last" />* </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>First Name </dt>
				<dd><s:textfield name="employee.firstname" value="%{employee.firstname}" size="20" maxlength="30" id="emp_first" /> </dd>
			</dl>
		</s:if>
		<s:else>
			<dl class="fn1-output-field">
				<dt>Employee Name **</dt>
				<dd><s:textfield name="employee.fullname" value="%{employee.fullname}" size="20" maxlength="60" required="true" id="emp_name" title="start typing employee last name " />To add a new employee, start typing his/her last name then pick from the list 
				</dd>
			</dl>
		</s:else>
		<s:if test="employee.hasPhone()">				
			<dl class="fn1-output-field">
				<dt>Phone </dt>
				<dd><a href="<s:property value='#application.url' />phone.action?id=<s:property value='employee.phone_id' />"><s:property value="employee.phone" /></a></dd>
				<s:if test="employee.phone.hasPhoneExts()">
					<dd>Ext <s:select name="employee.ext_id" value="%{employee.ext_id}" list="employee.phone.phoneExts" listKey="id" listValue="ext_number" headerKey="-1" headerValue="Pick/Remove Ext" /></dd>
				</s:if>
				<s:elseif test="employee.phone.hasOnePhoneExt()"><dd>
					<s:checkbox name="employee.del_ext_id" value="%{employee.ext_id}" /> 
					Ext **<s:property value="%{employee.phoneExt}" /></dd>
				</s:elseif>
			</dl>
		</s:if>
		<dl class="fn1-output-field">
			<dt>Mail Box </dt>
			<dd><s:textfield name="employee.mailBox" value="%{employee.mailBox}" size="10" maxlength="10" /> </dd>
		</dl>
		<s:if test="employee.id == ''">
			<s:submit name="action" employee="button" value="Save" class="fn1-btn"/></dd>
		</s:if>
		<s:else>
			<s:submit name="action" employee="button" value="Save Changes" class="fn1-btn"/>
			<s:if test="employee.phone.hasOnePhoneExt()">
				<s:submit name="action" employee="button" value="Remove Extension" class="fn1-btn"/>
			</s:if>
		</s:else>
	</div>
</s:form>
<s:if test="employees != null">
	<s:set var="employees" value="employees" />
	<s:set var="employeesTitle" value="employeesTitle" />
	<%@  include file="employees.jsp" %>
</s:if>
<%@  include file="footer.jsp" %>


