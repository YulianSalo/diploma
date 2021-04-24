#!/usr/bin/env groovy
def os_release=release="https://downloads.raspberrypi.org/raspios_lite_armhf/images/raspios_lite_armhf-2021-03-25/2021-03-04-raspios-buster-armhf-lite.zip"
pipeline {
    agent any 
    stages {
        stage('Download OS Release') { 
            steps {
                // sh "wget ${release}"
                sh """
                mv ^[0-9][0-9][0-9][0-9]-0-9][0-9].*zip${5} raspberry.zip
                unzip raspberry.zip
                rm ^[0-9][0-9][0-9][0-9]-0-9][0-9].*zip$
                """
            }
        }
    }
}