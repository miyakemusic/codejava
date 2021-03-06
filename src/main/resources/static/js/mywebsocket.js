class MyWebSocket {
	constructor(callback, onClose) {
		var connection = new WebSocket('ws://' + window.location.host + '/ws');
		connection.onopen = function(e) {
			console.log(e);
		}
		connection.onerror = function(error) {
			console.log(error);
		}
		connection.onmessage = function(e) {
			var obj = JSON.parse(e.data);
			callback(obj);
		}
		connection.onclose = function() {
			onClose();
			console.log('websocket closed');
		}
	}
}