Awesome! Now let’s move to **Kubernetes Hour 3: Writing YAML & Managing Deployments** 📄🚀

---

## 🕒 **Kubernetes Hour 3 – YAML Basics & Deployment Management**

### 🎯 Goals

* Learn to write Kubernetes resources using **YAML**
* Understand **declarative** vs **imperative**
* Create and manage a **Deployment + Service** using YAML
* Apply, update, and delete configurations

---

## ✅ Step 1: Why Use YAML?

So far, you’ve used `kubectl create ...` which is **imperative** — “do this right now.”

But in real-world practice, we use **YAML files** to declare the **desired state**, then apply it using:

```bash
kubectl apply -f <file>
```

This approach is:

* Version-controlled (YAML in Git)
* Repeatable and testable
* Easier to update and roll back

---

## ✅ Step 2: Create Your Deployment YAML (10 min)

Make a file called `deployment.yaml`:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: hello-node
spec:
  replicas: 2
  selector:
    matchLabels:
      app: hello-node
  template:
    metadata:
      labels:
        app: hello-node
    spec:
      containers:
        - name: hello-node
          image: nginxdemos/hello
          ports:
            - containerPort: 80
```

> This deploys 2 pods running `nginxdemos/hello`

---

## ✅ Step 3: Create a Matching Service YAML (5 min)

Add a second file called `service.yaml`:

```yaml
apiVersion: v1
kind: Service
metadata:
  name: hello-node
spec:
  type: NodePort
  selector:
    app: hello-node
  ports:
    - port: 80
      targetPort: 80
      nodePort: 30080
```

> This exposes the Pods via a stable `NodePort` on port 30080

---

## ✅ Step 4: Apply the YAML Files (5 min)

```bash
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
```

Then confirm:

```bash
kubectl get deployments
kubectl get pods
kubectl get svc
```

You should see 2 Pods running and a Service exposing them.

👉 Visit in browser:

```bash
minikube service hello-node
```

or

```bash
curl http://localhost:30080
```

---

## ✅ Step 5: Update the Deployment (10 min)

Let’s scale it to 3 replicas.

Modify `deployment.yaml`:

```yaml
spec:
  replicas: 3
```

Then re-apply:

```bash
kubectl apply -f deployment.yaml
kubectl get pods
```

You should now see 3 pods running. That’s declarative power 💥

---

## ✅ Step 6: Clean Up (Optional)

```bash
kubectl delete -f service.yaml
kubectl delete -f deployment.yaml
```

---

## 🧠 Recap

| Command             | What it does                                   |
| ------------------- | ---------------------------------------------- |
| `kubectl apply -f`  | Creates or updates resource from YAML          |
| `kubectl delete -f` | Deletes resource                               |
| `kubectl get`       | View resource status                           |
| YAML `Deployment`   | Defines desired # of pods and container config |
| YAML `Service`      | Exposes pods via a stable address/port         |

---

Let me know if you want:

* A breakdown of how YAML maps to what happens in the cluster
* Or ready to continue with **Hour 4: Services Deep Dive (ClusterIP / NodePort / LoadBalancer)**?
