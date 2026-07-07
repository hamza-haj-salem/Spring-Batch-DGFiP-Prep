pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                 bat 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
    }
     post {
            success {
                publishChecks name: 'Jenkins Build',
                              title: 'Build réussi',
                              summary: 'Le pipeline Jenkins a réussi',
                              text: 'Toutes les étapes sont terminées avec succès'
            }

            failure {
                publishChecks name: 'Jenkins Build',
                              title: 'Build échoué',
                              summary: 'Le pipeline Jenkins a échoué',
                              text: 'Une ou plusieurs étapes ont échoué'
            }
        }
}