# GIPT
![Build Status](https://github.com/uyqn/gipt/actions/workflows/ci.yml/badge.svg)
![Codecov Coverage](https://codecov.io/gh/uyqn/gipt/branch/main/graph/badge.svg)
![GitHub Release](https://img.shields.io/github/v/release/uyqn/gipt)
![License](https://img.shields.io/github/license/uyqn/gipt)
![Contributors](https://img.shields.io/github/contributors/uyqn/gipt)
![Last Commit](https://img.shields.io/github/last-commit/uyqn/gipt)
![GitHub issues](https://img.shields.io/github/issues/uyqn/gipt)
![GitHub pull requests](https://img.shields.io/github/issues-pr/uyqn/gipt)

## Tech Stack
![Kotlin](https://img.shields.io/badge/Kotlin-%230095D5.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-%2302303A.svg?style=for-the-badge&logo=gradle&logoColor=white)
![OpenAI](https://img.shields.io/badge/OpenAI-%23007ACC.svg?style=for-the-badge&logo=openai&logoColor=white)
![GitHub](https://img.shields.io/badge/GitHub-%23121011.svg?style=for-the-badge&logo=github&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/GitHub_Actions-%232671E5.svg?style=for-the-badge&logo=github-actions&logoColor=white)

## Description
GIPT is a project that aims utilize OpenAI's GPT to generate commit messages that follows the specification of [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/).

## Installation
Clone the repository using either of the following commands in your terminal (e.g. Git Bash, WSL, or Terminal):
1. Using HTTPS:
    ```bash
    git clone https://github.com/uyqn/gipt.git
    ```
2. Using SSH:
    ```bash
    git clone git@github.com:uyqn/gipt.git 
    ```
3. Using GitHub CLI:
    ```bash
    gh repo clone uyqn/gipt
    ```
4. Change directory to the project folder:
    ```bash
    cd gipt
    ```
5. Create a bin directory in your home directory:
    ```bash
    mkdir ~/bin
    ```
6. Add the bin directory to your PATH:
   - For macOS:
       ```bash
       echo 'export PATH=$HOME/bin:/usr/local/bin:$PATH' >> ~/.zshrc
       ```
   - For Linux / WSL / Git Bash: 
        ```bash
        echo 'export PATH=$HOME/bin:/usr/local/bin:$PATH' >> ~/.bashrc
        ```
7. Create a script (e.g. gipt) that will be used to run the project in the bin directory:
    ```bash
   cat << EOF > ~/bin/gipt
    #!/bin/bash

    # Check if --debug is in the arguments
    if [[ "$*" == *"--debug"* ]]; then
      # Run in debug mode
      $(pwd)/gradlew  -p $(pwd)/ run --console=plain --debug-jvm --args="\$(pwd) \$*"
    else
      # Run normally
      $(pwd)/gradlew  -p $(pwd)/ run --console=plain --args="\$(pwd) \$*"
    fi
    EOF
    ```
8. Make the script executable:
    ```bash
    chmod +x ~/bin/gipt
    ```
9. Reload your shell:
   - For macOS:
       ```bash
       source ~/.zshrc
       ```
   - For Linux / WSL / Git Bash:
        ```bash
        source ~/.bashrc
        ```
## Usage
If you have been following the instructions provided in the [installation](#installation) section, you can now run the project using the following command:
```bash
gipt commit
```