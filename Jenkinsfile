#!/usr/bin/env groovy

pipeline {
    agent any
    tools {
        jdk "jdk-21"
    }
    environment {
        MODRINTH_API_TOKEN     = credentials('jared-modrinth-token')
        CURSEFORGE_API_TOKEN     = credentials('jared-curseforge-token')
    }
    stages {
        stage('Clean') {
            steps {
                echo 'Cleaning Project'
                sh 'chmod +x gradlew'
                sh './gradlew clean'
            }
        }
        stage('Build') {
            steps {
                echo 'Building'
                sh './gradlew build'
            }
        }
        stage('Publish') {
            stages {
                stage('Publish Maven') {
                    steps {
                        echo 'Deploying to Maven'
                        sh './gradlew publish'
                    }
                }
                stage('Publish CurseForge') {
                    steps {
                        echo 'Deploying to CurseForge'
                        sh './gradlew publishCurseForge modrinth'
                    }
                }
            }
        }
    }
    post {
        always {
            archive 'build/libs/**.jar'
            archive 'changelog.md'
        }
    }
}
