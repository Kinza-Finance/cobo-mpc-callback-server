package com.cobo.tss.example;

import com.fasterxml.jackson.annotation.JsonProperty;

class AddressWhitelistRequest {
    public String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

class AddressWhitelistResponse {
    public int status;
    public String error;

    public AddressWhitelistResponse(int status, String error) {
        this.status = status;
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

class ListAddressWhitelistResponse {

    @JsonProperty("address_list")
    public String[] addressList;

    public ListAddressWhitelistResponse(String[] addressList) {
        this.addressList = addressList;
    }

    public String[] getAddressList() {
        return addressList;
    }

    public void setAddressList(String[] addressList) {
        this.addressList = addressList;
    }
}