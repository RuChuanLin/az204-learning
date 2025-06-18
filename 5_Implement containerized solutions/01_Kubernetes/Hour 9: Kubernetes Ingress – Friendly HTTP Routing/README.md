Perfect — let’s continue with **Option 1: Kubernetes Networking – Ingress Controller** 🌐📥

This is a major step toward production-level architecture — no more random NodePorts like `http://localhost:31234`. Instead, you’ll access apps like:

```
http://hello.local
http://my-app.local/api
```

---

## 🧭 Hour 9: Kubernetes Ingress – Friendly HTTP Routing

### 🎯 Goals

* Understand what Ingress is and how it differs from Services
* Deploy an **NGINX Ingress Controller** in Minikube
* Define **Ingress rules** to route URLs to different Services
* Use `hello.local` or custom DNS in your browser

---

## ✅ Step 1: What is Ingress?

An **Ingress** is a K8s resource that:

* Accepts **external HTTP/HTTPS traffic**
* Routes it to the correct Service based on **host/path rules**

💡 Think of it as a **reverse proxy** inside your cluster.

| You Have:          | Ingress Helps With         |
| ------------------ | -------------------------- |
| `node-app-service` | `/api` route               |
| `frontend-service` | `/` or `hello.local` route |
| Multiple services  | 1 entry point for all      |

---

## ✅ Step 2: Enable Ingress Addon in Minikube

```bash
minikube addons enable ingress
```

Wait 30–60 seconds and verify:

```bash
kubectl get pods -n ingress-nginx
kubectl get svc -n ingress-nginx
```

Look for a Pod like `ingress-nginx-controller` to be `Running`.

---

## ✅ Step 3: Add Host Entry for Your Domain

Add this to your `/etc/hosts` (Linux/macOS) or `C:\Windows\System32\drivers\etc\hosts` (Windows):

```
127.0.0.1 hello.local
```

Or use:

```bash
echo "$(minikube ip) hello.local" | sudo tee -a /etc/hosts
```

This makes your fake domain route to the Ingress controller.

---

## ✅ Step 4: Define Ingress Resource

Assume you already have a service like `node-app-service`.

Create `ingress.yaml`:

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: hello-ingress
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
spec:
  rules:
    - host: hello.local
      http:
        paths:
          - path: /
            pathType: Prefix
            backend:
              service:
                name: node-app-service
                port:
                  number: 3000
```

Apply it:

```bash
kubectl apply -f ingress.yaml
```

Then visit:
👉 `http://hello.local` in your browser
✅ You should see your Node.js app

---

## ✅ Step 5: (Optional) Route Multiple Apps

If you have two services:

```yaml
rules:
  - host: hello.local
    http:
      paths:
        - path: /
          backend:
            service:
              name: frontend
              port:
                number: 80
        - path: /api
          backend:
            service:
              name: node-api
              port:
                number: 3000
```

---

## 🧠 Recap

| Component     | Purpose                                      |
| ------------- | -------------------------------------------- |
| Ingress       | HTTP/HTTPS gateway for your cluster          |
| Host rule     | Route by domain name (e.g. `hello.local`)    |
| Path rule     | Route by URL path (e.g. `/api`)              |
| Ingress Addon | Provides the NGINX reverse proxy in Minikube |

---

Would you like to:

* Try **TLS support** (with self-signed certs)?
* Or move to **Option 2: CI/CD for Kubernetes** next?
