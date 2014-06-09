/**
 * 
 */
function addRow(tableID, traineeName, traineeGrade) 
{
 
	var table = document.getElementById(tableID);
	var rowCount = table.rows.length;
	var row = table.insertRow(rowCount);
 
	var cell1 = row.insertCell(0);
	cell1.innerHTML = traineeName
	 
    var cell2 = row.insertCell(1);
    cell2.innerHTML = rowCount + 1;
	 
    var cell3 = row.insertCell(2);
    cell3.innerHTML = traineeGrade;
}