def call(String project, String hubUser) {
    bat "docker image build -t ${hubUser}/${project} ."
    bat "docker tag ${hubUser}/${project} ${hubUser}/${project}:${ImageTag}"
    bat "docker tag ${hubUser}/${project} ${hubUser}/${project}:latest"
    withCredentials([usernamePassword(
            credentialsId: "docker_cred",
            usernameVariable: "USER",
            passwordVariable: "PASS"
    )]) {
        bat "docker login -u '$USER' -p 'Netw0rk#ng'"
    }
    bat "docker image push ${hubUser}/${project}:${ImageTag}"
    bat "docker image push ${hubUser}/${project}:latest"
}
