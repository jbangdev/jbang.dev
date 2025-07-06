# watch docs-site for changes
watch-local-docs:
    watchexec --bell -w .. --print-events  -e adoc,png,yml just local-docs

local-docs:
    cd docs-site; npx antora generate local-playbook.yml

docs:
    cd docs-site; npx antora generate playbook.yml

open-docs:
    open docs-site/build/site/index.html

# clone the relevant folders for documentation
setup-docs:
    -git clone https://github.com/jbangdev/jbang ../jbang
    -git clone https://github.com/jbangdev/jbang-vscode ../jbang-vscode
    -git clone https://github.com/jbangdev/jbang-idea ../jbang-idea

    



