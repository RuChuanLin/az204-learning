Fantastic — let’s jump into **Kubernetes Hour 6: Health Checks & Resource Limits** 💊📉

---

## 🕕 **Kubernetes Hour 6 – Probes & Resource Management**

### 🎯 Goals

* Learn how Kubernetes **detects app health**
* Add **liveness** and **readiness** probes to your deployment
* Use **CPU/memory limits** to manage resource usage

---

## ✅ Step 1: Why Health Checks Matter

Kubernetes needs to know:

| Probe Type    | What it means                            | What K8s does if it fails    |
| ------------- | ---------------------------------------- | ---------------------------- |
| **Readiness** | "Is the app *ready* to receive traffic?" | Stops routing traffic to Pod |
| **Liveness**  | "Is the app *alive* or stuck/crashed?"   | Restarts the Pod             |

These help K8s **self-heal** and scale traffic **safely**.

---

## ✅ Step 2: Add Probes to Your Deployment (10 min)

Update your `deployment.yaml` like this:

```yaml
spec:
  containers:
    - name: hello-node
      image: nginxdemos/hello
      ports:
        - containerPort: 80
      livenessProbe:
        httpGet:
          path: /
          port: 80
        initialDelaySeconds: 5
        periodSeconds: 10
      readinessProbe:
        httpGet:
          path: /
          port: 80
        initialDelaySeconds: 2
        periodSeconds: 5
```

Apply it:

```bash
kubectl apply -f deployment.yaml
kubectl describe pod <pod-name>
```

You’ll see the probe results under the **Conditions** and **Events** sections.

---

## ✅ Step 3: Simulate Failure (Optional)

You can simulate a failure by changing the path to a 404:

```yaml
livenessProbe:
  httpGet:
    path: /invalid
    port: 80
```

K8s will **restart the Pod** when it fails.

---

## ✅ Step 4: Add Resource Limits (10 min)

In real deployments, you don’t want one Pod eating all your CPU or RAM.

Add this to your container spec:

```yaml
resources:
  requests:
    cpu: "100m"      # guaranteed minimum
    memory: "64Mi"
  limits:
    cpu: "500m"      # maximum allowed
    memory: "128Mi"
```

This tells the scheduler:

* **Requests** = minimum needed to run
* **Limits** = hard cap

You can check Pod usage:

```bash
kubectl top pods
```

If `kubectl top` isn’t installed:

```bash
minikube addons enable metrics-server
```

---

## 🧠 Recap

| Feature          | Purpose                                  |
| ---------------- | ---------------------------------------- |
| `readinessProbe` | Prevents traffic to unready Pods         |
| `livenessProbe`  | Automatically restarts unhealthy Pods    |
| `resources`      | Schedules efficiently & prevents overuse |

---

You're now deploying apps that are **resilient** and **resource-aware**! 💪

Next up in **Hour 7**:
➡️ **Volumes & Persistent Storage** – so your data doesn’t disappear when Pods restart. Ready to go?
