<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="todispose" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<h1>Employees To Be Removed</h1>
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
	<p>After scaning directory service, a number of employees need to be removed either they left or mismatch their names. <br />
  <p>To remove those mark the checkboxes infront of their names that you want to be removed. Uncheck the ones you want to keep. The phone numbers and extensions will not be deleted and can be assigned to others.
	</p>
		<div class="tt-row-container">
		<s:if test="canDispose()">		
			<s:set var="disposes" value="todisposes" />
			<s:set var="disposesTitle" value="toBeDisposedTitle" />
			<s:set var="toDispose" value="'true'" />
			<%@  include file="disposes.jsp" %>
			<s:submit name="action" employee="button" value="Dispose" class="fn1-btn"/></dd>
		</s:if>
	</div>
</s:form>
<s:if test="haveDisposeds()">
	<s:set var="toDispose" value="'false'" />
	<s:set var="disposes" value="disposeds" />
	<s:set var="disposesTitle" value="disposedTitle" />
	<%@  include file="disposes.jsp" %>
</s:if>
<%@  include file="footer.jsp" %>


