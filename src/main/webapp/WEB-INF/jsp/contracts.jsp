<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#contractsTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>Name</b></th>
			<th align="center"><b>Related Billing</b></th>						
			<th align="center"><b>Start Date</b></th>
			<th align="center"><b>End Date</b></th>
			<th align="center"><b>Notification Date</b></th>
			<th align="center"><b>Days before Expire</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#contracts">
			<tr>
				<td><a href="<s:property value='#application.url' />contract.action?id=<s:property value='id' />"><s:property value="name" /></a></td>
				<td><a href="<s:property value='#application.url' />billing.action?id=<s:property value='billing_id' />"><s:property value="billing_id" /></a></td>	
				<td><s:property value="start_date" /></td>
				<td><s:property value="end_date" /></td>
				<td><s:property value="notification_date" /></td>
				<td><s:property value="days_before_expire" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>

