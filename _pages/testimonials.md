---
permalink: /testimonials/
layout: splash
---

## Testimonials

Below is a few testimonials found from Twitter related to JBang!

{% for tweet in site.data.testimonials %}
  {% twitter tweet hide_thread=true align=right width=350 %}
{% endfor %}