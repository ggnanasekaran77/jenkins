FROM jenkins/jenkins:2.319.1-lts

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt

RUN set -x \
    && /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

 COPY jcasc/jenkins.yaml /usr/share/jenkins/ref/jenkins.yaml   

