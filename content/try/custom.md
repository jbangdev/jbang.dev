---
title: Create Custom JBang Try Link
link: /try/custom/
layout: splash
---
{|
## Create Custom JBang Try Link

Create a shareable link to the JBang try page with your own custom code and/or link to GitHub repository or Gist.

### Link Generator

Create a custom shareable link to `/try/` with pre-filled repository, code, or auto-redirect:


<div class="link-generator">
  <div class="generator-form">
    <div class="form-group">
     <div class="form-group">
       <label for="code">Java Code (optional):</label>
       <textarea id="code" rows="4" placeholder="IO.println(&quot;Hello JBang!&quot;);"></textarea>
       <small class="form-help">Pre-fill the code editor with this code</small>
     </div>
    </div>
    <div class="form-group">
      <label for="repo-url">Repository URL (optional):</label>
      <input type="url" id="repo-url" placeholder="https://github.com/username/repo or https://gist.github.com/username/gist-id" />
      <small class="form-help">GitHub repository or Gist URL for Jupyter environment</small>
    </div>
    <div class="form-group">
      <label for="title">Repository Title (optional):</label>
      <input type="text" id="title" placeholder="My Awesome Project" />
      <small class="form-help">Friendly name for the repository (shown in button text)</small>
    </div>
    <div class="form-group">
      <label for="branch">Branch (optional):</label>
      <input type="text" id="branch" placeholder="main" />
      <small class="form-help">Leave empty for default branch</small>
    </div>
    <div class="form-group">
      <label for="filepath">File/Path (optional):</label>
      <input type="text" id="filepath" placeholder="notebooks/example.ipynb" />
      <small class="form-help">Specific file or directory to open in Jupyter</small>
    </div>
    <div class="form-group">
      <label for="redirect">Auto-Redirect (optional):</label>
      <input type="number" id="redirect" placeholder="Leave empty for no redirect" min="0" max="10" />
      <small class="form-help">Seconds to wait before redirecting to Jupyter (0 for immediate, empty for no redirect)</small>
    </div>
  </div>
  
  <div class="generated-link" id="generated-link">
    <h4>Generated Try Link:</h4>
    <div class="link-container">
      <input type="text" id="custom-link" readonly />
      <button type="button" id="copy-link" class="btn btn--inverse">
        📋 Copy
      </button>
    </div>
    <a href="#" id="launch-link" class="btn btn--primary" target="_blank">
      🚀 Open Try Page
    </a>
  </div>
</div>

### How it Works

All fields are optional! The link updates in real-time as you type:

1. **Code**: Java code to pre-fill in the try page editor
2. **Repository**: GitHub repository or Gist URL - updates the Jupyter launch link on `/try/`
3. **Title**: Friendly name shown in the "Launch" button (e.g., "My Project" → "🚀 Open My Project")
4. **Branch**: Specific branch to use (defaults to main)
5. **File Path**: Specific file or directory to open in Jupyter
6. **Redirect**: Auto-redirect to Jupyter after specified seconds (empty = no redirect)
7. **Result**: Generates a shareable `/try/` link - defaults to `/try/` if no fields are filled

### Generated Link Format

This tool generates links to `/try/` with the following format:

```
/try/?repo=REPO_URL&title=TITLE&branch=BRANCH&filepath=PATH&code=CODE&redirect=SECONDS
```

**Parameters:**
- `code` (optional): URL-encoded Java code to pre-fill the editor
- `repo` (optional): GitHub repository or Gist URL
  - Example: `https://github.com/username/repo`
  - Example: `https://gist.github.com/username/gist-id`
- `title` (optional): Friendly name for the repository shown in button
  - Example: `My Awesome Project` → button shows "🚀 Open My Awesome Project"
- `branch` (optional): Branch name (defaults to `main`)
- `filepath` (optional): Path to specific file or directory
- `redirect` (optional): Seconds to wait before auto-redirecting to Jupyter
  - Empty value or `0` = immediate redirect
  - Omit parameter = no redirect

**Example Generated Links:**

Pre-fill code:
```
/try/?code=IO.println(%22Hello%20JBang!%22);
```

Repository with redirect:
```
/try/?repo=https://github.com/jbangdev/jbang-jupyter-examples&redirect=3
```

Gist with code:
```
/try/?repo=https://gist.github.com/user/abc123&code=IO.println(%22Demo%22);
```


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

.form-group input,
.form-group textarea {
  padding: 0.75rem;
  border: 1px solid #ced4da;
  border-radius: 4px;
  font-size: 1rem;
  font-family: inherit;
}

.form-group textarea {
  font-family: monospace;
  resize: vertical;
}

.form-group input:focus,
.form-group textarea:focus {
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
  const titleInput = document.getElementById('title');
  const branchInput = document.getElementById('branch');
  const filepathInput = document.getElementById('filepath');
  const codeTextarea = document.getElementById('code');
  const redirectInput = document.getElementById('redirect');
  const customLinkInput = document.getElementById('custom-link');
  const launchLink = document.getElementById('launch-link');
  const copyBtn = document.getElementById('copy-link');

  // Read URL parameters and pre-fill form
  const urlParams = new URLSearchParams(window.location.search);
  const repoParam = urlParams.get('repo');
  const titleParam = urlParams.get('title');
  const branchParam = urlParams.get('branch');
  const filepathParam = urlParams.get('filepath');
  const codeParam = urlParams.get('code');
  const redirectParam = urlParams.get('redirect');
  
  if (repoParam) {
    repoUrlInput.value = repoParam;
  }
  if (titleParam) {
    titleInput.value = titleParam;
  }
  if (branchParam) {
    branchInput.value = branchParam;
  }
  if (filepathParam) {
    filepathInput.value = filepathParam;
  }
  if (codeParam) {
    codeTextarea.value = decodeURIComponent(codeParam);
  }
  if (redirectParam !== null) {
    redirectInput.value = redirectParam;
  }

  // Function to generate the link
  function generateLink() {
    const repoUrl = repoUrlInput.value.trim();
    const title = titleInput.value.trim();
    const branch = branchInput.value.trim();
    const filepath = filepathInput.value.trim();
    const code = codeTextarea.value.trim();
    const redirect = redirectInput.value.trim();

    // Build /try/ URL with parameters
    const tryParams = new URLSearchParams();
    
    if (repoUrl) {
      tryParams.append('repo', repoUrl);
    }
    if (title) {
      tryParams.append('title', title);
    }
    if (branch) {
      tryParams.append('branch', branch);
    }
    if (filepath) {
      tryParams.append('filepath', filepath);
    }
    if (code) {
      tryParams.append('code', code);
    }
    if (redirect !== '') {
      tryParams.append('redirect', redirect);
    }
    
    // Build the final URL - default to /try/ if no parameters
    const queryString = tryParams.toString();
    const relativePath = queryString ? '/try/?' + queryString : '/try/';
    
    // Build full URL with host for copying
    const fullUrl = window.location.origin + relativePath;

    // Update the link display (show full URL) and href (relative path)
    customLinkInput.value = fullUrl;
    launchLink.href = relativePath;
  }
  
  // Generate link on any input change
  repoUrlInput.addEventListener('input', generateLink);
  titleInput.addEventListener('input', generateLink);
  branchInput.addEventListener('input', generateLink);
  filepathInput.addEventListener('input', generateLink);
  codeTextarea.addEventListener('input', generateLink);
  redirectInput.addEventListener('input', generateLink);
  
  // Generate initial link
  generateLink();

  copyBtn.addEventListener('click', function() {
    customLinkInput.select();
    navigator.clipboard.writeText(customLinkInput.value)
      .then(() => {
        // Visual feedback
        const originalText = copyBtn.textContent;
        copyBtn.textContent = '✅ Copied!';
        setTimeout(() => {
          copyBtn.textContent = originalText;
        }, 2000);
      })
      .catch(() => {
        // Fallback or error feedback
        const originalText = copyBtn.textContent;
        copyBtn.textContent = '❌ Copy failed';
        setTimeout(() => {
          copyBtn.textContent = originalText;
        }, 2000);
      });
  });
});
</script>
|}
