#
# GRAKN.AI - THE KNOWLEDGE GRAPH
# Copyright (C) 2020 Grakn Labs Ltd
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Affero General Public License as
# published by the Free Software Foundation, either version 3 of the
# License, or (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Affero General Public License for more details.
#
# You should have received a copy of the GNU Affero General Public License
# along with this program.  If not, see <https://www.gnu.org/licenses/>.
#

workspace(name = "simulation")

################################
# Load @graknlabs_dependencies #
################################
load("//dependencies/graknlabs:repositories.bzl", "graknlabs_dependencies")
graknlabs_dependencies()

# Load Antlr
load("@graknlabs_dependencies//builder/antlr:deps.bzl", antlr_deps = "deps")
antlr_deps()
load("@rules_antlr//antlr:deps.bzl", "antlr_dependencies")
antlr_dependencies()

# Load Bazel
load("@graknlabs_dependencies//builder/bazel:deps.bzl","bazel_common", "bazel_deps", "bazel_toolchain")
bazel_common()
bazel_deps()
bazel_toolchain()

# Load gRPC
load("@graknlabs_dependencies//builder/grpc:deps.bzl", grpc_deps = "deps")
grpc_deps()
load("@com_github_grpc_grpc//bazel:grpc_deps.bzl",
com_github_grpc_grpc_deps = "grpc_deps")
com_github_grpc_grpc_deps()
load("@stackb_rules_proto//java:deps.bzl", "java_grpc_compile")
java_grpc_compile()
load("@stackb_rules_proto//node:deps.bzl", "node_grpc_compile")
node_grpc_compile()

# Load Java
load("@graknlabs_dependencies//builder/java:deps.bzl", java_deps = "deps")
java_deps()
load("@graknlabs_dependencies//library/maven:rules.bzl", "maven")

# Load Kotlin
load("@graknlabs_dependencies//builder/kotlin:deps.bzl", kotlin_deps = "deps")
kotlin_deps()
load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kotlin_repositories", "kt_register_toolchains")
kotlin_repositories()
kt_register_toolchains()

# Load NodeJS
load("@graknlabs_dependencies//builder/nodejs:deps.bzl", nodejs_deps = "deps")
nodejs_deps()
load("@build_bazel_rules_nodejs//:defs.bzl", "node_repositories")
node_repositories()

# Load Python
load("@graknlabs_dependencies//builder/python:deps.bzl", python_deps = "deps")
python_deps()
load("@rules_python//python:pip.bzl", "pip_repositories", "pip3_import")
pip_repositories()
pip3_import(
    name = "graknlabs_dependencies_ci_pip",
    requirements = "@graknlabs_dependencies//tool:requirements.txt",
)
load("@graknlabs_dependencies_ci_pip//:requirements.bzl",
graknlabs_dependencies_ci_pip_install = "pip_install")
graknlabs_dependencies_ci_pip_install()

# Load Docker
load("@graknlabs_dependencies//distribution/docker:deps.bzl", docker_deps = "deps")
docker_deps()

# Load Checkstyle
load("@graknlabs_dependencies//tool/checkstyle:deps.bzl", checkstyle_deps = "deps")
checkstyle_deps()

# Load Sonarcloud
load("@graknlabs_dependencies//tool/sonarcloud:deps.bzl", "sonarcloud_dependencies")
sonarcloud_dependencies()

# Load Unused Deps
load("@graknlabs_dependencies//tool/unuseddeps:deps.bzl", unuseddeps_deps = "deps")
unuseddeps_deps()

#####################################################################
# Load @graknlabs_bazel_distribution (from @graknlabs_dependencies) #
#####################################################################
load("@graknlabs_dependencies//distribution:deps.bzl", distribution_deps = "deps")
distribution_deps()

pip3_import(
    name = "graknlabs_bazel_distribution_pip",
    requirements = "@graknlabs_bazel_distribution//pip:requirements.txt",
)
load("@graknlabs_bazel_distribution_pip//:requirements.bzl",
graknlabs_bazel_distribution_pip_install = "pip_install")
graknlabs_bazel_distribution_pip_install()

load("@graknlabs_bazel_distribution//github:dependencies.bzl", "tcnksm_ghr")
tcnksm_ghr()

load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")
git_repository(
    name = "io_bazel_skydoc",
    remote = "https://github.com/graknlabs/skydoc.git",
    branch = "experimental-skydoc-allow-dep-on-bazel-tools",
)

load("@io_bazel_skydoc//:setup.bzl", "skydoc_repositories")
skydoc_repositories()

load("@io_bazel_rules_sass//:package.bzl", "rules_sass_dependencies")
rules_sass_dependencies()

