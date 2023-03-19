def call(String project, String hubUser) {
    bat "docker rmi ${hubUser}/${project}:${ImageTag}"
    bat "docker rmi ${hubUser}/${project}:latest"
}
