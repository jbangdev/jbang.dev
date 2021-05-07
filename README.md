## jbang.dev website

Source code for https://jbang.dev

### Build and run with bundle

Install ruby with bundler and then do:

```
bundle install
bundle exec jekyll serve --livereload
```

### Build and run with docker

First choose your jekyll version tag from [Docker Hub](https://hub.docker.com/r/jekyll/jekyll/tags?page=1&ordering=last_updated).

Example, when building with Jekyll 4.2.0 -

```
docker run --rm -p 4000:4000 \
  --volume="$PWD:/srv/jekyll" \
  -it jekyll/jekyll:4.2.0 \
  jekyll serve --livereload
```
Navigate to http://localhost:4000 for local site.