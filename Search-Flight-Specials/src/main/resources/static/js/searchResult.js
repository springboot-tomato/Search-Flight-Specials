//画面が開いたときに実行
window.onload = function() {
	//searchFlightからJsonデータを取得
	const flightDetails = JSON.parse(localStorage.getItem('flightDetails'));
	const selectOption = JSON.parse(localStorage.getItem('selectOption'))
	//Jsonデータを取得した場合にカードに航空券の情報を入れる
	if (flightDetails && Array.isArray(flightDetails)) {
		let cardsHtml = "";
		//片道・往復で分けて実施
		if (selectOption == 1) {
			$.each(flightDetails, function(index, flight) {
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
			});
		}
		else {
			$.each(flightDetails, function(index, flight) {
				cardsHtml += `
                    <div class="col">
                        <div class="card h-100">
                            <div class="card-header bg-secondary text-white">
                                出発：${flight.inbound.carrier || 'N/A'}
								<hr style = "margin-top:5px;margin-bottom:3px">
								復路：${flight.outbound.carrier || 'N/A'}
                            </div>
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-6">
                                        <h5 class="card-title">${flight.inbound.departureTerminal || 'N/A'}</h5>
                                        <p class="card-text">${flight.inbound.departureAt[1] || 'N/A'}</p>
										<h5 class="card-title">${flight.outbound.departureTerminal || 'N/A'}</h5>
                                       	<p class="card-text">${flight.outbound.departureAt[1] || 'N/A'}</p>
                                    </div>
                                    <div class="col-6 text-end">
                                        <h5 class="card-title">${flight.inbound.arrivalTerminal || 'N/A'}</h5>
                                        <p class="card-text">${flight.inbound.arrivalAt[1] || 'N/A'}</p>
										<h5 class="card-title">${flight.outbound.arrivalTerminal || 'N/A'}</h5>
                                        <p class="card-text">${flight.outbound.arrivalAt[1] || 'N/A'}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="card-footer">
                                <strong>料金: ${flight.inbound.fee || 'N/A'}</strong>
                            </div>
                        </div>
                    </div>
                `;
			});
		}



		$("#results").html(cardsHtml);
	} else {
		console.error("flightDetails is not available or not an array.");
		$("#results").html("<p class='alert alert-warning'>フライト情報が見つかりません。</p>");
	}
}