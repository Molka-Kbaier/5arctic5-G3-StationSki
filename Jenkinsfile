pipeline {
    agent any

    environment {
        MAVEN_OPTS = '-Dmaven.test.failure.ignore=true' // Ignorer les échecs de tests
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'molkakbaier_5ARCTIC5_G3', url: 'https://github.com/Molka-Kbaier/5arctic5-G3-StationSki.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    // Construire le projet en ignorant les tests
                    sh 'mvn clean package -DskipTests=true'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Exécuter les tests
                    sh 'mvn test'
                }
            }
        }

        stage('Install') {
            steps {
                script {
                    // Installer le package dans le dépôt local
                    sh 'mvn install'
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
