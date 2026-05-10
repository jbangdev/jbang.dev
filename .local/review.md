# JBang AppStore & IDE Page — Vue/Vuetify Removal

## What Changed

### Removed globally (from `templates/partials/head/custom.html`)
- **Vue 2** (`unpkg.com/vue@2`) — EOL since Dec 2023
- **Vuetify 2.5.0** CSS + JS + MDI fonts
- **Axios** (unpinned version)
- **ClipboardJS 2.0.8**
- Vue hljs plugin directive

highlight.js kept in global head (used by blog posts and try page).

### `content/appstore.html` — Full rewrite
- **Oat 0.6.1** CSS+JS loaded page-locally (not globally)
- Vanilla JS replaces Vue: `fetch()` for data, `navigator.clipboard` for copy
- Search with URL deep-linking (`?q=...`)
- Repo grouping via `<details>/<summary>` (collapsible)
- Pagination (20 repo groups per page)
- Loading spinner + error alert
- Toast notification on clipboard copy
- Mobile-responsive CSS
- Deprecated `<center>` tags replaced with CSS

### `content/ide.html` — Rewrite
- Vuetify `v-tabs` → Oat `<ot-tabs>` web component
- All content preserved, cleaner semantic HTML
- Oat loaded page-locally

### `public/assets/css/appstore.css` — Expanded
- Full styling for groups, search toolbar, copy buttons, toast, pagination, responsive breakpoints

## Weight comparison

| Before | After |
|--------|-------|
| Vue 2 (~100 KB) | — |
| Vuetify 2 (~300 KB CSS+JS) | — |
| MDI font (~200 KB) | — |
| Axios (~14 KB) | — |
| ClipboardJS (~8 KB) | — |
| **~620 KB on every page** | **~39 KB on appstore/ide only** (Oat 8KB + custom 3KB + appstore.json 545KB fetched async) |
