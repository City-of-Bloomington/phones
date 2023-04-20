<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<h3><s:property value="reportTitle" /></h3>
<h4> <s:property value="title" /></h4>
<table border="1" width="90%">
	<tr><th>Department</th><th>Phone </th><th>User</th></tr>
	<s:iterator value="report.rows">
		<s:iterator status="status">
			<tr>
				<td><s:property value="first" /></td>		
				<td><s:property value="second" /></td>
				<td><s:property value="third" /></td>
			</tr>
		</s:iterator>
	</s:iterator>
</table>			
<%@  include file="footer.jsp" %>	























































