
<%@ taglib uri="/struts-tags" prefix="s" %>
<% 
response.setHeader("Expires", "0");
response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
response.setHeader("Pragma", "public");
response.setHeader("Content-Disposition","inline; filename=phone_list.csv");
response.setContentType("application/csv");
%>
<s:iterator value="report.rows"><s:iterator status="status">"<s:property value="first" />",	<s:property value="second" />,"<s:property value="third" />"
</s:iterator></s:iterator>
























































