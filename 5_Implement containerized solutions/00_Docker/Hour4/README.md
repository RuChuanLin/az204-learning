Great momentum! Let’s dive into **Docker Hour 4: Volumes – Persistent Storage** 💾

---

## 🕓 **Docker Hour 4 – Docker Volumes & Data Persistence**

### 🎯 Goals

* Understand ephemeral nature of containers
* Learn how to persist data with **volumes**
* Use both **named volumes** and **bind mounts**

---

### ✅ Step 1: Why Volumes? (10 min)

By default, data written **inside a container** is lost when the container is removed. Volumes solve this by storing data on the **host machine**.

**Types of Volumes:**

| Type         | Description                                  |
| ------------ | -------------------------------------------- |
| Named Volume | Managed by Docker; stored in Docker folder   |
| Bind Mount   | Maps a specific host folder to the container |

---

### ✅ Step 2: Use Named Volume with a Database (20 min)

Let’s use **PostgreSQL** as an example.

```bash
docker volume create pgdata

docker run -d \
  --name my-postgres \
  -e POSTGRES_PASSWORD=mysecret \
  -v pgdata:/var/lib/postgresql/data \
  -p 5432:5432 \
  postgres
```

Now try:

```bash
docker volume inspect pgdata
```

Even if you stop/remove the container, the data in `pgdata` will remain.

To test:

```bash
docker stop my-postgres
docker rm my-postgres

docker run -d \
  --name my-postgres \
  -e POSTGRES_PASSWORD=mysecret \
  -v pgdata:/var/lib/postgresql/data \
  -p 5432:5432 \
  postgres
```

Your data is still there!

---

### ✅ Step 3: Use Bind Mount (15 min)

Let’s bind a folder from your host to a container.

1. Create a local folder:

```bash
mkdir $(pwd)/html
echo "<h1>Hello from bind mount</h1>" > html/index.html
```

2. Run nginx and mount that folder:

```bash
docker run -d \
  --name my-nginx-bind \
  -p 8082:80 \
  -v $(pwd)/html:/usr/share/nginx/html \
  nginx
```

👉 Visit: [http://localhost:8082](http://localhost:8082)

Change `index.html` and refresh the browser. Instant update!

---

### ✅ Step 4: Cleanup (Optional)

```bash
docker stop my-nginx-bind my-postgres
docker rm my-nginx-bind my-postgres
docker volume rm pgdata
```

---

### 🧠 Recap

| Concept         | Example                         |
| --------------- | ------------------------------- |
| Create volume   | `docker volume create myvol`    |
| Use volume      | `-v myvol:/container/path`      |
| Use bind mount  | `-v /host/path:/container/path` |
| Inspect volumes | `docker volume inspect myvol`   |

---

You're now storing data like a pro!

Ready to move on to **Hour 5: Docker Networking – Container Communication**? Let me know!
