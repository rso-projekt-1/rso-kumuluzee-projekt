@echo off
kubectl get svc
kubectl get deployment

kubectl create -f .\kubernetes-config-files\order-service.yaml
kubectl create -f .\kubernetes-config-files\customer-service.yaml
kubectl create -f .\kubernetes-config-files\video-service.yaml
kubectl create -f .\kubernetes-config-files\playlists-service.yaml
kubectl create -f .\kubernetes-config-files\recommender-service.yaml
kubectl create -f .\kubernetes-config-files\rating-service.yaml
kubectl create -f .\kubernetes-config-files\friends-service.yaml
kubectl create -f .\kubernetes-config-files\subscribe-service.yaml

timeout 5

kubectl create -f .\kubernetes-config-files\order-deployment.yaml
kubectl create -f .\kubernetes-config-files\customer-deployment.yaml
kubectl create -f .\kubernetes-config-files\video-deployment.yaml
kubectl create -f .\kubernetes-config-files\playlists-deployment.yaml
kubectl create -f .\kubernetes-config-files\recommender-deployment.yaml
kubectl create -f .\kubernetes-config-files\rating-deployment.yaml
kubectl create -f .\kubernetes-config-files\friends-deployment.yaml
kubectl create -f .\kubernetes-config-files\subscribe-deployment.yaml


kubectl get svc
kubectl get deployment