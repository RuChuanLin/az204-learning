I am learning Azure cosmos DB, please help me clarify the difference of each consistency level


Great! Azure Cosmos DB offers **five well-defined consistency levels** to balance between **performance, availability, and consistency**. Let’s walk through them in order of **strongest to weakest consistency**:

---

### 🌟 1. **Strong Consistency**

* **Behavior**: Reads always return the **most recent committed write**.
* **Analogy**: Like a bank—when you deposit money, any branch will instantly reflect that update.
* **Trade-off**: High latency & limited to a **single region**.
* **Use case**: Critical financial data, real-time systems where correctness is key.

---

### ⚖️ 2. **Bounded Staleness**

* **Behavior**: Reads lag behind writes by a known **time interval** (e.g., 5 seconds) or a known number of **versions** (e.g., last 10 writes).
* **Analogy**: Like a news website showing headlines delayed by a few seconds.
* **Trade-off**: Configurable balance; can work across multiple regions.
* **Use case**: Collaborative apps where minor delays are tolerable.

---

### 📈 3. **Session Consistency** (Default)

* **Behavior**: Guarantees **monotonic reads and writes**, **read-your-writes**, and **writes-follow-reads** within the same session (i.e., user).
* **Analogy**: Like a user on an e-commerce site always seeing their own cart updates, even if others might see outdated data.
* **Trade-off**: Great for user-centric apps; weaker across users.
* **Use case**: Shopping carts, user profiles, mobile apps.

---

### 🧭 4. **Consistent Prefix**

* **Behavior**: Reads never see out-of-order writes, but may miss some **recent writes**.
* **Analogy**: Like watching a recorded video—you may be behind live, but you never skip ahead or see scenes out of order.
* **Trade-off**: Good latency and availability, but less up-to-date.
* **Use case**: Event logging, feed-based systems where order matters but timeliness is relaxed.

---

### 🌀 5. **Eventual Consistency**

* **Behavior**: No ordering guarantees; **updates will eventually propagate**.
* **Analogy**: Like a tweet that shows up on your feed minutes after being posted.
* **Trade-off**: Lowest latency, highest availability.
* **Use case**: Non-critical metadata, caching layers, analytics feeds.

---

### 🔁 Quick Comparison Table:

| Consistency Level | Most Recent     | Order Guaranteed | Global Support       | Latency |
| ----------------- | --------------- | ---------------- | -------------------- | ------- |
| Strong            | ✅ Yes           | ✅ Yes            | ❌ Single region only | Highest |
| Bounded Staleness | ⏱️ Some delay   | ✅ Yes            | ✅ Yes                | High    |
| Session (default) | ✅ (per session) | ✅ Yes            | ✅ Yes                | Medium  |
| Consistent Prefix | ❌ No            | ✅ Yes            | ✅ Yes                | Low     |
| Eventual          | ❌ No            | ❌ No             | ✅ Yes                | Lowest  |

---

Let me know if you'd like help selecting the best level for a specific application or workload!
