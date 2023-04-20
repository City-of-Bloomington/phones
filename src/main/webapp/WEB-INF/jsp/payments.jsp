<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="money">
	<caption><s:property value="#paymentsTitle" /></caption>
	<thead>
		<tr>
			<th>ID</th>
			<th>Period</th>
			<th>Base Monthly</th>
			<th>911 Emergency</th>
			<th>Fed. Universal</th>
			<th>IN Universal</th>
			<th>IN Util Receipt </th>
			<th>Telecom </th>
			<th>Additional Charges </th>
			<th>Information Charges </th>
			<th>AT&T Internet </th>
			<th>Local Toll Charges </th>
			<th>Reports </th>
			<th>YP Charges </th>
			<th>Credits </th>
			<th>Total </th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#payments" status="row">
			<tr>
				<td><a href="<s:property value='#application.url' />payment.action?id=<s:property value='id' />"> <s:property value="id" /></a></td>
				<s:if test="#row.first">
					<td> <s:property value="periodWithYear" /></td>
				</s:if>
				<s:else>
					<td><s:property value="period" /></td>
				</s:else>
				<td class="money"><s:property value="base_monthly"   /></td>
				<td class="money"><s:property value="emergency"   /></td>
				<td class="money"><s:property value="federal_univ"   /></td>
				<td class="money"><s:property value="in_univ"   /></td>
				<td class="money"><s:property value="in_util_receipt"   /></td>
				<td class="money"><s:property value="telecom"   /></td>
				<td class="money"><s:property value="add_charge"   /></td>
				<td class="money"><s:property value="information"   /></td>
				<td class="money"><s:property value="internet"   /></td>
				<td class="money"><s:property value="local_toll"   /></td>
				<td class="money"><s:property value="reports"   /></td>
				<td class="money"><s:property value="yp"   /></td>
				<td class="money"><s:property value="credits"   /></td>
				<td class="money"><s:property value="total" /></td> 
			</tr>
		</s:iterator>
		<tr>
		<s:iterator var="one" value="#totalArrStr">
			<td class="money"><s:property /></td>
		</s:iterator>
		</tr>
	</tbody>
</table>
