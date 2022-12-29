# callback-server-py

## 环境配置
有两种方式安装Python：一种是通过安装包安装，另一种是pyenv安装。

pyenv是一个非常好用的Python环境管理工具。有以下主要特性：
* 方便的安装、管理不同版本的Python，而且不需要sudo权限，不会污染系统的Python版本
* 可以修改当前用户使用的默认Python版本
* 集成virtualenv，自动安装、激活
* 命令行自动补全

网络上已经有了大量的安装教程，这里就不再赘述

值得提醒的一点就是，我们推荐按照Python3.0及以上的版本，不再使用Python2.x版本。

## 代码组成说明
```markdown
callback-server-py
├── cobo-tss-node-risk-control-pub.key   // TSS Node 端提供的 RSA 公钥
├── callback-server-pri.pem              // Callback Server 端生成的 RSA 私钥
├── main.py                              // 主入口程序
├── requirements.txt                     // 配置依赖库
└── README.md
```

## 样例运行
### 依赖库下载
> **Note**
> 
> 为了保持开发环境的整洁性，我们可以使用python提供的venv来给每个程序提供一套完全隔离的运行环境。
>
> 具体步骤如下，在当前工作目录执行以下命令：
> 
> ~~~ 
> python3.x -m venv ./venv
> ~~~
> 
> 进入新生成的venv目录下的/bin文件夹，执行以下命令：
> 
> ~~~
> source activate
> ~~~
> 
> 执行完上述命令之后，您将注意到命令提示符变了，有个(venv)前缀，表示当前环境是一个名为venv的Python环境。
> 
> 之后，您就可以放心去执行下面的命令，完全不用担心不同工程间的干扰。样例程序执行完成之后，甚至可以直接将新生成的venv目录删除。
> 
> 
在运行之前，我们需要将工程所依赖的库下载到本地，可以使用以下命令下载：
```markdown
pip3.x install -r ./requirements.txt 
```
命令运行完成之后，相关的依赖库就已经下载到了本地。

### 服务配置
在样例执行之前，我们还需要对TSS Node和CallBack服务进行配置，具体可以参考Cobo[官方文档](https://docs.google.com/document/d/1ifQMVqCSyc129OGq7AKo7t5QBBkkAeu9svLfX4lKPhI/edit#heading=h.zh8q167fpjo3)。

### 服务运行
完成服务配置之后，我们就可以启动CallBack服务了。
```markdown
python3.x main.py
```
之后，我们就可以继续启动TSS Node了，可以参考官方文档的[相关章节](https://docs.google.com/document/d/1ifQMVqCSyc129OGq7AKo7t5QBBkkAeu9svLfX4lKPhI/edit#heading=h.3shma34oqi61)。

