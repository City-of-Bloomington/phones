<%@  include file="header.jsp" %>
<h3>Welcome to Phones</h3>
<s:if test="hasActionErrors()">
  <div class="errors">
    <s:actionerror/>
  </div>
</s:if>
<div class="tt-row-container">			
Please Select one of the following options from the top menu:
<ul>
	<li> To add a new phone line, select 'Phones' tab</li>
	<li> To add a new special circuit line, select 'Special Circuits'</li>	
	<li> To add a new billing, select 'Billings'</li>
	<li> To add a new vendor, select 'Vendors'</li>	
	<li> To search for certain phone, select the 'Search'</li>
	<li> To run reports, select the 'Reports'</li>
	<li> When you are done, click on your name link in top and then click 'Logout' </li>
</ul>
</div>

<%@  include file="footer.jsp" %>
