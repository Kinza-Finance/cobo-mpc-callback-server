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

type KeyGenMeta struct {
	CoboID    string   `json:"cobo_id"`
	Threshold int      `json:"threshold"`
	NodeIDs   []string `json:"node_ids"`
}

type KeySignMeta struct {
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
	ApiKey           string         `json:"api_key"`
	Spender          string         `json:"spender"`
	NodeIds          []string       `json:"node_ids"`
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
	RawTx          string `json:"raw_tx"`
	DerivationPath string `json:"derivation_path"`
}

type KeyReshareMeta struct {
	CoboID       string   `json:"cobo_id"`
	OldThreshold int      `json:"old_threshold"`
	OldNodeIds   []string `json:"old_node_ids"`
	NewThreshold int      `json:"new_threshold"`
	NewNodeIds   []string `json:"new_node_ids"`
}

type CallBackRequest struct {
	RequestID   string `json:"request_id,omitempty"`
	RequestType int    `json:"request_type,omitempty"`
	Meta        string `json:"meta,omitempty"`
}

type CallBackResponse struct {
	Status    int    `json:"status,omitempty"`
	RequestID string `json:"request_id,omitempty"`
	Action    string `json:"action,omitempty"` //[APPROVE, REJECT, WAIT]
	ErrStr    string `json:"error,omitempty"`
}
