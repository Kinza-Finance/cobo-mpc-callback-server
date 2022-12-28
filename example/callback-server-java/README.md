# callback-server-java

## Development Environment  
### Install Java SDK
The JVM is a virtual machine that runs Java class files in a portable way. Clients need to install JVM first.

Do note that the following global variables should be configured first.
```markdown
JAVA_HOME=xxx
JRE_HOME=$JAVA_HOME/jre
PATH=$PATH:$JAVA_HOME/bin
```
### Install Maven
#### Introduction 
Maven is a build automation tool used primarily for Java projects. Its default directory is as shown below:  
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
The project name, a-maven-project, is displayed in the root directory. 

Pom.xml is an XML file that contains information about the project and configuration details used by Maven to build the project. 

The src/main/java directory contains Java class files of a Maven project.

The src/test/resources directory is where Java libraries store their configuration files.

The src/test/java directory is where test use case codes are stored.

The src/test/resources directory is where test resources are stored.

The target directory contains all compiled and packaged files. 

Please note that clients should not modify or configure the standard directory structure. Otherwise, Maven may fail to run properly. 

#### Install Maven 
Do note that the following global variables should be configured first.
```markdown
export MAVEN_HOME=xxx
export PATH=$PATH:$MAVEN_HOME/bin
```

## Components 
```markdown
callback-server-java
├── pom.xml // Configuration details used by Maven to build the project
├── src
│   └── main
│      ├── java
│      │   └── com/cobo/tss/example
│      │        ├── CallbackServer.java  // Callback server's main program
│      │        ├── Types.java           // Risk control-related structs 
│      │        └── WhiteListRule.java   // Risk control whitelist  
│      └── resources
│          ├── cobo-tss-node-risk-control-pub.key   // TSS Node‘s RSA public key  
│          └── callback-server-pri.pem              // Callback server's RSA private key  
└── README.md
```

## Run Codes
<B>Clients are recommended to use an integrated development environment that supports the Java language to complete the following steps (e.g. compile codes, run service)</B>. If no integrated development environment has been installed, clients may also use the following commands. 

### Compile Codes
```markdown
mvn compile clean
mvn compile
```
After the codes are successfully compiled, a `target` folder will be generated in the current directory by default. The folder contains all complied Java class files.

### Configure server  
Before execution, clients need to configure the TSS Node and callback server. For more information, please refer to [TSS Node User Guide] (https://docs.google.com/document/d/1J3tuFnv-jWm20-JoCQ1uYRhLYeU-IbqOyyCPHunbYr4/edit).

<B>Note</B>

Maven has a standard directory structure. During configuration, clients will need to place the RSA public key and the RSA private key into the following directory: `src/main/resources`.
### Start  service
Once the callback server has been successfully configured, clients can proceed to start the callback service.
```markdown
mvn exec:exec -Dexec.executable="java" -Dexec.args="-classpath %classpath com.cobo.tss.example.CallbackServer"
```
Clients can start up the TSS Node once all aforementioned steps are completed. For more information, please refer to [TSS Node User Guide] (https://docs.google.com/document/d/1J3tuFnv-jWm20-JoCQ1uYRhLYeU-IbqOyyCPHunbYr4/edit).

### Configure whitelist  
We've provided Java code samples to help clients configure a whitelist for risk control purposes. Only addresses added to the whitelist will be called upon during key signing (i.e. KeySign). Please refer to the steps below for more information.
> **Important**
>
> All code samples are for reference only and should not be used directly in any production environment. Please ensure that all best practices are in place for API authentication. 
>
#### Add to whitelist
Request
```markdown
curl --location --request POST '127.0.0.1:11020/add_rcv_address' \
--header 'Content-Type: application/json' \
--data-raw '{"address": "0xEEACb7a5e53600c144C0b9839A834bb4b39E540c"}'
```
Response:
```json
{
  "status": 200,
  "error": ""
}
```
Please note that only ETH and BTC addresses can be added to the whitelist. Otherwise, an error will be returned.
Request:
```markdown
curl --location --request POST '127.0.0.1:11020/add_rcv_address' \
--header 'Content-Type: application/json' \
--data-raw '{"address": "0xEEACb7a5e53600c144C0b9839A834bb4b39E540c123"}'
```
Response:
```json
{
  "status": 400,
  "error": "Receiver address is not valid btc or eth address"
}
```
#### Remove from whitelist
Request:
```markdown
curl --location --request POST '127.0.0.1:11020/rm_rcv_address' \
--header 'Content-Type: application/json' \
--data-raw '{"address": "0xEEACb7a5e53600c144C0b9839A834bb4b39E540e"}'
```
Response:
```json
{
  "status": 200,
  "error": ""
}
```
#### Query whitelist
Request:
```markdown
curl --location --request GET '127.0.0.1:11020/list_rcv_address' \
--header 'Content-Type: application/json'
```
Response:
```json
{
  "address_list": [
    "0xEEACb7a5e53600c144C0b9839A834bb4b39E540e",
    "0xEEACb7a5e53600c144C0b9839A834bb4b39E540c"
  ]
}
```
