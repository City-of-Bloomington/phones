<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#vendorsTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Name</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#vendors">
			<tr>
				<td><a href="<s:property value='#application.url' />vendor.action?id=<s:property value='id' />"> <s:property value="id" /></a></td>
				<td><s:property value="name" /></td>				
			</tr>
		</s:iterator>
	</tbody>
</table>
