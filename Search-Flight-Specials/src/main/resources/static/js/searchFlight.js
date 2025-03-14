//引数宣言
const ticketOptions = document.getElementsByName('ticketOption');
const originInput = document.getElementById('origin');
const destinationInput = document.getElementById('destination');
const departureDateInput = document.getElementById('departureDate');
const arrivalDateInput = document.getElementById('arrivalDate');
const originSearchResults = document.getElementById('originSearchResults');
const destinationSearchResults = document.getElementById('destinationSearchResults');
const resultTable = document.getElementById('resultTable');

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

	const origin = originInput.value;
	const destination = destinationInput.value;
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
						localStorage.setItem('flightDetails', JSON.stringify(data.flightDetails)); // dataデータをJSONに変換してsearchResultに転送
						window.location.href = "/search-results-page";
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
originInput.addEventListener('input', async function() {
	const originValue = originInput.value;
	try { console.log("後でｃｓｖから取得し、データを取得") }
	finally { }
})

destinationInput.addEventListener('input', async function() {
	const destinationValue = destinationInput.value;
	try { console.log("後でｃｓｖから取得し、データを取得") }
	finally { }
})

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