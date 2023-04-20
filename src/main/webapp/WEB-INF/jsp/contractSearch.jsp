<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="contractSearch" id="form_id" method="post" >
	<h1>Search Contracts</h1>
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
			<dt>Contract ID </dt>
			<dd><s:textfield name="contralst.id" value="%{contralst.id}" size="10" maxlength="10"  /> </dd>				
		</dl>		
		<dl class="fn1-output-field">
			<dt>Billing </dt>
			<dd><s:select name="contralst.billing_id" value="%{contralst.billing_id}"            list="billings" listKey="id" listValue="name" headerKey="-1" headerValue="All"  /> </dd>
		</dl>			
		<dl class="fn1-output-field">
			<dt>Vendor </dt>
			<dd><s:select name="contralst.vendor_id" value="%{contralst.vendor_id}"            list="vendors" listKey="id" listValue="name" headerKey="-1" headerValue="All"  /> </dd>			
		</dl>
		<dl class="fn1-output-field">
			<dt>Date Type</dt>		
			<dd><s:radio name="contralst.whichDate" value="contralst.whichDate" list="#{'c.start_date':'Start Date','c.end_date':'End Date','c.notification_date':'Notification Date'}" />(Date type that you want to be used in date range below)</dd>
		</dl>						
		<dl class="fn1-output-field">
			<dt>Date Range</dt>
			<dd> from:<s:textfield name="contralst.date_from" value="%{contralst.date_from}" size="10" maxlength="10"  cssClass="date" /> to: <s:textfield name="contralst.date_to" value="%{contralst.date_to}" size="10" maxlength="10"  cssClass="date" /> </dd>				
		</dl>
		<dl class="fn1-output-field">			
			<dt>Expire Status </dt>
			<dd><s:radio name="contralst.expire_status" value="%{contralst.expire_status}" list="#{'all':'All','expired':'Expired','active':'Active'}" /></dd>
		</dl>
	</div>
	<s:submit name="action" type="button" value="Submit" class="fn1-btn"/>
</s:form>
<s:if test="hasContracts()">
	<s:set var="contracts" value="contracts" />
	<s:set var="contractsTitle" value="contractsTitle" />
	<%@  include file="contracts.jsp" %>
</s:if>
<%@  include file="footer.jsp" %>


