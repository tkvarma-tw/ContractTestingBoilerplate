FROM jenkins/jenkins:2.271

ENV JENKINS_UC_DOWNLOAD="http://mirrors.jenkins-ci.org"

# Install required plugins
RUN jenkins-plugin-cli -p git:4.5.0 blueocean:1.8.4 job-dsl:1.70 workflow-aggregator:2.6

# Skip initial setup
ENV JAVA_OPTS -Djenkins.install.runSetupWizard=false