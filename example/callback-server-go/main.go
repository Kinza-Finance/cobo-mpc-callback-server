package main

import "github.com/cobo/cobo-mpc-callback-server-examples/example/callback-server-go/service"

func main() {
	config := &service.CallBackConfig{
		ServiceName:               "TEST-001",
		Endpoint:                  "localhost:11020",
		TokenExpireMinutes:        2,
		CallBackPubKeyPath:        "./cobo-tss-node-risk-control-pub.key",   // TSS Node's pub key to verify token received
		CallBackHandlerPriKeyPath: "./customer-risk-control-server-pri.key", // local pri key to generate token
		EnableDebug:               false,
	}

	if cbService := service.NewCallBackService(config); cbService != nil {
		cbService.Start()
	}
}
