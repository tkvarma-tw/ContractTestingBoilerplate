def gitUrl = 'https://github.com/tkvarma-tw/ContractTestingBoilerplate'

// Main build and deploy job for consumer and provider each (continuous deployment case)
['age-consumer', 'date-provider'].each {
    def app = it
    pipelineJob("$app-build-and-deploy") {
        definition {
            cpsScm {
                scm {
                    git {
                        remote {
                            url(gitUrl)
                        }
                        branch('master')
                        extensions {}
                    }
                }
                scriptPath("$app/jenkins/cd/Jenkinsfile")
            }
        }
    }
}

// Separate build and deploy jobs for consumer and provider each (non-continuous deployment case)
['age-consumer', 'date-provider'].each {
    def app = it
    ['build', 'deploy'].each {
        def phase = it
        pipelineJob("$app-$phase") {
            definition {
                cpsScm {
                    scm {
                        git {
                            remote {
                                url(gitUrl)
                            }
                            branch('master')
                            extensions {}
                        }
                    }
                    scriptPath("$app/jenkins/without-cd/Jenkinsfile-$phase")
                }
            }
        }
    }
}

// Branch job for consumer
pipelineJob("age-consumer-branch-with-removed-field") {
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url(gitUrl)
                    }
                    branch('remove-field')
                    extensions {}
                }
            }
            scriptPath("messaging-app/jenkins/cd/Jenkinsfile")
        }
    }
}
// Provider job that only executes contract tests, usually triggered by webhook
pipelineJob("date-provider-run-contract-tests") {
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url(gitUrl)
                    }
                    branch('master')
                    extensions {}
                }
            }
            scriptPath("date-provider/jenkins/Jenkinsfile-contract-tests")
        }
    }
}