#
# Copyright (C) 2021 Vaticle
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

load("@bazel_tools//tools/build_defs/repo:git.bzl", "git_repository")

def vaticle_dependencies():
    git_repository(
        name = "vaticle_dependencies",
        remote = "https://github.com/vaticle/dependencies",
        commit = "34c260ae7858b08dd6247d43c8c373847b113d68",  # sync-marker: do not remove this comment, this is used for sync-dependencies by @vaticle_dependencies
    )

def vaticle_typedb_client_java():
    git_repository(
        name = "vaticle_typedb_client_java",
        remote = "https://github.com/vaticle/typedb-client-java",
        commit = "743c130a60414e78133f42c08e4d546bc73bc471",  # sync-marker: do not remove this comment, this is used for sync-dependencies by @vaticle_typedb_client_java
    )

def vaticle_factory_tracing():
    git_repository(
        name = "vaticle_factory_tracing",
        remote = "https://github.com/vaticle/factory-tracing",
        commit = "7b4a3933139af670a7b7c925fc20de40d63caa41"  # sync-marker: do not remove this comment, this is used for sync-dependencies by @vaticle_factory_tracing
    )
