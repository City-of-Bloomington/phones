<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="phone" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id != ''">
		<h1>Edit Phone: <s:property value="phone.phoneNumber" /></h1>
		<s:hidden id="phone.id" name="phone.id" value="%{id}" />
	</s:if>
	<s:else>
		<h1>New phone</h1>
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
			<s:if test="phone.hasPLines()">			
			** mark the checkbox in front of each line user's name and click on 'Remove Line Users' button <br />
			</s:if>
			<s:else>
			** Enter either a Service Name or Employee name but not both
			</s:else>
		</s:if>
		<s:else>
			You must hit 'Save' button to save data. <br />
		</s:else>
	</p>
	<div class="tt-row-container">
		<div class="tt-split-container">		
			<s:if test="phone.id != ''">
				<dl class="fn1-output-field">
					<dt>ID </dt>
					<dd><s:property value="phone.id" /> </dd>
				</dl>
				<s:if test="phone.hasPlines()">
					<dl class="fn1-output-field">
						<dt>Line User(s) </dt>
						<td>**  
						<s:iterator var="one" value="phone.plines">
							<input type="checkbox" name="phone.del_pline_ids" value="<s:property value='id' />" /> <s:property value="info" />	
						</s:iterator>
						</td>									
					</dl>
				</s:if>
				<s:else>
					<dl class="fn1-output-field">
						<dt>New Service Name </dt>
						<td>** <s:textfield name="phone.name" value="" size="20" maxlength="70" title="Type the service name" />(Service name only, to add an employee use 'New Employee' field below)
						</td>									
					</dl>
					<dl class="fn1-output-field">
						<dt>New Employee </dt>
						<td>** <s:textfield name="phone.fullname" value="" size="20" maxlength="70" id="emp_name" title="Type employee fullname then pick from list" />
							<s:if test="phone.hasPhoneExts()">			
							Ext:	<s:select name="phone.emp_ext_id" value="" list="%{phone.phoneExts}" listKey="id" listValue="ext_number" headerKey="-1" headerValue="Pick Extention" />
							</s:if>							
						</td>									
					</dl>					
				</s:else>
			</s:if>
			<dl class="fn1-output-field">
				<dt>Number </dt>
				<dd><s:textfield name="phone.phoneNumber" value="%{phone.phoneNumber}" size="12" maxlength="12" placeHolder="000-000-0000" required="true" titl="phone number in xxx-xxx-xxxx format" id="p_phone" />* </dd>
			</dl>
			<s:if test="phone.hasPhoneExts()">			
				<dl class="fn1-output-field">
					<dt>Extension(s) </dt>			
					<dd><s:iterator var="one" value="phone.phoneExts" status="rowStatus">
							<s:property value="ext_number" /><s:if test="!#rowStatus.last">, </s:if>
						</s:iterator>
					</dd>
				</dl>					
			</s:if>
			<dl class="fn1-output-field">
				<dt>New Extension(s) </dt>						
				<dd><s:textfield name="phone.ext_numbers" value="" size="15" maxlength="30" title="phone extension in 0000 format, for more than one use 0000 0000 0000 space separated numbers "/>You can add one or multiple phone extensions by separating them with a space <br />
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Signal Type</dt>
				<dd>
					<s:radio name="phone.signal_type" value="%{phone.signal_type}" list="#{'Analog':'Analog','Digital':'Digital','Cellular':'Cellular'}" />*
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Model </dt>
				<dd><s:textfield name="phone.model" value="%{phone.model}" size="20" maxlength="20" titl="phone model" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Address </dt>
				<dd>
					<s:if test="hasAddresses()">
						<s:select name="phone.address_id" value="%{phone.address_id}" list="addresses" listKey="id" listValue="name" headerKey="-1" headerValue="Pick address"  />
					</s:if>
				</dd>
			</dl>			
			<dl class="fn1-output-field">
				<dt>Location</dt>
				<dd><s:textfield name="phone.location" value="%{phone.location}" size="25" maxlength="30" title="in addition to address you can specify room room, loby, etc" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Other use</dt>
				<dd><s:textfield name="phone.otherUse" value="%{phone.otherUse}" size="20" maxlength="30" title="Any other use than reqular desk use" /></dd>
			</dl>			
			<dl class="fn1-output-field">
				<dt>Department </dt>
				<dd>
					<s:if test="hasDepartments()">
						<s:select name="phone.department_id" value="%{phone.department_id}" list="departments" listKey="id" listValue="name" headerKey="-1" headerValue="Pick department"  />
					</s:if>
				</dd>
			</dl>
			<s:if test="phone.hasDivisions()">
				<dl class="fn1-output-field">
					<dt>Division </dt>
					<dd><s:select name="phone.division_id" value="%{phone.division_id}" list="phone.divisions" listKey="id" listValue="name" headerKey="-1" headerValue="Pick division"  /> </dd>
				</dl>					
			</s:if>
			<dl class="fn1-output-field">
				<dt>Billing </dt>
				<dd>
					<s:if test="hasBillings()">
						<s:select name="phone.billing_id" value="%{phone.billing_id}"            list="billings" listKey="id" listValue="name" headerKey="-1" headerValue="Pick billing"  />
					</s:if>
				</dd>
			</dl>			
			<dl class="fn1-output-field">
				<dt>Port </dt>
				<dd><s:textfield name="phone.port" value="%{phone.port}" size="20" maxlength="30" title="connection port" /></dd>
			</dl>
		</div>
		<div class="tt-split-container">
			<dl class="fn1-output-field">
				<dt>Wall Plate </dt>
				<dd><s:radio name="phone.wallPlate" value="%{phone.wallPlate}" list="#{'Red':'Red','Green':'Green','Blue':'Blue','Yellow':'Yellow'}" title="wall plate color" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Wall Plate #</dt>
				<dd><s:textfield name="phone.wallPlateNum" value="%{phone.wallPlateNum}" size="20" maxlength="30" title="wall plate number" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Use type </dt>
				<dd><s:radio name="phone.type" value="%{phone.type}" list="#{'Phone':'Phone','Fax':'Fax line','Elevator':'Elevator phone','FireAlarm':'FireAlarm'}" title="phone line use" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Active Status </dt>			
				<s:if test="phone.hasPLines()">
					<dd>Active</dd>
				</s:if>
				<s:else>
					<dd>Inactive</dd>
				</s:else>
			</dl>
			<dl class="fn1-output-field">			
				<dt>Notes </dt>
				<dd><s:textarea name="phone.notes" value="%{phone.notes}" cols="50" rows="5" title="any notes about this line" />	</dd>
			</dl>
		</div>
	</div>
	<s:if test="phone.id == ''">
		<s:submit name="action" type="button" value="Save" class="fn1-btn"/>
	</s:if>
	<s:else>
		<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
		<s:if test="phone.hasPlines()">
			<s:submit name="action" type="button" value="Remove Line User(s)" class="fn1-btn" />
		</s:if>
		<s:elseif test="phone.canBeDeleted()">
			<s:submit name="action" type="button" value="Delete" class="fn1-btn"/>
		</s:elseif>
	</s:else>
</s:form>
<s:if test="phone.id == ''">
	<s:if test="hasPhones()">
		<s:set var="phonesTitle" value="phonesTitle" />
		<s:set var="phones" value="phones" />
		<s:if test="hasNextPage()">
			<s:set var="nextPage" value="nextPage" />
		</s:if>
		<s:if test="hasPrevPage()">
			<s:set var="prevPage" value="prevPage" />
		</s:if>
		<s:set var="pageNumber" value="pageNumber" />		
		<%@  include file="phones.jsp" %>
	</s:if>
</s:if>
<%@  include file="footer.jsp" %>


