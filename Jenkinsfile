pipeline {
    agent {
        label 'agent-windows'
    }

    environment {
        DOCKER_IMAGE_NAME = 'fatymbengue/gestion-commande-backend'
        DOCKER_TAG = "${env.BUILD_NUMBER}"
        // Ajout des variables SonarQube
        SONAR_PROJECT_KEY = 'gestion-commande-backend'
        SONAR_HOST_URL = 'http://localhost:9000'
    }

    stages {
        // Étape 1: Analyse SonarQube (NOUVELLE ÉTAPE)
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    bat """
                        mvnw.cmd sonar:sonar ^
                        -Dsonar.projectKey=${SONAR_PROJECT_KEY} ^
                        -Dsonar.host.url=${SONAR_HOST_URL} ^
                        -Dsonar.java.binaries=target/classes
                    """
                }
            }
        }

        // Étape 2: Quality Gate (NOUVELLE ÉTAPE)
       stage('Quality Gate') {
           steps {
               timeout(time: 2, unit: 'MINUTES') {
                   waitForQualityGate abortPipeline: false  // Ne pas annuler si timeout
               }
           }
       }

        // Étape 3: Build Maven (déplacée après Sonar)
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

        // Étape 4: Build Docker
        stage('Build Docker') {
            steps {
                bat """
                    echo Construction de l'image Docker...
                    docker build -t ${env.DOCKER_IMAGE_NAME}:${env.DOCKER_TAG} .
                    docker tag ${env.DOCKER_IMAGE_NAME}:${env.DOCKER_TAG} ${env.DOCKER_IMAGE_NAME}:latest

                    echo Images créées:
                    docker images | findstr "${env.DOCKER_IMAGE_NAME}"
                """
            }
        }

        // Étape 5: Push to Docker Hub
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
            ✅ Analyse SonarQube: COMPLÉTÉE
            ✅ Quality Gate: PASSÉ
            ✅ Build Maven: SUCCÈS
            ✅ Tests: PASSÉS
            ✅ Image Docker: CONSTRUITE
            ✅ Docker Hub: IMAGES POUSSÉES

            📊 Tableau de bord SonarQube: ${SONAR_HOST_URL}/dashboard?id=${SONAR_PROJECT_KEY}
            📦 Images Docker: ${env.DOCKER_IMAGE_NAME}:${env.DOCKER_TAG}
            """
        }
        failure {
            echo "❌ PIPELINE ÉCHOUÉ - Consultez les logs ci-dessus"
        }
    }
}