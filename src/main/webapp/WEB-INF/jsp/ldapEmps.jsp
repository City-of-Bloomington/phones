<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#empsTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>Name</b></th>
			<th align="center"><b>Department</b></th>
			<th align="center"><b>Division</b></th>
			<th align="center"><b>Phone</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#emps">
			<tr>
				<td><s:property value="fullname" /></td>
				<td><s:property value="dept" /></td>
				<td><s:property value="division" /></td>
				<td><s:property value="phone_number" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>
