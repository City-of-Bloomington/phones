<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="cellDevice" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id != ''">
		<s:hidden id="cellDevice.id" name="cellDevice.id" value="%{id}" />		
		<h1>Edit Cell Device: <s:property value="cellDevice.wirelessNum" /></h1>
	</s:if>
	<s:else>
		<h1>New Cell Device</h1>
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
			If you make any change, please hit the 'Save Changes' button <br />
			If the phone line is not in service any more just remove the employee from the line <br />
		</s:if>
		<s:else>
			You must hit 'Save' button to save data. <br />
		</s:else>
	</p>
	<div class="tt-row-container">
		<div class="tt-split-container">		
			<s:if test="cellDevice.id != ''">
				<dl class="fn1-output-field">
					<dt>ID </dt>
					<dd><s:property value="cellDevice.id" /> </dd>
				</dl>
			</s:if>
			<s:if test="cellDevice.userName == ''">
				<dl class="fn1-output-field">
					<dt>New Employee/Use Name </dt>
					<td>** <s:textfield name="cellDevice.userName" value="" size="20" maxlength="70" id="emp_name" title="Type employee fullname then pick from list" />
					</td>									
				</dl>					
			</s:if>
			<s:else>
				<dl class="fn1-output-field">
					<dt>User Name </dt>
					<dd> <s:textfield name="cellDevice.userName" value="%{cellDevice.userName}" size="20" maxlength="70" title="Enter service use name or employee name" />
					</dd>									
				</dl>
			</s:else>
			<dl class="fn1-output-field">
				<dt>User Type</dt>
				<dd>
					<s:radio name="cellDevice.userType" value="%{cellDevice.userType}" list="#{'Employee':'Employee','Other':'Other'}" />*
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Service Type</dt>
				<dd>
					<s:radio name="cellDevice.serviceType" value="%{cellDevice.serviceType}" list="#{'B':'B','C':'C','D':'D','V':'V'}" />*
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Make </dt>
				<dd><s:textfield name="cellDevice.make" value="%{cellDevice.make}" size="20" maxlength="30" required="true" />* </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Model </dt>
				<dd><s:textfield name="cellDevice.model" value="%{cellDevice.model}" size="20" maxlength="30" required="true" />* </dd>
			</dl>			
			<dl class="fn1-output-field">
				<dt>Wireless Number </dt>
				<dd><s:textfield name="cellDevice.wirelessNum" value="%{cellDevice.wirelessNum}" size="12" maxlength="12" placeHolder="000-000-0000" required="true" titl="phone number in xxx-xxx-xxxx format" id="p_phone" />* </dd>
			</dl>
			
			<dl class="fn1-output-field">
				<dt>SIM Number </dt>
				<dd><s:textfield name="cellDevice.simNum" value="%{cellDevice.simNum}" size="25" maxlength="25" required="true" />* </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Device IMEI </dt>
				<dd><s:textfield name="cellDevice.deviceImei" value="%{cellDevice.deviceImei}" size="20" maxlength="20" required="true" />* </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Contract Start Date </dt>
				<dd><s:textfield name="cellDevice.contractStartDate" value="%{cellDevice.contractStartDate}" size="10" maxlength="10" class="date" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Contract End Date </dt>
				<dd><s:textfield name="cellDevice.contractEndDate" value="%{cellDevice.contractEndDate}" size="10" maxlength="10" class="date" /> </dd>
			</dl>			
			<dl class="fn1-output-field">
				<dt>Device Effective Date </dt>
				<dd><s:textfield name="cellDevice.deviceEffectiveDate" value="%{cellDevice.deviceEffectiveDate}" size="10" maxlength="10" class="date" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>User Effective Date </dt>
				<dd><s:textfield name="cellDevice.userEffectiveDate" value="%{cellDevice.userEffectiveDate}" size="10" maxlength="10" class="date" /> </dd>
			</dl>			
			<dl class="fn1-output-field">
				<dt>Department </dt>
				<dd>
					<s:if test="hasDepartments()">
						<s:select name="cellDevice.department_id" value="%{cellDevice.department_id}" list="departments" listKey="id" listValue="name" headerKey="-1" headerValue="Pick department"  />
					</s:if>
				</dd>
			</dl>
			<s:if test="cellDevice.hasDivisions()">
				<dl class="fn1-output-field">
					<dt>Division </dt>
					<dd><s:select name="cellDevice.division_id" value="%{cellDevice.division_id}" list="cellDevice.divisions" listKey="id" listValue="name" headerKey="-1" headerValue="Pick division"  /> </dd>
				</dl>					
			</s:if>
			<dl class="fn1-output-field">
				<dt>Billing </dt>
				<dd>
					<s:if test="hasBillings()">
						<s:select name="cellDevice.billing_id" value="%{cellDevice.billing_id}"            list="billings" listKey="id" listValue="info" headerKey="-1" headerValue="Pick billing"  />
					</s:if>
				</dd>
			</dl>			
			<dl class="fn1-output-field">
				<dt>Status </dt>			
				<dd><s:checkbox name="cellDevice.inactive" value="%{cellDevice.inactive}" />Inactive</dd>
			</dl>
			<dl class="fn1-output-field">			
				<dt>Notes </dt>
				<dd><s:textarea name="cellDevice.notes" value="%{cellDevice.notes}" cols="50" rows="5" title="any notes about this line" />	</dd>
			</dl>
		</div>
	</div>
	<s:if test="cellDevice.id == ''">
		<s:submit name="action" type="button" value="Save" class="fn1-btn"/>
	</s:if>
	<s:else>
		<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
		<s:submit name="action" type="button" value="Delete" class="fn1-btn"/>
	</s:else>
</s:form>
<s:if test="cellDevice.id == ''">
	<s:if test="hasCellDevices()">
		<s:set var="cellDevicesTitle" value="cellDevicesTitle" />
		<s:set var="cellDevices" value="cellDevices" />
		<s:if test="hasNextPage()">
			<s:set var="nextPage" value="nextPage" />
		</s:if>
		<s:if test="hasPrevPage()">
			<s:set var="prevPage" value="prevPage" />
		</s:if>
		<s:set var="pageNumber" value="pageNumber" />		
		<%@  include file="cellDevices.jsp" %>
	</s:if>
</s:if>
<%@  include file="footer.jsp" %>


