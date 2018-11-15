import jetbrains.buildServer.configs.kotlin.v2018_1.*
import jetbrains.buildServer.configs.kotlin.v2018_1.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2018_1.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2018_1.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2018.1"

project {

    vcsRoot(GitGithubComShengyouKotlindslAngularDemoGitRefsHeadsMaster)

    buildType(Build)
}

object Build : BuildType({
    name = "Build, Lint and Test"

    artifactRules = """
        dist => dist.zip
        coverage => coverage.zip
    """.trimIndent()

    vcs {
        root(GitGithubComShengyouKotlindslAngularDemoGitRefsHeadsMaster)
    }

    steps {
        script {
            name = "Install Dependency"
            scriptContent = "npm install"
        }
        script {
            name = "Lint"
            scriptContent = "ng lint"
        }
        script {
            name = "Test"
            scriptContent = "ng test --watch=false --browsers=ChromeHeadless --code-coverage=true"
        }
        script {
            name = "Build"
            scriptContent = "ng build --prod"
        }
    }

    triggers {
        vcs {
        }
    }
})

object GitGithubComShengyouKotlindslAngularDemoGitRefsHeadsMaster : GitVcsRoot({
    name = "git@github.com:shengyou/kotlindsl-angular-demo.git#refs/heads/master"
    pollInterval = 10
    url = "git@github.com:shengyou/kotlindsl-angular-demo.git"
    branchSpec = "+:refs/heads/*"
    authMethod = defaultPrivateKey {
        userName = "git"
    }
})
