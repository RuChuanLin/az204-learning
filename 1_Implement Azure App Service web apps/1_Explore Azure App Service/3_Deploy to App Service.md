**Back (Answer/Explanation)**  
- **Deployment Methods**  
  1. **Manual:** Use Azure CLI (`az webapp up`), FTP, or ZIP deploy to push your app directly.  
  2. **Automated (CI/CD):** Connect a Git repo (e.g., GitHub, Azure DevOps). Whenever you push changes, Azure App Service automatically builds and deploys them.

- **Deployment Slots**  
  - You can create multiple slots (e.g., **staging**, **production**) under the same App Service Plan.  
  - Deploy and test changes in the staging slot first.  
  - When everything is verified, **swap** the staging slot with production for a near-zero-downtime release.