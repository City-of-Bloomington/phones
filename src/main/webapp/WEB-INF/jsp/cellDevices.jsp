<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:if test="#cellDevices">
	<table class="fn1-table">
		<caption><s:property value="#cellDevicesTitle" /></caption>
		<thead>
			<tr>
				<th align="center"><b>Wireless Number</b></th>
				<th align="center"><b>User</b></th>				
				<th align="center"><b>Make & Model</b></th>
				<th align="center"><b>Service Type</b></th>
				<th align="center"><b>User Type</b></th>
				<th align="center"><b>Dept & Division</b></th>				
				<th align="center"><b>Billing</b></th>
				<th align="center"><b>Contract Start, End Date</b></th>
				<th align="center"><b>Sim Number</b></th>				
				<th align="center"><b>Active?</b></th>
			</tr>
		</thead>
		<tbody>
			<s:iterator var="one" value="#cellDevices">
				<tr>
					<td><a href="<s:property value='#application.url' />cellDevice.action?id=<s:property value='id' />"><s:property value="wirelessNum" /></a></td>
					<td><s:property value="userName" /></td>					
					<td><s:property value="makeAndModel" /></td>
					<td><s:property value="serviceType" /></td>
					<td><s:property value="userType" /></td>					
					<td><s:property value="departmentInfo" /></td>
					<td><a href="<s:property value='#application.url' />billing.action?id=<s:property value='billing_id' />"><s:property value="billing.info" /></a></td>					
					<td><s:property value="contractStartEndDate" /></td>
					<td><s:property value="simNum" /></td>					
					<td><s:if test="inactive">No</s:if><s:else>Yes</s:else></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<p style="text-align:center">
		<s:if test="#prevPage != null">
			<a href="<s:property value='#prevPage' />"> < Prev Page </a>
		</s:if>
		<s:if test="#pageNumber != null">		
			Page: <s:property value='#pageNumber' />
		</s:if>			
		<s:if test="#nextPage != null">
			<a href="<s:property value='#nextPage' />">Next Page > </a>
		</s:if>
	</p>
	
</s:if>
