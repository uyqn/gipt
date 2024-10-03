## [1.0.1](https://github.com/uyqn/gipt/compare/v1.0.0...v1.0.1) (2024-10-03)


### Bug Fixes

* **workflow:** restrict issue link check to pull requests targeting main branch ([98645e3](https://github.com/uyqn/gipt/commit/98645e3bcd6b5dbb4165cba6f06a5c1051419ec1))

# 1.0.0 (2024-10-03)


### Bug Fixes

* **ci:** correct regex pattern for issue linking in GitHub Action ([994d5d8](https://github.com/uyqn/gipt/commit/994d5d8d67d1b89bde23f09e67f2463cb9112e96))
* **ci:** ensure Node.js dependencies are installed for custom issue-link checker ([d13f4f4](https://github.com/uyqn/gipt/commit/d13f4f4f9dc608cff38bfd66aa957ee42e6e1eea))
* **ci:** run issue-link check on commit pushes to PR branches ([4e377e9](https://github.com/uyqn/gipt/commit/4e377e96894a094ef830fe09a643fbf2f1908794))
* **ci:** specify Java distribution in GitHub Actions workflow ([92fc83a](https://github.com/uyqn/gipt/commit/92fc83a3b870d222406b6b22193a3c561558a0f6))
* **ci:** switch to pre-built action for checking issue links in PRs ([21c0279](https://github.com/uyqn/gipt/commit/21c0279dd1212cb1eb345d1f0a59ce276322b23f))
* **ci:** update Node.js version and install dependencies for issue-link checker ([47d4247](https://github.com/uyqn/gipt/commit/47d42476dac351564897ea51a6d5db6e6b37dd0c))
* **ci:** use LottieFiles action to check for issue links in PRs ([2d9fe6d](https://github.com/uyqn/gipt/commit/2d9fe6d9573db3b56a43f30546e8250ac81703d0))
* **github:** update PR template and workflow for issue linking check ([d225f8e](https://github.com/uyqn/gipt/commit/d225f8e76ed9d649640e8bb26b153386adfc6a22)), closes [#1](https://github.com/uyqn/gipt/issues/1)
* **logging:** enhance log message for OpenAI responses and update logging levels ([0bac1e4](https://github.com/uyqn/gipt/commit/0bac1e438bb88439e0843dcfb45cb5bc7a90405c))


### Features

* add Azure OpenAI integration and update dependencies ([80ca22c](https://github.com/uyqn/gipt/commit/80ca22ca51aa87c60fcb54be7294765d43d89808))
* add Mockito Kotlin and MockK dependencies, refactor MockOpenAiClientImpl, and ignore more files in coverage ([02ed3e0](https://github.com/uyqn/gipt/commit/02ed3e07acf6baa3d31e374b6e5ed1c0e817bf33))
* add SonarLint project settings configuration ([a503f70](https://github.com/uyqn/gipt/commit/a503f7018c1c8ff609c21f1366e1e55098a82767))
* add UI design configuration and command structure for commit generation ([61ee203](https://github.com/uyqn/gipt/commit/61ee203ab7c8f9e6a2713db936ef76f545cf2dfe))
* **ci:** add action.yml for custom issue-link checker and Node.js library mappings ([fab88fa](https://github.com/uyqn/gipt/commit/fab88fa23a58e1a902a81a31342be45f7630e6b7))
* **ci:** add custom issue-link-checker action for GitHub PRs ([6a7c95c](https://github.com/uyqn/gipt/commit/6a7c95cc60944bb07e0b710bfc7b208d31091da0))
* **ci:** add GitHub Actions workflow for Kotlin linting and testing ([02c9bcf](https://github.com/uyqn/gipt/commit/02c9bcf64bb5bbda6126979cfc0273cf32bc0c04))
* **client:** Implement OpenAI Client Structure with Azure Support ([2b1bd7c](https://github.com/uyqn/gipt/commit/2b1bd7cc2cf871367ca13839770fab7d851a9f0f))
* **config:** add configuration for environment variables using dotenv ([24f5c79](https://github.com/uyqn/gipt/commit/24f5c797e4091e833ec9a7458fd11ba7ea924651))
* enhance GiptCommit logging and commit message extraction ([5b406c6](https://github.com/uyqn/gipt/commit/5b406c66b43301493ff6aabed3a5c02f46ecfcb6))
* **gipt:** incorporate logging for empty staging area in GiptCommit command ([08dec9d](https://github.com/uyqn/gipt/commit/08dec9deedafe3f644507a0aa868cc04ce41bded))
* **git:** add GitFacade class to handle staged diff using JGit ([9a87bbe](https://github.com/uyqn/gipt/commit/9a87bbe2f2384b31cf635e335dd878c208c53923))
* **git:** enhance GitFacade with staged diff fallback and add unit tests ([be15cfa](https://github.com/uyqn/gipt/commit/be15cfae97873aa8e3ec85081932e5e10e40eb8d))
* **github:** add pull request template and workflow to enforce issue linking ([f42b968](https://github.com/uyqn/gipt/commit/f42b9685f861f5ac1e873a3155aad76c20a18aa6))
* **http:** add HttpService to handle HTTP requests ([9330b64](https://github.com/uyqn/gipt/commit/9330b64b7524621bb26cf6af91c3c656ba8e5737))
* **logging:** enhance logging for Azure OpenAI requests and responses ([f72f40a](https://github.com/uyqn/gipt/commit/f72f40a6667f0e405794551e96f3c2c00ed59981))
* **models:** restructure OpenAI client models and add codecov configuration ([3b17bbf](https://github.com/uyqn/gipt/commit/3b17bbfe43c3f46e8887a762295981421d00ad03))
* refactor OpenAI client structure and enhance commit message extraction ([c984ebc](https://github.com/uyqn/gipt/commit/c984ebc459f342be08ee6d130cfb61f8f54ea40c))
* **release:** trigger workflow on push to main branch ([f0765ca](https://github.com/uyqn/gipt/commit/f0765ca277b30139180792925a904db16aaaad46))