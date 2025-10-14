---
title: Try JBang
link: /try/
layout: splash
---
{|
## Try JBang

Below is an embedded notebook where you can write Java code using JBang directives, click run and see it execute.

<!-- Configure and load Thebe - must be before the thbe js is loaded !-->
<script type="text/x-thebe-config">
  {
        bootstrap: true,
        requestKernel: true,
        persistKernel: false,
        useJupyterLite: false,
        useBinder: true,
        binderOptions: {
          repo: "jupyter-java/jupyter-java-binder",
          ref: "jbang",
          binderUrl: 'https://mybinder.org'
        },
        kernelOptions: {
          kernelName: "jbang-jbang",
        },
        codeMirrorConfig: {
          theme: "default",
          lineNumbers: true,
          lineWrapping: true
        }
      }
      
</script>

<script src="https://unpkg.com/thebe@0.9.3/lib/index.js"></script>
<link rel="stylesheet" href="https://unpkg.com/thebe@0.9.3/lib/thebe.css">

 <style>
   /* Set Jupyter/Thebe code font size for try page */
   :root {
     --jp-code-font-size: 20px;
   }

  /* Editor controls - buttons and status in one row */
  .editor-controls {
    display: flex;
    align-items: center;
    gap: 1rem;
    margin: 0;
    padding: 0.5rem 0.75rem;
    background: #f8f9fa;
    border: 1px solid #dee2e6;
    border-radius: 4px;
  }

  .editor-controls .thebe-status {
    margin: 0;
    padding: 0;
    flex: 1;
    border: none !important;
    background: transparent !important;
    font-size: 0.85rem;
  }

  /* Make all status elements inline */
  .thebe-status-stub,
  .thebe-status-field,
  .thebe-status-message {
    display: inline;
    margin: 0 0.25rem;
    font-size: 0.85rem;
    font-style: normal;
  }

  .thebe-status-message {
    color: #999;
    font-size: 0.8rem;
  }

  /* Hide unwanted Thebe buttons */
  .thebe-restartall-button,
  .thebe-runall-button {
    display: none !important;
  }
  
  /* Reduce spacing around editor */
  .listingblock {
    margin: 0 0 1.5rem 0;
  }
  
  .listingblock .content {
    margin: 0;
  }
  
  .listingblock pre {
    margin: 0;
  }
  
  .thebelab-cell {
    margin: 0 !important;
  }

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

<!-- 
<script>
thebe.on("status", function (evt, data) {
    console.log("Status changed:", data.status, data.message);
});
</script> 
-->
<!--<div class="thebe-activate"></div>-->

<div class="listingblock">
  <div class="editor-controls">
   <div class="thebe-status"></div>
  </div>
  |}

{#include partials/try-samples.html /}

{|
  <div class="content">
    <pre class="highlight hljs-copy-wrapper">
      <code class="language-java hljs" data-lang="java" data-executable="true">
///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS com.github.lalyos:jfiglet:0.0.8
import com.github.lalyos.jfiglet.FigletFont;
IO.println(FigletFont.convertOneLine("Hello JBang")); 
      </code>
    </pre> 
  </div>
</div>

You can also open a full interactive development environment.

  <div class="try-option jupyter">
    <div class="try-option-header">
      <h3>🚀 JBang powered Jupyter Notebook</h3>
      <p>Interactive development environment with rich output and data visualization</p>
    </div>
    <div class="try-option-content">
      <p>Experience JBang in a full Jupyter notebook environment with:</p>
      <ul>
        <li>Interactive code execution</li>
        <li>Rich output and visualizations</li>
        <li>Multiple files</li>
        <li>Use your own github or gist</li>
      </ul>
      <div class="try-option-actions">
        <a href="https://mybinder.org/v2/gh/jupyter-java/jupyter-java-binder/jbang?urlpath=git-pull%3Frepo%3Dhttps%253A%252F%252Fgithub.com%252Fjbangdev%252Fjbang-jupyter-examples%26urlpath%3Dlab%252Ftree%252Fcontent%252F%26branch%3Dmain%26targetPath%3Dcontent" 
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

  
|}