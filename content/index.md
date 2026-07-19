---
layout: splash
title: JBang
header:
  overlay_image: /assets/images/slider/bg-1.webp
  actions:
    - label: "<i class='fas fa-download'></i> Download"
      url: "/download"
    - label: "Try"
      url: "/try"
excerpt: Lets Students, Educators and Professional Developers create, edit and run self-contained source-only Java programs with unprecedented ease.
feature_row:
  - image_path: /assets/images/carbon-deps.webp
    title: "Embedded Dependencies"
    excerpt: "Automatic fetching of any dependency using <code>//DEPS group:artifact:version</code>
              or <code>@Grab</code> annotations directly from the source code."
  - image_path: /assets/images/carbon-install.webp
    title: "Install & Run Anywhere"
    excerpt: "JBang installs and run on Windows, Linux, macOS, Docker and Github Actions as well as usable from Maven and Gradle plugins"
    url: "/download"
    btn_label: "Download"
    btn_class: "btn--primary"
  - image_path: /assets/images/carbon-java.webp
    title: "No Java ? No Problem!"
    excerpt: "Java will automatically be downloaded when needed."
feature_row2:
  - image_path: /assets/images/feature-version-juggle.svg
    title: "Java 8 and higher"
    excerpt: "You can use any Java, from version 8 and up"
  - image_path: /assets/images/feature-appstore.webp
    title: "JBang AppStore"
    excerpt: "Use the JBang AppStore to find others application or publish your own from a git backed <code>jbang-catalog.json</code>"
    url: "/appstore"
    btn_label: "AppStore"
    btn_class: "btn--primary"
  - image_path: /assets/images/feature-ide.webp
    title: "Works in your IDE"
    excerpt: "Easy editing in Intellij, Eclipse, Visual Studio Code, Apache Netbeans, vim and emacs. All with proper content assist and debug"
---

{! #include partials/feature_row.html id="intro" type="center" !}

<!---
<center>
{#include partials/slot-machine.html learn_more_url="/everywhere" /}
</center>
-->

<center>
{#for post in site.collections.posts.take(1)}
    <div style="margin: 2.5rem auto; max-width: 600px; padding: 1.5rem; border-left: 4px solid #EB586F;">
      <p style="margin: 0 0 0.5rem 0; text-transform: uppercase; letter-spacing: 1px; font-size: 0.85rem; opacity: 0.7; font-weight: 600;">Latest Blog Post</p>
      <h2 class="archive__item-title" style="margin: 0 0 0.5rem 0;">
        <a href="{post.url}" rel="permalink">{post.title}</a>
      </h2>
      <p style="margin: 0; font-size: 0.9rem; opacity: 0.8;">{post.date.format('MMMM d, yyyy')}</p>
    </div>
{/for}
</center>

{#include partials/feature_row feature_row=page.data['feature_row'] /}


<div class="feature__wrapper twitter-wrapper">
{#let t=cdi:testimonials.list.random}
<div style="max-width: 550px; margin: 1.5rem auto; padding: 1.5rem; border: 1px solid #e1e4e8; border-radius: 12px; background: #fff; text-align: left;">
  <blockquote style="margin: 0 0 1rem 0; padding: 0; border: none; font-size: 1.05rem; line-height: 1.6; color: #1a1a1a;">
    <div style="margin: 0; white-space: pre-line;">{t.text}</div>
  </blockquote>
  <div style="font-size: 0.9rem; color: #555;"><strong>{t.author}</strong> <a href="{t.url}" style="color: #eb586f; text-decoration: none;">@{t.handle}</a> <span style="color: #999;">· {t.date}</span></div>
</div>
{/let}
<center><a href="/testimonials" class="btn btn--cta btn--primary">All Testimonials</a></center>
</div>

{#include partials/feature_row feature_row=page.data['feature_row2'] /}

### Watch

Below is the latest talk about JBang: "jbang - Unleash the power of Java"

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
