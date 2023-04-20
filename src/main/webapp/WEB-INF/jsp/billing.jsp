<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="billing" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id != ''">
		<h1>Edit Billing: <s:property value="billing.name" /></h1>
		<s:hidden id="billing.id" name="billing.id" value="%{id}" />
	</s:if>
	<s:else>
		<h1>New Billing</h1>
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
		<s:if test="billing.id != ''">
			<dl class="fn1-output-field">
				<dt>ID </dt>
				<dd><s:property value="billing.id" /> </dd>
			</dl>
		</s:if>
		<dl class="fn1-output-field">
			<dt>Foundation # </dt>
			<dd><s:textfield name="billing.foundation_account_num" value="%{billing.foundation_account_num}" size="30" maxlength="20" required="true" title="account number" />* </dd>
			</dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Account # </dt>
			<dd><s:textfield name="billing.account_num" value="%{billing.account_num}" size="30" maxlength="20" required="true" title="account number" />* </dd>
			</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Name </dt>
			<dd><s:textfield name="billing.name" value="%{billing.name}" size="30" maxlength="128" required="true" titl="billing name" />* </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Department</dt>
			<dd>
				<s:if test="hasDepartments()">
					<s:select name="billing.department_id" value="%{billing.department_id}" list="departments" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Dept"  />
				</s:if>
			</dd>
		</dl>
		<s:if test="billing.hasDivisions()">
			<dl class="fn1-output-field">
				<dt>Division </dt>
				<dd><s:select name="billing.division_id" value="%{billing.division_id}" list="billing.divisions" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Division"  /> </dd>
			</dl>
		</s:if>
		<dl class="fn1-output-field">
			<dt>Vendor </dt>
			<dd>
				<s:if test="hasVendors()">
					<s:select name="billing.vendor_id" value="%{billing.vendor_id}" list="vendors" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Vendor"  />
				</s:if>					
			</dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Address </dt>
			<dd><s:textfield name="billing.address" value="%{billing.address}" size="30" maxlength="128" title="vendor address" /> </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>City </dt>
			<dd><s:textfield name="billing.city" value="%{billing.city}" size="20" maxlength="40" title="vendor city " /> State: <s:textfield name="billing.state" value="%{billing.state}" size="2" maxlength="2" title="two letter state name" />  Zip code: <s:textfield name="billing.zip" value="%{billing.zip}" size="5" maxlength="10" title="5 or 10 numbers zip code xxxxx or xxxxx-xxxx format" /> </dd>
		</dl>
	</div>
	<s:if test="billing.id == ''">
		<s:submit name="action" type="button" value="Save" class="fn1-btn"/>
	</s:if>
	<s:else>
		<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
		<a href="<s:property value='#application.url' />doUpload.action?obj_type=Billing&obj_id=<s:property value='id' />" class="fn1-btn">Attachments</a>			
	</s:else>
</s:form>
<s:if test="billing.id == ''">
	<s:if test="hasBillings()">
		<s:set var="billings" value="billings" />
		<s:set var="billingsTitle" value="billingsTitle" />
		<%@  include file="billings.jsp" %>
	</s:if>
</s:if>
<s:else>
	<s:if test="billing.hasContracts()">
		<s:set var="contracts" value="%{billing.contracts}" />
		<s:set var="contractsTitle" value="'This bill contracts'" />
		<%@  include file="contracts.jsp" %>					
	</s:if>
	<s:if test="billing.hasAttachments()">
  <s:set var="uploads" value="%{billing.fileUploads}" />
	<s:set var="attachmentsTitle" value="'Related Attachments'" />
  <%@  include file="fileUploads.jsp" %>			
	</s:if>
</s:else>
<%@  include file="footer.jsp" %>


