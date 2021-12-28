#!groovy

def call() {
    def jobPattern = "${params.pattern}".trim()
    podTemplate(yaml: '''
    apiVersion: v1
    kind: Pod
    spec:
      containers:
      - name: maven
        image: maven:3.8.1-jdk-8
        command:
        - sleep
        args:
        - 99d
''') {
        node(POD_LABEL) {
            stage ("Seed Job Checkout") {
                git url: 'https://github.com/ggnanasekaran77/jenkins-job.git', branch: 'main'
            }
            stage ("pipelinemap jobs") {
                jobList = sh (script: """ git ls-tree --full-tree -r --name-only HEAD|egrep -iv ".gitignore|default|readme.md|.groovy" | sed -e "s/.yaml//" | egrep "${jobPattern}" """, returnStdout: 'True').trim().split('\n')
                log.info "Pipelinemap jobList ${jobList}"
                jobList.each {
                    def folderName = it.split('/')[0]
                    def jobName = it.split('/')[1]
                    log.info "Pipelinemap folderName ${folderName}"
                    log.info "Pipelinemap jobName ${jobName}"
                    jobDsl scriptText: """
                        folder ("${folderName}") {
                            displayName("${folderName}")
                            description("${folderName}")
                        }
                    """
                    jobDsl scriptText: """
                        pipelineJob ("${folderName}/${jobName}") {
                            parameters {
                                stringParam('appBranch', 'develop', 'Please provide Application Branch Name')
                            }
                            definition {
                                cps {
                                    sandbox(true)
                                    script("cicd()")
                                }
                            }
                        }
                    """
                }
            }
        }
    }
}