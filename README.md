# jenkins
Jenkins - minikube and pod template

## 
- Jenkins configuration as code
- Jenkins pipeline as code
- Jenkins Shared Lib
- Jenkins Dynamic pod template
- Customized yaml for our own CI
- Spring Cloud Config Server
- Ansible
- Jenkins + Config Server + Ansible

## Initial setup
- Docker (https://docs.docker.com/desktop/mac/install/)
- Docker Compose
- Minikube (https://minikube.sigs.k8s.io/docs/start/)

## Docker and Docker Compose Version
```bash
❯ docker-compose version
docker-compose version 1.29.2, build 5becea4c
docker-py version: 5.0.0
CPython version: 3.9.0
OpenSSL version: OpenSSL 1.1.1h  22 Sep 2020
❯ docker version
Client:
 Cloud integration: v1.0.20
 Version:           20.10.10
 API version:       1.41
 Go version:        go1.16.9
 Git commit:        b485636
 Built:             Mon Oct 25 07:43:15 2021
 OS/Arch:           darwin/amd64
 Context:           default
 Experimental:      true

Server: Docker Engine - Community
 Engine:
  Version:          20.10.10
  API version:      1.41 (minimum version 1.12)
  Go version:       go1.16.9
  Git commit:       e2f740d
  Built:            Mon Oct 25 07:41:30 2021
  OS/Arch:          linux/amd64
  Experimental:     false
 containerd:
  Version:          1.4.11
  GitCommit:        5b46e404f6b9f661a205e28d59c982d3634148f8
 runc:
  Version:          1.0.2
  GitCommit:        v1.0.2-0-g52b36a2
 docker-init:
  Version:          0.19.0
  GitCommit:        de40ad0
```


## Setup Minikube for Jenkins

```bash
minikube start --memory 8192 --cpus 4 --driver=docker
kubectl create ns jenkins
kubectl -n jenkins apply -f manifest/sa.yaml
k -n jenkins get sa jenkins
kubectl -n jenkins get secret 
# kubectl -n jenkins get secret jenkins-token-qxtds -o jsonpath={.data.token} | base64 -d
```

## Kube and Docker Domains
- host.docker.internal
- host.minikube.internal

```bash
eyJhbGciOiJSUzI1NiIsImtpZCI6IjF2YVdlX0Z1ZE5FY1RKOVMtRHQydFhlWlNYR3o4RzZKcGQwZDBkNXFzNXMifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJqZW5raW5zIiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImplbmtpbnMtdG9rZW4tcXh0ZHMiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoiamVua2lucyIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50LnVpZCI6ImQ1N2E0OWE3LTcyMzAtNDgzOC05NTRjLTdjZDI5OGRiNjQ0ZiIsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDpqZW5raW5zOmplbmtpbnMifQ.py9t2rYKOhPzELl4-qEpoyGHxADGbGYC5bGJ0jbr6LRESBON0lHKEl3MAGKdSpLEfGW3AnK8qgVWdb1_rh_ezhKJuEPGDmYFzWjryNOD49PuD2z4bSrfT11QHCSyb4aTXqYZxD-lbktWXG9jQ-EatAxBfUvUbYwejDBFgyzY9xCHEzpnsEcrHu8Ald_70hAlcwACPsCt5DizX1_qB8jHVq3AhEoLGZnq3InLI0bjEmW-0ANBnZk7AwNa-qHlAWBdhyZKTqT53ksIDWG7GLoPSbtyo51eZ9U5w2O0O5LpjHLeSYar1RQmmUfTEpBtmHQ5FwVolOEDAGwdyASQzDn0NA
```