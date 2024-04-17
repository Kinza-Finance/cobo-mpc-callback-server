# callback-server-py

## Development Environment  
Node.js can be installed via an installation package or the pyenv tool.

Pyenv is a simple tool to manage multiple versions of Python. It has the following features:
* Lets clients change the global Python version on a per-user basis 
* Provides support for per-project Python versions
* Lets clients create pyenv-virtualenv to automate the process 
* Searches for commands from multiple versions of Python at a time

Clients are recommended to use Python 3.0 and above. 

## Components 
```markdown
callback-server-py
├── cobo-tss-node-risk-control-pub.key   // TSS Node's RSA public key  
├── callback-server-pri.pem              // Callback server's RSA private key  
├── main.py                              // Root of the binary
├── requirements.txt                     // List of dependencies required
└── README.md
```

## Run Codes
### Download Dependencies
> **Note**
> 
> Clients can use the venv module to create lightweight virtual environments, each with their own independent set of Python packages installed in their site directories.
>
> Clients need to execute the following command in their current directory:
> 
> ~~~ 
> python3.x -m venv ./venv
> ~~~
> 
> Clients can head to the /bin folder under the newly generated venv directory and execute the following command:
> 
> ~~~
> source activate
> ~~~
> 
> The prompt name will have a venv prefix once the above command has been successfully executed. This indicates that an isolated Python environment, named as venv, has been created.
> 
> Clients can proceed to the following steps, while keeping dependencies required by different projects separate. After all code samples have been successfully executed, clients may also delete the newly generated venv directory.
> 
> 
Clients can now execute the following command to download the dependencies required by the project to local storage.  
```markdown
pip3.x install -r ./requirements.txt 
```


### Configure server  
Before execution, clients need to configure the TSS Node and callback server. For more information, please refer to [[TSS Node User Guide](https://docs.cobo.com/cobo-mpc-waas/v/simplified-chinese/readme/tss-node-shi-yong-zhi-nan)].

### Start service
Once the callback server has been successfully configured, clients can proceed to start the callback service.
```markdown
python3.x main.py
```
Clients can start up the TSS Node once all aforementioned steps are completed. For more information, please refer to [[TSS Node User Guide](https://docs.cobo.com/cobo-mpc-waas/v/simplified-chinese/readme/tss-node-shi-yong-zhi-nan)].

