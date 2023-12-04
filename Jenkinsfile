pipeline{
  agent any
  stages{
    stage("Git-Checkout"){
      steps{
        git(
          url:"https://github.com/saadqureshi-viacom18/spring-boot-docker-demo.git",
          branch: "main"
          changelog: true,
          poll: true
          )
      }
    }
  }
}