load("@build_bazel_rules_nodejs//:defs.bzl", "node_repositories")
node_repositories()

load("@io_bazel_rules_sass//:defs.bzl", "sass_repositories")
sass_repositories()

load("@graknlabs_bazel_distribution//common:dependencies.bzl", "bazelbuild_rules_pkg")
bazelbuild_rules_pkg()

load("@rules_pkg//:deps.bzl", "rules_pkg_dependencies")
rules_pkg_dependencies()

#################################
# Load @graknlabs_grabl_tracing #
#################################
load("//dependencies/graknlabs:repositories.bzl", "graknlabs_grabl_tracing")
graknlabs_grabl_tracing()

################################
## Load @graknlabs_client_java #
################################
#load("//dependencies/graknlabs:repositories.bzl", "graknlabs_client_java")
#graknlabs_client_java()
#load("@graknlabs_client_java//dependencies/maven:artifacts.bzl", graknlabs_client_java_artifacts = "artifacts")

#########################################################
## Load @graknlabs_common (from @graknlabs_client_java) #
########################################################
#load("@graknlabs_client_java//dependencies/graknlabs:repositories.bzl", "graknlabs_common")
#graknlabs_common()

########################################################
## Load @graknlabs_graql (from @graknlabs_client_java) #
########################################################
#load("@graknlabs_client_java//dependencies/graknlabs:repositories.bzl", "graknlabs_graql")
#graknlabs_graql()
#load("@graknlabs_graql//dependencies/maven:artifacts.bzl", graknlabs_graql_artifacts = "artifacts")

###########################################################
## Load @graknlabs_protocol (from @graknlabs_client_java) #
###########################################################
#load("@graknlabs_client_java//dependencies/graknlabs:repositories.bzl", "graknlabs_protocol")
#graknlabs_protocol()
#load("@graknlabs_protocol//dependencies/maven:artifacts.bzl", graknlabs_protocol_artifacts = "artifacts")

#######################################
# Load @graknlabs_grakn_core_artifact #
#######################################
load("//dependencies/graknlabs:artifacts.bzl", "graknlabs_grakn_core_artifact")
graknlabs_grakn_core_artifact()

###############
# Load @maven #
###############
load("//dependencies/maven:artifacts.bzl", "artifacts")
maven(
    artifacts,
#    graknlabs_graql_artifacts +
#    graknlabs_protocol_artifacts +
#    graknlabs_client_java_artifacts
)

load("@rules_jvm_external//:defs.bzl", rje_maven_install = "maven_install")
rje_maven_install(
    name = "neo4j",
    artifacts = ["org.neo4j.driver:neo4j-java-driver:4.1.0",
                 "io.grakn.client:grakn-client:1.7.2",
                 "io.graql:graql-lang:1.0.6",
                 "io.grakn.protocol:grakn-protocol:1.0.5",
                 ],
    repositories = [
        "https://repo1.maven.org/maven2",
        "https://repo.grakn.ai/repository/maven/",
    ],
    strict_visibility = True,
    version_conflict_policy = "pinned"
)

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")
http_archive(
    name = "io_bazel_rules_groovy",
    url = "https://github.com/bazelbuild/rules_groovy/archive/0.0.6.tar.gz",
    sha256 = "21c7172786623f280402d3b3a2fc92f36568afad5a4f6f5ea38fd1c6897aecf8",
    strip_prefix = "rules_groovy-0.0.6",
)
#Rather than use these recommended lines, we use the below such that we have access to the groovy library in our groovy scripts
#load("@io_bazel_rules_groovy//groovy:repositories.bzl", "rules_groovy_dependencies")
#rules_groovy_dependencies()

http_archive(
    name = "groovy_sdk_artifact",
    urls = [
        "https://mirror.bazel.build/dl.bintray.com/groovy/maven/apache-groovy-binary-3.0.6.zip",
        "https://dl.bintray.com/groovy/maven/apache-groovy-binary-3.0.6.zip",
    ],
    build_file_content = """
filegroup(
    name = "sdk",
    srcs = glob(["groovy-3.0.6/**"]),
    visibility = ["//visibility:public"],
)
java_import(
    name = "groovy",
    jars = ["groovy-3.0.6/lib/groovy-3.0.6.jar", "groovy-3.0.6/lib/groovy-templates-3.0.6.jar"],
    visibility = ["//visibility:public"],
)
java_import
"""
)

bind(
    name = "groovy-sdk",
    actual = "@groovy_sdk_artifact//:sdk",
)
bind(
    name = "groovy",
    actual = "@groovy_sdk_artifact//:groovy",
)
