# callback-server-rust

## Development Environment  
Rustup is an installer for the systems programming language Rust. Rust supports a number of platforms and there are many builds of Rust available at any time. Rustup manages these builds in a consistent way on each platform that Rust supports. The beta and nightly channels are supported by Rust. 

If clients has already downloaded and installed Rustup, please execute 'rustup update' to upgrade it to the latest version. Otherwise, please refer to the following guides. 

For macOS, Linux or UNIX-like operating systems, clients can use the following commands to download Rustup and install Rust.
~~~
curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh
~~~

For the Windows operating system, clients can refer to [[Rust for Windows](https://learn.microsoft.com/en-us/windows/dev-environment/rust/rust-for-windows)]. 

## Components
```markdown
callback-server-rust
├── pom.xml // Configuration details used by Maven to build the project
├── src
│   ├── main.rs                                  // Root of the binary
│   ├── cobo-tss-node-risk-control-pub.key       // TSS Node's RSA public key
│   └── callback-server-pri.pem                  // Callback server's RSA private key
├── Cargo.lock                                   // Exact information related to dependencies
├── Cargo.toml                                   // Description of dependencies 
└── README.md
```

## Run Codes
### Compile Codes
> **Note**
> 
> Cargo is Rust's build system and package manager. Most Rust developers use Cargo to manage their Rust projects as Cargo can handle a number of tasks, such as building codes, downloading the libraries these codes depends on, and building those libraries. Please note that these libraries are referred to as dependencies.
> 
> The Cargo toolchain will be installed by default when clients configure the development environment.
```markdown
cargo build
```

### Configure Server  
Before execution, clients need to configure the TSS Node and callback server. For more information, please refer to [[TSS Node User Guide](https://docs.google.com/document/d/1J3tuFnv-jWm20-JoCQ1uYRhLYeU-IbqOyyCPHunbYr4/edit)].

### Start Service
Once the callback server has been successfully configured, clients can proceed to start the callback service.
```markdown
cargo run 
```
Clients can start up the TSS Node once all aforementioned steps are completed. For more information, please refer to [[TSS Node User Guide](https://docs.google.com/document/d/1J3tuFnv-jWm20-JoCQ1uYRhLYeU-IbqOyyCPHunbYr4/edit)].

