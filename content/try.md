---
title: Try JBang
link: /try/
layout: splash
---
{|
## Try JBang

Below is an embedded editor where you can write Java code using JBang directives, click run and see it execute.

<div class="notice--warning" style="background: #fff8e1; border: 1px solid #ffe082; border-radius: 4px; padding: 1rem; margin-bottom: 1.5rem;">
  <strong>Note:</strong>
  This JBang try page is relying on a free public Binder service (<a href="https://mybinder.readthedocs.io/en/latest/about/user-guidelines.html" target="_blank" rel="noopener">MyBinder</a>) and is subject to usage limits and temporary availability issues. For more details, see the <a href="https://mybinder.readthedocs.io/en/latest/about/user-guidelines.html" target="_blank" rel="noopener">Binder user guidelines</a>.
  Use a <a href="/ide/">dedicated IDE</a> if these constraints does not work for you.
</div>

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
          kernelName: "jbang",
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

<script>
// Handle URL parameters for pre-filling and redirects
document.addEventListener('DOMContentLoaded', function() {
  const urlParams = new URLSearchParams(window.location.search);
  const repoParam = urlParams.get('repo');
  const branchParam = urlParams.get('branch');
  const filepathParam = urlParams.get('filepath');
  const codeParam = urlParams.get('code');
  const redirectParam = urlParams.get('redirect');
  
  // Update Jupyter link with parameters if provided
  if (repoParam) {
    const jupyterLinkBtn = document.querySelector('.try-option.jupyter .btn--primary');
    if (jupyterLinkBtn) {
      // Build MyBinder URL
      const base = 'https://mybinder.org/v2/gh/jupyter-java/jupyter-java-binder/jbang';
      const content = 'content';
      const branch = branchParam || 'main';
      const filepath = filepathParam || '';
      const pathToOpen = filepath ? ('lab/tree/' + content + '/' + filepath) : ('lab/tree/' + content + '/');
      
      const gitPullParams = new URLSearchParams();
      gitPullParams.append('repo', repoParam);
      gitPullParams.append('urlpath', pathToOpen);
      gitPullParams.append('branch', branch);
      gitPullParams.append('targetPath', content);
      
      const gitPullUrlpath = 'git-pull?' + gitPullParams.toString();
      const encodedNestedUrlpath = encodeURIComponent(gitPullUrlpath);
      const jupyterUrl = base + '?urlpath=' + encodedNestedUrlpath;
      
      jupyterLinkBtn.href = jupyterUrl;
      
      // Update button text with title or repo URL
      const titleParam = urlParams.get('title');
      if (titleParam) {
        jupyterLinkBtn.textContent = '🚀 Open ' + titleParam;
      } else {
        // Extract a friendly name from the repo URL
        let repoName = repoParam;
        try {
          const urlObj = new URL(repoParam);
          const pathParts = urlObj.pathname.split('/').filter(p => p);
          if (pathParts.length >= 2) {
            repoName = pathParts[pathParts.length - 2] + '/' + pathParts[pathParts.length - 1];
          }
        } catch (e) {
          // Keep full URL if parsing fails
        }
        jupyterLinkBtn.textContent = '🚀 Open ' + repoName;
      }
    }
  }
  
  // Insert code into editor if provided
  if (codeParam) {
    try {
      const decodedCode = decodeURIComponent(codeParam);
      
      // Method 1: Try to set it immediately before Thebe loads
      const codeBlock = document.querySelector('[data-executable="true"]');
      if (codeBlock) {
        codeBlock.textContent = decodedCode;
      }
      
      // Method 2: Also try after Thebe loads by monitoring the CodeMirror instance
      const checkEditor = setInterval(function() {
        const codeBlock = document.querySelector('[data-executable="true"]');
        const codeMirror = document.querySelector('.CodeMirror');
        
        if (codeMirror && codeMirror.CodeMirror) {
          // CodeMirror is loaded, update it directly
          codeMirror.CodeMirror.setValue(decodedCode);
          clearInterval(checkEditor);
        } else if (codeBlock && !codeMirror) {
          // Thebe hasn't initialized yet, keep updating the original element
          codeBlock.textContent = decodedCode;
        }
      }, 100);
      
      // Stop checking after 10 seconds
      setTimeout(() => clearInterval(checkEditor), 10000);
    } catch (e) {
      console.error('Failed to decode code parameter:', e);
    }
  }
  
  // Handle redirect if specified
  if (redirectParam !== null) {
    const redirectDelay = redirectParam === '' ? 0 : parseInt(redirectParam, 10) || 0;
    const jupyterLink = document.querySelector('.try-option.jupyter .btn--primary');
    
    if (jupyterLink) {
      const targetUrl = jupyterLink.href;
      
      if (redirectDelay > 0) {
        // Show countdown message
        const messageDiv = document.createElement('div');
        messageDiv.style.cssText = 'position: fixed; top: 20px; left: 50%; transform: translateX(-50%); background: #f37626; color: white; padding: 1rem 2rem; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.15); z-index: 9999; font-size: 1.1rem;';
        messageDiv.innerHTML = `Redirecting to Jupyter in <strong id="countdown">${redirectDelay}</strong> seconds...`;
        document.body.appendChild(messageDiv);
        
        let remaining = redirectDelay;
        const countdownEl = document.getElementById('countdown');
        
        const countdownInterval = setInterval(() => {
          remaining--;
          if (countdownEl) {
            countdownEl.textContent = remaining;
          }
          if (remaining <= 0) {
            clearInterval(countdownInterval);
            window.location.href = targetUrl;
          }
        }, 1000);
      } else {
        // Immediate redirect
        window.location.href = targetUrl;
      }
    }
  }
});
</script>

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
      <h3>🚀 JBang powered Jupyter Environment</h3>
      <p>Interactive development environment with rich output and data visualization. Uses MyBinder.</p>
    </div>
    <div class="try-option-content">
      <p>Experience JBang in a full Jupyter notebook environment with:</p>
      <ul>
        <li>Run .java, .jsh, and more using JBang</li>
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
        <a href="/try/custom/" class="btn btn--inverse">
          Create Custom Link
        </a>
      </div>
    </div>
  </div>
</div>


  
|}