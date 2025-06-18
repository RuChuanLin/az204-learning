Excellent! Now let’s move on to **Docker Hour 6: Docker Compose – Multi-Container Management** 🧩

---

## 🕕 **Docker Hour 6 – Docker Compose**

### 🎯 Goals

* Understand why we use **Docker Compose**
* Learn the `docker-compose.yml` format
* Run a multi-container app with **1 command**

---

### ✅ Step 1: What is Docker Compose? (10 min)

When your app has **multiple services** (e.g., frontend + backend + database), managing them with raw `docker run` commands is painful.

**Docker Compose** allows you to define and run multi-container apps using a YAML config file.

---

### ✅ Step 2: Setup Directory Structure (5 min)

Create a project folder (if you haven’t already):

```
my-compose-app/
├── index.js
├── package.json
├── Dockerfile
└── docker-compose.yml  <-- this is new
```

---

### ✅ Step 3: Create `docker-compose.yml` (15 min)

```yaml
version: "3.8"

services:
  db:
    image: postgres
    container_name: my-postgres
    environment:
      POSTGRES_PASSWORD: secret
    volumes:
      - dbdata:/var/lib/postgresql/data

  app:
    build: .
    container_name: node-app
    depends_on:
      - db
    ports:
      - "3000:3000"
    environment:
      PGHOST: db
      PGUSER: postgres
      PGPASSWORD: secret
      PGDATABASE: postgres

volumes:
  dbdata:
```

---

### ✅ Step 4: Update `index.js` to Use Environment Variables

```js
const { Client } = require('pg');
const http = require('http');

const client = new Client();
client.connect();

const server = http.createServer(async (req, res) => {
  const result = await client.query('SELECT NOW()');
  res.end('DB Time: ' + result.rows[0].now);
});

server.listen(3000, () => {
  console.log('Server running on port 3000');
});
```

✅ This uses environment variables from Compose.

---

### ✅ Step 5: Launch the Stack! (10 min)

From inside the folder:

```bash
docker compose up --build
```

Visit: 👉 [http://localhost:3000](http://localhost:3000)
You should see: **DB Time: ...**

You can stop everything with:

```bash
docker compose down
```

---

### 🧠 Recap

| Task                    | Command                     |
| ----------------------- | --------------------------- |
| Start app               | `docker compose up`         |
| Stop app                | `docker compose down`       |
| Build + run             | `docker compose up --build` |
| Define volumes/networks | In `docker-compose.yml`     |

---

You're now managing **multiple services with one file** — way cleaner!

Next up:
➡️ **Hour 7: Docker Best Practices – Layering, Caching, Optimization**
Let me know when you're ready!
