pipeline {
	agent any

	environment {
		mavenHome = tool 'jenkins-maven'
	}

	tools {
		jdk 'java-17'
	}

	stages {


  stage('SonarQube analysis') {
    withSonarQubeEnv(credentialsId: 'sonar-id', installationName: 'sonar') { // You can override the credential to be used
      bat "mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.7.0.1746:sonar"
    }
  }

		stage('Build'){
			steps {
				bat "mvn clean install -DskipTests"
			}
		}

		stage('Test'){
			steps{
				bat "mvn test"
				junit '**/surefire-reports/**/*.xml'
			}
		}

		stage('report'){
			steps{
				bat "mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent package"
				jacoco()
			}
		}


	}
}
