pipeline {
    agent any
    tools {
        maven 'Maven'
    }
    // environment {
    //     DOCKERHUB_CREDENTIALS = credentials('dockerhub')
    // }
    stages {
        stage("Pipeline Start") {
            steps {
                echo "Pipeline Started"
                emailext subject: 'Pipeline Started', body: 'Pipeline has started.', to: ''
            }
        }
        
        stage("Git-Checkout") {
            steps {
                git(
                    url: "https://github.com/saadqureshi-viacom18/spring-boot-docker-demo.git",
                    branch: "main",
                    changelog: true,
                    poll: true 
                )
            }
        }

        stage('Approval Stage') {
            steps {
                script {
                    def approvalMailBody = "Please proceed or abort the pipeline.\n\n"
                    approvalMailBody += "To proceed, click [here](${BUILD_URL}).\n\n"
                    approvalMailBody += "To abort, reply to this email with 'ABORT' in the subject line."
                    
                    emailext subject: 'Pipeline Approval Required', body: approvalMailBody, to: 'mailto:saadq9870@gmail.com'
                    
                    // Wait for user input, abort if 'ABORT' is in the email subject line
                    def userInput = emailext (
                        subject: 'Waiting for Approval', 
                        body: 'Reply with "ABORT" to abort the pipeline.', 
                        to: 'mailto:saadq9870@gmail.com'
                    )
                    
                    if (userInput.subject == 'ABORT') {
                        currentBuild.result = 'ABORTED'
                        error('Pipeline aborted by user.')
                    }
                }
            }
        }    
        stage('Docker Build') {
            steps {
                sh 'sudo docker build -t saadqureshi9870/spring:${BUILD_NUMBER} .'
            }
        }
    }
    post {
        success {
            script {
                // archiveArtifacts artifacts: 'trivy-report.html', allowEmptyArchive: true, onlyIfSuccessful: true
                emailext body: 'Pipeline Build Successfully', 
                    recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], 
                    subject: 'Pipeline Success',
                    to: 'zabiralfiya6@gmail.com' 
            }
        }
        failure {
            script {
                emailext body: 'Pipeline Failure occurred.', 
                    recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']], 
                    subject: 'Pipeline Failure',
                    to: 'zabiralfiya6@gmail.com' 
            }
        }

        // always {
        //     archiveArtifacts artifacts: 'trivy-report.pdf', fingerprint: true
        // }
    }
}
