<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="managePhones" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<h1>Manage Phones </h1>
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
	</p>
	<div class="tt-row-container">
		<dl class="fn1-output-field">
			<dt>New run </dt>
			<dd>Click on 'Start' to start the process of finding new employees on the directory with phones and find all employees that are not on the directory any more to remove
			</dd>
		</dl>
		<s:submit name="action" type="button" value="Start" class="fn1-btn"/>
</s:form>
<s:if test="action != ''">
	<s:if test="manage.hasDiscardEmps()">
		<s:set var="emps" value="manage.discardEmps" />
		<s:set var="empsTitle" value="'Employees can be removed '" />
		<%@  include file="ldapEmps.jsp" %>
	</s:if>	
	<s:if test="manage.hasNewEmps()">
		<s:set var="emps" value="manage.newEmps" />
		<s:set var="empsTitle" value="'New Employees added'" />
		<%@  include file="ldapEmps.jsp" %>
	</s:if>
</s:if>
<s:else>
	<s:if test="hasScans()">
		<s:set var="scans" value="scans" />
		<s:set var="scansTitle" value="scansTitle" />
		<%@  include file="scans.jsp" %>
	</s:if>
</s:else>
<%@  include file="footer.jsp" %>


