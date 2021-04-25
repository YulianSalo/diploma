#!/usr/bin/env groovy
def os_release=release="https://downloads.raspberrypi.org/raspios_lite_armhf/images/raspios_lite_armhf-2021-03-25/2021-03-04-raspios-buster-armhf-lite.zip"
def reg_pattern=/^[0-9][0-9][0-9][0-9]-0-9][0-9].*zip$/
pipeline {
    agent any 
    stages {
        stage('Download OS Release') { 
            steps {
                // sh "wget ${release}"
                sh """
                searched_file=\$(ls -R | grep ^[0-9][0-9][0-9][0-9]-[0-9][0-9].*zip\$)
                mv \$searched_file raspberry.zip
                unzip raspberry.zip
                """
            }
        }
    }
}