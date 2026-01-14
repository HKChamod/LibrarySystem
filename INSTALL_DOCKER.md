# How to Install Docker Desktop on Windows

## Step 1: Download

1.  Go to the official Docker website: [https://www.docker.com/products/docker-desktop/](https://www.docker.com/products/docker-desktop/)
2.  Click **"Download for Windows"**.

## Step 2: Install

1.  Double-click the downloaded `Docker Desktop Installer.exe`.
2.  When prompted, ensure **"Use WSL 2 instead of Hyper-V"** is checked (recommended for better performance).
3.  Follow the installation wizard instructions.
4.  **Important**: If asked to log out or restart your computer, please do so.

## Step 3: Verify Installation

1.  After restarting, search for "Docker Desktop" in the Windows running apps or start menu and open it.
2.  Wait for the whale icon in the taskbar to stop animating (it should say "Docker Desktop is running").
3.  Open a terminal (PowerShell or Command Prompt) and run:
    ```powershell
    docker --version
    docker compose version
    ```
    You should see version numbers for both.

## Step 4: Run the Project

Once Docker is installed and running:

1.  Open your project folder in the terminal:
    ```powershell
    cd "e:\My Projects\Others\Java\LibrarySystem"
    ```
2.  Run the application:
    ```powershell
    docker-compose up --build
    ```
