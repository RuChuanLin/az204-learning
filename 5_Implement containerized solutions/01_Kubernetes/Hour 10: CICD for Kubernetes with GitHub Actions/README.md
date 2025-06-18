Awesome — let’s get into **Option 2: CI/CD for Kubernetes** 🔁🚀
This is where your workflow goes from “manual apply + build” → to **automated push-to-deploy**.

---

## 🕘 Hour 10: CI/CD for Kubernetes with GitHub Actions

### 🎯 Goals

* Automate the full flow:

  1. Build Docker image
  2. Push to container registry (e.g., Docker Hub or ACR)
  3. Apply updated manifests to Kubernetes
* Use **GitHub Actions** for CI/CD
* Optionally: version and rollout images automatically

---

## ✅ Step 1: Basic Folder Structure

```
my-k8s-app/
├── .github/
│   └── workflows/
│       └── deploy.yaml        # CI/CD pipeline
├── Dockerfile
├── index.js
├── package.json
├── k8s/
│   ├── deployment.yaml
│   └── service.yaml
```

---

## ✅ Step 2: Dockerfile (Recap)

```Dockerfile
FROM node:18-alpine
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
EXPOSE 3000
CMD ["npm", "start"]
```

---

## ✅ Step 3: GitHub Secrets Setup

In your GitHub repo:

Go to **Settings → Secrets → Actions**, and add:

| Name               | Description                           |
| ------------------ | ------------------------------------- |
| `DOCKER_USERNAME`  | Your Docker Hub username              |
| `DOCKER_PASSWORD`  | Your Docker Hub password or token     |
| `KUBE_CONFIG_DATA` | Base64-encoded kubeconfig (see below) |

---

### 🔐 To get `KUBE_CONFIG_DATA` (for Minikube):

```bash
cat ~/.kube/config | base64 | pbcopy
```

Or:

```bash
cat ~/.kube/config | base64 -w0
```

Paste that as the secret.

---

## ✅ Step 4: GitHub Actions Workflow

📄 `.github/workflows/deploy.yaml`

```yaml
name: CI/CD for K8s Node App

on:
  push:
    branches: [ main ]

jobs:
  deploy:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout code
        uses: actions/checkout@v3

      - name: Log in to Docker Hub
        run: echo "${{ secrets.DOCKER_PASSWORD }}" | docker login -u "${{ secrets.DOCKER_USERNAME }}" --password-stdin

      - name: Build and push image
        run: |
          docker build -t ${{ secrets.DOCKER_USERNAME }}/node-k8s-app:latest .
          docker push ${{ secrets.DOCKER_USERNAME }}/node-k8s-app:latest

      - name: Set up kubectl
        uses: azure/setup-kubectl@v3

      - name: Set up kubeconfig
        run: |
          echo "${{ secrets.KUBE_CONFIG_DATA }}" | base64 -d > $HOME/.kube/config

      - name: Deploy to Kubernetes
        run: |
          kubectl apply -f k8s/deployment.yaml
          kubectl apply -f k8s/service.yaml
```

---

## ✅ Step 5: Use `latest` or Git-based Tags

To version images automatically:

```yaml
docker build -t ${{ secrets.DOCKER_USERNAME }}/node-k8s-app:${{ github.sha }} .
```

And update your `deployment.yaml` to use that `${{ github.sha }}` via `envsubst` or a templating tool (e.g., Kustomize, Helm).

---

## 🧠 Recap

| Step                | Tool             |
| ------------------- | ---------------- |
| CI/CD pipeline      | GitHub Actions   |
| Image build & push  | Docker CLI       |
| Cluster apply       | `kubectl apply`  |
| Secrets management  | GitHub → Actions |
| Optional templating | Helm / Kustomize |

---

Would you like to try a:

* More advanced GitOps-style flow?
* Move to **Option 3: Horizontal Pod Autoscaling (HPA)**?
