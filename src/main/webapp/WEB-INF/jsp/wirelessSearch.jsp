<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="wirelessSearch" id="form_id" method="post" >
	<h1>Wireless Search</h1>
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
		<dl class="fn1-output-field">
			<dt>Device ID </dt>
			<dd><s:textfield name="cellst.id" value="%{cellst.id}" size="10" maxlength="10"  /> </dd>				
		</dl>
		<dl class="fn1-output-field">
			<dt>User Name </dt>
			<dd><s:textfield name="cellst.userName" value="%{cellst.userName}" size="20" maxlength="30"  title="you can use employee name or service name" /> </dd>				
		</dl>
		<dl class="fn1-output-field">
			<dt>Wireless Number</dt>
			<dd><s:textfield name="cellst.wirelessNum" value="%{cellst.wirelessNum}" size="12" maxlength="12" title="device wireless number" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Service Type</dt>
			<dd>
				<s:radio name="cellst.serviceType" value="%{cellst.serviceType}" list="#{'-1':'All','B':'B','C':'C','D':'D','V':'V'}" />
			</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>User Type</dt>
			<dd>
				<s:radio name="cellst.userType" value="%{cellst.userType}" list="#{'-1':'All','Employee':'Employee','Other':'Other'}" />
			</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Make </dt>
			<dd><s:textfield name="cellst.make" value="%{cellst.make}" size="10" maxlength="20" title="device make" /> </dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Model </dt>
			<dd><s:textfield name="cellst.model" value="%{cellst.model}" size="10" maxlength="20" title="device model" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>SIM # </dt>
			<dd><s:textfield name="cellst.simNum" value="%{cellst.simNum}" size="25" maxlength="25" title="sim number" /> </dd>
		</dl>			
		<s:if test="hasDepartments()">			
			<dl class="fn1-output-field">
				<dt>Department </dt>
				<dd><s:select name="cellst.department_id" value="%{cellst.department_id}" list="departments" listKey="id" listValue="name" headerKey="-1" headerValue="All"  /> </dd>
			</dl>
		</s:if>
		<s:if test="hasBillings()">					
			<dl class="fn1-output-field">
				<dt>Billing </dt>
				<dd><s:select name="cellst.billing_id" value="%{cellst.billing_id}"            list="billings" listKey="id" listValue="info" headerKey="-1" headerValue="All"  /> </dd>
			</dl>
		</s:if>
		<dl class="fn1-output-field">			
			<dt>Status </dt>
			<dd><s:radio name="cellst.activeStatus" value="%{cellst.activeStatus}" list="#{'-1':'All','Active':'Active only','Inactive':'Inactive only'}" />Yes</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Contract Epire Date </dt>
			<dd>From:<s:textfield name="cellst.dateFrom" value="%{cellst.dateFrom}" size="10" maxlength="10" title="expire date starting from mm/dd/yyyy format" class="date" /> To:<s:textfield name="cellst.dateTo" value="%{cellst.dateTo}" size="10" maxlength="10" title="expire date ending at mm/dd/yyyy format" class="date" /></dd>
		</dl>				
	</div>
	<s:submit name="action" type="button" value="Submit" class="fn1-btn"/>
</s:form>
<s:if test="hasCellDevices()">
	<s:set var="cellsTitle" value="cellsTitle" />
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
<%@  include file="footer.jsp" %>


