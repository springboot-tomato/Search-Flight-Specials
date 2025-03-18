//引数宣言
const ticketOptions = document.getElementsByName('ticketOption');
const originInput = document.getElementById('origin');
const destinationInput = document.getElementById('destination');
const departureDateInput = document.getElementById('departureDate');
const arrivalDateInput = document.getElementById('arrivalDate');
const originSearchResults = document.getElementById('originSearchResults');
const destinationSearchResults = document.getElementById('destinationSearchResults');
const resultTable = document.getElementById('resultTable');
let debounceTimer;
let iataFlg = false;
let origin = "";
let destination = "";

//検索ボタンを押下した時の実施されるFunction
//AsyncでAmadeusAPIからデータを取得する際にFetchをAwait（Loading…機能）
document.getElementById('flight-search-form').addEventListener('submit', async (e) => {
	e.preventDefault();

	const searchButton = document.getElementById('searchButton');
	const loadingSpinner = document.getElementById('loadingSpinner');

	//検索ボタン非活性およびLoading活性
	searchButton.disabled = true;
	searchButton.classList.add('d-none');
	loadingSpinner.classList.remove('d-none');

	const selectOption = document.querySelector('input[name="ticketOption"]:checked').value;

	const departureDate = departureDateInput.value;
	const arrivalDate = arrivalDateInput.value;

	const newDepartureDate = departureDate.replace(/\//g, '-'); //'/\//g'は'/'の正規表現
	const newArrivalDate = arrivalDate.replace(/\//g, '-');

	const csrfToken = document.querySelector("[name='_csrf']").getAttribute("content");

	var fetchUrl = '';

	/*selectOption
		1→片道
		2→往復
	*/
	if (selectOption === '1') {
		fetchUrl = 'https://test.api.amadeus.com/v2/shopping/flight-offers?' + `originLocationCode=${origin}&destinationLocationCode=${destination}&departureDate=${newDepartureDate}&currencyCode=JPY&adults=1&travelClass=BUSINESS&max=10`
	}
	else {
		fetchUrl = 'https://test.api.amadeus.com/v2/shopping/flight-offers?' + `originLocationCode=${origin}&destinationLocationCode=${destination}&departureDate=${newDepartureDate}&returnDate=${newArrivalDate}&currencyCode=JPY&adults=1&travelClass=BUSINESS&max=10`
	}

	try {
		fetch(fetchUrl,
			{
				headers: {
					Authorization: `Bearer ${token}`
				}
			})
			.then(resp => resp.json())
			.then((results) => {
				console.log("SpringにRequestする前:", results);
				fetch('/api/search-results', {
					method: 'POST',
					headers: {
						'Content-Type': 'application/json',
						'X-CSRF-TOKEN': csrfToken
					},
					body: JSON.stringify(results) // resultsデータをJSONに変換してSpringに転送
				})
					.then(resp => resp.json())
					.then(data => {
						console.log(data)
						if (selectOption === '1') {
							//直行だけデータとして取得
							const flights = data.flightDetails.filter(flight => flight.direct === 1);
							localStorage.setItem('flightDetails', JSON.stringify(flights)); // dataデータをJSONに変換してsearchResultに転送
						}
						else {
							//直行だけデータとして取得、
							const outboundFlights = data.flightDetails.filter(flight => flight.departureTerminal === origin && flight.arrivalTerminal === destination && flight.direct === 1);
							const inboundFlights = data.flightDetails.filter(flight => flight.arrivalTerminal === origin && flight.departureTerminal === destination && flight.direct === 1);
							let roundTrips = [];
							//行きは直行帰りは乗継などで航空券の情報が間違うかもしれないので制御
							for (let i = 0; i < outboundFlights.length; i++) {
								let j = i;
								while (outboundFlights[i].fee != inboundFlights[j].fee) {
									j++;
									if (inboundFlights.length <= j) {
										break;
									}
								}
								if (inboundFlights.length <= j) {
									break;
								}
								roundTrips.push({
									outbound: outboundFlights[i],
									inbound: inboundFlights[j]
								})
							}
							localStorage.setItem('flightDetails', JSON.stringify(roundTrips));
						}
						localStorage.setItem('selectOption', JSON.stringify(selectOption));
//						window.location.href = "/search-results-page";
					})
					.catch(error => {
						console.error("Error sending data to Spring:", error);
						searchButton.disabled = false;
						searchButton.classList.remove('d-none');
						loadingSpinner.classList.add('d-none');
					});
			})
	} catch (error) {
		console.error('Error:', error);
		searchButton.disabled = false;
		searchButton.classList.remove('d-none');
		loadingSpinner.classList.add('d-none');
	}
});

/*
	検索：ソウル
	リターン：ICN、GMP（IATAコード）
*/

originInput.addEventListener('input', function() {

	clearTimeout(debounceTimer);
	debounceTimer = setTimeout(() => {
		const csrfToken = document.querySelector("[name='_csrf']").getAttribute("content");
		const originValue = this.value;
		const originSearchResults = document.getElementById('originSearchResults');

		function getByteLength(str) {
			return new Blob([str]).size;
		}

		//検索は4バイト超過16バイト以下の場合に実施
		if (getByteLength(originValue) < 4 || getByteLength(originValue) >= 16) return;
		//inputがFocusOutする時で実行されるため、Flagで制御
		if (originSearchResults.textContent.length === 0 && iataFlg) {
			iataFlg = false;
			return;
		}

		fetch('/api/search-iataCode', {
			method: 'POST',
			headers: {
				'Content-Type': 'application/json',
				'X-CSRF-TOKEN': csrfToken
			},
			body: JSON.stringify({ keyword: originValue })
		})
			.then(resp => {
				if (!resp.ok) {
					throw new Error('Network response was not ok');
				}
				return resp.json();
			})
			.then(data => {
				console.log(data)
				if (data.length != 0) {
					const dropdownList = document.createElement('ul');
					dropdownList.className = 'list-group';

					data.forEach(item => {
						const listItem = document.createElement('li');
						listItem.className = 'list-group-item list-group-item-action';
						listItem.textContent = item.label;
						listItem.addEventListener('click', () => {
							originInput.value = item.label;
							origin = item.value;
							originSearchResults.innerHTML = '';
						});
						dropdownList.appendChild(listItem);
					});

					originSearchResults.innerHTML = '';
					originSearchResults.appendChild(dropdownList);
				} else {
					originSearchResults.innerHTML = '<p class="text-muted">検索結果がありません</p>';
					iataFlg = true;
				}
			})
			.catch(error => {
				console.error('Error:', error);
			});
	}, 300);
});

destinationInput.addEventListener('input', async function() {
	const destinationValue = destinationInput.value;
	try { console.log("後でｃｓｖから取得し、データを取得") }
	finally { }
})

document.addEventListener('click', (event) => {
	if (!originInput.contains(event.target) && !originSearchResults.contains(event.target)) {
		originSearchResults.innerHTML = '';
	}
});

//本日より前の日、帰国日より後の日はキャレンダーから選択不可
const departurePicker = new tempusDominus.TempusDominus(document.getElementById('departureDatePicker'), {
	restrictions: {
		minDate: new Date(),
	},
	display: {
		components: {
			calendar: true,
			date: true,
			month: true,
			year: true,
			decades: false,
			clock: false
		},
		icons: {
			type: 'icons',
			date: 'fa fa-solid fa-calendar',
			previous: 'fa fa-solid fa-chevron-left',
			next: 'fa fa-solid fa-chevron-right'
		}
	},
	localization: {
		locale: 'ja'
	}
});

//出発日より前の日はキャレンダーから選択不可
const returnPicker = new tempusDominus.TempusDominus(document.getElementById('returnDatePicker'), {
	restrictions: {
		minDate: new Date(),
	},
	display: {
		components: {
			calendar: true,
			date: true,
			month: true,
			year: true,
			decades: false,
			clock: false
		},
		icons: {
			type: 'icons',
			date: 'fa fa-solid fa-calendar',
			previous: 'fa fa-solid fa-chevron-left',
			next: 'fa fa-solid fa-chevron-right'
		}
	},
	localization: {
		locale: 'ja'
	}
});

departurePicker.subscribe(tempusDominus.Namespace.events.change, (e) => {
	if (e.date) {
		const minReturnDate = new Date(e.date);
		minReturnDate.setHours(0, 0, 0, 0);

		returnPicker.updateOptions({
			restrictions: {
				minDate: minReturnDate
			}
		});
	}
});

returnPicker.subscribe(tempusDominus.Namespace.events.change, (e) => {
	if (e.date) {
		const maxDepartureDate = new Date(e.date);
		maxDepartureDate.setHours(23, 59, 59, 999);

		departurePicker.updateOptions({
			restrictions: {
				maxDate: maxDepartureDate
			}
		});
	}
});

//ラベルを選択した場合にキャレンダーを表示
departureDateInput.addEventListener('click', function() {
	departurePicker.toggle();
});

arrivalDateInput.addEventListener('click', function() {
	returnPicker.toggle();
});

//片道・往復のラジオボタンを選択した場合にupdateReturnDateVisibilityを実行
document.querySelectorAll('input[name=ticketOption]').forEach((elem) => {
	elem.addEventListener("change", function(event) {
		updateReturnDateVisibility();
	});
});

//片道・往復で表示されるContainerを制御
function updateReturnDateVisibility() {

	const selectOption = document.querySelector('input[name="ticketOption"]:checked').value;
	const returnDateContainer = document.getElementById("returnDateContainer");

	if (selectOption === '2') {
		returnDateContainer.classList.remove("d-none");
		arrivalDateInput.required = true;
	} else {
		returnDateContainer.classList.add("d-none");
		arrivalDateInput.required = false;
	}
}

//BackSpaceなどで往復選択で帰国日のContainerが表示されるため、１回実行
window.onload = updateReturnDateVisibility;
window.addEventListener('pageshow', updateReturnDateVisibility);