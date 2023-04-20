<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#scansTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Date & Time</b></th>
			<th align="center"><b>Outcome?</b></th>
			<th align="center"><b>Failure Reason</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#scans">
			<tr>
				<td><s:property value="id" /></a></td>
				<td><s:property value="time" /></td>
				<td><s:property value="outcome" /></td>
				<td><s:property value="failure_reason" />&nbsp;</td>				
			</tr>
		</s:iterator>
	</tbody>
</table>
