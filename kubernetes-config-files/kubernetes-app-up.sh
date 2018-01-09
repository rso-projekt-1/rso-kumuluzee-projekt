#!/bin/bash

# etcd

kubectl create -f etcd-service.yaml
kubectl create -f etcd.yaml  

# user

kubectl create -f postgres-customers-service.yaml
kubectl create -f postgres-customers.yaml
kubectl create -f customer-service.yaml
kubectl create -f customer-deployment.yaml

# video

kubectl create -f postgres-orders-service.yaml
kubectl create -f postgres-orders.yaml
kubectl create -f order-service.yaml
kubectl create -f order-deployment.yaml

# info

kubectl get pods
kubectl get deployment
kubectl get service
