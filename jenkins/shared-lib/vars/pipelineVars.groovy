#!groovy

def call() {
    def pipelineVars = [
        buildTool: [
            gradle: [image: "gradle:7.2.0-jdk11"],
            maven: [image: "gradle:6.9.2-jdk11"],
        ]
    ]
    return pipelineVars
}