#!/usr/bin/env groovy
def os_release=release="https://downloads.raspberrypi.org/raspios_lite_armhf/images/raspios_lite_armhf-2021-03-25/2021-03-04-raspios-buster-armhf-lite.zip"

pipeline {
    agent any
    
    environment {
    // Using returnStdout
    CC = """${sh(
            returnStdout: true,
            script: 'echo "clang"'
        )}""" 
    // Using returnStatus
    EXIT_STATUS = """${sh(
            returnStatus: true,
            script: 'exit 1'
        )}"""
    }

    stages {
        stage('Download OS Release') { 
            steps {
                // sh "wget ${release}"
                sh """
                searched_file=\$(ls -R | grep ^[0-9][0-9][0-9][0-9]-[0-9][0-9].*zip\$)
                mv \$searched_file raspberry_os_lite.zip
                unzip raspberry.zip
                """
            }
        }

        stage('Prepare OS Image') { 
            steps {
                sh """
                unzip raspberry.zip
                qemu-img convert -f raw raspberry.img -O vmdk raspberry.vmdk
                """
            }
        }

        // stage('Copy OS Image To VMWare ESXi Host') { 
        //     steps {
        //         // sh """
        //         // unzip raspberry.zip
        //         // qemu-img convert -f raw raspberry.img -O vmdk raspberry.vmdk
        //         // """
        //     }
        // }

        // stage('Provision OS Image On VMWare ESXi Host') { 
        //     steps {
        //         //vmkfstools -i 2020-05-27-raspios-buster-arm64.vmdk -d thin raspios-buster-arm64.vmdk
        //         // sh """
        //         // unzip raspberry.zip
        //         // qemu-img convert -f raw raspberry.img -O vmdk raspberry.vmdk
        //         // """
        //     }
        // }

        // stage('Place OS Image On VMWare ISCI Storage') { 
        //     steps {
        //         //vmkfstools -i 2020-05-27-raspios-buster-arm64.vmdk -d thin raspios-buster-arm64.vmdk
        //         // sh """
        //         // unzip raspberry.zip
        //         // qemu-img convert -f raw raspberry.img -O vmdk raspberry.vmdk
        //         // """
        //     }
        // }
    }
}