#!/bin/bash

# etcd

kubectl delete -f etcd-service.yaml
kubectl delete -f etcd.yaml

# user

kubectl delete -f postgres-customers-service.yaml
kubectl delete -f postgres-customers.yaml
kubectl delete -f customer-service.yaml
kubectl delete -f customer-deployment.yaml

# video

kubectl delete -f postgres-orders-service.yaml
kubectl delete -f postgres-orders.yaml
kubectl delete -f order-service.yaml
kubectl delete -f order-deployment.yaml
 
