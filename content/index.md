---
layout: splash
title: "JBang - Run anything. Anywhere."
hero_title: "Run anything. Anywhere."
hero_kicker: "JBang · The easy way to run Java"
header:
  overlay_color: "#13151f"
  actions:
    - label: "<i class='fas fa-download'></i> Get JBang"
      url: "/download"
    - label: "<i class='fas fa-play'></i> Try in your browser"
      url: "/try"
excerpt: "JBang runs Java source files, scripts, full applications, anything on Maven Central — even code straight from a URL. No project setup, no build files. No Java installed? No problem."
feature_row:
  - image_path: /assets/images/carbon-deps.webp
    title: "Dependencies in your source"
    excerpt: "Declare dependencies right where you use them with <code>//DEPS group:artifact:version</code>
              and JBang fetches them automatically — no build file required."
  - image_path: /assets/images/carbon-install.webp
    title: "Install & Run Anywhere"
    excerpt: "JBang installs and runs on Windows, Linux and macOS — and in Docker, GitHub Actions, Maven, Gradle, even via <code>npx</code>, <code>pipx</code> and <code>uvx</code>."
    url: "/download"
    btn_label: "Download"
    btn_class: "btn--primary"
  - image_path: /assets/images/carbon-java.webp
    title: "No Java? No Problem!"
    excerpt: "JBang automatically downloads the Java version your code needs — your users never have to think about it."
feature_row2:
  - image_path: /assets/images/feature-version-juggle.svg
    title: "Java 8 and higher"
    excerpt: "Use any Java from version 8 up to the latest release — JBang juggles versions for you."
  - image_path: /assets/images/feature-appstore.webp
    title: "JBang AppStore"
    excerpt: "Discover ready-to-run apps and tools, or publish your own from a git backed <code>jbang-catalog.json</code>."
    url: "/appstore"
    btn_label: "Browse the AppStore"
    btn_class: "btn--primary"
  - image_path: /assets/images/feature-ide.webp
    title: "Works in your IDE"
    excerpt: "Easy editing in IntelliJ, Eclipse, Visual Studio Code, Apache NetBeans, vim and emacs. All with proper content assist and debug."
    url: "/ide"
    btn_label: "IDE Setup"
    btn_class: "btn--primary"
---

<div class="home-terminal">
  <div class="home-terminal__bar">
    <span class="home-terminal__dot home-terminal__dot--red"></span>
    <span class="home-terminal__dot home-terminal__dot--yellow"></span>
    <span class="home-terminal__dot home-terminal__dot--green"></span>
    <span class="home-terminal__name">jbang — run anything</span>
  </div>
  <pre class="home-terminal__body"><code><span class="t-comment"># run Java source directly — no project, no build file</span>
<span class="t-prompt">$</span> jbang hello.java

<span class="t-comment"># Kotlin, Groovy, JShell — even code blocks in Markdown</span>
<span class="t-prompt">$</span> jbang script.kt

<span class="t-comment"># run any runnable artifact straight from Maven Central</span>
<span class="t-prompt">$</span> jbang info.picocli:picocli-codegen:4.6.3

<span class="t-comment"># run apps published in the JBang AppStore</span>
<span class="t-prompt">$</span> jbang gavsearch@jbangdev quarkus

<span class="t-comment"># run code right off the web</span>
<span class="t-prompt">$</span> jbang https://gist.github.com/maxandersen/f43b4c52dfcfc42dcd59a04e49acf6ec</code></pre>
</div>

<section class="home-section">
  <p class="home-section__kicker">Not just scripting</p>
  <h2 class="home-section__title">One tool. Everything Java can do.</h2>
  <p class="home-section__lead">JBang started as the easiest way to script with Java. Today it is the easiest way to run <em>anything</em> built for the JVM — for students, scripters and teams shipping real tools.</p>
  <div class="home-grid">
    <div class="home-grid__item">
      <i class="fas fa-file-code" aria-hidden="true"></i>
      <h3>Java source files</h3>
      <p>Run <code>.java</code> files instantly — a single file or a whole directory of sources. Any Java from 8 to the latest release.</p>
    </div>
    <div class="home-grid__item">
      <i class="fas fa-terminal" aria-hidden="true"></i>
      <h3>Scripts &amp; one-liners</h3>
      <p>JShell <code>.jsh</code>, Kotlin, Groovy and even Markdown files run just as easily — perfect for automation and quick experiments.</p>
    </div>
    <div class="home-grid__item">
      <i class="fas fa-cube" aria-hidden="true"></i>
      <h3>Anything on Maven Central</h3>
      <p>Point JBang at <code>group:artifact:version</code> and it fetches and runs the artifact. Local JARs work too.</p>
    </div>
    <div class="home-grid__item">
      <i class="fas fa-globe" aria-hidden="true"></i>
      <h3>Code from any URL</h3>
      <p>GitHub, GitLab, Bitbucket, gists or any https URL — run code right off the web, with trust checks built in.</p>
    </div>
    <div class="home-grid__item">
      <i class="fas fa-rocket" aria-hidden="true"></i>
      <h3>Full applications</h3>
      <p>CLIs, Quarkus and Spring services, desktop apps. <code>jbang app install</code> puts them on your PATH like any native tool.</p>
    </div>
    <div class="home-grid__item">
      <i class="fas fa-robot" aria-hidden="true"></i>
      <h3>AI tools &amp; MCP servers</h3>
      <p>Modern dev tools, agents and MCP servers ship as JBang apps — your users run them with a single command, no install guide needed.</p>
    </div>
  </div>
</section>

{#include partials/feature_row feature_row=page.data['feature_row'] /}

{#for post in site.collections.posts.take(1)}
<div class="home-latest-post">
  <p class="home-latest-post__label">Latest Blog Post</p>
  <h2 class="home-latest-post__title">
    <a href="{post.url}" rel="permalink">{post.title}</a>
  </h2>
  <p class="home-latest-post__date">{post.date.format('MMMM d, yyyy')}</p>
</div>
{/for}

<div class="feature__wrapper twitter-wrapper">
{#twitter cdi:testimonials.list.random align="center" width=550 /}
<center><a href="/testimonials" class="btn btn--cta btn--primary">All Testimonials</a></center>
</div>

{#include partials/feature_row feature_row=page.data['feature_row2'] /}

<section class="home-section">
  <p class="home-section__kicker">Watch</p>
  <h2 class="home-section__title">JBang — Unleash the power of Java</h2>
  <div class="fluid-width-video-wrapper" style="width: 100%; position: relative; padding-bottom: 56.25%; box-sizing: border-box;">
      <iframe
          style="position: absolute; top: 0; left: 0; height: 100%; width: 100%"
          width="1280"
          height="720"
          src="https://youtube.com/embed/cpKwBbz1sf0"
          frameborder="0"
          allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
          allowfullscreen
          loading="lazy"
      >
      </iframe>
  </div>
</section>

<section class="home-cta">
  <h2>Ready to run?</h2>
  <p>One line. Any OS. Java included.</p>
  <pre class="home-cta__cmd"><code>curl -Ls https://sh.jbang.dev | bash -s - app setup</code></pre>
  <p>
    <a href="/download" class="btn btn--large home-cta__btn">All install options</a>
    <a href="/documentation/jbang/latest/" class="btn btn--large home-cta__btn home-cta__btn--ghost">Read the docs</a>
  </p>
</section>
