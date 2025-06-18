You're on fire! 🔥 Let’s continue with **Docker Hour 5: Networking – Container Communication**

---

## 🕔 **Docker Hour 5 – Docker Networking**

### 🎯 Goals

* Understand Docker network types
* Connect multiple containers
* Enable service discovery using container names

---

### ✅ Step 1: Networking Basics (10 min)

When you run containers, Docker automatically connects them to a **default bridge network**, unless specified otherwise.

**Docker Network Types:**

| Type              | Description                                          |
| ----------------- | ---------------------------------------------------- |
| **bridge**        | Default for standalone containers on a single host   |
| **host**          | Shares host’s network stack (Linux only)             |
| **none**          | Isolated container                                   |
| **overlay**       | For multi-host networking (used with Swarm)          |
| **custom bridge** | Enables DNS-based name resolution between containers |

---

### ✅ Step 2: Create a Custom Network (5 min)

Custom networks allow **container name-based DNS**:

```bash
docker network create mynet
```

---

### ✅ Step 3: Connect Web App to DB (35 min)

We’ll run a simple backend + database setup.

---

#### 🐘 Step 3a: Run a PostgreSQL Container

```bash
docker run -d \
  --name my-postgres \
  --network mynet \
  -e POSTGRES_PASSWORD=secret \
  postgres
```

---

#### 🟦 Step 3b: Build Your Node App with DB Access

**1. Update `index.js`:**

```js
const { Client } = require('pg');
const http = require('http');

const client = new Client({
  host: 'my-postgres',
  user: 'postgres',
  password: 'secret',
  database: 'postgres',
});

client.connect();

const server = http.createServer(async (req, res) => {
  const result = await client.query('SELECT NOW()');
  res.end('DB Time: ' + result.rows[0].now);
});

server.listen(3000, () => {
  console.log('Server running on port 3000');
});
```


**✅ Updated `package.json` for Node.js + PostgreSQL**

```json
{
  "name": "docker-app",
  "version": "1.0.0",
  "main": "index.js",
  "scripts": {
    "start": "node index.js"
  },
  "dependencies": {
    "pg": "^8.11.1"
  }
}
```


**2. Rebuild your image:**

```bash
docker build -t my-node-db-app .
```

**3. Run the app on the same network:**

```bash
docker run -d \
  --name node-app \
  --network mynet \
  -p 3000:3000 \
  my-node-db-app
```

👉 Visit: [http://localhost:3000](http://localhost:3000)
You should see something like: **DB Time: 2025-05-16 09:00:00**

---

### ✅ Step 4: Inspect the Network

```bash
docker network inspect mynet
```

You’ll see both containers are attached and can communicate using **container names**.

---

### 🧠 Recap

| Task                     | Command                        |
| ------------------------ | ------------------------------ |
| Create custom network    | `docker network create mynet`  |
| Connect container to it  | `--network mynet`              |
| Use DNS-style discovery  | Container name as hostname     |
| View network connections | `docker network inspect mynet` |

---

Next up in **Hour 6**:
➡️ **Docker Compose: Managing Multi-Container Apps**
Let me know when you're ready!
