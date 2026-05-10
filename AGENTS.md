# AGENTS.md / CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is the source for the [jbang.dev](https://jbang.dev) website — built with **Quarkus Roq** (a static site generator on Quarkus) and **Antora** for documentation. Java 21, Maven.

## Prerequisites

Install [mise](https://mise.jdx.dev/) — it manages all tool versions (Java 21, Maven, Node, Quarkus CLI, jbang, etc.) via `mise.toml`.

## Common Commands

| Command | Purpose |
|---------|---------|
| `mise preview` | Dev mode with live-reload at http://localhost:8080 |
| `mise serve` | Generate production site to `target/roq/` and serve at http://localhost:8181 |
| `mise build` | `mvn package` — builds site + Antora docs to `public/documentation` |
| `mvn test` | Run JUnit 5 tests |
| `mvn test -Dtest=JBangSiteTest` | Run a single test class |
| `mise local-docs` | Generate Antora docs using local playbook (requires sibling repos) |
| `mise setup-docs` | Clone sibling repos (`../jbang`, `../jbang-vscode`, `../jbang-idea`) needed for local docs |

## Architecture

### Content & Templating (Roq)

- **`content/`** — Site pages and blog posts. Posts live in `content/posts/` with date-prefixed filenames (e.g., `2024-01-15-title.adoc`). Pages are Markdown or AsciiDoc.
- **`templates/layouts/`** — Qute page layouts (`single.html`, `post.html`, `splash.html`, etc.). Default layout is `single.html` (configured in `application.properties`).
- **`templates/partials/`** — Reusable Qute template partials (head, footer, masthead, etc.).
- **`src/main/resources/templates/`** — Additional Qute templates: custom tags (`tags/twitter.html`, `tags/youtube.html`) and format templates (`fm/rss.html`).
- **`public/`** — Static assets served as-is (CSS, JS, images, fonts).

### Data Files

- **`data/`** — YAML data files consumed by templates: `navigation.yaml`, `testimonials.yaml`, `leaderboard.yaml`, `trysamples.yml`, `metadata.yaml`.

### Java Backend

- **`src/main/java/dev/jbang/site/`** — Quarkus beans that provide data/logic to templates:
  - `Extensions.java`, `ListExtensions.java` — Qute template extensions
  - `Leaderboard.java` — Leaderboard data handling
  - `TrySamples.java` — Try/playground sample loading
  - `TwitterClient.java` — Twitter integration
  - `PageHeader.java` — Page header utilities
- **`src/test/java/`** — Tests use `quarkus-junit5` and `quarkus-roq-testing`.

### Documentation (Antora)

- **`docs-site/`** — Antora playbook and config. Builds documentation from sibling repos (`jbang`, `jbang-vscode`, `jbang-idea`).
- Antora runs as a Maven plugin during `process-resources` phase; output goes to `public/documentation/`.

### Configuration

- **`src/main/resources/application.properties`** — Roq site config (default layout, collections, logging).
- **`pom.xml`** — Quarkus 3.x + Roq 1.x, Antora Maven plugin, Java 21.

## Conventions

- Follow Conventional Commits (`feat:`, `fix:`, `chore:`).
- Content filenames use kebab-case; posts use date prefixes.
- Prefer `.adoc` for long-form content, `.md` for simple pages.
- Generated output lands in `target/roq/` — verify before merging.
