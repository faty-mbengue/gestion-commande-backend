pipeline {
    agent {
        label 'agent-windows'
    }

    environment {
        DOCKER_IMAGE_NAME = 'fatymbengue/gestion-commande-backend'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
    }

    stages {
        // Étape 1: Build Maven (déjà bon)
        stage('Build and Test') {
            steps {
                bat 'mvnw.cmd clean package'
            }
            post {
                success {
                    archiveArtifacts 'target/*.jar'
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        // Étape 2: Build Docker (À CORRIGER - enlever dir!)
        stage('Build Docker') {
            steps {
                // ENLEVER dir('gestion_commande') - Dockerfile est à la racine
                bat """
                    echo Construction de l'image Docker...
                    docker build -t ${env.DOCKER_IMAGE_NAME}:${env.DOCKER_TAG} .
                    docker tag ${env.DOCKER_IMAGE_NAME}:${env.DOCKER_TAG} ${env.DOCKER_IMAGE_NAME}:latest

                    echo Images créées:
                    docker images | findstr "${env.DOCKER_IMAGE_NAME}"
                """
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
                            echo Connexion à Docker Hub...
                            echo %PASS% | docker login -u %USER% --password-stdin

                            echo Push des images...
                            docker push ${env.DOCKER_IMAGE_NAME}:${env.DOCKER_TAG}
                            docker push ${env.DOCKER_IMAGE_NAME}:latest

                            echo Déconnexion...
                            docker logout

                            echo "✅ Images poussées avec succès!"
                        """
                    }
                }
            }
        }
    }

    post {
        success {
            echo """
            🎉 PIPELINE COMPLET RÉUSSI !
            ============================
            ✅ Build Maven: SUCCÈS
            ✅ Tests: PASSÉS (1 test)
            ✅ Image Docker: CONSTRUITE
            ✅ Docker Hub: IMAGES POUCHÉES

            📦 Images disponibles:
            - ${env.DOCKER_IMAGE_NAME}:${env.DOCKER_TAG}
            - ${env.DOCKER_IMAGE_NAME}:latest

            🐳 Vérifiez sur: https://hub.docker.com/r/fatymbengue/gestion-commande-backend
            """
        }
        failure {
            echo "❌ PIPELINE ÉCHOUÉ - Consultez les logs ci-dessus"
        }
    }
}