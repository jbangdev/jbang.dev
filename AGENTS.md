# Repository Guidelines

## Project Structure & Module Organization
- `content/`: Blog posts and pages (Markdown/AsciiDoc). Posts typically follow `content/posts/YYYY-MM-DD-title.adoc|.md`.
- `public/`: Static assets served as-is (e.g., images). `CNAME` lives in `content/CNAME`.
- `src/main/resources/`: Site configuration (e.g., `application.properties`).
- `templates/`: Roq page layouts and partials used to render content.
- `docs-site/`: Antora-based docs; builds to `docs-site/build/site` locally and to `public/documentation` via Maven.
- `mise.toml`: Tool installs Developer commands for dev, build, and docs.
- `pom.xml`: Quarkus + Roq build (Java 21, Maven). Output site under `target/roq/`.

## Build, Test, and Development Commands
- `mise preview`: Run Quarkus dev mode; live-reloads content at `http://localhost:8080`.
- `mise serve`: Generate production site to `target/roq` and serve it on `http://localhost:8181`.
- `mise build`: Run `mvn package` to build and generate docs to `public/documentation`.
- `mvn test`: Run unit tests (JUnit 5, Quarkus testing).
- Docs: `mise local-docs` (local Antora playbook), `mise docs` (full docs build), `mise open-docs`.

## Coding Style & Naming Conventions
- Java: Use Java 21, 4-space indentation, descriptive names; keep classes small and cohesive.
- Content: Use kebab-case filenames; posts use date prefixes as shown above; prefer `.adoc` for long-form docs, `.md` for simple pages.
- Templates: Keep layouts in `templates/`; reuse partials; avoid inline styles.
- No enforced formatter in Maven—match existing code and run your IDE formatter consistently.

## Testing Guidelines
- Frameworks: JUnit 5 with `quarkus-junit5`.
- Location: Place tests under `src/test/java`; name classes `*Test`.
- Commands: Run `mvn test` for unit tests. No strict coverage threshold, but add/adjust tests when changing logic or templates.

## Commit & Pull Request Guidelines
- Commits: Follow Conventional Commits (e.g., `feat:`, `fix:`, `chore:`). Keep messages imperative and scoped.
- PRs: Provide a clear description, link related issues, and include before/after screenshots for visual or content changes (`content/`, `templates/`).
- Size: Prefer small, focused PRs. Update docs alongside code when relevant (`docs-site/`).

## Agent Discovery & Skills

This site publishes agent discovery metadata. The skill content itself lives in a separate repo:
- **Skills repo:** [github.com/jbangdev/agent-skills](https://github.com/jbangdev/agent-skills)
- **Discovery index:** `public/.well-known/agent-skills/index.json` (points to the skills repo)

### Keeping the index in sync

When skills are added, renamed, or updated in `jbangdev/agent-skills`, the index here must be updated to match:
1. Check skill names and URLs in `public/.well-known/agent-skills/index.json` match the repo structure.
2. Recompute digests: `curl -sL <skill-url> | shasum -a 256` and update the `digest` field.
3. Keep descriptions in the index consistent with the `description` field in each SKILL.md frontmatter.

### Other agent discovery files

| File | Purpose | Notes |
|------|---------|-------|
| `public/robots.txt` | Content Signals (`ai-train`, `search`, `ai-input`) | All set to `yes` — JBang is open source and wants AI visibility |
| `public/.well-known/api-catalog` | API catalog (RFC 9727) | Points to documentation; no machine-readable API spec exists |
| `templates/partials/head/custom.html` | HTML `<link>` elements for agent discovery | `api-catalog` and `service-doc` relations |

### Link response headers

GitHub Pages does not support custom HTTP headers. The HTML `<link>` elements in `head/custom.html` serve as the fallback. For proper `Link:` HTTP headers, configure a Cloudflare Transform Rule (Modify Response Header) on the homepage.

## Security & Configuration Tips
- Do not commit secrets or tokens. Site behavior is configured via `src/main/resources/application.properties`.
- Verify generated output in `target/roq/` before merging.
