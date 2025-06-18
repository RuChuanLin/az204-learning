### Storage account → Container → Blob —the three‑level namespace

| Level               | What it is                                                                                   | Key facts (May 2025)                                                                                                                                                                                                                                              | Typical URL                                                                                   |
| ------------------- | -------------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | --------------------------------------------------------------------------------------------- |
| **Storage account** | A *globally unique* namespace and billing/security boundary for all data in Azure Storage.   | • One account can host blobs, files, queues, and tables (GPv2)  <br>• You pick region, redundancy (LRS/ZRS/GZRS/GRS), performance tier (Standard/Premium)  <br>• All usage, IAM/RBAC, network rules, and encryption settings are applied at this level.           | `https:// \<account‑name>.blob.core.windows.net` ([Microsoft Learn][1], [Microsoft Learn][2]) |
| **Container**       | A logical *folder* inside the account that groups blobs and sets a **default access scope**. | • Unlimited per account  <br>• Each has its own ACL / public‑access setting  <br>• Acts like a directory but can’t be nested (unless you enabled the Data Lake “hierarchical namespace,” which adds true folders).                                                | `https:// \<account>.blob.core.windows.net/\<container>` ([Microsoft Learn][1])               |
| **Blob**            | The actual object (file). Types: **block**, **append**, **page**.                            | • Name can include slashes so you *can* emulate sub‑folders  <br>• Independent properties: tier (Hot/Cool/Archive for block & append blobs), metadata, immutability policy, versioning, snapshots  <br>• Max size: block ≈ 190 TiB, append ≈ 195 GiB, page 8 TiB. | `https:// \<account>.blob.core.windows.net/\<container>/\<blob‑name>` ([Microsoft Learn][3])  |

---

#### How they work together

1. **Create a storage account** – defines region, redundancy, networking, encryption keys, and gets two 512‑bit access keys.
2. **Create containers** – think “top‑level directories.” Set container‑wide public access (“private”, “blob”, “container”) or leave private and use Azure AD / SAS for fine‑grained access.
3. **Upload blobs** – the SDK or REST API addresses every object with the 3‑part path above. You can version, snapshot, tier, or soft‑delete blobs independently.

---

#### Security & access control quick view

| Scope         | Options you usually configure                                                                                                     |
| ------------- | --------------------------------------------------------------------------------------------------------------------------------- |
| **Account**   | Network rules (private endpoints, IP ranges), CMK encryption, Azure RBAC (Owner/Contributor/Blob Data Reader, etc.), shared keys. |
| **Container** | Public access level, stored access policies, POSIX‑style ACLs if hierarchical namespace is enabled.                               |
| **Blob**      | SAS tokens, object‑level immutability/retention, customer‑provided keys (per request), object‑level version & legal hold.         |

Everything is encrypted at rest with Microsoft‑managed keys by default, and TLS 1.2+ in transit. ([Microsoft Learn][4], [Microsoft Learn][5])

---

### Take‑away

* **Hierarchy = account ▶ containers ▶ blobs**; all URLs and API calls follow that structure.
* **Containers** give you a simple, flat grouping plus ACL boundary.
* **Blobs** carry the data, tier, and object‑level policies.
  Keep this mental model and you’ll know exactly where to set each policy or performance option when designing Azure Blob solutions.

[1]: https://learn.microsoft.com/en-us/azure/storage/blobs/storage-blobs-introduction?utm_source=chatgpt.com "Introduction to Azure Blob Storage - Learn Microsoft"
[2]: https://learn.microsoft.com/en-us/azure/storage/common/storage-account-overview?utm_source=chatgpt.com "Storage account overview - Azure Storage | Microsoft Learn"
[3]: https://learn.microsoft.com/en-us/rest/api/storageservices/blob-service-rest-api?utm_source=chatgpt.com "Azure Blob Storage REST API - Learn Microsoft"
[4]: https://learn.microsoft.com/en-us/azure/storage/common/storage-redundancy?utm_source=chatgpt.com "Azure Storage - Data redundancy - Learn Microsoft"
[5]: https://learn.microsoft.com/en-us/azure/storage/blobs/data-lake-storage-introduction?utm_source=chatgpt.com "Introduction to Azure Data Lake Storage - Learn Microsoft"
