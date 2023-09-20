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
				junit '**/surefire-reports/**/*.xml'
			}
		}

		stage('report'){
			steps{
				bat "mvn org.jacoco:jacoco-maven-plugin:prepare-agent"
				jacoco()
			}
		}

		stage('SonarQube analysis') {
	  		steps {
    				withSonarQubeEnv('sonar') {	    
     							 bat "mvn sonar:sonar"
    							}
   				}
  		}

		stage("Quality Gate") {
            		steps {
                		timeout(time: 1, unit: 'HOURS') {
                    						// Parameter indicates whether to set pipeline to UNSTABLE if Quality Gate fails
                    						// true = set pipeline to UNSTABLE, false = don't
                    						waitForQualityGate abortPipeline: true
                						}
            			}
        	}

		stage('deploy'){
			steps{
				bat "mvn tomcat7:deploy"
			}
		}


	}
}
