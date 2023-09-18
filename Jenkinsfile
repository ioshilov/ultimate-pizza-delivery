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
    withSonarQubeEnv('sonar') { // You can override the credential to be used
      bat "mvn clean package sonar:sonar"
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
