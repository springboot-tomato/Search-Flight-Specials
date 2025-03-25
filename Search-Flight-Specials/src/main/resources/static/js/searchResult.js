// 画面が開いたときに実行
window.onload = function() {
    // searchFlightからJsonデータを取得
    const flightData = JSON.parse(localStorage.getItem('flightDetails'));
    const selectOption = JSON.parse(localStorage.getItem('selectOption'));

    // Jsonデータを取得した場合、にカードに航空券の情報を入れる
    if (flightData && typeof flightData === 'object') {
        // 初期画面には直行便を表示
        displayFlights('direct');

        // 直行/経由 ラジオボタンクリック イベントリスナー追加
        document.querySelectorAll('input[name="flightType"]').forEach(radio => {
            radio.addEventListener('change', function() {
                const flightType = this.value;
                displayFlights(flightType);
            });
        });
    } else {
        console.error("flightDetails is not available or not an object.");
        $("#results").html("<p class='alert alert-warning'>フライト情報が見つかりません。</p>");
    }

    // 航空便の種類によって航空便情報を画面に表示
	function displayFlights(flightType) {
	    let cardsHtml = "";
	    let flights = [];

	    if (selectOption === '1') { // 片道
	        if (flightType === 'direct') {
	            flights = flightData.direct;
	        } else if (flightType === 'transit') {
	            flights = flightData.transit;
	        }
	    } else { // 往復
	        if (flightType === 'direct') {
	            flights = flightData.direct;
	        } else if (flightType === 'transit') {
	            flights = flightData.transit;
	        }
	    }

	    // フライト情報に基づいてカードHTMLを作成
	    if (flights && Array.isArray(flights)) { // flightsが配列であることを確認
	        $.each(flights, function(index, flight) {
	            if (flightType === 'direct') {
	                if (selectOption == 1) { // 片道
						if (flightType === 'direct') {
						    flights = flightData.direct;
						} else if (flightType === 'transit') {
						    flights = flightData.transit;
						}
	                } else { // 往復
					    if (flightType === 'direct') {
					        flights = flightData.direct;
					    } else if (flightType === 'transit') {
					        flights = flightData.transit;
					    }
					}
	            } else if (flightType === 'transit') {
	                // 経由便カード作成コード
	                cardsHtml += `
	                    <div class="col">
	                        <div class="card h-100">
	                            <div class="card-header bg-secondary text-white">
	                                ${flight.carrier || 'N/A'} (경유)
	                            </div>
	                            <div class="card-body">
	                                ${flight.segments.map(segment => `
	                                    <div class="row mb-2">
	                                        <div class="col-6">
	                                            <h5 class="card-title">${segment.departureTerminal || 'N/A'}</h5>
	                                            <p class="card-text">${segment.departureAt[1] || 'N/A'}</p>
	                                        </div>
	                                        <div class="col-6 text-end">
	                                            <h5 class="card-title">${segment.arrivalTerminal || 'N/A'}</h5>
	                                            <p class="card-text">${segment.arrivalAt[1] || 'N/A'}</p>
	                                        </div>
	                                    </div>
	                                `).join('')}
	                            </div>
	                            <div class="card-footer">
	                                <strong>料金: ${flight.fee || 'N/A'}</strong>
	                            </div>
	                        </div>
	                    </div>
	                `;
	            }
	        });
	    } else {
	        cardsHtml = "<p class='alert alert-warning'>フライト情報が見つかりません。</p>";
	    }

	    $("#results").html(cardsHtml); // 結果表示
	}
