<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
<s:form action="reportPhone" method="post">    
  <h3> Phones Report</h3>
  <s:if test="hasActionErrors()">
		<div class="errors">
      <s:actionerror/>
		</div>
  </s:if>
  <s:if test="hasActionMessages()">
		<div class="welcome">
      <s:actionmessage/>
		</div>
  </s:if>
	<div class="tt-row-container">
		<p>
			Optional: You may specify the department and output type<br />
			Default output is HTML
		</p>
		<table border="1" width="90%">
			<tr>
				<td>
					<table width="100%">
						<tr>
							<td>Departments</td> 
							<td>
								<s:if test="hasDepartments()">
									<s:select name="report.department_id" value="reprot.department_id" list="departments" listKey="id" listValue="name" headerKey="-1" headerValue="All" />
								</s:if>
							</td>
						</tr>
						<tr>
							<td>Output type</td> 
							<td><s:checkbox name="outputType" value="%{outputType}"	 />		CSV (to use with Excel)</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<s:submit name="action" type="button" value="Submit" class="fn1-btn" />
	</div>
</s:form>
<%@  include file="footer.jsp" %>	






































