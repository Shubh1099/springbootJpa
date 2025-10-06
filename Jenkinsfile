pipeline {
    agent any

    tools {
        jdk 'JDK-17'
        maven 'MAVEN-3.9.10'
    }

    stages {
        stage('checkout') 
        {
            steps 
            {
                git credentialsId: 'CD', url: 'https://github.com/Shubh1099/springbootJpa.git'
            }
         }
        stage('build') 
        {
            steps 
            {
                bat 'mvn clean install'
            }
        }
        stage('static analysis')
        {
            steps
            {
                bat 'mvn pmd:pmd'
            }
        }
         stage('package')
        {
            steps
            {
                bat 'mvn package'
            }
        }
        stage('Build Docker Image') {
            steps {
                echo "Building Docker image..."
                bat 'docker build -t my-springboot-app .'
            }
        }

        stage('Deploy Container') {
            steps {
                echo "Deploying Spring Boot container..."
                // Stop & remove old container if it exists, then run the new one
                bat '''
                    docker stop my-springboot-app || echo "No running container"
                    docker rm my-springboot-app || echo "No old container to remove"
                    docker run -d -p 8081:8082 --name my-springboot-app my-springboot-app
                '''
            }
        }
    }
    
    
}
