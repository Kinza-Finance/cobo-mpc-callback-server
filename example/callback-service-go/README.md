# callback-service-go

## 环境配置

Go有多种安装方式，以下是三种最常见的安装方式，您可以选择自己喜欢的方式安装：

* Go标准包安装：Go提供了方便的安装包，支持Windows、Linux、Mac等系统。这种方式适合快速安装，可根据自己的系统位数下载好相应的安装包，一路 next 就可以轻松安装了。推荐这种方式 
* 第三方工具安装：目前有很多方便的第三方软件包工具，例如 Ubuntu 的 apt-ge t和 wget、Mac 的 homebrew 等。这种安装方式适合那些熟悉相应系统的用户。 
* Go源码安装：这是一种标准的软件安装方式。对于经常使用Unix类系统的用户，尤其对于开发者来说，从源码安装可以自己定制。

最后，如果您想在同一个系统中安装多个版本的Go，您可以参考第三方工具[GVM](https://github.com/moovweb/gvm)，这是目前在这方面做的最好的工具，除非您知道怎么处理。

无论使用那种方式安装，都强烈建议您在环境变量中设置如下信息：
```markdown
export GOROOT=go_install_path  
export GOPATH=$HOME/gopath (可选配置)
export PATH=$PATH:$GOROOT/bin:$GOPATH/bin
```
上面这些命令对于Mac和Unix用户来说最好是写入.bashrc或者.zshrc文件，对于windows用户来说当然是写入环境变量。

## 代码组成说明
```markdown
callback-service-go
├── service                              // Callback Server程序
├── cobo-tss-node-risk-control-pub.key   // TSS Node端提供的RSA通信公钥
├── customer-risk-control-server-pri.key // Callback 端生成的通信私钥
├── main.go                              // 主入口程序
└── README.md
```

## 样例运行
### 代码编译
```markdown
go build
```
代码编译之后，默认会在当前目录生成一个`callback-service-go`的可执行文件。

### 服务配置
在样例执行之前，我们还需要对TSS Node和CallBack服务进行配置，具体可以参考COBO[官方文档](https://docs.google.com/document/d/1ifQMVqCSyc129OGq7AKo7t5QBBkkAeu9svLfX4lKPhI/edit#heading=h.zh8q167fpjo3)。

### 服务运行
完成服务配置之后，我们就可以启动CallBack服务了。
```markdown
./callback-service-go
```
之后，我们就可以继续启动TSS Node了，可以参考官方文档的[相关章节](https://docs.google.com/document/d/1ifQMVqCSyc129OGq7AKo7t5QBBkkAeu9svLfX4lKPhI/edit#heading=h.3shma34oqi61)。

### 白名单操作
在我们提供的golang语言版本的样例中，我们还实现了一个简单的白名单风控功能，对KeySign的目标接收地址进行风控，以下是其简单的使用说明。
#### 添加白名单
请求示例：
```markdown
curl --location --request POST '127.0.0.1:11020/add_rcv_address' \
--header 'Content-Type: application/json' \
--data-raw '{"address": "0xEEACb7a5e53600c144C0b9839A834bb4b39E540c"}'
```
响应示例：
```json
{
    "status": 200
}
```
白名单对地址格式也有一个简单的要求：只能添加以太坊或者比特币格式的地址，如果地址格式不对，将会返回错误。
请求示例：
```markdown
curl --location --request POST '127.0.0.1:11020/add_rcv_address' \
--header 'Content-Type: application/json' \
--data-raw '{"address": "0xEEACb7a5e53600c144C0b9839A834bb4b39E540cxyz"}'
```
响应示例：
```json
{
    "status": 400,
    "error": "Receiver address is not valid btc or eth address"
}
```
#### 移出白名单
请求示例：
```markdown
curl --location --request POST '127.0.0.1:11020/rm_rcv_address' \
--header 'Content-Type: application/json' \
--data-raw '{"address": "coboWqM1vC676RsVkuoyPL1YReGd8sUXxbeypKCiirien9wdQ"}'
```
响应示例：
```json
{
    "status": 200
}
```
#### 查询白名单
请求示例：
```markdown
curl --location --request GET '127.0.0.1:11020/list_rcv_address' \
--header 'Content-Type: application/json'
```
响应示例：
```json
{
    "address_list": [
        "0xEEACb7a5e53600c144C0b9839A834bb4b39E540c"
    ]
}
```
