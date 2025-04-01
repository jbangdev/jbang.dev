---
layout: splash
header:
  overlay_image: /assets/images/slider/bg-1.jpg
  actions:
    - label: "<i class='fas fa-download'></i> Download"
      url: "/download"
    - label: "Try"
      url: "/try"
excerpt: "Lets Students, Educators and Professional Developers create, edit and run self-contained source-only Java programs with unprecedented ease."
feature_row:
  - image_path: /assets/images/carbon-deps.png
    title: "Embedded Dependencies"
    excerpt: "Automatic fetching of any dependency using `//DEPS group:artifact:version` 
              or `@Grab` annotations directly from the source code."
  - image_path: /assets/images/carbon-install.png
    title: "Install & Run Anywhere"
    excerpt: "JBang installs and run on Windows, Linux, macOS, Docker and Github Actions as well as usable from Maven and Gradle plugins"
    url: "/download"
    btn_label: "Download"
    btn_class: "btn--primary"
  - image_path: /assets/images/carbon-java.png
    title: "No Java ? No Problem!"
    excerpt: "Java will automatically be downloaded when needed."
feature_row2:
  - image_path: /assets/images/feature-version-juggle.svg
    title: "Java 8 and higher"
    excerpt: "You can use any Java, from version 8 and up"
  - image_path: /assets/images/feature-appstore.png
    title: "JBang AppStore"
    excerpt: "Use the JBang AppStore to find others application or publish your own from a git backed `jbang-catalog.json`"
    url: "/appstore"
    btn_label: "AppStore"
    btn_class: "btn--primary"
  - image_path: /assets/images/feature-ide.png
    title: "Works in your IDE"
    excerpt: "Easy editing in Intellij, Eclipse, Visual Studio Code, Apache Netbeans, vim and emacs. All with proper content assist and debug"
---

{% include feature_row id="intro" type="center" %}

<center>
{% include slot-machine.html learn_more_url="/everywhere" %}
</center>

<hr>

{% include feature_row %}

<div class="feature__wrapper">

{% assign rdtestimonials =site.data.testimonials | sample: 1 %}
{% for tweet in rdtestimonials %}
  {% twitter tweet hide_thread=true align=center width=350 %}
{% endfor %}
<center><a href="/testimonials" class="btn btn--primary">All Testimonials</a></center>
</div>

{% include feature_row id="feature_row2" %}


    
### Watch <a name="watch"/>
    
Below is latest talk about JBang: "jbang - Unleash the power of Java"
 
<iframe width="1280" height="720" src="https://youtube.com/embed/cpKwBbz1sf0" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
         