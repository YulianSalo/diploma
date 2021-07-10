#!/usr/bin/env groovy
pipeline {
    
    agent any

    stages {
    
        stage("script"){
            stages {
                stage('Prepare OS Release 1') {
                    
                    steps {
                        script {
                            // os_release = echo"\$(echo \$(python3 link_parser.py rpi_os_lite))"
                            FILE_CONTENT = readFile("rpi_os_lite_rel.txt")
                        }
                    }
                } 

                stage('Prepare OS Release 2') {
                    
                    steps {
                        script {
                            OS_RELEASE = sh( script: "sudo python3 link_parser.py rpi_os_lite", , returnStdout: true).trim()
                            sh "echo ${OS_RELEASE}"
                        }
                    }
                        //file_content = readFile("rpi_os_lite_rel.txt")
                } 

                stage('Prepare OS Release 3'){

                    steps {
                    
                        script{

                            if (OS_RELEASE  == FILE_CONTENT) {
                                
                                echo "Done. Nothing new"

                            } else {
                                sh """
                                wget ${OS_RELEASE}
                                searched_file=\$(ls -R | grep ^[0-9][0-9][0-9][0-9]-[0-9][0-9].*zip\$)
                                unzip \$searched_file
                                os_var=\$(echo \$searched_file | rev | cut -c5- | rev)
                                qemu-img convert -f raw "\$os_var.img" -O vmdk "\$os_var.vmdk"
                                sshpass -f "psswd/vmware_psswd" scp "\$os_var.vmdk" root@192.168.0.100:/vmfs/volumes/5f4e581f-ffea726b-37a8-dca632e64c14/"\$os_var.vmdk"
                                """
                                echo "Done. Nothing new"
                                
                            }
                        }   
                    } 
                }
            }
        }
    }
}
