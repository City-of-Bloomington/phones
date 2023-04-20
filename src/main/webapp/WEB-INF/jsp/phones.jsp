<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:if test="#phones">
	<table class="fn1-table">
		<caption><s:property value="#phonesTitle" /></caption>
		<thead>
			<tr>
				<th align="center"><b>Number</b></th>
				<th align="center"><b>Signal Type</b></th>
				<th align="center"><b>Address</b></th>
				<th align="center"><b>Location</b></th>
				<th align="center"><b>Dept & Division</b></th>				
				<th align="center"><b>Billing</b></th>
				<th align="center"><b>Wall Plate</b></th>
				<th align="center"><b>Use Type</b></th>
				<th align="center"><b>Active?</b></th>
			</tr>
		</thead>
		<tbody>
			<s:iterator var="one" value="#phones">
				<tr>
					<td><a href="<s:property value='#application.url' />phone.action?id=<s:property value='id' />"><s:property value="phoneNumber" /></a></td>
					<td><s:property value="signal_type" /></td>
					<td><s:property value="address" /></td>
					<td><s:property value="location" /></td>
					<td><s:property value="departmentInfo" /></td>					
					<td><s:property value="billing" /></td>
					<td><s:property value="wallPlateInfo" /></td>
					<td><s:property value="type" /></td>
					<td><s:if test="active">Yes</s:if><s:else>No</s:else></td>
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
