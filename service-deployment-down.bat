@echo off
kubectl get svc
kubectl get deployment

kubectl delete -f .\kubernetes-config-files\order-service.yaml
kubectl delete -f .\kubernetes-config-files\customer-service.yaml
kubectl delete -f .\kubernetes-config-files\video-service.yaml
kubectl delete -f .\kubernetes-config-files\playlists-service.yaml
kubectl delete -f .\kubernetes-config-files\recommender-service.yaml
kubectl delete -f .\kubernetes-config-files\rating-service.yaml


kubectl delete -f .\kubernetes-config-files\order-deployment.yaml
kubectl delete -f .\kubernetes-config-files\customer-deployment.yaml
kubectl delete -f .\kubernetes-config-files\video-deployment.yaml
kubectl delete -f .\kubernetes-config-files\playlists-deployment.yaml
kubectl delete -f .\kubernetes-config-files\recommender-deployment.yaml
kubectl delete -f .\kubernetes-config-files\rating-deployment.yaml


kubectl get svc
kubectl get deployment