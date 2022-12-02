# callback-server-rust

## 环境配置
Rust由工具rustup安装和管理。rustup用于管理不同平台下的Rust构建版本并使其互相兼容，支持安装由Beta和Nightly频道发布的版本，并支持其他用于交叉编译的编译版本。

如果您曾经安装过rustup，可以执行rustup update来升级Rust。如果没有，则可以按照以下方式安装：

如果您的系统是amcOs、Linux或其它类Unix系统，可以使用以下命令安装Rustup和Rust。
~~~
curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh
~~~

如果您的系统是Windows，则可以参考以下[文档](https://learn.microsoft.com/zh-cn/windows/dev-environment/rust/setup)进行安装。

## 代码组成说明
```markdown
callback-server-rust
├── pom.xml // maven工程配置文件
├── src
│   ├── main.rs                                  // 主入口程序
│   ├── tss-node-callback-client-pub.key         // TSS Node 端提供的 RSA 公钥
│   └── callback-server-pri.pem                  // Callback Server 端生成的 RSA 私钥
├── Cargo.lock                                   // 配置依赖库的源和版本信息
├── Cargo.toml                                   // 配置依赖库
└── README.md
```

## 样例运行
### 编译代码
> **Note**
> 
> Cargo是Rust的构建系统和包管理器。大多数Rust开发人员使用Cargo来管理他们的Rust项目，因为它可以为你处理很多任务，比如构建代码、下载依赖库并编译这些库。（我们把代码所需要的库叫做依赖（dependencies））。
> 
> 我们在环境配置环节，其实就已经默认安装了Cargo工具链，不再需要额外安装
```markdown
cargo build
```

### 服务配置
在样例执行之前，我们还需要对TSS Node和CallBack服务进行配置，具体可以参考Cobo[官方文档](https://docs.google.com/document/d/1ifQMVqCSyc129OGq7AKo7t5QBBkkAeu9svLfX4lKPhI/edit#heading=h.zh8q167fpjo3)。

### 服务运行
完成服务配置之后，我们就可以启动CallBack服务了。
```markdown
cargo run 
```
之后，我们就可以继续启动TSS Node了，可以参考官方文档的[相关章节](https://docs.google.com/document/d/1ifQMVqCSyc129OGq7AKo7t5QBBkkAeu9svLfX4lKPhI/edit#heading=h.3shma34oqi61)。

