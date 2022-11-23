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
)

type PackageDataClaim struct {
	PackageData []byte `json:"package_data,omitempty"`
	jwt.StandardClaims
}

// =================== TSS related ===================.

type RawMeta struct {
	Custody    string `json:"custody,omitempty"`
	Blockchain string `json:"blockchain,omitempty"`
	Customer   string `json:"customer,omitempty"`
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
