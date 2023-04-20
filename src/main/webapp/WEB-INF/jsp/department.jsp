<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="division" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="division.id == ''">
		<h1>New Division</h1>
	</s:if>
	<s:else>
		<h1>Edit Division <s:property value="division.name" /></h1>
		<s:hidden name="division.id" value="%{division.id}" />
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
	<s:if test="division.id != ''">
		<dl class="fn1-output-field">
			<dt>ID</dt>
			<dd><s:property value="%{division.id}" /></dd>
		</dl>
	</s:if>
	<dl class="fn1-output-field">
		<dt>Name</dt>
		<dd><s:textfield name="division.name" size="10" value="%{division.name}" required="true" title="division name" /></dd>
	</dl>	
	<dl class="fn1-output-field">
		<dt>Alias </dt>
		<dd><s:textfield name="division.alias" value="%{division.alias}" size="20" maxlength="30" title="Alias name" /> </dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Department </dt>
		<dd><s:select name="division.department_id" value="%{division.department_id}" list="departments" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Dept" required="true" /> </dd>
	</dl>	
	<dl class="fn1-output-field">
		<dt>Long Distance Code </dt>
		<dd><s:textfield name="division.longDistanceCode" value="%{division.longDistanceCode}" size="10" maxlength="19" title="Long distance code" /> </dd>
	</dl>	
	<s:if test="division.id == ''">
		<s:submit name="action" type="button" value="Save" class="fn1-btn"/>
	</s:if>
	<s:else>
		<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
	</s:else>
</s:form>
<s:if test="divisions != null && divisions.size() > 0">
	<s:set var="divisions" value="%{divisions}" />
	<s:set var="divisionsTitle" value="divisionsTitle" />
	<%@  include file="divisions.jsp" %>
</s:if>

<%@  include file="footer.jsp" %>


