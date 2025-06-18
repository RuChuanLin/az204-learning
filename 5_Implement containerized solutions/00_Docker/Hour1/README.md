Awesome! Let's begin with **Docker Hour 1: What is Docker? & First Container** 🚀

---

## 🕐 **Docker Hour 1 – What is Docker? & First Container**

### ✅ Step 1: Understand the Basics (20 min)

#### 📌 What is Docker?

Docker is a platform that allows you to **package your app and all its dependencies** into a single unit called a **container**. This ensures your app runs the same everywhere.

#### 🔧 Containers vs Virtual Machines

| Feature     | Virtual Machine       | Docker Container        |
| ----------- | --------------------- | ----------------------- |
| OS Boot     | Full OS (e.g. Ubuntu) | Shares host OS kernel   |
| Size        | GBs                   | MBs                     |
| Boot Time   | Minutes               | Seconds                 |
| Isolation   | Strong (Hypervisor)   | Process-level (cgroups) |
| Performance | Slower (heavier)      | Faster (lighter)        |

#### 📦 Key Components

* **Docker Engine**: The background service that runs containers
* **Docker CLI**: Command-line tool (`docker`) to talk to Docker Engine
* **Docker Hub**: Default image registry

---

### ✅ Step 2: Install Docker (10 min)

Choose your platform:

* **Windows/Mac**: Install Docker Desktop
  [https://www.docker.com/products/docker-desktop/](https://www.docker.com/products/docker-desktop/)

* **Linux (Ubuntu example)**:

  ```bash
  sudo apt update
  sudo apt install docker.io -y
  sudo systemctl start docker
  sudo systemctl enable docker
  sudo usermod -aG docker $USER  # log out and in again
  ```

Check if it's installed:

```bash
docker --version
docker info
```

---

### ✅ Step 3: Run Your First Containers (30 min)

#### 🔹 Test Image

```bash
docker run hello-world
```

This confirms your Docker installation works by running a small container that prints a message.

#### 🔹 Run Nginx Web Server

```bash
docker run -d -p 8080:80 nginx
```

* `-d`: run in background (detached)
* `-p 8080:80`: map container port 80 to your local port 8080
* `nginx`: image from Docker Hub

Open your browser:
👉 [http://localhost:8080](http://localhost:8080)
You should see the **Welcome to nginx!** page.

#### 🔹 List Running Containers

```bash
docker ps
```

#### 🔹 Stop and Remove Container

```bash
docker stop <container_id>
docker rm <container_id>
```

---

### 🧠 Quick Recap

| Command                          | Purpose                 |
| -------------------------------- | ----------------------- |
| `docker run hello-world`         | Run test container      |
| `docker run -d -p 8080:80 nginx` | Start web server        |
| `docker ps`                      | Show running containers |
| `docker stop` / `docker rm`      | Stop/remove containers  |

---

Let me know once you're done! Then we’ll move to **Hour 2: Docker CLI Essentials** where you’ll get hands-on with more commands and `alpine` image 🧪
