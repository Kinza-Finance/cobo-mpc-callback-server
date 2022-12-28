# callback-server-go

## Development Environment

The most common installation methods of the Go programming language are listed below. Clients should choose a method that fits its business and security requirements. 

* Standard installation package: The installation package provided by Go supports Windows, Linux, Max and other operating systems. Clients are recommended to use this method for quick and easy installation of Go. 
* Third-party software: Clients can handle the installation of Go using a package manager or an installer such as Homebrew (macOS), GNU Wget (Ubuntu), and apt-get (Ubuntu). This method is suitable for clients who are already familiar with the client-side operating system.
* Go source code installation: Clients can build and run Go from the source code (e.g. compile the source code, copy the binaries, customize environment variables). This method is suitable for clients who are already familiar with Unix-like operating systems. 

To install multiple versions of Go within the same operating system, Clients can refer to [Go Version Manager] (https://github.com/moovweb/gvm). GVM is an open source tool for managing Go environments. 

Do note that the following global variables should be configured first in regardless of the installation method.  
```markdown
export GOROOT=go_install_path  
export GOPATH=$HOME/gopath (optional)
export PATH=$PATH:$GOROOT/bin:$GOPATH/bin
```
For clients with maxOS or Unix-like operating systems, the above global variables should be included in a .bashrc or .zshrc file. For clients with the Windows operating system, these variables should be written into environment variables.
## Components
```markdown
callback-server-go
├── service                              // Callback server program  
├── cobo-tss-node-risk-control-pub.key   // TSS Node's RSA public key  
├── callback-server-pri.pem              // Callback server's RSA private key  
├── main.go                              // Entry point of an executable program
└── README.md
```

## Run Codes
### Compile Codes
```markdown
go build
```
After the codes are successfully compiled, a `callback-server-go` executable file will be generated in the current directory by default. 

### Configure server  
Before execution, clients need to configure the TSS Node and callback server. For more information, please refer to [TSS Node User Guide] (https://docs.google.com/document/d/1J3tuFnv-jWm20-JoCQ1uYRhLYeU-IbqOyyCPHunbYr4/edit).

### Start  service
Once the callback server has been successfully configured, clients can proceed to start the callback service.
```markdown
./callback-server-go
```
Clients can start up the TSS Node once all aforementioned steps are completed. For more information, please refer to [TSS Node User Guide] (https://docs.google.com/document/d/1J3tuFnv-jWm20-JoCQ1uYRhLYeU-IbqOyyCPHunbYr4/edit).

### Configure whitelist  
We've provided Go code samples to help clients configure a whitelist for risk control purposes. Only addresses added to the whitelist will be called upon during key signing (i.e. KeySign). Please refer to the steps below for more information.
> **Important**
> 
> All code samples are for reference only and should not be used directly in any production environment. Please ensure that all best practices are in place for API authentication. 
> 
#### Add to whitelist
Request:
```markdown
curl --location --request POST '127.0.0.1:11020/add_rcv_address' \
--header 'Content-Type: application/json' \
--data-raw '{"address": "0xEEACb7a5e53600c144C0b9839A834bb4b39E540c"}'
```
Response:
```json
{
    "status": 200
}
```
Please note that only ETH and BTC addresses can be added to the whitelist. Otherwise, an error will be returned.
Request:
```markdown
curl --location --request POST '127.0.0.1:11020/add_rcv_address' \
--header 'Content-Type: application/json' \
--data-raw '{"address": "0xEEACb7a5e53600c144C0b9839A834bb4b39E540cxyz"}'
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
--data-raw '{"address": "coboWqM1vC676RsVkuoyPL1YReGd8sUXxbeypKCiirien9wdQ"}'
```
Response:
```json
{
    "status": 200
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
        "0xEEACb7a5e53600c144C0b9839A834bb4b39E540c"
    ]
}
```
