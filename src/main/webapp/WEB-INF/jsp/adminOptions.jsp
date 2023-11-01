<%@  include file="header.jsp" %>
<h3>Admin Options</h3>
<s:if test="hasActionErrors()">
  <div class="errors">
    <s:actionerror/>
  </div>
</s:if>
<div class="tt-row-container">			
    Please Select one of the following options:
    <ul>
	<li>
	    <a href="./managePhones.action">Manage Phones</a>
	</li>
	<li>
	    <a href="./report.action">Stats Report</a>
	</li>
	<li>
	    <a href="./reportPhone.action">Phones Report</a>				</li>
	<li>
	    <a href="./paymentReport.action">Payments Report</a>
	</li>
	<li>
	    <a href="./payment.action">Payments</a>
	</li>
	<li>
	    <a href="./vendor.action">Vendors</a>
	</li>
	<li>
	    <a href="./address.action">Addresses</a>					</li>
	<li>
	    <a href="./user.action">Users</a>
	</li>
    </ul>
</div>

<%@  include file="footer.jsp" %>
