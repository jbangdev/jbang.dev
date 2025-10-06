---
title: Try JBang with Jupyter
link: /try/jupyter/
layout: splash
---
{|
## Try JBang with Jupyter

Create a custom Jupyter notebook environment with your own GitHub repository or Gist.

### Quick Start

Launch the pre-configured JBang Jupyter environment with [example notebook](https://github.com/jbangdev/jbang-jupyter-examples):

<a href="https://mybinder.org/v2/gh/jupyter-java/jupyter-java-binder/jbang?urlpath=git-pull%3Frepo%3Dhttps%253A%252F%252Fgithub.com%252Fjbangdev%252Fjbang-jupyter-examples%26urlpath%3Dlab%252Ftree%252Fcontent%252F%26branch%3Dmain%26targetPath%3Dcontent" 
   class="btn btn--primary btn--large" target="_blank">
  🚀 Launch JBang Jupyter Environment
</a>

### Custom Link Generator

Create a custom Jupyter environment with your own repository or Gist:

### Quick Preset

Try these:

<div class="preset-buttons">
  <a href="/try/jupyter/?repo=https://github.com/jbangdev/jbang-jupyter-examples" class="btn btn--primary">
    📚 JBang Examples
  </a>
</div>


<div class="link-generator">
  <div class="generator-form">
    <div class="form-group">
      <label for="repo-url">Repository URL:</label>
      <input type="url" id="repo-url" placeholder="https://github.com/username/repo or https://gist.github.com/username/gist-id" />
      <small class="form-help">Enter a GitHub repository or Gist URL</small>
    </div>
    <div class="form-group">
      <label for="branch">Branch (optional):</label>
      <input type="text" id="branch" placeholder="main" />
      <small class="form-help">Leave empty for default branch</small>
    </div>
    <div class="form-group">
      <label for="filepath">File/Path (optional):</label>
      <input type="text" id="filepath" placeholder="notebooks/example.ipynb" />
      <small class="form-help">Specific file or directory to open</small>
    </div>
    <button type="button" id="generate-link" class="btn btn--primary btn--large" style="width: 100%; margin-top: 1rem;">
      🔗 Generate Jupyter Link
    </button>
  </div>
  
  <div class="generated-link" id="generated-link" style="display: none;">
    <h4>Your Custom Jupyter Link:</h4>
    <div class="link-container">
      <input type="text" id="custom-link" readonly />
      <button type="button" id="copy-link" class="btn btn--inverse">
        📋 Copy
      </button>
    </div>
    <a href="#" id="launch-link" class="btn btn--primary" target="_blank">
      🚀 Launch Jupyter
    </a>
  </div>
</div>

### How it Works

1. **Repository**: Your GitHub repository or Gist containing Jupyter notebooks
2. **Branch**: The specific branch to use (defaults to main/master)
3. **File Path**: Optional specific file or directory to open in Jupyter
4. **Launch**: Opens your custom environment in MyBinder with JBang kernel


### Examples

<div class="examples">
  <div class="example">
    <h4>GitHub Repository</h4>
    <code>https://github.com/username/my-java-notebooks</code>
    <p>Opens the repository in Jupyter with JBang kernel</p>
  </div>
  
  <div class="example">
    <h4>GitHub Gist</h4>
    <code>https://gist.github.com/username/abc123def456</code>
    <p>Opens the Gist as a Jupyter environment</p>
  </div>
  
  <div class="example">
    <h4>Specific File</h4>
    <code>https://github.com/username/repo + path: notebooks/demo.ipynb</code>
    <p>Opens a specific notebook file directly</p>
  </div>
</div>

<style>
/* Use Minimal Mistakes button styles - no custom button CSS needed */

.preset-buttons {
  display: flex;
  gap: 1rem;
  margin: 2rem 0;
  flex-wrap: wrap;
}

.preset-buttons .btn {
  flex: 1;
  min-width: 200px;
  text-align: center;
}

.link-generator {
  background: #f8f9fa;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  padding: 2rem;
  margin: 2rem 0;
}

.generator-form {
  display: grid;
  gap: 1rem;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-group label {
  font-weight: 600;
  margin-bottom: 0.5rem;
  color: #333;
}

.form-group input {
  padding: 0.75rem;
  border: 1px solid #ced4da;
  border-radius: 4px;
  font-size: 1rem;
}

.form-group input:focus {
  outline: none;
  border-color: #f37626;
  box-shadow: 0 0 0 2px rgba(243, 118, 38, 0.25);
}

.form-help {
  color: #6c757d;
  font-size: 0.875rem;
  margin-top: 0.25rem;
}

.generated-link {
  margin-top: 2rem;
  padding-top: 2rem;
  border-top: 1px solid #dee2e6;
}

.generated-link h4 {
  margin: 0 0 1rem 0;
  color: #333;
}

.link-container {
  display: flex;
  gap: 0.5rem;
  margin: 1rem 0;
}

.link-container input {
  flex: 1;
  padding: 0.75rem;
  border: 1px solid #ced4da;
  border-radius: 4px;
  background: white;
  font-family: monospace;
  font-size: 0.9rem;
}

.examples {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 1.5rem;
  margin: 2rem 0;
}

.example {
  background: white;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  padding: 1.5rem;
}

.example h4 {
  margin: 0 0 1rem 0;
  color: #f37626;
}

.example code {
  background: #f8f9fa;
  padding: 0.5rem;
  border-radius: 4px;
  font-family: monospace;
  display: block;
  margin: 0.5rem 0;
  word-break: break-all;
}

.example p {
  margin: 0.5rem 0 0 0;
  color: #666;
  font-size: 0.9rem;
}

@media (max-width: 768px) {
  .link-container {
    flex-direction: column;
  }
  
  .examples {
    grid-template-columns: 1fr;
  }
}
</style>

<script>
document.addEventListener('DOMContentLoaded', function() {
  const repoUrlInput = document.getElementById('repo-url');
  const branchInput = document.getElementById('branch');
  const filepathInput = document.getElementById('filepath');
  const generateBtn = document.getElementById('generate-link');
  const generatedLinkDiv = document.getElementById('generated-link');
  const customLinkInput = document.getElementById('custom-link');
  const copyBtn = document.getElementById('copy-link');
  const launchLink = document.getElementById('launch-link');

  // Read URL parameters and pre-fill form
  const urlParams = new URLSearchParams(window.location.search);
  const repoParam = urlParams.get('repo');
  const branchParam = urlParams.get('branch');
  const filepathParam = urlParams.get('filepath');
  
  if (repoParam) {
    repoUrlInput.value = repoParam;
  }
  if (branchParam) {
    branchInput.value = branchParam;
  }
  if (filepathParam) {
    filepathInput.value = filepathParam;
  }
  
  // Auto-generate link if all required parameters are present
  if (repoParam) {
    generateBtn.click();
  }

  generateBtn.addEventListener('click', function() {
    const repoUrl = repoUrlInput.value.trim();
    if (!repoUrl) {
      alert('Please enter a repository URL');
      return;
    }

    // Parse GitHub URL
    let githubUrl, branch, filepath;
    
    if (repoUrl.includes('gist.github.com')) {
      // Handle Gist URLs - use the original Gist URL directly
      githubUrl = repoUrl;
      branch = branchInput.value.trim() || 'main';
    } else if (repoUrl.includes('github.com')) {
      // Handle regular GitHub URLs
      githubUrl = repoUrl.replace(/\.git$/, '');
      branch = branchInput.value.trim() || 'main';
    } else {
      alert('Please enter a valid GitHub repository or Gist URL');
      return;
    }

    filepath = filepathInput.value.trim();

    // Build MyBinder URL following the Java implementation
    const base = 'https://mybinder.org/v2/gh/jupyter-java/jupyter-java-binder/jbang';
    const content = 'content';
    
    // Build the path to open
    const pathToOpen = 'lab/tree/' + content + '/' + filepath;
    
    // Build the nested urlpath value (before encoding)
    const gitPullParams = new URLSearchParams();
    gitPullParams.append('repo', githubUrl);
    gitPullParams.append('urlpath', pathToOpen);
    gitPullParams.append('branch', branch);
    gitPullParams.append('targetPath', content);
    
    const gitPullUrlpath = 'git-pull?' + gitPullParams.toString();
    
    // Encode the nested urlpath value
    const encodedNestedUrlpath = encodeURIComponent(gitPullUrlpath);
    
    // Compose the final URL
    const finalUrl = base + '?urlpath=' + encodedNestedUrlpath;

    // Show generated link
    customLinkInput.value = finalUrl;
    launchLink.href = finalUrl;
    generatedLinkDiv.style.display = 'block';
  });

  copyBtn.addEventListener('click', function() {
    customLinkInput.select();
    document.execCommand('copy');
    
    // Visual feedback
    const originalText = copyBtn.textContent;
    copyBtn.textContent = '✅ Copied!';
    setTimeout(() => {
      copyBtn.textContent = originalText;
    }, 2000);
  });
});
</script>
|}
