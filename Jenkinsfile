pipeline {
    agent any
    stages {
        stage("cloning") {
            steps {
                echo "========cloning========"
                git url: "https://github.com/Molka-Kbaier/5arctic5-G3-StationSki.git",
                    branch: "TamimHmizi_5Arctic5-G3"
            }
        }
        stage("compiling") {
            steps {
                echo "========compiling========"
                sh "mvn clean compile"
            }
        }
        stage("packaging nexus") {
            steps {
                echo "========packaging to Nexus========"
                sh 'mvn clean deploy -DskipTests'
            }
        }
    }
    
    post {
        always {
            echo "========always========"
        }
        success {
            echo "========pipeline executed successfully========"
        }
        failure {
            echo "========pipeline execution failed========"
        }
    }
}
