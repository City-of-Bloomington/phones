<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="serviceLine" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id == ''">
		<h1>New Service Line</h1>
	</s:if>
	<s:else>
		<h1>Edit Service Line</h1>
		<s:hidden id="serviceLine_id" name="serviceLine.id" value="%{serviceLine.id}" />
		<s:hidden name="serviceLine.phone_id" value="%{serviceLine.phone_id}" />
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
	<div class="tt-row-container">
		<s:if test="serviceLine.id != ''">		
		<dl class="fn1-output-field">
			<dt>ID </dt>
			<dd><s:property value="serviceLine.id" /> </dd>
		</dl>
		</s:if>		
		<dl class="fn1-output-field">
			<dt>Name </dt>
			<dd><s:textfield name="serviceLine.name" value="%{serviceLine.name}" size="20" maxlength="60" required="true" />* </dd>
		</dl>
		<dl class="fn1-output-field">
			<dt>Mail Box </dt>
			<dd><s:textfield name="serviceLine.mailBox" value="%{serviceLine.mailBox}" size="10" maxlength="10" /> </dd>
		</dl>
			<dl class="fn1-output-field">
				<dt>Phone </dt>
				<s:if test="serviceLine.hasPhone()">
					<dd><a href="<s:property value='#application.url' />phone.action?id=<s:property value='serviceLine.phone_id' />"><s:property value="serviceLine.phone" /></a></dd>
				</s:if>
				<s:else>
					<dd><s:textfield name="serviceLine.phone_number" value="%{serviceLine.phone_number}" size="12" maxlength="20" /> </dd>					
				</s:else>					
			</dl>			
			<s:if test="serviceLine.id == ''">
			<s:submit name="action" serviceLine="button" value="Save" class="fn1-btn"/></dd>
		</s:if>
		<s:else>
			<s:submit name="action" serviceLine="button" value="Save Changes" class="fn1-btn"/>
		</s:else>
	</div>
</s:form>
<s:if test="serviceLines != null">
	<s:set var="serviceLines" value="serviceLines" />
	<s:set var="serviceLinesTitle" value="serviceLinesTitle" />
	<%@  include file="serviceLines.jsp" %>
</s:if>
<%@  include file="footer.jsp" %>


