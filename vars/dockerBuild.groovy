def call(String project, String hubUser) {
    bat "docker image build -t ${hubUser}/${project} ."
    bat "docker tag ${hubUser}/${project} ${hubUser}/${project}:${ImageTag}"
    bat "docker tag ${hubUser}/${project} ${hubUser}/${project}:latest"
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
    bat "docker image push ${hubUser}/${project}:${ImageTag}"
    bat "docker image push ${hubUser}/${project}:latest"
}
