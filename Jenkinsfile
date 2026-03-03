pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', 
                    branches: [[name: '*/main/vivino-test']], 
                    userRemoteConfigs: [[url: 'https://github.com/Garima0101/vivino-selenium-automation.git']]])
            }
        }
        stage('Build & Test') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Archive Test Results') {
            steps {
                junit '**/target/surefire-reports/*.xml'
            }
        }
    }
}