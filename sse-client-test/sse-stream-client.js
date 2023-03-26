const config = require('./commons.js');
const EventSource = require("eventsource");

var sseClient = new EventSource(config.DEFAULT_URL + "/user/updated-stream-sse", config.EVENTSOURCE_OPTS);

sseClient.onmessage = (e) => {
    console.log("--- Received a new message ---");
    console.log(e);
}

sseClient.addEventListener(
    "USER_CREATED",
    (e) => {
        console.log("--- Received a new USER_CREATED event ---");
        console.log(e);
    }
)

sseClient.onopen = (e) => { console.log("opened connection", e) }

sseClient.onerror = (e) => { console.log("connection error", e) }

