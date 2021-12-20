#!groovy

def call() {

    def gitURL = "https://github.com/ggnanasekaran77/configserver.git"
    pipeline {
        agent {
            kubernetes {
                defaultContainer 'gradle'
                yaml '''
                    apiVersion: v1
                    kind: Pod
                    metadata:
                      labels:
                        cicd: gradle-docker
                    spec:
                      containers:
                      - name: gradle
                        image: gradle:7.2.0-jdk11
                        command:
                        - cat
                        tty: true
                        volumeMounts:
                        - name: build-cache
                          mountPath: /tmp
                      - name: docker
                        image: docker:19.03.1-dind
                        securityContext:
                          privileged: true
                      volumes:
                      - name: build-cache
                        hostPath:
                          path: /tmp        
                '''
            }
        }
        options {
            timestamps()
            ansiColor('xterm')
            buildDiscarder(logRotator(numToKeepStr: '20', artifactNumToKeepStr: '20'))
            timeout(time: 60, unit: 'MINUTES')
            disableConcurrentBuilds()
        }
        parameters {
            string(name: 'appBranch', defaultValue: 'main', description: "Application Branch name")
        }
        stages {
            stage('Checkout') {
                steps {
                    git url: gitURL, branch: appBranch
                }
            }
            stage('App Build') {
                steps {
                    sh 'mkdir -p /tmp/cache'  
                    sh "gradle clean build -g /tmp/cache"
                }
            }
            stage('Doc Build') {
                steps {
                    container('docker') {
                        sh 'docker build -t ggnanasekaran77/configserver .'
                    }
                }
            }
        }
        post {
            always {
                cleanWs()
                dir("${env.WORKSPACE}@tmp") {
                    deleteDir()
                }
                dir("${env.WORKSPACE}@script") {
                    deleteDir()
                }
                dir("${env.WORKSPACE}@script@tmp") {
                    deleteDir()
                }
            }
        }
    }
}