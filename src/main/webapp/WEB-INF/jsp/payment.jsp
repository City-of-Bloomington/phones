<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="payment" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id == ''">
		<h1>New Service Payment</h1>
	</s:if>
	<s:else>
		<h1>Edit Service Payment: <s:property value="payment.id" /> </h1>
		<s:hidden id="id" name="payment.id" value="%{payment.id}" />
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
		</s:if>
		<s:else>
			You must hit 'Save' button to save data.
		</s:else>
	</p>
	<table class="fn1-table">
		<tr>
			<th>Vendor </th>
			<td><s:select name="payment.vendor_id" value="%{payment.vendor_id}" list="vendors" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Vendor" /></td>
		</tr>
		<tr>
			<th>Period: Start </th>
			<td><s:textfield name="payment.start_period" value="%{payment.start_period}" size="10" maxlength="20" required="true" cssClass="date" />* </td><th>End</th><td> <s:textfield name="payment.end_period" value="%{payment.end_period}" size="10" maxlength="20" required="true" cssClass="date" />*</td>
		</tr>
		<tr>
			<th>Base Monthly</th>
			<td><s:textfield name="payment.base_monthly" value="%{payment.base_monthly}" size="10" maxlength="10" /></td>
			<th>911 Emergency</th>
			<td><s:textfield name="payment.emergency" value="%{payment.emergency}" size="10" maxlength="10" /></td>
		</tr>
		<tr>			
			<th>Fed. Universal</th>
			<td><s:textfield name="payment.federal_univ" value="%{payment.federal_univ}" size="10" maxlength="10" /></td>			

			<th>IN Universal</th>
			<td><s:textfield name="payment.in_univ" value="%{payment.in_univ}" size="10" maxlength="10" /></td>
		</tr>
		<tr>			
			<th>IN Util Receipt </th>
			<td><s:textfield name="payment.in_util_receipt" value="%{payment.in_util_receipt}" size="10" maxlength="10" /></td>
			<th>Telecommunication </th>
			<td><s:textfield name="payment.telecom" value="%{payment.telecom}" size="10" maxlength="10" /></td>
		</tr>
		<tr>			
			<th>Additional Charges </th>
			<td><s:textfield name="payment.add_charge" value="%{payment.add_charge}" size="10" maxlength="10" /></td>
			<th>Information Charges </th>
			<td><s:textfield name="payment.information" value="%{payment.information}" size="10" maxlength="10" /></td>
		</tr>
		<tr>			
			<th>AT&T Internet </th>
			<td><s:textfield name="payment.internet" value="%{payment.internet}" size="10" maxlength="10" /></td>
			<th>Local Toll Charges </th>
			<td><s:textfield name="payment.local_toll" value="%{payment.local_toll}" size="10" maxlength="10" /></td>
		</tr>
		<tr>
			<th>Reports </th>
			<td><s:textfield name="payment.reports" value="%{payment.reports}" size="10" maxlength="10" /></td>			
			<th>YP Charges </th>
			<td><s:textfield name="payment.yp" value="%{payment.yp}" size="10" maxlength="10" /></td>
		</tr>
		<tr>		
			<th>Credits </th>
			<td><s:textfield name="payment.credits" value="%{payment.credits}" size="10" maxlength="10" /></td>			
			<th>Total </th>
			<td><s:property value="%{payment.total}" /></td>
		</tr>
	</table>
		<s:if test="payment.id == ''">
			<s:submit name="action" payment="button" value="Save" class="fn1-btn"/>
		</s:if>
		<s:else>
			<s:submit name="action" payment="button" value="Save Changes" class="fn1-btn"/>
			<a href="<s:property value='#application.url' />doUpload.action?obj_type=Payment&obj_id=<s:property value='id' />" class="fn1-btn">Attachments</a>
			<a href="<s:property value='#application.url' />payment.action" class="fn1-btn">New Payment</a>
		</s:else>
</s:form>
<s:if test="payment.id != ''">
	<s:if test="contract.hasAttachments()">
  <s:set var="uploads" value="%{contract.fileUploads}" />
	<s:set var="attachmentsTitle" value="'Related Attachments'" />
  <%@  include file="fileUploads.jsp" %>			
	</s:if>
</s:if>
<s:else>
	<s:if test="payments != null">
		<s:set var="payments" value="payments" />
		<s:set var="totalArrStr" value="totalArrStr" />
		<s:set var="paymentsTitle" value="paymentsTitle" />
		<%@  include file="payments.jsp" %>
	</s:if>
</s:else>
<%@  include file="footer.jsp" %>


