<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#billingsTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>Name</b></th>
			<th align="center"><b>Foundation #</b></th>			
			<th align="center"><b>Account #</b></th>
			<th align="center"><b>Department & Division</b></th>
			<th align="center"><b>Vendor</b></th>
			<th align="center"><b>Address</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#billings">
			<tr>
				<td><a href="<s:property value='#application.url' />billing.action?id=<s:property value='id' />"><s:property value="name" /></a></td>
				<td><s:property value="foundation_account_num" /></td>								
				<td><s:property value="account_num" /></td>				
				<td><s:property value="departmentInfo" /></td>
				<td><s:property value="vendor" /></td>
				<td><s:property value="fullAddress" /></td>
			</tr>
		</s:iterator>
	</tbody>
</table>

