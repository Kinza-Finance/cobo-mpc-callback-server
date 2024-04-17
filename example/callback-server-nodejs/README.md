# callback-server-nodejs

## Development Environment  
Node.js can be installed via an installation package or the nvm tool.

Nvm is a version manager for node.js. It is designed to be installed per-user and invoked per-shell. 

Nvm allows clients to install different versions of Node, and switch between these versions depending on the projects.

Clients are recommended to use nvm to install node.js. 

Do note that clients will need to configure the following global variable if node.js is installed using the nvm tool.  
```markdown
export NVM_DIR=xxx
```

## Components
```markdown
callback-server-nodejs
├── cobo-tss-node-risk-control-pub.key   // TSS Node's RSA public key  
├── callback-server-pri.pem              // Callback server's RSA private key  
├── server.js                            // Root of the binary
├── package.json                         // List of dependencies required
├── package-lock.json                    // Lockfile with dependency information & version numbers
└── README.md
```

## Run Codes
### Download Dependencies
Clients can now execute the following command to download the dependencies required by the project to local storage.  
```markdown
npm install
```
Once the above command has been executed, a node_modules directory will be generated locally. The directory contains all dependencies of the project.

### Configure Server  
Before execution, clients need to configure the TSS Node and callback server. For more information, please refer to [[TSS Node User Guide](https://docs.cobo.com/cobo-mpc-waas/v/simplified-chinese/readme/tss-node-shi-yong-zhi-nan)].

### Start Service
Once the callback server has been successfully configured, clients can proceed to start the callback service.
```markdown
node server.js 
```
Clients can start up the TSS Node once all aforementioned steps are completed. For more information, please refer to [[TSS Node User Guide](https://docs.cobo.com/cobo-mpc-waas/v/simplified-chinese/readme/tss-node-shi-yong-zhi-nan)].

