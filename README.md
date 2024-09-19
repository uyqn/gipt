# GIPT
![Build Status](https://github.com/uyqn/gipt/actions/workflows/ci.yml/badge.svg)
![Coverage Status](https://coveralls.io/repos/github/uyqn/gipt/badge.svg?branch=main)
![GitHub Release](https://img.shields.io/github/v/release/uyqn/gipt)

## Description
GIPT is a project that aims utilize OpenAI's GPT to generate commit messages that follows the specification of [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/).

## Installation
Clone the repository using either of the following commands:
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
   
## Usage
In order to use the project, follow the following steps for your respective operating system:
### macOS
1. Change directory to the project folder:
    ```bash
    cd gipt
    ```
2. Create a bin directory in your home directory:
    ```bash
    mkdir ~/bin
    ```
3. Add the bin directory to your PATH:
    ```bash
    echo 'export PATH=$HOME/bin:/usr/local/bin:$PATH' >> ~/.zshrc
    ```
4. Create a script (e.g gipt) that will be used to run the project in the bin directory:
    ```bash
   cat << EOF > ~/bin/gipt
    #!/bin/bash

    $(pwd)/gradlew  -p $(pwd)/ run --args="\$(pwd) \$*"
    EOF
    ```
5. Make the script executable:
    ```bash
    chmod +x ~/bin/gipt
    ```
6. Reload your shell:
    ```bash
    source ~/.zshrc
    ```
7. Run the project:
    ```bash
    gipt
    ```
   
### Windows
use Git Bash or WSL and run the same commands as macOS. Except, when reloading the shell, use the following command:
```bash
source ~/.bashrc
```
   
   