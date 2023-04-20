<?xml version="1.0" encoding="UTF-8" ?>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<s:head />
	<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />
	<link rel="SHORTCUT ICON" href="./images/favicon.ico" />
	<link rel="stylesheet" href="//bloomington.in.gov/static/fn1-releases/dev/css/default.css" type="text/css" />
	<link rel="stylesheet" href="//bloomington.in.gov/static/fn1-releases/dev/css/kirkwood.css" type="text/css" />
	<link rel="stylesheet" href="./js/ol.css" type="text/css" />

  
	<style>
	table.money th{
		text-align:center;
		font-size: 70%;
		border:2px solid blue;
	}
	table.money td{
		text-align:right;
		font-size: 70%;
		border:2px solid blue;
	}
	table.money{
		border:2px solid blue;
	}
	</style>


		
  <title>Phones</title>
  <script type="text/javascript">
    var APPLICATION_URL = '<s:property value='#application.url' />';
  </script>
</head>
<body class="fn1-body">
  <header class="fn1-siteHeader">
    <div class="fn1-siteHeader-container">
      <div class="fn1-site-title">
        <h1 id="application_name"><a href="<s:property value='#application.url'/>">Phones</a></h1>
        <div class="fn1-site-location" id="location_name"><a href="<s:property value='#application.url'/>">City of Bloomington, IN</a></div>
      </div>
      <s:if test="#session != null && #session.user != null">
        <div class="fn1-site-utilityBar">
          <nav id="user_menu">
            <div class="menuLauncher"><s:property value='#session.user.fullname' /></div>
            <div class="menuLinks closed" style="background-color:wheat">
							<br />
              <a href="<s:property value='#application.url'/>Logout">Logout</a>
            </div>
          </nav>
          <s:if test="#session.user.isAdmin()">					
						<nav id="admin_menu">
							<div class="menuLauncher">Admin</div>
							<div class="menuLinks closed" style="background-color:wheat">
								<br />
								<a href="./managePhones.action">Manage Phones</a>
								<a href="./report.action">Stats Report</a>
								<a href="./reportPhone.action">Phones Report</a>								
								<a href="./paymentReport.action">Payments Report</a>
								<a href="./payment.action">Payments</a>				
								<a href="./vendor.action">Vendors</a>
								<a href="./address.action">Addresses</a>								
								<a href="./user.action">Users</a>
								<a href="./import.action">Data Import</a>								
							</div>
						</nav>
          </s:if>
        </div>
	  </s:if>
	</div>
	<div class="fn1-nav1">
      <nav class="fn1-nav1-container">
				<a href="<s:property value='#application.url'/>phone.action">Phones</a>
				<a href="<s:property value='#application.url'/>circuit.action">Special Circuits</a>
				<a href="<s:property value='#application.url'/>cellDevice.action">Cell Devices</a>
				<a href="<s:property value='#application.url'/>billing.action">Billing</a>
				<a href="<s:property value='#application.url'/>contract.action">Contract</a>				
				<a href="<s:property value='#application.url'/>pline.action">Line Users</a>
				<a href="<s:property value='#application.url'/>todispose.action">Dispose Lines</a>
				<a href="<s:property value='#application.url'/>search.action">Land Line Search</a>
				<a href="<s:property value='#application.url'/>wirelessSearch.action">Wireless Search</a>
      </nav>
    </div>
  </header>
  <main>
    <div class="fn1-main-container">
