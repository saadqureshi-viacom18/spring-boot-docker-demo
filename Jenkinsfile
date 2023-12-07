pipeline {
    agent any
   
    parameters {
        choice(name: 'APPROVAL', choices: ['Proceed', 'Abort'], description: 'Select "Proceed" to continue or "Abort" to stop the pipeline.')
    }

    stages {
        stage("Pipeline Start") {
            steps {
                echo "Pipeline Started"
                emailext subject: 'Pipeline Started', body: 'Pipeline has started. Manual approval is required.\n\nClick [here](' + getApprovalLink() + ') to provide approval.', to: 'kasareabhishek79@gmail.com'
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
                    input message: 'Pipeline Approval Required', parameters: [choice(name: 'APPROVAL', choices: ['Proceed', 'Abort'])]
                    
                    // Process the user's choice
                    if (params.APPROVAL == 'Abort') {
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

def getApprovalLink() {
    return "${BUILD_URL}parameters/"
}
