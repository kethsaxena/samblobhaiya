pipeline {
    agent any

    environment {
        // Paths for SDKMAN on WSL
        SCALA_HOME = '/home/psaxena/.sdkman/candidates/scala/current'
        JAVA_HOME = '/home/psaxena/.sdkman/candidates/java/current'
        GRADLE_HOME = '/home/psaxena/.sdkman/candidates/gradle/current'
        
        // Deployment directories
        WINDOWS_DEPLOY_DIR = 'C:\projects\LocalDeploy'
    }

    stages {
        stage('Checkout') {
            steps {
                // Clone the repository
                git url: 'https://github.com/kethsaxena/samblobhaiya.git', branch: 'main'
            }
        }

        stage('Build') {
            steps {
                // Clean and build the project using WSL
                sh """
                source $HOME/.sdkman/bin/sdkman-init.sh
                gradle clean build distZip
                """
            }
        }

        stage('Test') {
            steps {
                // Run tests using WSL
                sh """
                source $HOME/.sdkman/bin/sdkman-init.sh
                gradle test
                """
            }
        }

        stage('Deploy Locally - Windows') {
            when {
                expression { isWindows() }
            }
            steps {
                // Deploy locally on Windows
                bat """
                if exist "%WINDOWS_DEPLOY_DIR%" rmdir /s /q "%WINDOWS_DEPLOY_DIR%"
                mkdir "%WINDOWS_DEPLOY_DIR%"
                tar -xf build\\distributions\\my-mvp-project.zip -C "%WINDOWS_DEPLOY_DIR%"
                """
            }
        }

        stage('Deploy to Simulated Linux Environment') {
            when {
                expression { !isWindows() }
            }
            steps {
                // Deploy locally to a directory simulating Linux environment
                sh """
                rm -rf $LINUX_DEPLOY_DIR
                mkdir -p $LINUX_DEPLOY_DIR
                unzip -o build/distributions/my-mvp-project.zip -d $LINUX_DEPLOY_DIR
                $LINUX_DEPLOY_DIR/my-mvp-project/bin/my-mvp-project
                """
            }
        }
    }

    post {
        always {
            // Clean up
            sh 'rm -rf build'
        }

        success {
            // Notify success
            echo 'Deployment was successful!'
        }

        failure {
            // Notify failure
            echo 'Deployment failed!'
        }
    }
}

def isWindows() {
    return isUnix() ? false : true
}
