@echo off
kubectl get svc
kubectl get deployment

kubectl create -f .\kubernetes-config-files\order-service.yaml
kubectl create -f .\kubernetes-config-files\customer-service.yaml

timeout 5

kubectl create -f .\kubernetes-config-files\order-deployment.yaml
kubectl create -f .\kubernetes-config-files\customer-deployment.yaml


kubectl get svc
kubectl get deployment