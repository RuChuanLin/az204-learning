Great! Let’s continue with **Docker Hour 2: CLI Essentials & Container Lifecycle** 🚀

---

## 🕑 **Docker Hour 2 – Docker CLI Essentials**

### 🎯 Goals

* Practice using Docker CLI
* Learn to inspect, stop, remove, and interact with containers
* Understand container lifecycle

---

### ✅ Step 1: Docker CLI Basics (15 min)

Here are the most common Docker commands:

| Command                        | Description                            |
| ------------------------------ | -------------------------------------- |
| `docker ps [-a]`               | List running (`-a` for all) containers |
| `docker stop <id>`             | Stop a running container               |
| `docker rm <id>`               | Remove a container                     |
| `docker images`                | List local images                      |
| `docker rmi <image>`           | Remove an image                        |
| `docker exec -it <id> sh/bash` | Open shell inside container            |

---

### ✅ Step 2: Run a Lightweight Container (30 min)

We’ll use the tiny Linux distribution `alpine`.

```bash
docker pull alpine
docker run -it --name test-alpine alpine sh
```

* `-it`: interactive terminal
* `--name`: name the container
* `alpine`: image name
* `sh`: command to run

Now you are *inside* the container’s shell!

Try:

```sh
echo "Hello from inside container"
exit
```

---

### ✅ Step 3: Practice Container Lifecycle

```bash
docker ps -a             # View all containers
docker start test-alpine # Restart a stopped container
docker exec -it test-alpine sh  # Go back inside
docker stop test-alpine
docker rm test-alpine
```

---

### ✅ Step 4: Run a Named Web Server (Optional)

```bash
docker run -d --name my-nginx -p 8081:80 nginx
docker logs my-nginx
docker exec -it my-nginx sh
```

You’ll find the root directory at `/usr/share/nginx/html`.

---

### ✅ Step 5: Image Cleanup

```bash
docker images              # List images
docker rmi alpine nginx    # Remove images (if containers are deleted)
```

---

### 🧠 Recap

| Task                   | Command Example                      |
| ---------------------- | ------------------------------------ |
| Enter container shell  | `docker exec -it <id> sh`            |
| List containers        | `docker ps -a`                       |
| Start/stop container   | `docker start` / `docker stop`       |
| Remove container/image | `docker rm` / `docker rmi`           |
| Run and name container | `docker run --name my-container ...` |

---

Let me know when you’re ready for **Hour 3: Building Your Own Docker Image with Dockerfile** – you'll write your own image next! 🛠️
