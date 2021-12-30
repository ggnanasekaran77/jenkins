#!groovy

def call(pipelineYaml) {

    pipeline {
        agent {
            kubernetes {
                defaultContainer "${pipelineYaml.build.type}"
                yaml podTemplateYaml(pipelineYaml)
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
                    git url: "${pipelineYaml.gitURL}", branch: appBranch
                }
            }
            stage('App Build') {
                steps {
                    sh 'mkdir -p /tmp/cache'  
                    sh "${pipelineYaml.build.command} -g /tmp/cache"
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