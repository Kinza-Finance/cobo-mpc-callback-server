# callback-server-nodejs

## 环境配置
有两种方式安装Node.js：一种是通过安装包或者源码安装，另一种是nvm安装。

nvm是用来管理多个node和npm版本的工具。不同项目，所依赖的node版本也不尽相同。安装nvm管理node版本，方便切换。

这里推荐使用nvm安装。

网络上已经有了大量的安装教程，这里就不再赘述

值得提醒的一点就是，如果是通过nvm安装，一定记得配置以下几个全局变量：
```markdown
export NVM_DIR=xxx
```

## 代码组成说明
```markdown
callback-server-nodejs
├── cobo-tss-node-risk-control-pub.key   // TSS Node端提供的RSA通信公钥
├── customer-risk-control-server-pri.key // Callback 端生成的通信私钥
├── server.js                            // 主入口程序
├── package.json                         // 配置依赖库
├── package-lock.json                    // 依赖库的具体源和版本信息
└── README.md
```

## 样例运行
### 依赖库下载
在运行之前，我们需要将工程所依赖的库下载到本地，可以使用以下命令下载：
```markdown
npm install
```
命令运行完成之后，将在本地生成一个node_modules目录，里面下载了该项目的各项依赖。

### 服务配置
在样例执行之前，我们还需要对TSS Node和CallBack服务进行配置，具体可以参考Cobo[官方文档](https://docs.google.com/document/d/1ifQMVqCSyc129OGq7AKo7t5QBBkkAeu9svLfX4lKPhI/edit#heading=h.zh8q167fpjo3)。

### 服务运行
完成服务配置之后，我们就可以启动CallBack服务了。
```markdown
node server.js 
```
之后，我们就可以继续启动TSS Node了，可以参考官方文档的[相关章节](https://docs.google.com/document/d/1ifQMVqCSyc129OGq7AKo7t5QBBkkAeu9svLfX4lKPhI/edit#heading=h.3shma34oqi61)。

