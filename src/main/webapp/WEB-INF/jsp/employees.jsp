<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#employeesTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Name</b></th>
			<th align="center"><b>Mail Box</b></th>
			<th align="center"><b>Phone</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#employees">
			<tr>
				<td><a href="<s:property value='#application.url' />employee.action?id=<s:property value='id' />"> <s:property value="id" /></a></td>
				<td><s:property value="fullname" /></td>
				<td><s:property value="mailBox" /></td>
				<td>
					<s:if test="hasPhone()">
						<s:property value="phone" />
					</s:if>
					<s:else>&nbsp;</s:else>
				</td>
			</tr>
		</s:iterator>
	</tbody>
</table>
