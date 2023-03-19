def call(String project, String hubUser) {
    sh "docker image build -t ${hubUser}/${project} ."
    sh "docker tag ${hubUser}/${project} ${hubUser}/${project}:${ImageTag}"
    sh "docker tag ${hubUser}/${project} ${hubUser}/${project}:latest"
    withCredentials([usernamePassword(
            credentialsId: "docker_cred",
            usernameVariable: "USER",
            passwordVariable: "PASS"
    )]) {
         docker login --username='$USER' --password='$PASS'
    }
    sh "docker image push ${hubUser}/${project}:${ImageTag}"
    sh "docker image push ${hubUser}/${project}:latest"
}
