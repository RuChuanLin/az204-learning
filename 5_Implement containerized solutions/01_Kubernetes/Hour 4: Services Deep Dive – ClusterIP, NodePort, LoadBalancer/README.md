Got it — we’ll revisit "object vs resource" later. Now let’s move on to **Kubernetes Hour 4: Services Deep Dive – ClusterIP, NodePort, LoadBalancer** 🌐🧠

---

## 🕓 **Kubernetes Hour 4 – Understanding Service Types**

### 🎯 Goals

* Understand **why Services are needed**
* Learn the 3 main Service types:

  * `ClusterIP`
  * `NodePort`
  * `LoadBalancer`
* Know when to use each
* Try them in Minikube

---

## ✅ Step 1: Why Do We Need Services?

Pods are **ephemeral**:

* Restarted during updates or crashes
* Get new IP addresses every time

A **Service** solves this by:

* Giving a **stable virtual IP (VIP)** inside the cluster
* Load balancing traffic to multiple pods
* Acting as a **DNS name** (e.g., `my-service.default.svc.cluster.local`)

---

## ✅ Step 2: Types of Kubernetes Services

### 🔹 1. **ClusterIP** (default)

* Exposes the service *internally* to other Pods in the cluster
* Not reachable from outside the cluster

```yaml
spec:
  type: ClusterIP
```

✅ Use when your service is **only used inside the cluster**, like a backend DB or internal API.

---

### 🔹 2. **NodePort**

* Exposes the service on a port on **each Node’s IP**
* Port is in the range **30000–32767**
* Allows access from **outside the cluster**

```yaml
spec:
  type: NodePort
  ports:
    - port: 80
      nodePort: 30080
```

✅ Use for **development**, **Minikube**, or quick manual access.

---

### 🔹 3. **LoadBalancer**

* Exposes the service using a **cloud provider’s load balancer** (e.g., Azure, AWS, GCP)
* Automatically gets a **public IP**
* Not available in Minikube by default

```yaml
spec:
  type: LoadBalancer
```

✅ Use for **production-facing services** in cloud environments.

---

## ✅ Step 3: Hands-On – Try ClusterIP and NodePort (15 min)

Let’s change your `service.yaml` for **ClusterIP**:

```yaml
apiVersion: v1
kind: Service
metadata:
  name: hello-node-clusterip
spec:
  type: ClusterIP
  selector:
    app: hello-node-app
  ports:
    - port: 80
      targetPort: 80
```

Apply it:

```bash
kubectl apply -f service.yaml
kubectl get svc
```

Try accessing it from **inside the cluster**:

```bash
kubectl run test-pod --rm -it --image=busybox -- /bin/sh
wget -qO- http://hello-node-clusterip
```

➡️ It works from **inside** the cluster, not from your browser.

---

Now try NodePort again:

```yaml
apiVersion: v1
kind: Service
metadata:
  name: hello-node-nodeport
spec:
  type: NodePort
  selector:
    app: hello-node-app
  ports:
    - port: 80
      targetPort: 80
      nodePort: 30080
```

Apply:

```bash
kubectl apply -f service.yaml
minikube service hello-node-nodeport
```

Now you can access it in the browser again.

---

## 🧠 Recap

| Service Type | Scope                     | Accessible From | Use Case                          |
| ------------ | ------------------------- | --------------- | --------------------------------- |
| ClusterIP    | Inside cluster only       | Other Pods      | Internal APIs, DB, microservices  |
| NodePort     | Exposes port on each node | Your local PC   | Dev/test, Minikube access         |
| LoadBalancer | Public IP via cloud LB    | Internet        | Production/public-facing services |

---

Ready for **Hour 5: ConfigMaps & Secrets**?
Or want to try more networking tools like `port-forward` or Ingress first?
