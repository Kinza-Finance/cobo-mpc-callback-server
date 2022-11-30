# callback-service-java

## 环境配置
### 安装Java SDK
因为Java程序必须运行在JVM之上，所以，我们第一件事情就是安装JDK。
网络上已经有很多Java SDK的安装教程，这里就不再赘述。值得提醒的一点就是，一定记得配置以下几个全局变量：
```markdown
JAVA_HOME=xxx
JRE_HOME=$JAVA_HOME/jre
PATH=$PATH:$JAVA_HOME/bin
```
### 安装Maven
#### 简介
它是一个使用Maven管理的普通的Java项目，它的目录结构默认如下：
```markdown
a-maven-project
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   └── resources
│   └── test
│       ├── java
│       └── resources
└── target
```
项目的根目录a-maven-project是项目名，它有一个项目描述文件pom.xml，存放Java源码的目录是src/main/java，存放资源文件的目录是src/main/resources，存放测试源码的目录是src/test/java，存放测试资源的目录是src/test/resources，最后，所有编译、打包生成的文件都放在target目录里。这些就是一个Maven项目的标准目录结构。
所有的目录结构都是约定好的标准结构，千万不要随意修改目录结构。使用标准结构不需要做任何配置，Maven就可以正常使用。
#### 安装
网络上也有大量的Maven安装教程，这里也不再赘述。值得提醒的一点就是，一定记得配置以下几个全局变量：
```markdown
export MAVEN_HOME=xxx
export PATH=$PATH:$MAVEN_HOME/bin
```

## 代码组成说明
```markdown
callback-service-java
├── pom.xml // maven工程配置文件
├── src
│   └── main
│      ├── java
│      │   └── com.cobo.tss.example.CallbackServer.java // Callback Server主程序
│      └── resources
│          ├── cobo-tss-node-risk-control-pub.key       // TSS Node端提供的RSA通信公钥
│          └── customer-risk-control-server-pri.key     // Callback 端生成的通信私钥
└── README.md
```

## 样例运行
<B>强烈建议您使用支持JAVA的IDEA来完成以下操作</B>，在IDEA中进行代码编译与服务运行的一体化操作。如果没有安装IDEA或者不愿意使用IDEA，则也可以通过下面的命令行来完成操作。
### 代码编译
```markdown
mvn compile clean
mvn compile
```
代码编译之后，默认会在当前目录生成一个`target`文件夹，里面有编译好了的Class文件。

### 服务配置
在样例执行之前，我们还需要对TSS Node和CallBack服务进行配置，具体可以参考Cobo[官方文档](https://docs.google.com/document/d/1ifQMVqCSyc129OGq7AKo7t5QBBkkAeu9svLfX4lKPhI/edit#heading=h.zh8q167fpjo3)。

<B>说明</B>

因为Maven工程有自己严格的目录使用要求，因此，在对Java版本的服务进行配置时，所有相关的通信RSA公私钥都需要放到以下目录：`src/main/resources`。
### 服务运行
完成服务配置之后，我们就可以启动CallBack服务了。
```markdown
mvn exec:java -Dexec.mainClass="com.cobo.tss.example.CallbackServer"
```
之后，我们就可以继续启动TSS Node了，可以参考官方文档的[相关章节](https://docs.google.com/document/d/1ifQMVqCSyc129OGq7AKo7t5QBBkkAeu9svLfX4lKPhI/edit#heading=h.3shma34oqi61)。
