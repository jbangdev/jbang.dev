## DEPRECATED - use mise instaed!

quarkus := require('quarkus')
mvn := require('mvn')
jbang := require('jbang')

generate:
    mise run generate

serve:
    mise run serve

build:
    # mvn package is to ensure antora dev is present
    mise run build

preview:
    mise run preview


# watch docs-site for changes
watch-local-docs:
    mise run watch-local-docs

local-docs:
    mise run local-docs

docs:
    mise run docs

open-docs:
    mise run open-docs

# clone the relevant folders for documentation
setup-docs:
    mise run setup-docs
