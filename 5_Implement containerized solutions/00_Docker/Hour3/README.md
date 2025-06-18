Awesome! Let’s move on to **Docker Hour 3: Build Your Own Image with Dockerfile** 🛠️

---

## 🕒 **Docker Hour 3 – Dockerfile & Custom Image**

### 🎯 Goals

* Learn what a Dockerfile is
* Write your own Dockerfile
* Build and run your custom image

---

### ✅ Step 1: What is a Dockerfile? (10 min)

A **Dockerfile** is a script of instructions used to build a Docker image. It defines:

* **Base image** (`FROM`)
* **Commands to install software** (`RUN`)
* **Default command to run** (`CMD`)
* **Files to copy** (`COPY`, `ADD`)
* **Working directory** (`WORKDIR`)

Example:

```Dockerfile
FROM node:18
WORKDIR /app
COPY . .
RUN npm install
CMD ["node", "index.js"]
```

---

### ✅ Step 2: Create a Simple Web App (10 min)

Create a new folder `myapp/` with these two files:

**📄 index.js**

```js
const http = require('http');

const server = http.createServer((req, res) => {
  res.end('Hello from inside a Docker container!');
});

server.listen(3000, () => {
  console.log('Server running on port 3000');
});
```

**📄 package.json**

```json
{
  "name": "docker-app",
  "version": "1.0.0",
  "main": "index.js",
  "scripts": {
    "start": "node index.js"
  }
}
```

---

### ✅ Step 3: Create the Dockerfile (5 min)

In the same `myapp/` folder:

**📄 Dockerfile**

```Dockerfile
FROM node:18
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
CMD ["npm", "start"]
```

---

### ✅ Step 4: Build and Run (15 min)

From inside the `myapp/` directory:

```bash
docker build -t my-node-app .
docker images
docker run -d -p 3000:3000 my-node-app
```

Open: 👉 [http://localhost:3000](http://localhost:3000)

You should see: **Hello from inside a Docker container!**

---

### ✅ Step 5: Cleanup (Optional)

```bash
docker ps         # find container ID
docker stop <id>
docker rm <id>
docker rmi my-node-app
```

---

### 🧠 Recap

| Command                  | Purpose                        |
| ------------------------ | ------------------------------ |
| `FROM`                   | Start from a base image        |
| `COPY`                   | Copy files into image          |
| `RUN`                    | Execute command during build   |
| `CMD`                    | Default command to run         |
| `docker build -t name .` | Build image from Dockerfile    |
| `docker run -p`          | Run container and expose ports |

---

Ready for **Hour 4: Docker Volumes – Persistent Storage**?
You'll learn how to **store data** even after a container is removed. Let me know!
