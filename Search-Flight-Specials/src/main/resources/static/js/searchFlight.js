const ticketOptions = document.getElementsByName('ticketOption');
const originInput = document.getElementById('origin');
const destinationInput = document.getElementById('destination');
const departureDateInput = document.getElementById('departureDate');
const arrivalDateInput = document.getElementById('arrivalDate');
const originSearchResults = document.getElementById('originSearchResults');
const destinationSearchResults = document.getElementById('destinationSearchResults');
const resultTable = document.getElementById('resultTable');

document.getElementById('flight-search-form').addEventListener('submit', async (e) => {
	e.preventDefault();

	const selectOption = document.querySelector('input[name="ticketOption"]:checked').value;

	const origin = originInput.value;
	const destination = destinationInput.value;
	const departureDate = departureDateInput.value;
	const arrivalDate = arrivalDateInput.value;

	const newDepartureDate = departureDate.replace(/\//g, '-');
	const newArrivalDate = arrivalDate.replace(/\//g, '-');

	const csrfToken = document.querySelector("[name='_csrf']").getAttribute("content");

	var fetchUrl = '';

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
					body: JSON.stringify(results) // results データをJSONに変換して転送
				})
					.then(resp => resp.json())
					.then(data => {
						localStorage.setItem('flightDetails', JSON.stringify(data.flightDetails));
						window.location.href = "/search-results-page";
					})
					.catch(error => {
						console.error("Error sending data to Spring:", error);
					});
			})
	} catch (error) {
		console.error('Error:', error);
	}
});

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


const departurePicker = new tempusDominus.TempusDominus(document.getElementById('departureDatePicker'), {
	restrictions: {
		minDate: new Date()
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

const returnPicker = new tempusDominus.TempusDominus(document.getElementById('returnDatePicker'), {
	restrictions: {
		minDate: new Date()
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
		returnPicker.updateOptions({
			restrictions: {
				minDate: e.date
			}
		});
	}
});

document.querySelectorAll('input[name=ticketOption]').forEach((elem) => {
	elem.addEventListener("change", function(event) {
		updateReturnDateVisibility();
	});
});

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

window.onload = updateReturnDateVisibility;
window.addEventListener('pageshow', updateReturnDateVisibility);