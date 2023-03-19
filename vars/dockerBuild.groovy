def call(String project, String hubUser) {
    sh "docker image build -t ${hubUser}/${project} ."
    sh "docker tag ${hubUser}/${project} ${hubUser}/${project}:${ImageTag}"
    sh "docker tag ${hubUser}/${project} ${hubUser}/${project}:latest"
    environment {
    DOCKERHUB_CREDENTIALS = credentials('docker_cred')
    }
    withCredentials([usernamePassword(
            credentialsId: "docker_cred",
            usernameVariable: "USER",
            passwordVariable: "PASS"
    )]) {
        sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
    }
    sh "docker image push ${hubUser}/${project}:${ImageTag}"
    sh "docker image push ${hubUser}/${project}:latest"
}
