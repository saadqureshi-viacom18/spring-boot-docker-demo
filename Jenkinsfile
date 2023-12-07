def manualApprovalStage() {
    stage('Manual Approval') {
        steps {
            script {
                def approvalMailBody = """
                    <html>
                    <body>
                        <p style="font-size:16px;">Dear User,</p>
                        <p style="font-size:16px;">The pipeline has reached a manual approval stage.</p>
                        <p style="font-size:16px;">Please review the details below and make your decision.</p>
                        
                        <p style="font-size:16px;"><strong>Pipeline Details:</strong></p>
                        <ul>
                            <li><strong>Build URL:</strong> ${BUILD_URL}</li>
                            <li><strong>Project:</strong> ${JOB_NAME}</li>
                            <li><strong>Build Number:</strong> ${BUILD_NUMBER}</li>
                            <li><strong>Commit:</strong> ${SCM_COMMIT}</li>
                            <li><strong>Branch:</strong> ${BRANCH_NAME}</li>
                        </ul>
                        
                        <p style="font-size:16px;"><strong>Options:</strong></p>
                        <ul>
                            <li><strong>Proceed:</strong> Click [here](${BUILD_URL}input/) to approve and continue the pipeline.</li>
                            <li><strong>Abort:</strong> Reply to this email with 'ABORT' in the subject line to abort the pipeline.</li>
                        </ul>
                        
                        <p style="font-size:16px;">Thank you for your attention.</p>
                        <p style="font-size:16px;">Best regards,<br>Your Pipeline</p>
                    </body>
                    </html>
                """
                
                emailext subject: 'Manual Approval Required', body: approvalMailBody, to: 'kasareabhishek79@gmail.com', mimeType: 'text/html'
                
                // Wait for user input, abort if 'ABORT' is in the email subject line
                def userInput = emailext (
                    subject: 'Waiting for Approval', 
                    body: 'Reply with "ABORT" to abort the pipeline.', 
                    to: 'kasareabhishek79@gmail.com'
                )

                if (userInput.subject == 'ABORT') {
                    currentBuild.result = 'ABORTED'
                    error('Pipeline aborted by user.')
                }
            }
        }
    }
}

pipeline {
    agent any

    parameters {
        choice(name: 'APPROVAL', choices: ['Proceed', 'Abort'], description: 'Select "Proceed" to continue or "Abort" to stop the pipeline.')
    }

    stages {
        stage("Pipeline Start") {
            steps {
                script {
                    echo "Pipeline Started"
                    emailext subject: 'Pipeline Started', body: 'Pipeline has started.', to: 'kasareabhishek79@gmail.com'
                }
            }
        }

        stage("Generate Approval Link") {
            steps {
                script {
                    // Generate and send the approval link in the email
                    emailext subject: 'Manual Approval Required', body: getApprovalEmailBody(), to: 'kasareabhishek79@gmail.com'
                }
            }
        }

        customizeManualApprovalStage()

        stage('Git-Checkout') {
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

        stage('Abort Notification Stage') {
            when {
                expression { currentBuild.resultIsBetterOrEqualTo('ABORTED') }
            }
            steps {
                echo 'Sending email notification for pipeline abort...'
                emailext subject: 'Pipeline Aborted', body: 'The pipeline has been aborted.', to: 'mailto:saadq9870@gmail.com'
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

def getApprovalEmailBody() {
    def approvalMailBody = "Pipeline has started. Manual approval is required.\n\n"
    approvalMailBody += "Click [here](${BUILD_URL}input/) to provide approval."
    return approvalMailBody
}

def customizeManualApprovalStage() {
    // Call the customized manual approval stage function
    manualApprovalStage()
}
