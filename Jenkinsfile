pipeline {
    agent any
   
    stages {
        stage("Pipeline Start") {
            steps {
                echo "Pipeline Started"
                emailext subject: 'Pipeline Started', body: 'Pipeline has started.', to: 'kasareabhishek79@gmail.com'
            }
        }

        stage("Git-Checkout") {
            steps {
                script {
                    // Make sure to use credentials for Git if needed
                    git(
                        url: "https://github.com/saadqureshi-viacom18/spring-boot-docker-demo.git",
                        branch: "main",
                        changelog: true,
                        poll: true,
                        credentialsId: 'your-git-credentials-id'
                    )
                }
            }
        }

        stage('Approval Stage') {
            steps {
                script {
                    def approvalMailBody = "Please proceed or abort the pipeline.\n\n"
                    approvalMailBody += "To proceed, click [here](${BUILD_URL}).\n\n"
                    approvalMailBody += "To abort, reply to this email with 'ABORT' in the subject line."
                    
                    def userInput = emailext (
                        subject: 'Pipeline Approval Required', 
                        body: approvalMailBody, 
                        to: 'kasareabhishek79@gmail.com'
                    )
                    
                    // Wait for user input, abort if 'ABORT' is in the email subject line within a timeout
                    timeout(time: 1, unit: 'HOURS') {
                        // Loop until timeout or user responds
                        def responseReceived = false
                        while (!responseReceived) {
                            def inputResponse = emailext (
                                subject: 'Waiting for Approval', 
                                body: 'Reply with "ABORT" to abort the pipeline.', 
                                to: 'kasareabhishek79@gmail.com'
                            )
                            
                            if (inputResponse.subject == 'ABORT') {
                                currentBuild.result = 'ABORTED'
                                error('Pipeline aborted by user.')
                            } else if (inputResponse.subject != null) {
                                // User responded, break the loop
                                responseReceived = true
                            }
                            
                            sleep(30) // Adjust the sleep time based on your needs
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            script {
                emailext body: 'Pipeline Build Successfully', 
                    subject: 'Pipeline Success',
                    to: 'kasareabhishek79@gmail.com' 
            }
        }
        failure {
            script {
                emailext body: 'Pipeline Failure occurred.', 
                    subject: 'Pipeline Failure',
                    to: 'kasareabhishek79@gmail.com' 
            }
        }
    }
}
