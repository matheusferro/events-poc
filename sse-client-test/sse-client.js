var EventSource = require("eventsource")

var sseClient = new EventSource("http://localhost:8080/user/updated-stream", { withCredentials: false });

sseClient.onmessage = (e) => {
    console.log("--- Received a new message ---");
    console.log(e);
}

sseClient.onopen = (e) => { console.log("opened connection", e) }

sseClient.onerror = (e) => { console.log("connection error", e) }

