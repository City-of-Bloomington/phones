<%@  include file="header.jsp" %>
<!--
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<h3><s:property value="reportTitle" /></h3>
	<s:set var="payments" value="payments" />
	<s:set var="totalArrStr" value="totalArrStr" />
	<s:set var="paymentsTitle" value="reportTitle" />
	<%@  include file="payments.jsp" %>
<%@  include file="footer.jsp" %>	























































