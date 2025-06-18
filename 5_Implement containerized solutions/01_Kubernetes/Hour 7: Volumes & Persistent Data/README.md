Great — now we’re moving into **Kubernetes Hour 7: Volumes & Persistent Storage** 🧱💾

---

## 🕖 **Kubernetes Hour 7 – Volumes & Persistent Data**

### 🎯 Goals

* Understand **why containers lose data**
* Learn how to use **Volumes** for data persistence
* Understand **hostPath**, **emptyDir**, and **PersistentVolumeClaim (PVC)**
* Use a volume in a Pod

---

## ✅ Step 1: Why Volumes Matter

By default:

* A **Pod is ephemeral** (it can die, be restarted, rescheduled)
* Any files written inside a container are **lost when the Pod dies**

👉 We need volumes if:

* Your app writes logs, temp files, cache, uploads
* You need to preserve DB data

---

## ✅ Step 2: Volume Types (Overview)

| Volume Type              | Description                                           |
| ------------------------ | ----------------------------------------------------- |
| `emptyDir`               | Temporary scratch space, deleted when Pod is removed  |
| `hostPath`               | Mounts a host machine folder (use with Minikube only) |
| `PersistentVolume + PVC` | Recommended for real persistent data across Pods      |

---

## ✅ Step 3: Use an `emptyDir` Volume (Quick Example – 10 min)

Add to your `deployment.yaml`:

```yaml
spec:
  containers:
    - name: hello-node
      image: busybox
      command: [ "sh", "-c", "while true; do echo $(date) >> /data/log.txt; sleep 5; done" ]
      volumeMounts:
        - name: scratch-volume
          mountPath: /data
  volumes:
    - name: scratch-volume
      emptyDir: {}
```

Apply it:

```bash
kubectl apply -f deployment.yaml
kubectl exec -it <pod-name> -- tail -f /data/log.txt
```

✅ This demonstrates **temporary shared storage** that survives container restarts (but not Pod deletions).

---

## ✅ Step 4: PersistentVolume (PV) + PVC (15–20 min)

To **truly persist data**, we use:

* A **PersistentVolume**: storage offered by the cluster
* A **PersistentVolumeClaim**: storage requested by a Pod

---

### 🔹 Step 4a: Define PVC (`pvc.yaml`)

```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: demo-pvc
spec:
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 100Mi
```

Apply:

```bash
kubectl apply -f pvc.yaml
kubectl get pvc
```

---

### 🔹 Step 4b: Use the PVC in a Deployment

```yaml
spec:
  containers:
    - name: app
      image: busybox
      command: ["sh", "-c", "echo Hello > /mnt/data/hello.txt && sleep 3600"]
      volumeMounts:
        - name: demo-storage
          mountPath: /mnt/data
  volumes:
    - name: demo-storage
      persistentVolumeClaim:
        claimName: demo-pvc
```

✅ This volume now survives Pod restarts or rescheduling.

---

### 🧠 How it Works (Simplified):

```
PersistentVolume (PV) ← Bound → PersistentVolumeClaim (PVC) ← Mounted into → Pod
```

You can inspect:

```bash
kubectl get pv
kubectl get pvc
kubectl describe pvc demo-pvc
```

---

## 🧠 Recap

| Volume Type  | Use Case                                              |
| ------------ | ----------------------------------------------------- |
| `emptyDir`   | Temporary data between containers                     |
| `hostPath`   | Direct folder on host (Dev only)                      |
| `PVC` + `PV` | Persistent storage for databases, logs, uploads, etc. |

---

Ready for **Hour 8: Deploy a Multi-Container App (Node.js + PostgreSQL)** with all these concepts working together?
