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
	<link rel="stylesheet" href="<s:property value='#application.url' />css/jquery-ui.min-1.13.2.css" type="text/css" media="all" />
	<link rel="stylesheet" href="<s:property value='#application.url' />css/jquery-ui.theme.min-1.13.2.css" type="text/css" media="all" />
	<link rel="stylesheet" href="<s:property value='#application.url' />css/open-sans/open-sans.css" type="text/css" />
	<link rel="stylesheet" href="//bloomington.in.gov/static/fn1-releases/dev/css/default.css" type="text/css" />
	<link rel="stylesheet" href="//bloomington.in.gov/static/fn1-releases/dev/css/kirkwood.css" type="text/css" />
	<link rel="stylesheet" href="<s:property value='#application.url' />css/screen.css" type="text/css" />
	<link rel="stylesheet" href="<s:property value='#application.url' />css/ol.css" type="text/css" />
	<link rel="stylesheet" href="<s:property value='#application.url' />css/ol3-popup.css" type="text/css" />
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
		<div class="menuLauncher">		
		    <a href="<s:property value='#application.url'/>Logout">Logout</a>
		</div>
            </nav>
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
          <s:if test="#session.user.isAdmin()">
	      <a href="<s:property value='#application.url'/>adminOptions.action">Admin Options</a>
	  </s:if>
      </nav>
    </div>
  </header>
  <main>
    <div class="fn1-main-container">
