<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="contract" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id != ''">
		<h1>Edit Contract: <s:property value="contract.name" /></h1>
		<s:hidden id="contract.id" name="contract.id" value="%{id}" />
	</s:if>
	<s:else>
		<h1>New Contract</h1>
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
		<s:if test="id">
			If you make any change, please hit the 'Save Changes' button <br />
		</s:if>
		<s:else>
			You must hit 'Save' button to save data. <br />
		</s:else>
	</p>
	<div class="tt-row-container">
		<s:if test="contract.id != ''">
			<dl class="fn1-output-field">
				<dt>ID </dt>
				<dd><s:property value="contract.id" /> </dd>
			</dl>
		</s:if>
		<dl class="fn1-output-field">
			<dt>Name </dt>
			<dd><s:textfield name="contract.name" value="%{contract.name}" size="30" maxlength="128" required="true" titl="contract name" />* </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Related Billing</dt>
			<dd>
				<s:if test="hasBillings()">
					<s:select name="contract.billing_id" value="%{contract.billing_id}" list="billings" listKey="id" listValue="info" headerKey="-1" headerValue="Pick Billing" />
				</s:if>
			</dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Start Date </dt>
			<dd><s:textfield name="contract.start_date" value="%{contract.start_date}" size="10" maxlength="10" required="true" titl="contract start date" cssClass="date" />* </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>End Date </dt>
			<dd><s:textfield name="contract.end_date" value="%{contract.end_date}" size="10" maxlength="10" required="true" titl="contract end or expire date" cssClass="date" />* </dd>
		</dl>
		<s:if test="contract.id != ''">
			<dl class="fn1-output-field">
				<dt>Notification Date </dt>
				<dd><s:property value="contract.notification_date" /> (<s:property value="contract.days_before_expire" /> days for next notification email) </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Expires </dt>
				<dd><s:property value="contract.days_before_expire" /> (days from today) </dd>
			</dl>			
		</s:if>
		<dl class="fn1-output-field">
			<dt>Notes </dt>
			<dd><s:textarea name="contract.notes" value="%{contract.notes}" rows="5" cols="40" titl="any notes related to this contract" /> </dd>
		</dl>		
	</div>
	<s:if test="contract.id == ''">
		<s:submit name="action" type="button" value="Save" class="fn1-btn"/>
	</s:if>
	<s:else>
		<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
		<a href="<s:property value='#application.url' />doUpload.action?obj_type=Contract&obj_id=<s:property value='id' />" class="fn1-btn">Attachments</a>			
	</s:else>
	<a href="<s:property value='#application.url' />contractSearch.action" class="fn1-btn">Contracts Search</a>		
	
</s:form>
<s:if test="contract.id == ''">
	<s:if test="hasContracts()">
		<s:set var="contracts" value="contracts" />
		<s:set var="contractsTitle" value="contractsTitle" />
		<%@  include file="contracts.jsp" %>
	</s:if>
</s:if>
<s:else>
	<s:if test="contract.hasAttachments()">
  <s:set var="uploads" value="%{contract.fileUploads}" />
	<s:set var="attachmentsTitle" value="'Related Attachments'" />
  <%@  include file="fileUploads.jsp" %>			
	</s:if>
</s:else>
<%@  include file="footer.jsp" %>


