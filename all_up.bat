@echo off
kubectl get svc
kubectl get deployment

kubectl create -f .\kubernetes-config-files\etcd.yaml
kubectl create -f .\kubernetes-config-files\postgres-customers.yaml
kubectl create -f .\kubernetes-config-files\postgres-orders.yaml
kubectl create -f .\kubernetes-config-files\order-service.yaml
kubectl create -f .\kubernetes-config-files\customer-service.yaml

timeout 10

kubectl create -f .\kubernetes-config-files\order-deployment.yaml
kubectl create -f .\kubernetes-config-files\customer-deployment.yaml


kubectl get svc
kubectl get deployment