<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="search" id="form_id" method="post" >
	<h1>Search Land Lines</h1>
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
  <p> 
	</p>
	<div class="tt-row-container">
		<div class="tt-split-container">		
			<dl class="fn1-output-field">
				<dt>Phone ID </dt>
				<dd><s:textfield name="phonelst.id" value="%{phonelst.id}" size="10" maxlength="10"  /> </dd>				
			</dl>
			<dl class="fn1-output-field">
				<dt>User Name </dt>
				<dd><s:textfield name="phonelst.line_name" value="%{phonelst.line_name}" size="20" maxlength="30"  title="you can use partial name such as first name for an employee" />(Employee/Service) </dd>				
			</dl>
			<dl class="fn1-output-field">
				<dt>Phone Number</dt>
				<dd><s:textfield name="phonelst.phoneNumber" value="%{phonelst.phoneNumber}" size="12" maxlength="12" placeholder="000-000-0000" title="use 000-000-0000, 000-0000 or just 0000 format" />you may ignore area code </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Ext Number</dt>			
				<dd><s:textfield name="phonelst.ext_number" value="%{phonelst.ext_number}" size="12" maxlength="12" title="3 or 4 digits only" /> </dd>
			</dl>					
			<dl class="fn1-output-field">
				<dt>Signal Type</dt>
				<dd>
					<s:radio name="phonelst.signal_type" value="%{phonelst.signal_type}" list="#{'-1':'All','Analog':'Analog','Digital':'Digital','Cellular':'Cellular'}" />
				</dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Model </dt>
				<dd><s:textfield name="phonelst.model" value="%{phonelst.model}" size="10" maxlength="20" titl="phone model" /> </dd>
			</dl>
			<s:if test="addresses != null">
				<dl class="fn1-output-field">
					<dt>Address </dt>
					<dd>
						<s:if test="hasAddresses()">
							<s:select name="phonelst.address_id" value="%{phonelst.address_id}" list="addresses" listKey="id" listValue="name" headerKey="-1" headerValue="All"  />
						</s:if>
					</dd>
				</dl>
			</s:if>
			<s:if test="hasDepartments()">			
				<dl class="fn1-output-field">
					<dt>Department </dt>
					<dd><s:select name="phonelst.department_id" value="%{phonelst.department_id}" list="departments" listKey="id" listValue="name" headerKey="-1" headerValue="All"  /> </dd>
				</dl>
			</s:if>
			<s:if test="hasBillings()">					
				<dl class="fn1-output-field">
					<dt>Billing </dt>
					<dd><s:select name="phonelst.billing_id" value="%{phonelst.billing_id}"            list="billings" listKey="id" listValue="info" headerKey="-1" headerValue="All"  /> </dd>
				</dl>
			</s:if>
		</div>
		<div class="tt-split-container">
			<dl class="fn1-output-field">
				<dt>Wall Plate </dt>
				<dd><s:radio name="phonelst.wallPlate" value="%{phonelst.wallPlate}" list="#{'-1':'All','Red':'Red','Green':'Green','Blue':'Blue','Yellow':'Yellow'}" title="wall plate color" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Use type </dt>
				<dd><s:radio name="phonelst.type" value="%{phonelst.type}" list="#{'-1':'All','Phone':'Phone','Fax':'Fax line','Elevator':'Elevator phone','FireAlarm':'FireAlarm'}" title="phone line use" /></dd>
			</dl>			
			<dl class="fn1-output-field">			
				<dt>Status </dt>
				<dd><s:radio name="phonelst.activeStatus" value="%{phonelst.activeStatus}" list="#{'-1':'All','Active':'Active only','Inactive':'Inactive only'}" />Yes</dd>
			</dl>			
		</div>
	</div>
	<s:submit name="action" type="button" value="Submit" class="fn1-btn"/>
</s:form>
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
<%@  include file="footer.jsp" %>


