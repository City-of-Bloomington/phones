<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<table class="fn1-table">
	<caption><s:property value="#plinesTitle" /></caption>
	<thead>
		<tr>
			<th align="center"><b>ID</b></th>
			<th align="center"><b>Name</b></th>
			<th align="center"><b>Use Type</b></th>
			<th align="center"><b>Phone</b></th>
			<th align="center"><b>Mail Box</b></th>
		</tr>
	</thead>
	<tbody>
		<s:iterator var="one" value="#plines">
			<tr>
				<td><a href="<s:property value='#application.url' />pline.action?id=<s:property value='id' />"> <s:property value="id" /></a></td>
				<td><s:property value="name" /></td>
				<td><s:property value="type" /></td>				
				<td>
					<s:if test="hasPhone()">
						<s:property value="phone" />
					</s:if>
					<s:else>&nbsp;</s:else>							
				</td>
				<td>
					<s:if test="hasPhoneExt()">
						<s:property value="phoneExt.mail_box" />
					</s:if>
					<s:else>&nbsp;</s:else>					
				</td>				
			</tr>
		</s:iterator>
	</tbody>
</table>
<p style="text-align:center">
	<s:if test="#prevPage != null">
	<a href="<s:property value='#prevPage' />"> < Prev Page </a>
	</s:if>
	Page: <s:property value='#pageNumber' />
	<s:if test="#nextPage != null">
		<a href="<s:property value='#nextPage' />">Next Page > </a>
	</s:if>
</p>
