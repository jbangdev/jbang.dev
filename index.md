---
layout: splash
header:
  overlay_image: /assets/images/slider/bg-3.jpg
  actions:
    - label: "<i class='fas fa-download'></i> Download"
      url: "/download"
    - label: "Try"
      url: "#try"
excerpt: "Run Java from anywhere"
intro: 
  - excerpt: 'The easiest way to use Java as a beginner as well as experienced developer.'
feature_row:
  - image_path: https://mmistakes.github.io/minimal-mistakes/assets/images/mm-customizable-feature.png
    title: "Simplified Dependencies"
    excerpt: "Automatic fetching of any dependency using `//DEPS group:artifact:version` 
              or `@Grab` annotations."
  - image_path: https://mmistakes.github.io/minimal-mistakes/assets/images/mm-responsive-feature.png
    title: "Install & Run Anywhere"
    excerpt: "JBang installs and run on Windows, Linux, macOS, Docker and Github Actions"
    url: "/download"
    btn_label: "Download"
    btn_class: "btn--primary"
  - image_path: https://mmistakes.github.io/minimal-mistakes/assets/images/mm-free-feature.png
    title: "No Java ? No Problem!"
    excerpt: "Java will automatically be downloaded when needed."
feature_row2:
  - image_path: https://mmistakes.github.io/minimal-mistakes/assets/images/mm-customizable-feature.png
    title: "Easy Repositories"
    excerpt: "No need to remember the full url of Maven repositories. Use short hand, like `//REPOS jitpack` to easily add major repositories"
  - image_path: https://mmistakes.github.io/minimal-mistakes/assets/images/mm-responsive-feature.png
    title: "Executable scripts"
    excerpt: "`./helloworld.java` works on Linux, Mac & Windows w/bash. Use Java for true scripting."
  - image_path: https://mmistakes.github.io/minimal-mistakes/assets/images/mm-free-feature.png
    title: "Works in your IDE"
    excerpt: "Easy editing in Intellij, Eclipse, Visual Studio Code, Apache Netbeans, vim and emacs. All with proper content assist and debug"
feature_row3:
  - image_path: https://mmistakes.github.io/minimal-mistakes/assets/images/mm-customizable-feature.png
    title: "Scripting Catalogs"
    excerpt: "Run anyone's script or provide you own aliases from a git backed catalog, i.e. `jbang env@jbangdev`"
  - image_path: https://mmistakes.github.io/minimal-mistakes/assets/images/mm-responsive-feature.png
    title: "Java 8 and higher"
    excerpt: "You can run `.java` as scripts using Java 8+, `.jsh` works with Java 9+"
  - image_path: https://mmistakes.github.io/minimal-mistakes/assets/images/mm-free-feature.png
    title: "Cached Builds"
    excerpt: "Builds are cached making re-runs instant."
---

{% include feature_row id="intro" type="center" %}

{% include feature_row %}

{% include feature_row id="feature_row2" %}

{% include feature_row id="feature_row3" %}
