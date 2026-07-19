---
link: /testimonials/
layout: splash
---

## Testimonials

What people have said about JBang!

<div class="testimonials-grid">
{#for testimonial in cdi:testimonials.list}
<div class="testimonial-card">
  <blockquote class="testimonial-text">
    {testimonial.text}
  </blockquote>
  <div class="testimonial-meta">
    <strong>{testimonial.author}</strong>
    <span class="testimonial-handle">
      <a href="{testimonial.url}" rel="noopener noreferrer">@{testimonial.handle}</a>
    </span>
    <span class="testimonial-date">{testimonial.date}</span>
  </div>
</div>
{/for}
</div>

<style>
.testimonials-grid {
  max-width: 900px;
  margin: 2rem auto;
}
.testimonial-card {
  border: 1px solid #e1e4e8;
  border-radius: 12px;
  padding: 1.5rem;
  margin-bottom: 1.5rem;
  background: #fff;
}
.testimonial-text {
  margin: 0 0 1rem 0;
  padding: 0;
  border: none;
  font-size: 1.05rem;
  line-height: 1.6;
  color: #1a1a1a;
}
.testimonial-text p {
  margin: 0;
  white-space: pre-line;
}
.testimonial-meta {
  display: flex;
  flex-wrap: wrap;
  align-items: baseline;
  gap: 0.5rem;
  font-size: 0.9rem;
  color: #555;
}
.testimonial-handle a {
  color: #eb586f;
  text-decoration: none;
}
.testimonial-handle a:hover {
  text-decoration: underline;
}
.testimonial-date {
  color: #999;
}
.testimonial-date::before {
  content: "·";
  margin-right: 0.5rem;
}
</style>
