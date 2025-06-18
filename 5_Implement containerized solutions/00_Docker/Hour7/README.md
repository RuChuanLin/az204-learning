Awesome, you're moving fast and solid! 🚀
Let’s continue with **Docker Hour 7: Best Practices – Image Optimization & Layer Caching**

---

## 🕖 **Docker Hour 7 – Docker Best Practices**

### 🎯 Goals

* Write **efficient Dockerfiles**
* Understand **layer caching**
* Reduce **build time** and **image size**

---

### ✅ Step 1: Understand Image Layers (10 min)

Each line in a Dockerfile creates a **new layer**. Docker caches each layer to **speed up rebuilds**.

Bad Dockerfile:

```Dockerfile
COPY . .
RUN npm install
```

Better:

```Dockerfile
COPY package*.json ./
RUN npm install
COPY . .
```

👉 Why?
If your code changes but `package.json` doesn't, Docker can cache `npm install` instead of redoing it.

---

### ✅ Step 2: Updated Optimized Dockerfile (15 min)

```Dockerfile
# 1. Use official Node.js image
FROM node:18

# 2. Set working directory
WORKDIR /app

# 3. Install dependencies first (caching!)
COPY package*.json ./
RUN npm install

# 4. Copy application source
COPY . .

# 5. Define runtime command
CMD ["npm", "start"]
```

---

### ✅ Step 3: Multi-Stage Build (Optional Advanced)

If you're building a **React/Next.js** frontend or Go app, use multi-stage builds to avoid shipping dev tools into production.

Example for Node + TypeScript:

```Dockerfile
FROM node:18 AS builder
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build

FROM node:18-slim
WORKDIR /app
COPY --from=builder /app/dist .
CMD ["node", "main.js"]
```

---

### ✅ Step 4: Reduce Image Size (5 min)

* Use `node:18-slim` or `alpine` instead of full `node:18`
* Clean up cache:

  ```Dockerfile
  RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
  ```

---

### ✅ Step 5: Check Image Size & Layers (10 min)

```bash
docker image ls
docker history my-node-db-app
```

You’ll see each Dockerfile line as a separate cached layer.

---

### 🧠 Recap: Best Practices

| Tip                            | Why it matters                     |
| ------------------------------ | ---------------------------------- |
| Install dependencies early     | Reuse cache on app changes         |
| Use `.dockerignore`            | Prevent copying unnecessary files  |
| Use slim/alpine base images    | Smaller, faster                    |
| Use `COPY package*.json` first | Avoid reinstalling on code changes |
| Minimize RUN commands          | Combine them to reduce layer count |

---

You're now writing **efficient, scalable Dockerfiles**!

Ready for **Hour 8: Publishing to Docker Hub or Azure Container Registry (ACR)**?
Let’s push your image to the cloud next 🌩️
