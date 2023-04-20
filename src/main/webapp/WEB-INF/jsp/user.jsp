<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="user" id="form_id" method="post">
	<s:hidden name="action2" id="action2" value="" />
	<s:if test="user.id == ''">
		<h1>New User</h1>
	</s:if>
	<s:else>
		<h1>User: <s:property value="user.fullname" /></h1>
		<s:hidden name="user.id" value="%{user.id}" />
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
	<s:if test="user.id != ''">
		<dl class="fn1-output-field">
			<dt>ID</dt>
			<dd><s:property value="%{user.id}" /></dd>
		</dl>
	</s:if>
	<dl class="fn1-output-field">
		<dt>Username</dt>
		<dd><s:textfield name="user.username" size="10" value="%{user.username}" required="true" /></dd>
	</dl>	
	<dl class="fn1-output-field">
		<dt>First Name </dt>
		<dd><s:textfield name="user.firstname" value="%{user.firstname}" size="30" maxlength="128" title="First name" /> </dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>last Name </dt>
		<dd><s:textfield name="user.lastname" value="%{user.lastname}" size="30" maxlength="128" required="true" title="Last name" /> </dd>
	</dl>	
	<dl class="fn1-output-field">
		<dt>Roles</dt>
		<dd><s:radio name="user.role" value="%{user.role}" list="#{'View':'View Only','Edit':'Edit','Admin':'Admin'}" /></dd>
	</dl>
	<dl class="fn1-output-field">
		<dt>Inactive?</dt>
		<dd><s:checkbox name="user.inactive" value="%{user.inactive}"  /> Yes (check to disable)</dd>
	</dl>		
	<dl class="fn1-output-field">
		<dt>Notification</dt>
		<dd><s:checkbox name="user.mailNotification" value="%{user.mailNotification}"  /> Yes (Subscribe to receive scheduled emails)</dd>
	</dl>	
	<s:if test="user.id == ''">
		<s:submit name="action" type="button" value="Save" class="fn1-btn"/>
	</s:if>
	<s:else>
		<s:submit name="action" type="button" value="Save Changes" class="fn1-btn"/>
	</s:else>

</s:form>
<s:if test="users != null && users.size() > 0">
	<s:set var="users" value="%{users}" />
	<s:set var="usersTitle" value="usersTitle" />
	<%@  include file="users.jsp" %>
</s:if>

<%@  include file="footer.jsp" %>


