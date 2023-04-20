<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<?xml version="1.0" encoding="UTF-8" ?>
<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
  <head>
    <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
			var payType = ['Base Monthly',
			'911 Emergency',
			'Federal. Universal',
			'IN Universal',
			'IN Utility Receipt',
			'Telecommunication',
			'Additional Charges',
			'Information Charges',
			'AT&T Internet ',
			'Local Toll Charges',
			'Reports ',
			'YP Charges',
			'Credits',
			'Total'];
			var payValue = [
			<s:iterator var="one" value="totalArr" status="row">
				<s:property /><s:if test="#row.last"></s:if><s:else>,</s:else>
			</s:iterator>
			];
      // Load the Visualization API and the corechart package.
      google.charts.load('current', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.charts.setOnLoadCallback(drawChart);

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
      function drawChart() {
      // Create the data table.
      var data = new google.visualization.DataTable();
      data.addColumn('string', 'Payment Type');
      data.addColumn('number', 'Total');
			for(var i=0;i< payType.length-2;i++){
				if(payValue[i] < 0) payValue[i] = -payValue[i];
				data.addRows([payType[i],payValue[i]]);
			}
        // Set chart options
        var options = {'title':'Total Payments by Type',
                       'width':400,
                       'height':300};

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script>
  </head>
  <body>
    <!--Div that will hold the pie chart-->
    <div id="chart_div"></div>
  </body>
</html>


