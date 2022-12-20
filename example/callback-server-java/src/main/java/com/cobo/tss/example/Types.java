package com.cobo.tss.example;

import java.io.Serializable;
import java.security.PublicKey;

class CallBackRequest implements Serializable {
    public int request_type;

    public String request_id;

    public String request_detail;

    public String extra_info;

    public int getRequest_type() {
        return request_type;
    }

    public void setRequest_type(int request_type) {
        this.request_type = request_type;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getRequest_detail() {
        return request_detail;
    }

    public void setRequest_detail(String request_detail) {
        this.request_detail = request_detail;
    }

    public String getExtra_info() {
        return extra_info;
    }

    public void setExtra_info(String extra_info) {
        this.extra_info = extra_info;
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

class KeyGenDetail implements Serializable{
    public int threshold;

    public String[] node_ids;

    public int curve;

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

    public int getCurve() {
        return curve;
    }

    public void setCurve(int curve) {
        this.curve = curve;
    }
}

class KeyGenExtraInfo implements Serializable{
    public String cobo_id;

    public String getCobo_id() {
        return cobo_id;
    }

    public void setCobo_id(String cobo_id) {
        this.cobo_id = cobo_id;
    }
}

class KeySignDetail implements Serializable{
    public String root_pub_key;

    public String[] used_node_ids;

    public String[] bip32_path_list;

    public String[] msg_hash_list;

    public String getRoot_pub_key() {
        return root_pub_key;
    }

    public void setRoot_pub_key(String root_pub_key) {
        this.root_pub_key = root_pub_key;
    }

    public String[] getUsed_node_ids() {
        return used_node_ids;
    }

    public void setUsed_node_ids(String[] used_node_ids) {
        this.used_node_ids = used_node_ids;
    }

    public String[] getBip32_path_list() {
        return bip32_path_list;
    }

    public void setBip32_path_list(String[] bip32_path_list) {
        this.bip32_path_list = bip32_path_list;
    }

    public String[] getMsg_hash_list() {
        return msg_hash_list;
    }

    public void setMsg_hash_list(String[] msg_hash_list) {
        this.msg_hash_list = msg_hash_list;
    }
}

enum TransactionType
{
    WITHDRAW;
}

class KeySignExtraInfo implements Serializable {
    public String cobo_id;
    public String api_request_id;
    public String transaction_type;
    public String coin;
    public String from_address;
    public String amount ;
    public String to_address;
    public String to_address_details; // json, ToAddressDetail[]
    public long fee;
    public long gas_price;
    public long gas_limit;
    public String extra_parameters; // json
    public String replace_cobo_id;
    public String api_key;
    public String spender;
    public RawTx[] raw_tx; // RawTx
    public String note;

    public String getCobo_id() {
        return cobo_id;
    }

    public void setCobo_id(String cobo_id) {
        this.cobo_id = cobo_id;
    }

    public String getApi_request_id() {
        return api_request_id;
    }

    public void setApi_request_id(String api_request_id) {
        this.api_request_id = api_request_id;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    public String getTo_address_details() {
        return to_address_details;
    }

    public void setTo_address_details(String to_address_details) {
        this.to_address_details = to_address_details;
    }

    public long getFee() {
        return fee;
    }

    public void setFee(long fee) {
        this.fee = fee;
    }

    public long getGas_price() {
        return gas_price;
    }

    public void setGas_price(long gas_price) {
        this.gas_price = gas_price;
    }

    public long getGas_limit() {
        return gas_limit;
    }

    public void setGas_limit(long gas_limit) {
        this.gas_limit = gas_limit;
    }

    public String getExtra_parameters() {
        return extra_parameters;
    }

    public void setExtra_parameters(String extra_parameters) {
        this.extra_parameters = extra_parameters;
    }

    public String getReplace_cobo_id() {
        return replace_cobo_id;
    }

    public void setReplace_cobo_id(String replace_cobo_id) {
        this.replace_cobo_id = replace_cobo_id;
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

    public RawTx[] getRaw_tx() {
        return raw_tx;
    }

    public void setRaw_tx(RawTx[] raw_tx) {
        this.raw_tx = raw_tx;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

class ToAddressDetail implements Serializable{
    public String to_address;
    public String amount;

    public String getTo_address() {
        return to_address;
    }

    public void setTo_address(String to_address) {
        this.to_address = to_address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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
    public Input[] inputs_to_spend;
    public Input[] inputs_to_exclude;

    public Input[] getInputs_to_spend() {
        return inputs_to_spend;
    }

    public void setInputs_to_spend(Input[] inputs_to_spend) {
        this.inputs_to_spend = inputs_to_spend;
    }

    public Input[] getInputs_to_exclude() {
        return inputs_to_exclude;
    }

    public void setInputs_to_exclude(Input[] inputs_to_exclude) {
        this.inputs_to_exclude = inputs_to_exclude;
    }
}

class RawTx implements Serializable {
    public String raw_tx;

    public RawTx() {}

    public RawTx(String raw_tx) {
        this.raw_tx = raw_tx;
    }

    public String getRaw_tx() {
        return raw_tx;
    }

    public void setRaw_tx(String raw_tx) {
        this.raw_tx = raw_tx;
    }
}

class KeyReshareDetail implements Serializable{
    public String root_pub_key;

    public int curve;

    public String[] used_node_ids;

    public int old_threshold;

    public int new_threshold;

    public String[] new_node_ids;

    public String getRoot_pub_key() {
        return root_pub_key;
    }

    public void setRoot_pub_key(String root_pub_key) {
        this.root_pub_key = root_pub_key;
    }

    public int getCurve() {
        return curve;
    }

    public void setCurve(int curve) {
        this.curve = curve;
    }

    public String[] getUsed_node_ids() {
        return used_node_ids;
    }

    public void setUsed_node_ids(String[] used_node_ids) {
        this.used_node_ids = used_node_ids;
    }

    public int getOld_threshold() {
        return old_threshold;
    }

    public void setOld_threshold(int old_threshold) {
        this.old_threshold = old_threshold;
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

class KeyReshareExtraInfo implements Serializable{
    public String cobo_id;

    public String getCobo_id() {
        return cobo_id;
    }

    public void setCobo_id(String cobo_id) {
        this.cobo_id = cobo_id;
    }
}
