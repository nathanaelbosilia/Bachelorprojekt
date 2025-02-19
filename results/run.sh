#!/bin/bash

# wait until 5 seconds after the next minute to start the experiment
sleep $((60 - $(date +%S) + 5))

# start the chaos mesh experiment
# kubectl apply -f chaosmesh/monolith-pod-killer.yaml
# kubectl apply -f chaosmesh/ordering-ms-pod-killer.yaml
kubectl apply -f chaosmesh/random-ms-pod-killer.yaml

# start the k6 load test
k6 run --env PORT="54165" k6/create-orders-test-constant-arrival-rate.ts > results/experiment3b/microservices/run5.txt