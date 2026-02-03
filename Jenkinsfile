pipeline {
    agent {
        label 'agent-windows'
    }

    environment {
        DOCKER_IMAGE_NAME = 'fatymbengue/gestion-commande-backend'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
    }

    stages {
           stage('Build and Test') {
               steps {
                   bat 'mvnw.cmd clean package'
               }
           }


        // Étape 2: Build Docker Image
        stage('Build Docker') {
            steps {
                dir('gestion_commande') {
                    bat "docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_TAG} ."
                    bat "docker tag ${DOCKER_IMAGE_NAME}:${DOCKER_TAG} ${DOCKER_IMAGE_NAME}:latest"
                }
            }
        }

        // Étape 3: Push to Docker Hub
        stage('Push to Docker Hub') {
            steps {
                script {
                    withCredentials([usernamePassword(
                        credentialsId: '0b645248-5ff1-4028-9402-f5c77efce425',
                        usernameVariable: 'USER',
                        passwordVariable: 'PASS'
                    )]) {
                        bat """
                            echo %PASS% | docker login -u %USER% --password-stdin
                            docker push ${DOCKER_IMAGE_NAME}:${DOCKER_TAG}
                            docker push ${DOCKER_IMAGE_NAME}:latest
                            docker logout
                        """
                    }
                }
            }
        }
    }

    post {
        success {
            echo """
            🎉 SUCCÈS !
            Image Docker publiée sur Docker Hub:
            - ${DOCKER_IMAGE_NAME}:${DOCKER_TAG}
            - ${DOCKER_IMAGE_NAME}:latest
            """
        }
    }
}