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

        script {
            customizeManualApprovalStage()
        }

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
    stage('Handle Approval Decision') {
        steps {
            script {
                def userInput = input message: 'Pipeline Approval Required', parameters: [choice(name: 'APPROVAL', choices: ['Proceed', 'Abort'])]

                // Process the user's choice
                if (userInput == 'Abort') {
                    currentBuild.result = 'ABORTED'
                    error('Pipeline aborted by user.')
                }
            }
        }
    }
}
