import http from 'k6/http';

export const options = {
    // https://grafana.com/docs/k6/latest/using-k6/scenarios/#scenario-executors
    scenarios: {
        createOrder_shared_iterations: {
            executor: 'constant-arrival-rate',
            rate: 10,
            timeUnit: '1s',
            duration: '2m',
            preAllocatedVUs: 40
        }
    }
}
export default function () {
    const PORT = __ENV.PORT || "8080";
    const url = `http://localhost:${PORT}/api/orders`;

    const headers = {
        'Content-Type': 'application/json'
    };

    const payload = JSON.stringify({
        "articleId": "25984125-ebb5-4d43-81a7-033bee9cf6df",
        "customerId": "b8b4a824-89d0-432a-ae60-718928d78e99",
        "quantity": 0
    });

    http.post(url, payload, {headers: headers});
}