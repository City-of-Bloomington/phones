<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="vendor" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="id == ''">
		<h1>New Vendor</h1>
	</s:if>
	<s:else>
		<h1>Edit Vendor</h1>
		<s:hidden id="id" name="vendor.id" value="%{vendor.id}" />
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
		<s:if test="vendor.id != ''">		
		<dl class="fn1-output-field">
			<dt>ID </dt>
			<dd><s:property value="vendor.id" /> </dd>
		</dl>
		</s:if>		
		<dl class="fn1-output-field">
			<dt>Name </dt>
			<dd><s:textfield name="vendor.name" value="%{vendor.name}" size="20" maxlength="50" required="true" />* </dd>
		</dl>
		<s:if test="vendor.id == ''">
			<s:submit name="action" vendor="button" value="Save" class="fn1-btn"/></dd>
		</s:if>
		<s:else>
			<s:submit name="action" vendor="button" value="Save Changes" class="fn1-btn"/>
		</s:else>
	</div>
</s:form>
<s:if test="vendors != null">
	<s:set var="vendors" value="vendors" />
	<s:set var="vendorsTitle" value="vendorsTitle" />
	<%@  include file="vendors.jsp" %>
</s:if>
<%@  include file="footer.jsp" %>


