@echo off
kubectl get svc
kubectl get deployment

kubectl delete -f .\kubernetes-config-files\etcd.yaml
kubectl delete -f .\kubernetes-config-files\postgres-customers.yaml
kubectl delete -f .\kubernetes-config-files\postgres-orders.yaml
kubectl delete -f .\kubernetes-config-files\order-service.yaml
kubectl delete -f .\kubernetes-config-files\customer-service.yaml
kubectl delete -f .\kubernetes-config-files\order-deployment.yaml
kubectl delete -f .\kubernetes-config-files\customer-deployment.yaml

timeout 5

kubectl create -f .\kubernetes-config-files\etcd.yaml
kubectl create -f .\kubernetes-config-files\postgres-customers.yaml
kubectl create -f .\kubernetes-config-files\postgres-orders.yaml
kubectl create -f .\kubernetes-config-files\order-service.yaml
kubectl create -f .\kubernetes-config-files\customer-service.yaml

timeout 10

kubectl create -f .\kubernetes-config-files\order-deployment.yaml
kubectl create -f .\kubernetes-config-files\customer-deployment.yaml


