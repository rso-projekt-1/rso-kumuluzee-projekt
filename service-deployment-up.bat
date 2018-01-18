@echo off
kubectl get svc
kubectl get deployment

kubectl create -f .\kubernetes-config-files\order-service.yaml
kubectl create -f .\kubernetes-config-files\customer-service.yaml
kubectl create -f .\kubernetes-config-files\video-service.yaml
kubectl create -f .\kubernetes-config-files\playlists-service.yaml


timeout 5

kubectl create -f .\kubernetes-config-files\order-deployment.yaml
kubectl create -f .\kubernetes-config-files\customer-deployment.yaml
kubectl create -f .\kubernetes-config-files\video-deployment.yaml
kubectl create -f .\kubernetes-config-files\playlists-deployment.yaml

kubectl get svc
kubectl get deployment