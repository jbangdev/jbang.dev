
[linux,macos]
requirements:
    quarkus := require('quarkus')
    mvn := require('mvn')
    jbang := require('jbang')

[windows]
requirements:
    quarkus := require('quarkus.cmd')
    mvn := require('mvn.cmd')
    jbang := require('jbang.cmd')

generate: requirements
    quarkus build -Dquarkus.roq.generator.batch
    quarkus run
    echo 'Open target/roq/index.html'

serve: generate
    jbang roq@quarkiverse/quarkus-roq

build:
    # mvn package is to ensure antora dev is present
    mvn package

preview: build
    quarkus dev


# watch docs-site for changes
watch-local-docs:
    watchexec --bell -w .. --print-events  -e adoc,png,yml just local-docs

local-docs:
    cd docs-site; npx antora --stacktrace generate --clean local-playbook.yml

docs:
    cd docs-site; npx antora generate playbook.yml

open-docs:
    open docs-site/build/site/index.html

# clone the relevant folders for documentation
setup-docs:
    -git clone https://github.com/jbangdev/jbang ../jbang
    -git clone https://github.com/jbangdev/jbang-vscode ../jbang-vscode
    -git clone https://github.com/jbangdev/jbang-idea ../jbang-idea
