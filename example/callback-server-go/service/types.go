package service

import (
	"github.com/dgrijalva/jwt-go"
)

const (
	StatusOK             = 0
	StatusInvalidRequest = 10
	StatusInvalidToken   = 20
	StatusInternalError  = 30

	TypeKeyGen     = 1
	TypeKeySign    = 2
	TypeKeyReshare = 3

	TransactionTypeWidthDraw = "WITHDRAW"
)

type PackageDataClaim struct {
	PackageData []byte `json:"package_data,omitempty"`
	jwt.StandardClaims
}

// =================== TSS related ===================.

type KeyGenDetail struct {
	Threshold int      `json:"threshold,omitempty"`
	Curve     int      `json:"curve,omitempty"`
	NodeIDs   []string `json:"node_ids,omitempty"`
}

type KeySignDetail struct {
	RootPubKey    string   `json:"root_pub_key,omitempty"`
	UsedNodeIDs   []string `json:"used_node_ids,omitempty"`
	Bip32PathList []string `json:"bip32_path_list,omitempty"`
	MsgHashList   []string `json:"msg_hash_list,omitempty"`
}

type KeyReshareDetail struct {
	RootPubKey   string   `json:"root_pub_key,omitempty"`
	Curve        int      `json:"curve,omitempty"`
	UsedNodeIDs  []string `json:"used_node_ids,omitempty"`
	OldThreshold int      `json:"old_threshold,omitempty"`
	NewThreshold int      `json:"new_threshold,omitempty"`
	NewNodeIDs   []string `json:"new_node_ids,omitempty"`
}

type KeyGenExtraInfo struct {
	CoboID string `json:"cobo_id"`
}

type KeySignExtraInfo struct {
	CoboID           string         `json:"cobo_id"`
	ApiRequestID     string         `json:"api_request_id"`
	TransactionType  string         `json:"transaction_type"` // TransactionTypeEnum
	Coin             string         `json:"coin"`
	FromAddress      string         `json:"from_address"`
	Amount           string         `json:"amount"`
	ToAddress        string         `json:"to_address"`
	ToAddressDetails string         `json:"to_address_details"` // json, []KeySignToAddressDetail
	Fee              int            `json:"fee"`
	GasPrice         int            `json:"gas_price"`
	GasLimit         int            `json:"gas_limit"`
	ExtraParameters  string         `json:"extra_parameters"` // json
	ReplaceCoboID    string         `json:"replace_cobo_id"`
	ApiKey           string         `json:"api_key"`
	Spender          string         `json:"spender"`
	RawTx            []KeySignRawTx `json:"raw_tx"`
	Note             string         `json:"note"`
}

type KeySignToAddressDetail struct {
	ToAddress string `json:"to_address"`
	Amount    string `json:"amount"`
}

type KeySignExtraParameters struct {
	InputsToSpend   []Input `json:"inputsToSpend"`
	InputsToExclude []Input `json:"inputsToExclude"`
}

type Input struct {
	TxHash string `json:"tx_hash"`
	VoutN  int    `json:"vout_n"`
}

type KeySignRawTx struct {
	RawTx string `json:"raw_tx"`
}

type KeyReshareExtraInfo struct {
	CoboID string `json:"cobo_id"`
}

type CallBackRequest struct {
	RequestID     string `json:"request_id,omitempty"`
	RequestType   int    `json:"request_type,omitempty"`
	RequestDetail string `json:"request_detail,omitempty"`
	ExtraInfo     string `json:"extra_info,omitempty"`
}

type CallBackResponse struct {
	Status    int    `json:"status,omitempty"`
	RequestID string `json:"request_id,omitempty"`
	Action    string `json:"action,omitempty"` //[APPROVE, REJECT, WAIT]
	ErrStr    string `json:"error,omitempty"`
}
