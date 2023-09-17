pipeline {
	agent any

	environment {
		mavenHome = tool 'jenkins-maven'
	}

	tools {
		jdk 'java-17'
	}

	stages {

		stage('Build'){
			steps {
				bat "mvn clean install -DskipTests"
			}
		}

		stage('Test'){
			steps{
				bat "mvn test"
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
