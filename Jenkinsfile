pipeline {
    agent any
    tools {
        maven 'maven-3.3.9'
        jdk 'jdk1.8.0_20'
    }
    stages {
        stage('Initialize') {
            steps {
                echo "PATH = ${PATH}"
                echo "M2_HOME = ${M2_HOME}"
            }
        }
        stage('Build') {
            steps {
                sh 'mvn -Dmaven.test.failure.ignore=true install'
            }
            post {
                success {
                    echo 'post build steps go here'
                }
            }
        }
        stage('Browser testing') {
            steps {
                echo 'Testing browsers'

                script {
                    def browsers = ['chrome', 'firefox']
                    for (int i = 0; i < browsers.size(); ++i) {
                        echo "Testing the ${browsers[i]} browser"
                    }
                }
            }
        }
        stage('Force exceptions') {
            steps {
                echo 'Conditional failover'
                script {
        			try {
            			sh 'exit 0'
        				}
			        catch (exc) {
            			echo 'Something failed, I should sound the klaxons!'
                        mail to: 'robert.rong@juliusbaer.com', body: "Please go to ${BUILD_URL} and verify the build", subject: "Job '${JOB_NAME}' build : ${BUILD_NUMBER} is waiting for input"
            			error "Program failed, please read logs..."
        			}
                }
            }
        }
        
        stage('JUnit testing') {
            steps {
                echo 'Testing in memory MongoDB'
                script {
	                try {
    	                sh 'mvn test -B'
        	         } catch(err) {
            	        step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
                        mail to: 'robert.rong@juliusbaer.com', body: "Please go to ${BUILD_URL} and verify the test results", subject: "Job '${JOB_NAME}' with build  ${BUILD_NUMBER} test failures)}"
                	      throw err
                 	}
				}
            }
        }
        
                
    }
}
