<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<h3>Line Users </h3>
Note:to add a new line user, use the phone menu option <br />
<s:if test="hasPlines()">
	<s:set var="plines" value="plines" />
	<s:set var="plinesTitle" value="plinesTitle" />
	<s:if test="hasNextPage()">
		<s:set var="nextPage" value="nextPage" />
	</s:if>
	<s:if test="hasPrevPage()">
		<s:set var="prevPage" value="prevPage" />
	</s:if>
	<s:set var="pageNumber" value="pageNumber" />	
	<%@  include file="plines.jsp" %>
</s:if>
<%@  include file="footer.jsp" %>


