#!groovy

def info (message){
    echo "[DEVOPS-INFO-PIPE]: ${message}"
}

def error (message){
    echo "[DEVOPS-ERROR-PIPE]: ${message}"
}
