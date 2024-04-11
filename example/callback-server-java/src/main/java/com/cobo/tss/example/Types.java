package com.cobo.tss.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

class CallBackRequest implements Serializable {
    
    @JsonProperty("request_type")
    public int requestType;

    @JsonProperty("request_id")
    public String requestID;

    @JsonProperty("request_detail")
    public String requestDetail;

    @JsonProperty("extra_info")
    public String extraInfo;

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getRequestDetail() {
        return requestDetail;
    }

    public void setRequestDetail(String requestDetail) {
        this.requestDetail = requestDetail;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}

class CallBackResponse implements Serializable {
    public static final String ActionApprove = "APPROVE";
    public static final String ActionReject = "REJECT";

    public int status;
    @JsonProperty("request_id")
    public String requestID;
    public String action; //[APPROVE, REJECT]
    public String error;

    public CallBackResponse(int status, String requestID, String action, String errorInfo) {
        this.status = status;
        this.requestID = requestID;
        this.action = action;
        this.error = errorInfo;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRequestID() {
        return this.requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getError() {
        return this.error;
    }

    public void setError(String errorInfo) {
        this.error = errorInfo;
    }

    @Override
    public String toString() {
        return status + " - " + requestID + " - " + action + " - " + error;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class KeyGenDetail implements Serializable{
    public int threshold;

    @JsonProperty("node_ids")
    public String[] nodeIDs;

    public int curve;

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String[] getNodeIDs() {
        return nodeIDs;
    }

    public void setNodeIDs(String[] nodeIDs) {
        this.nodeIDs = nodeIDs;
    }

    public int getCurve() {
        return curve;
    }

    public void setCurve(int curve) {
        this.curve = curve;
    }
}

class KeyGenExtraInfo implements Serializable{
    @JsonProperty("cobo_id")
    public String coboID;

    public String getCoboID() {
        return coboID;
    }

    public void setCoboID(String coboID) {
        this.coboID = coboID;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class KeySignDetail implements Serializable{
    @JsonProperty("group_id")
    public String groupID;

    @JsonProperty("root_pub_key")
    public String rootPubKey;

    @JsonProperty("used_node_ids")
    public String[] usedNodeIDs;

    @JsonProperty("bip32_path_list")
    public String[] bip32PathList;

    @JsonProperty("msg_hash_list")
    public String[] msgHashList;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getRootPubKey() {
        return rootPubKey;
    }

    public void setRootPubKey(String rootPubKey) {
        this.rootPubKey = rootPubKey;
    }

    public String[] getUsedNodeIDs() {
        return usedNodeIDs;
    }

    public void setUsedNodeIDs(String[] usedNodeIDs) {
        this.usedNodeIDs = usedNodeIDs;
    }

    public String[] getBip32PathList() {
        return bip32PathList;
    }

    public void setBip32PathList(String[] bip32PathList) {
        this.bip32PathList = bip32PathList;
    }

    public String[] getMsgHashList() {
        return msgHashList;
    }

    public void setMsgHashList(String[] msgHashList) {
        this.msgHashList = msgHashList;
    }
}

enum TransactionType
{
    TRANSACTION_FROM_WEB, // 100
    TRANSACTION_FROM_API, // 102
    TRANSACTION_RBF_API_SPEEDUP, // 103
    TRANSACTION_RBF_WEB_SPEEDUP, // 104
    TRANSACTION_RBF_API_DROP, // 105
    TRANSACTION_RBF_WEB_DROP, // 106

}

enum TransactionOperation
{
    TRANSFER, // 100
    CONTRACT_CALL, // 200
}

class KeySignExtraInfo implements Serializable {
    @JsonProperty("cobo_id")
    public String coboID;
    @JsonProperty("api_request_id")
    public String apiRequestID;
    @JsonProperty("transaction_type")
    public int transactionType; // TransactionTypeEnum

    public int operation; // TransactionOperationEnum

    public String coin;

    public int decimal;

    @JsonProperty("from_address")
    public String fromAddress;
    public String amount ;

    @JsonProperty("to_address")
    public String toAddress;


    @JsonProperty("to_address_details")
    public String toAddressDetails; // json, ToAddressDetail[]
    public long fee;

    @JsonProperty("gas_price")
    public long gasPrice;

    @JsonProperty("gas_limit")
    public long gasLimit;

    @JsonProperty("extra_parameters")
    public String extraParameters; // json

    @JsonProperty("replace_cobo_id")
    public String replaceCoboID;

    @JsonProperty("api_key")
    public String apiKey;
    public String spender;

    @JsonProperty("raw_tx")
    public RawTx[] rawTx; // RawTx
    public String note;

    public String getCoboID() {
        return coboID;
    }

    public void setCoboID(String coboID) {
        this.coboID = coboID;
    }

    public String getApiRequestID() {
        return apiRequestID;
    }

    public void setApiRequestID(String apiRequestID) {
        this.apiRequestID = apiRequestID;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public int getDecimal() {
        return decimal;
    }

    public void setDecimal(int decimal) {
        this.decimal = decimal;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getToAddressDetails() {
        return toAddressDetails;
    }

    public void setToAddressDetails(String toAddressDetails) {
        this.toAddressDetails = toAddressDetails;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public long getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(long gasPrice) {
        this.gasPrice = gasPrice;
    }

    public long getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(long gasLimit) {
        this.gasLimit = gasLimit;
    }

    public String getExtraParameters() {
        return extraParameters;
    }

    public void setExtraParameters(String extraParameters) {
        this.extraParameters = extraParameters;
    }

    public String getReplaceCoboID() {
        return replaceCoboID;
    }

    public void setReplaceCoboID(String replaceCoboID) {
        this.replaceCoboID = replaceCoboID;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSpender() {
        return spender;
    }

    public void setSpender(String spender) {
        this.spender = spender;
    }

    public RawTx[] getRawTx() {
        return rawTx;
    }

    public void setRawTx(RawTx[] rawTx) {
        this.rawTx = rawTx;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

class ToAddressDetail implements Serializable{
    @JsonProperty("to_address")
    public String toAddress;
    public String amount;

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}

class Input implements Serializable {
    @JsonProperty("tx_hash")
    public String txHash;

    @JsonProperty("vout_n")
    public int voutN;

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public int getVoutN() {
        return voutN;
    }

    public void setVoutN(int voutN) {
        this.voutN = voutN;
    }
}

class ExtraParameters implements Serializable {
    @JsonProperty("inputs_to_spend")
    public Input[] inputsToSpend;

    @JsonProperty("inputs_to_exclude")
    public Input[] inputsToExclude;

    public Input[] getInputsToSpend() {
        return inputsToSpend;
    }

    public void setInputsToSpend(Input[] inputsToSpend) {
        this.inputsToSpend = inputsToSpend;
    }

    public Input[] getInputsToExclude() {
        return inputsToExclude;
    }

    public void setInputsToExclude(Input[] inputsToExclude) {
        this.inputsToExclude = inputsToExclude;
    }
}

class RawTx implements Serializable {
    @JsonProperty("raw_tx")
    public String rawTx;

    @JsonProperty("derivation_path")
    public String derivationPath;

    public RawTx() {}

    public RawTx(String rawTx, String derivationPath) {
        this.rawTx = rawTx;
        this.derivationPath = derivationPath;
    }

    public String getRawTx() {
        return rawTx;
    }

    public void setRawTx(String rawTx) {
        this.rawTx = rawTx;
    }

    public String getDerivationPath() {
        return derivationPath;
    }

    public void setDerivationPath(String derivationPath) {
        this.derivationPath = derivationPath;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class KeyReshareDetail implements Serializable{
    @JsonProperty("old_group_id")
    public String oldGroupID;

    @JsonProperty("root_pub_key")
    public String rootPubKey;

    public int curve;

    @JsonProperty("used_node_ids")
    public String[] usedNodeIDs;

    @JsonProperty("old_threshold")
    public int oldThreshold;

    @JsonProperty("new_threshold")
    public int newThreshold;

    @JsonProperty("new_node_ids")
    public String[] newNodeIDs;

    public String getOldGroupID() {
        return oldGroupID;
    }

    public void setOldGroupID(String oldGroupID) {
        this.oldGroupID = oldGroupID;
    }

    public String getRootPubKey() {
        return rootPubKey;
    }

    public void setRootPubKey(String rootPubKey) {
        this.rootPubKey = rootPubKey;
    }

    public int getCurve() {
        return curve;
    }

    public void setCurve(int curve) {
        this.curve = curve;
    }

    public String[] getUsedNodeIDs() {
        return usedNodeIDs;
    }

    public void setUsedNodeIDs(String[] usedNodeIDs) {
        this.usedNodeIDs = usedNodeIDs;
    }

    public int getOldThreshold() {
        return oldThreshold;
    }

    public void setOldThreshold(int oldThreshold) {
        this.oldThreshold = oldThreshold;
    }

    public int getNewThreshold() {
        return newThreshold;
    }

    public void setNewThreshold(int newThreshold) {
        this.newThreshold = newThreshold;
    }

    public String[] getNewNodeIDs() {
        return newNodeIDs;
    }

    public void setNewNodeIDs(String[] newNodeIDs) {
        this.newNodeIDs = newNodeIDs;
    }
}

class KeyReshareExtraInfo implements Serializable{

    @JsonProperty("cobo_id")
    public String coboID;

    public String getCoboID() {
        return coboID;
    }

    public void setCoboID(String coboID) {
        this.coboID = coboID;
    }
}
