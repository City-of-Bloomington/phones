<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="circuit" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id != ''">
		<h1>Edit Special Circuit: <s:property value="circuit.name" /></h1>
		<s:hidden id="circuit.id" name="circuit.id" value="%{id}" />
	</s:if>
	<s:else>
		<h1>New Special Circuit</h1>
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
		<div class="tt-split-container">		
			<s:if test="circuit.id != ''">
				<dl class="fn1-output-field">
					<dt>ID </dt>
					<dd><s:property value="circuit.id" /> </dd>
				</dl>
			</s:if>
			<dl class="fn1-output-field">
				<dt>Name </dt>
				<dd><s:textfield name="circuit.name" value="%{circuit.name}" size="20" maxlength="30" required="true" titl="circuit name" />* </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Model </dt>
				<dd><s:textfield name="circuit.model" value="%{circuit.model}" size="10" maxlength="20" titl="circuit model" /> </dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Address </dt>
				<dd>
					<s:if test="hasAddresses()">
					<s:select name="circuit.address_id" value="%{circuit.address_id}" list="addresses" listKey="id" listValue="name" headerKey="-1" headerValue="Pick address"  />
					</s:if>
				</dd>
			</dl>			
			<dl class="fn1-output-field">
				<dt>Location</dt>
				<dd><s:textfield name="circuit.location" value="%{circuit.location}" size="20" maxlength="30" title="in addition to address you can specify room room, loby, etc" /></dd>
			</dl>
			<dl class="fn1-output-field">
				<dt>Department </dt>
				<dd>
					<s:if test="hasDepartments()">
						<s:select name="circuit.department_id" value="%{circuit.department_id}" list="departments" listKey="id" listValue="name" headerKey="-1" headerValue="Pick department"  />
					</s:if>
				</dd>
			</dl>
			<s:if test="circuit.hasDivisions()">
				<dl class="fn1-output-field">
					<dt>Division </dt>
					<dd><s:select name="circuit.division_id" value="%{circuit.division_id}" list="circuit.divisions" listKey="id" listValue="name" headerKey="-1" headerValue="Pick division"  /> </dd>
				</dl>
			</s:if>
		</div>
		<div class="tt-split-container">
			<dl class="fn1-output-field">
				<dt>Billing </dt>
				<dd>
					<s:if test="hasBillings()">
						<s:select name="circuit.billing_id" value="%{circuit.billing_id}" list="billings" listKey="id" listValue="name" headerKey="-1" headerValue="Pick billing"  />
					</s:if>
				</dd>
			</dl>			
			<dl class="fn1-output-field">			
				<dt>Is Active? </dt>
				<dd><s:checkbox name="circuit.active" value="%{circuit.active}" title="check if this line is still in service" />Yes</dd>
			</dl>			
			<dl class="fn1-output-field">			
				<dt>Notes </dt>
				<dd><s:textarea name="circuit.notes" value="%{circuit.notes}" cols="50" rows="5" title="any notes about this line" />	</dd>
			</dl>
		</div>
	</div>
	<s:if test="circuit.id == ''">
		<s:submit name="action" type="button" value="Save" class="fn1-btn"/>
	</s:if>
	<s:else>
		<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
		<a href="<s:property value='#application.url' />doUpload.action?circuit_id=<s:property value='id' />" class="fn1-btn">Attachments</a>			
		<s:submit name="action" type="button" value="Cancel This Circuit" class="fn1-btn" onclick="return verifyCancel();" />
	</s:else>
</s:form>
<s:if test="circuit.id == ''">
	<s:if test="hasCircuits()">
		<s:set var="circuits" value="circuits" />
		<s:set var="circuitsTitle" value="circuitsTitle" />
		<%@  include file="circuits.jsp" %>
	</s:if>
</s:if>
<%@  include file="footer.jsp" %>


