#!groovy

def call(pipelineYaml) {

  def buildImage = pipelineYaml.build?.image?:pipelineYaml.buildTool."${pipelineYaml.build.type}".image  
  def podTemplateYaml = """
apiVersion: v1
kind: Pod
metadata:
  labels:
    cicd: "${pipelineYaml.build.type}-docker"
spec:
  containers:
  - name: "${pipelineYaml.build.type}"
    image: "${buildImage}" 
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
"""
  log.info "podTemplateYaml podTemplateYaml: ${podTemplateYaml}"
  log.info "podTemplateYaml buildImage: ${buildImage}"
  return podTemplateYaml
}