package com.cobo.tss.example;

import java.io.Serializable;
import java.security.PublicKey;

class CallBackRequest implements Serializable {
    public int request_type;

    public String request_id;

    public String meta;

    public int getRequest_type() {
        return this.request_type;
    }

    public void setRequest_type(int requestType) {
        this.request_type = requestType;
    }

    public String getRequest_id() {
        return this.request_id;
    }

    public void setRequest_id(String requestID) {
        this.request_id = requestID;
    }

    public String getMeta() {
        return this.meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }
}

class CallBackResponse implements Serializable {
    public static final String ActionApprove = "APPROVE";
    public static final String ActionReject = "REJECT";
    public static final String ActionWait = "WAIT";

    public int status;
    public String request_id;
    public String action; //[APPROVE, REJECT, WAIT]
    public String error;

    public CallBackResponse(int status, String requestID, String action, String errorInfo) {
        this.status = status;
        this.request_id = requestID;
        this.action = action;
        this.error = errorInfo;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRequest_id() {
        return this.request_id;
    }

    public void setRequest_id(String requestID) {
        this.request_id = requestID;
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
        return status + " - " + request_id + " - " + action + " - " + error;
    }
}

class KeyGenMeta implements Serializable{
    public String cobo_id;
    public int threshold;
    public String[] node_ids;

    public String getCobo_id() {
        return cobo_id;
    }

    public void setCobo_id(String cobo_id) {
        this.cobo_id = cobo_id;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public String[] getNode_ids() {
        return node_ids;
    }

    public void setNode_ids(String[] node_ids) {
        this.node_ids = node_ids;
    }
}

class KeySignMeta implements Serializable {
    public String cobo_id;
    public String request_id;
    public String coin;
    public String from_address;
    public String to_address;
    public long amount ;
    public String amount_str;
    public String to_address_details; // json
    public String fee;
    public String extra_parameters; // json
    public String api_key;
    public String spender;
    public String[] node_ids;
    public RawTx[] raw_tx; // RawTx

    public String getCobo_id() {
        return cobo_id;
    }

    public void setCobo_id(String cobo_id) {
        this.cobo_id = cobo_id;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getFrom_address() {
        return from_address;
    }

    public void setFrom_address(String from_address) {
        this.from_address = from_address;
    }

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getAmount_str() {
        return amount_str;
    }

    public void setAmount_str(String amount_str) {
        this.amount_str = amount_str;
    }

    public String getTo_address_details() {
        return to_address_details;
    }

    public void setTo_address_details(String to_address_details) {
        this.to_address_details = to_address_details;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getExtra_parameters() {
        return extra_parameters;
    }

    public void setExtra_parameters(String extra_parameters) {
        this.extra_parameters = extra_parameters;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getSpender() {
        return spender;
    }

    public void setSpender(String spender) {
        this.spender = spender;
    }

    public String[] getNode_ids() {
        return node_ids;
    }

    public void setNode_ids(String[] node_ids) {
        this.node_ids = node_ids;
    }

    public RawTx[] getRaw_tx() {
        return raw_tx;
    }

    public void setRaw_tx(RawTx[] raw_tx) {
        this.raw_tx = raw_tx;
    }
}

class ToAddressDetails implements Serializable{
    public String to_address;
    public int amount;

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}

class Input implements Serializable {
    public String tx_hash;
    public int vout_n;

    public String getTx_hash() {
        return tx_hash;
    }

    public void setTx_hash(String tx_hash) {
        this.tx_hash = tx_hash;
    }

    public int getVout_n() {
        return vout_n;
    }

    public void setVout_n(int vout_n) {
        this.vout_n = vout_n;
    }
}

class ExtraParameters implements Serializable {
    public Input[] inputsToSpend;
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
    public String raw_tx;
    public String[] derivation_path;

    public RawTx() {}

    public RawTx(String raw_tx, String[] derivation_path) {
        this.raw_tx = raw_tx;
        this.derivation_path = derivation_path;
    }

    public RawTx(String raw_tx) {
        this.raw_tx = raw_tx;
    }

    public RawTx(String[] derivation_path) {
        this.derivation_path = derivation_path;
    }

    public String getRaw_tx() {
        return raw_tx;
    }

    public void setRaw_tx(String raw_tx) {
        this.raw_tx = raw_tx;
    }

    public String[] getDerivation_path() {
        return derivation_path;
    }

    public void setDerivation_path(String[] derivation_path) {
        this.derivation_path = derivation_path;
    }
}

class KeyReshareMeta implements Serializable{
    public String cobo_id;
    public int old_threshold;
    public String[] old_node_ids;
    public int new_threshold;
    public String[] new_node_ids;

    public String getCobo_id() {
        return cobo_id;
    }

    public void setCobo_id(String cobo_id) {
        this.cobo_id = cobo_id;
    }

    public int getOld_threshold() {
        return old_threshold;
    }

    public void setOld_threshold(int old_threshold) {
        this.old_threshold = old_threshold;
    }

    public String[] getOld_node_ids() {
        return old_node_ids;
    }

    public void setOld_node_ids(String[] old_node_ids) {
        this.old_node_ids = old_node_ids;
    }

    public int getNew_threshold() {
        return new_threshold;
    }

    public void setNew_threshold(int new_threshold) {
        this.new_threshold = new_threshold;
    }

    public String[] getNew_node_ids() {
        return new_node_ids;
    }

    public void setNew_node_ids(String[] new_node_ids) {
        this.new_node_ids = new_node_ids;
    }
}
