window.onload = function() {
	const flightDetails = JSON.parse(localStorage.getItem('flightDetails'));

	if (flightDetails && Array.isArray(flightDetails)) {
		let tableBody = "";

		$.each(flightDetails, function(index, flight) {
			if (flight.direct === 1) {
				tableBody += "<tr>";
				tableBody += "<td>" + (flight.carrier || 'N/A') + "</td>";
				tableBody += "<td>" + (flight.departureAt[1] || 'N/A') + "</td>";
				tableBody += "<td>" + (flight.departureTerminal || 'N/A') + "</td>";
				tableBody += "<td>" + (flight.arrivalAt[1] || 'N/A') + "</td>";
				tableBody += "<td>" + (flight.arrivalTerminal || 'N/A') + "</td>";
				tableBody += "<td>" + (flight.fee || 'N/A') + "</td>";
				tableBody += "</tr>";
			}
		});

		$("#resultTable tbody").append(tableBody);
	} else {
		console.error("flightDetails is not available or not an array.");
	}
}
