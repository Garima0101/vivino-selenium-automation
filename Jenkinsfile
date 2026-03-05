pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main/vivino-test', 
                    url: 'https://github.com/Garima0101/vivino-selenium-automation.git'
            }
        }

        stage('Build & Test') {
            steps {
                bat 'mvn clean test'
            }
        }

        stage('Archive Test Results') {
            steps {
                testng '**/test-output/testng-results/*.xml'
            }
        }
    }
}