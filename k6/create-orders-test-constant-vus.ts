import http from 'k6/http';
import {check} from "k6";
import {Rate} from "k6/metrics";

export const errorRate = new Rate('errors');

export const options = {
    thresholds: {
        http_req_failed: ['rate<0.01'], // http errors should be less than 1%, availability
        http_req_duration: ['p(95)<200'], // 95% of requests should be below 200ms, latency
    },
    // https://grafana.com/docs/k6/latest/using-k6/scenarios/#scenario-executors
    scenarios: {
        createOrder_constant_vus: {
            executor: 'constant-vus',
            vus: 100,
            duration: '2m'
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

    let response = http.post(url, payload, {headers: headers});

    check(response, {
        'status is 201': (r) => r.status === 201,
        'response time < 200ms': (r) => r.timings.duration < 200,
        'no server errors': (r) => r.status < 500,
    });

    if (response.status != 201) {
        console.log(`operation: createOrder, url: ${url}, Status:${response.status}`);
        errorRate.add(1);
    }
}