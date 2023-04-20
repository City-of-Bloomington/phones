<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<h3><s:property value="reportTitle" /></h3>
<s:iterator value="report.all">
	<s:iterator status="status">
		<s:if test="#status.index == 0">
			<h4> <s:property value="title" /></h4>
			<table border="1" width="60%">
				<tr>
					<td><label><s:property value="first" /></label></td>		
					<td><label><s:property value="second" /></label></td>
					<s:if test="size == 3">
						<td><label><s:property value="third" /></label></td>
					</s:if>
				</tr>
		</s:if>
		<s:else>
			<tr>
				<td><s:property value="first" /></td>
				<s:if test="size == 2">
					<td align="right"><s:property value="second" /></td>
				</s:if>
				<s:else>
					<td><s:property value="second" /></td>
				</s:else>
				<s:if test="size == 3">
					<td align="right"><s:property value="third" /></td>
				</s:if>		
			</tr>
			<s:if test="#status.last">
			</table>			
			</s:if>
		</s:else>
	</s:iterator>
</s:iterator>

<%@  include file="footer.jsp" %>	























































