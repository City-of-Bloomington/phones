<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="import" id="form_id" method="post" >
	<s:hidden name="action2" id="action2" value="" />
	<h1>Import CSV data</h1>
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
	<p>* indicate required field</p>
	<div class="tt-row-container">
		<dl class="fn1-output-field">
			<dt>Csv File </dt>
			<dd><s:select name="file_name" value="%{file_name}" list="%{fileList}" required="true" headerKey="-1" headerValue="Pick file" />* </dd>
		</dl>		
		<dl class="fn1-output-field">
			<dt>Department </dt>
			<dd><s:select name="dept_id" value="%{dept_id}" list="%{departments}" listKey="id" listValue="alias" headerKey="-1" headerValue="Pick Dept" />* </dd>
		</dl>		
		<s:submit name="action" type="button" value="Import" class="fn1-btn"/></dd>
	</div>
</s:form>
<%@  include file="footer.jsp" %>


