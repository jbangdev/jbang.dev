---
title: Try JBang
link: /try/
layout: splash
---
{|
## Try JBang

Choose how you'd like to try JBang:

<div class="try-options">
  <div class="try-option jdoodle">
    <div class="try-option-header">
      <h3>⚡ Quick Try</h3>
      <p>Simple, fast code execution in your browser</p>
    </div>
    <div class="try-option-content">
      <p>Get started quickly with a lightweight development environment:</p>
      <ul>
        <li>Instant code execution</li>
        <li>No setup required</li>
        <li>Perfect for quick tests</li>
        <li>Runs in your browser</li>
      </ul>
      <div class="try-option-actions">
        <a href="/try/jdoodle/" class="btn btn--primary">
          Try with JDoodle
        </a>
      </div>
    </div>
  </div>

  <div class="try-option jupyter">
    <div class="try-option-header">
      <h3>🚀 Jupyter Notebook</h3>
      <p>Interactive development environment with rich output and data visualization</p>
    </div>
    <div class="try-option-content">
      <p>Experience JBang in a full Jupyter notebook environment with:</p>
      <ul>
        <li>Interactive code execution</li>
        <li>Rich output and visualizations</li>
        <li>Markdown documentation</li>
        <li>Data science libraries</li>
      </ul>
      <div class="try-option-actions">
        <a href="https://mybinder.org/v2/gh/jupyter-java/jupyter-java-binder/jbang?urlpath=git-pull%3Frepo%3Dhttps%253A%252F%252Fgithub.com%252Fjupyter-java%252Fjupyter-java-examples%26urlpath%3Dlab%252Ftree%252Fjbang%252F%26branch%3Djbang%26targetPath%3Djbang" 
           class="btn btn--primary" target="_blank">
          Launch Jupyter Environment
        </a>
        <a href="/try/jupyter/" class="btn btn--inverse">
          Create Custom Link
        </a>
      </div>
    </div>
  </div>
</div>

<style>
.try-options {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 2rem;
  margin: 2rem 0;
}

.try-option {
  border: 2px solid #e1e5e9;
  border-radius: 12px;
  padding: 1.5rem;
  transition: all 0.3s ease;
  background: #fff;
}

.try-option:hover {
  border-color: #007acc;
  box-shadow: 0 4px 12px rgba(0, 122, 204, 0.1);
  transform: translateY(-2px);
}

.try-option.jupyter {
  border-color: #f37626;
}

.try-option.jupyter:hover {
  border-color: #f37626;
  box-shadow: 0 4px 12px rgba(243, 118, 38, 0.1);
}

.try-option-header h3 {
  margin: 0 0 0.5rem 0;
  font-size: 1.5rem;
  color: #333;
}

.try-option-header p {
  margin: 0 0 1rem 0;
  color: #666;
  font-size: 1rem;
}

.try-option-content ul {
  margin: 1rem 0;
  padding-left: 1.5rem;
}

.try-option-content li {
  margin: 0.5rem 0;
  color: #555;
}

.try-option-actions {
  margin-top: 1.5rem;
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

/* Use Minimal Mistakes button styles - no custom button CSS needed */

@media (max-width: 768px) {
  .try-options {
    grid-template-columns: 1fr;
    gap: 1rem;
  }
  
  .try-option-actions {
    flex-direction: column;
  }
  
  .btn {
    width: 100%;
  }
}
</style>
|}