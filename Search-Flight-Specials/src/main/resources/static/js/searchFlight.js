const ticketOptions = document.getElementsByName('ticketOption');
const originInput = document.getElementById('origin');
const destinationInput = document.getElementById('destination');
const departureDateInput = document.getElementById('departureDate');
const arrivalDateInput = document.getElementById('arrivalDate');
const originSearchResults = document.getElementById('originSearchResults');
const destinationSearchResults = document.getElementById('destinationSearchResults');

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
		fetchUrl = 'https://test.api.amadeus.com/v2/shopping/flight-offers?' + `originLocationCode=${origin}&destinationLocationCode=${destination}&departureDate=${newDepartureDate}&currencyCode=USD&adults=1&travelClass=BUSINESS&max=10`
	}
	else {
		fetchUrl = 'https://test.api.amadeus.com/v2/shopping/flight-offers?' + `originLocationCode=${origin}&destinationLocationCode=${destination}&departureDate=${newDepartureDate}&returnDate=${newArrivalDate}&currencyCode=USD&adults=1&travelClass=BUSINESS&max=10`
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
						console.log("SpringにResponseされた後:", data);
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

$(function() {
	var dateFormat = "yy/mm/dd";
	$("#departureDate").datepicker({
		dateFormat: dateFormat,
		minDate: 0,
		changeMonth: true,
		onClose: function(selectedDate) {
			$("#arrivalDate").datepicker("option", "minDate", selectedDate);
		}
	});
	$("#arrivalDate").datepicker({
		dateFormat: dateFormat,
		changeMonth: true,
		onClose: function(selectedDate) {
			$("#departureDate").datepicker("option", "maxDate", selectedDate);
		}
	});
});

function toggleArrivalDate() {

	const selectOption = document.querySelector('input[name="ticketOption"]:checked').value;

	if (selectOption === '2') {
		arrivalDateInput.style.display = '';
		arrivalDateInput.required = true;
	} else {
		arrivalDateInput.style.display = 'none';
		arrivalDateInput.required = false;
	}

}

ticketOptions.forEach(option => { option.addEventListener('change', toggleArrivalDate) });
toggleArrivalDate();
