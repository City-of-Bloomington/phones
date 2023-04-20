<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#disposesTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Name</b></th>
			<th align="center"><b>Scan Date-Time</b></th>
			<th align="center"><b>Decision?</b></th>
			<th align="center"><b>User</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#disposes">
			<tr>
				<s:if test="#toDispose">
					<td><input type="checkbox" name="todispose.dispose_ids" value="<s:property value='id' />"> - <s:property value="id" /></td>
				</s:if>
				<s:else>
					<td><s:property value="id" /></a></td>
				</s:else>
				<td><s:property value="name" /></td>
				<td><s:property value="date_time" /></td>				
				<td>
					<s:if test="agree">
						Yes
					</s:if>
					<s:else>&nbsp;</s:else>
				</td>
				<td>
					<s:if test="agree">
						<s:property value="user" />
					</s:if>					
					<s:else>&nbsp;</s:else>
				</td>				
			</tr>
		</s:iterator>
	</tbody>
</table>
