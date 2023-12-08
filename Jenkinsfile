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
                    emailext subject: 'Pipeline Started', body: 'Pipeline has started.', to: 'saadq9870@gmail.com'
                }
            }
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
        
        stage("Generate Approval Link") {
            steps {
                script {
                    // Generate and send the approval link in the email
                    emailext subject: 'Manual Approval Required', body: getApprovalEmailBody(), to: 'saadq9870@gmail.com'
                }
            }
        }

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

    post {
        success {
            script {
                emailext body: 'Pipeline Build Successfully',
                    subject: 'Pipeline Success',
                    to: 'saadq9870@gmail.com'
            }
        }
        failure {
            script {
                emailext body: 'Pipeline Failure occurred.',
                    subject: 'Pipeline Failure',
                    to: 'saadq9870@gmail.com'
            }
        }
    }
}

def getApprovalEmailBody() {
    def approvalMailBody = "Pipeline has started. Manual approval is required.\n\n"
    approvalMailBody += '''Click on this link 
                            (${BUILD_URL}input/) - To Provide Approval.'''
    return approvalMailBody
}
