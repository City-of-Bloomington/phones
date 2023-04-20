<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:if test="#circuits">
	<table class="fn1-table">
		<caption><s:property value="#circuitsTitle" /></caption>
		<thead>
			<tr>
				<th align="center"><b>Name</b></th>
				<th align="center"><b>Model</b></th>
				<th align="center"><b>Address</b></th>
				<th align="center"><b>Location</b></th>
				<th align="center"><b>Dept & Division</b></th>				
				<th align="center"><b>Billing</b></th>
				<th align="center"><b>Notes</b></th>
				<th align="center"><b>Active?</b></th>
			</tr>
		</thead>
		<tbody>
			<s:iterator var="one" value="#circuits">
				<tr>
					<td><a href="<s:property value='#application.url' />circuit.action?id=<s:property value='id' />"><s:property value="name" /></a></td>
					<td><s:property value="model" /></td>
					<td><s:property value="address" /></td>
					<td><s:property value="location" /></td>
					<td><s:property value="departmentInfo" /></td>					
					<td><s:property value="billing" /></td>
					<td><s:property value="notes" /></td>
					<td><s:property value="type" /></td>
					<td><s:if test="active" />Yes</if><s:else>No</s:else></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</s:if>
