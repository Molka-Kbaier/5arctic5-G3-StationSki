pipeline {
    agent any
    stages {
        stage("cloning") {
            steps {
                echo "========cloning with git========"
                sh "git config --global http.postBuffer 524288000"
                git url: "https://github.com/Molka-Kbaier/5arctic5-G3-StationSki.git",
                    branch: "TamimHmizi_5Arctic5-G3",
                    credentialsId:"github"
            }
        }
        stage("compiling") {
            steps {
                echo "========compiling with maven========"
                sh "mvn clean compile"
            }
        }
        stage("Testing") {
            steps {
                echo "========Testing with maven========"
                sh "mvn clean test"
            }
        }
        stage("Scan"){
            steps{
                echo "========Analyzing with Sonarqube========"
                withSonarQubeEnv(installationName: 'sonarqube-server'){
                    sh 'mvn sonar:sonar'
                }
            }
        }
        stage("Quality Gate"){
            steps{
                echo "========Checking Quality Gate========"
                timeout(time: 5,unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage("Deploying nexus") {
            steps {
                echo "========Deploying to Nexus========"
                sh 'mvn clean deploy -DskipTests'
            }
        }
        stage("Building image"){
            steps{
                sh "docker build -t tamimhmizi/tamimhmizi_g3_stationski . "
            }
        }
        stage("Pushing to DockerHub") {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
                    sh "docker push tamimhmizi/tamimhmizi_g3_stationski"
                }
            }
        }
        stage("Running containers"){
            steps{
                sh "docker-compose down \
                docker-compose up -d"
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