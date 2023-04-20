<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
<s:form action="paymentReport" method="post">    
  <h3> Payment Report</h3>
	Note: Charts option will display total payments' pie chart for the year or date range selected.<br />
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
		<table border="1" width="90%">
			<tr>
				<td>
					<table width="100%">
						<tr><td colspan="2">You may choose year or date range </td></tr>
						<tr><td>Year:</td><td><s:select name="report.year" value="%{report.year}" list="years" listKey="id" listValue="name" headerKey="-1" headerValue="Pick Year" /></td></tr>
						<tr>
							<td>Date:</td>
							<td>from <s:textfield name="report.date_from" value="%{report.date_from}" size="10" maxlength="10" cssClass="date" /> to <s:textfield name="report.date_to" value="%{report.date_to}" size="10" maxlength="10" cssClass="date" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<s:submit name="action" type="submit" value="Submit" class="fn1-btn" />
		<s:submit name="action" type="submit" value="Charts" class="fn1-btn" />
	</div>

</s:form>
<%@  include file="footer.jsp" %>	






































