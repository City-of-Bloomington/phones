<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@  include file="header.jsp" %>
<s:form action="report" method="post">    
  <h3> Phones Stats Report</h3>
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
						<tr>
							<td class="money">1- </td>		
							<td><s:checkbox name="report.signal_type" value="report.signal_type" />Signal Type</td>
						</tr>
						<tr>
							<td class="money">2- </td>				
							<td><s:checkbox name="report.type" value="report.type" />Use Types</td>
						</tr>
						<tr>
							<td class="money">3- </td>				
							<td><s:checkbox name="report.dept" value="report.dept" />Departments</td>
						</tr>
						<tr>
							<td class="money">4- </td>		
							<td><s:checkbox name="report.addr" value="report.addr" />Addresses</td>
						</tr>
						<tr>
							<td class="money">5- </td>				
							<td><s:checkbox name="report.bill" value="report.bill" />Billings</td>
						</tr>
						<tr>
							<td class="money">6- </td>				
							<td><s:checkbox name="report.vendor" value="report.vendor" />Vendors</td>
						</tr>		
						<tr>
							<td class="money">7- </td>	
							<td><s:checkbox name="report.status" value="report.status" />Active Status</td>
						</tr>
						<tr>
							<td colspan="2">(Optional: you may specify the billing and/or deparment for your report)</td>
						</tr>
						<tr>
							<td>Billing</td>
							<td>
								<s:if test="hasBillings()">
									<s:select name="report.billing_id" value="reprot.billing_id" list="billings" listKey="id" listValue="name" headerKey="-1" headerValue="All" />
								</s:if>
							</td>
						</tr>
						<tr>
							<td>Departments</td> 
							<td>
								<s:if test="hasDepartments()">
									<s:select name="report.department_id" value="reprot.department_id" list="departments" listKey="id" listValue="name" headerKey="-1" headerValue="All" />
								</s:if>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<s:submit name="action" type="button" value="Submit" class="fn1-btn" />
	</div>

</s:form>
<%@  include file="footer.jsp" %>	






































