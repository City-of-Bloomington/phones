<!--
 * @copyright Copyright (C) 2014-2016 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<?xml version="1.0" encoding="UTF-8" ?>
<%@ taglib prefix="s" uri="/struts-tags" %>
<html>
  <head>
    <!--Load the AJAX API-->
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">

      // Load the Visualization API and the corechart package.
      google.charts.load('current', {'packages':['corechart']});

      // Set a callback to run when the Google Visualization API is loaded.
      google.charts.setOnLoadCallback(drawChart);

      // Callback that creates and populates a data table,
      // instantiates the pie chart, passes in the data and
      // draws it.
			var payType = [
				'Base Monthly',
				'911 Emergency',
				'Federal. Universal',
				'IN Universal',
				'IN Utility Receipt',
				
				'Telecommunication',
				'Additional Charges',
				'Information Charges',
				'AT&T Internet',
				'Local Toll Charges',
				
				'Reports ',
				'YP Charges',
				'Credits'
			];
			var payValue = [
			<s:iterator var="one" value="totalArr" status="row">
				<s:property /><s:if test="#row.last"></s:if><s:else>,</s:else>
			</s:iterator>];		

		function findPayTable(){
			var payTable = [];
			for(var j=0;j<payType.length;j++){
				var temp = [];
				temp[0] = payType[j]+" $"+payValue[j];
				temp[1] = payValue[j];
				payTable[j] = temp;
			};
			return payTable;
		}
    function drawChart() {

      // Create the data table.
			var payTable = findPayTable();
			var data = new google.visualization.DataTable();
				data.addColumn('string', 'Payment Type');
				data.addColumn('number', 'Totals');
				data.addRows(payTable);
        // Set chart options
				var options = {'title':'<s:property value="chartTitle" />',
					'width':800,
					'height':400,
					'chartArea':{'left':10}
				};

        // Instantiate and draw our chart, passing in some options.
        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
    </script>
  </head>

  <body>
    <!--Div that will hold the pie chart-->
    <div id="chart_div"></div>

		Note: to have more details hoover your mouse on the slices of the chart
  </body>
</html>


