Awesome — let’s dive into **Kubernetes Hour 5: ConfigMaps & Secrets** 🔐🧪

---

## 🕔 **Kubernetes Hour 5 – ConfigMaps & Secrets**

### 🎯 Goals

* Understand how Kubernetes handles **configuration data**
* Learn the difference between **ConfigMaps** and **Secrets**
* Mount configs as **environment variables** or **files**
* Use both in a Deployment

---

## ✅ Step 1: Why Use ConfigMaps and Secrets?

You never want to:
❌ Hard-code database URLs, API keys, credentials, or environment settings in your container image or YAML.

Instead, use:

| Type          | Stores                        | Use For                          |
| ------------- | ----------------------------- | -------------------------------- |
| **ConfigMap** | Plain-text config data        | App settings, feature flags, etc |
| **Secret**    | Base64-encoded sensitive data | Passwords, tokens, certs         |

---

## ✅ Step 2: Create a ConfigMap (10 min)

### Method 1: Imperative

```bash
kubectl create configmap app-config \
  --from-literal=APP_MODE=debug \
  --from-literal=APP_COLOR=blue
```

### Method 2: Declarative `configmap.yaml`

```yaml
apiVersion: v1
kind: ConfigMap
metadata:
  name: app-config
data:
  APP_MODE: "debug"
  APP_COLOR: "blue"
```

Apply it:

```bash
kubectl apply -f configmap.yaml
```

---

## ✅ Step 3: Use ConfigMap in a Deployment (10 min)

Update `deployment.yaml` to use env vars from the ConfigMap:

```yaml
spec:
  containers:
    - name: hello-node
      image: nginxdemos/hello
      ports:
        - containerPort: 80
      env:
        - name: APP_MODE
          valueFrom:
            configMapKeyRef:
              name: app-config
              key: APP_MODE
        - name: APP_COLOR
          valueFrom:
            configMapKeyRef:
              name: app-config
              key: APP_COLOR
```

Apply and verify:

```bash
kubectl apply -f deployment.yaml
kubectl exec -it <pod-name> -- env
```

You should see `APP_MODE=debug` and `APP_COLOR=blue`.

---

## ✅ Step 4: Create a Secret (5 min)

```bash
kubectl create secret generic app-secret \
  --from-literal=API_KEY=my-super-secret
```

View (note: it’s base64, not encrypted):

```bash
kubectl get secret app-secret -o yaml
```

---

## ✅ Step 5: Use Secret in Deployment (5 min)

Add to `deployment.yaml`:

```yaml
env:
  - name: API_KEY
    valueFrom:
      secretKeyRef:
        name: app-secret
        key: API_KEY
```

Then:

```bash
kubectl exec -it <pod-name> -- printenv | grep API_KEY
```

✅ Your pod now reads the secret without it being hardcoded.

---

## 🧠 Recap

| Feature    | ConfigMap        | Secret                                        |
| ---------- | ---------------- | --------------------------------------------- |
| Stores     | Plaintext values | Sensitive values (base64)                     |
| Used for   | Env config       | Passwords, tokens                             |
| Mounted as | Env vars, files  | Env vars, files                               |
| Encrypted? | ❌ No             | ❌ Only base64 (encode, not secure by default) |

---

Let me know if you want to:

* Mount config/secret as files instead of env vars
* Use `.env` style loading
* Or move on to **Hour 6: Health Checks & Resource Limits**!
