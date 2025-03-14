//画面が開いたときに実行
window.onload = function() {
	//searchFlightからJsonデータを取得
	const flightDetails = JSON.parse(localStorage.getItem('flightDetails'));

	//Jsonデータを取得した場合にカードに航空券の情報を入れる
	if (flightDetails && Array.isArray(flightDetails)) {
		let cardsHtml = "";

		$.each(flightDetails, function(index, flight) {
			if (flight.direct === 1) {
				cardsHtml += `
                    <div class="col">
                        <div class="card h-100">
                            <div class="card-header bg-secondary text-white">
                                ${flight.carrier || 'N/A'}
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-6">
                                        <h5 class="card-title">${flight.departureTerminal || 'N/A'}</h5>
                                        <p class="card-text">${flight.departureAt[1] || 'N/A'}</p>
                                    </div>
                                    <div class="col-6 text-end">
                                        <h5 class="card-title">${flight.arrivalTerminal || 'N/A'}</h5>
                                        <p class="card-text">${flight.arrivalAt[1] || 'N/A'}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="card-footer">
                                <strong>料金: ${flight.fee || 'N/A'}</strong>
                            </div>
                        </div>
                    </div>
                `;
			}
		});

		$("#results").html(cardsHtml);
	} else {
		console.error("flightDetails is not available or not an array.");
		$("#results").html("<p class='alert alert-warning'>フライト情報が見つかりません。</p>");
	}
}