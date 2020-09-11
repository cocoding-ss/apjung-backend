pipeline {
    environment {
//         registry = "labyu/wiro-account"
//         registryCredential = 'dockerhub'
    }
    agent any
    stages {
        stage('Git checkout') {
            steps {
                git credentialsId: 'wirome-backend', url: 'git@github.com:cocoding-ss/wirome-backend.git'
            }
        }
//         stage('Gradle build') {
//             steps {
//                 sh "./gradlew clean build -Pprofile=dev"
//                 echo "NUMBER : ${currentBuild.number}"
//             }
//         }
//         stage('Build Image') {
//             steps {
//                 script {
//                     dockerImage = docker.build registry + ":${currentBuild.number}"
//                     dockerLatestImage = docker.build registry + ":latest"
//
//                 }
//             }
//         }
//         stage('Deploy Image') {
//             steps{
//                 script {
//                     docker.withRegistry( '', registryCredential ) {
//                         dockerImage.push()
//                     }
//                     docker.withRegistry( '', registryCredential ) {
//                         dockerLatestImage.push()
//                     }
//                 }
//             }
//         }
//         stage('Remove Image') {
//             steps{
//                 sh "docker rmi $registry:${currentBuild.number}"
//                 sh "docker rmi $registry:latest"
//             }
//         }
//         stage('k8s deploy') {
//             steps {
//                 echo "Not Ready"
//             }
//         }
    }
    post {
//         success {
//             slackSend (channel: '#wirome-log', color: '#00FF00', message: "SUCCESSFUL: Job '위로미 Account 서비스 [${currentBuild.number}]' (${env.BUILD_URL})")
//         }
//         failure {
//             slackSend (channel: '#wirome-log', color: '#FF0000', message: "FAILED: Job '${위로미 Account 서비스} [${currentBuild.number}]' (${env.BUILD_URL})")
//         }
    }
}