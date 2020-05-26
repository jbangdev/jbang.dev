require 'pathname'

module Jekyll
  module UrlRelativizer
    ## this initially overrodes relative_url default filter to actually generate relative urls
    ## based on github.com/jekyll/jekyll/pull/6362/ (and with some bugfixes from comments)
    ## BUT plugins like jekyll-feed relies on relative_url to return an *absolute* url starting with '/' otherwise it errors during jekyll serve.
    ## Thus now using this relativize_url in all my templates/includes to ensure
    ## relative content.
    def relativize_url(input)
      return if input.nil?
      input    = ensure_leading_slash(input)
      page_url = @context.registers[:page]["url"]
      page_dir = Pathname(page_url).parent

      target   = page_url.end_with?("/") ? Pathname(page_url) : page_dir
      result   = Pathname(input).relative_path_from(target).to_s
      ## warn(input + ' -> (' + page_url.to_s + ', ' + ', ' + page_dir.to_s + ')=' + result)

      result
    end
  end
end

Liquid::Template.register_filter(Jekyll::UrlRelativizer)
