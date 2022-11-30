package com.cobo.tss.example;

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
    public String[] address_list;

    public ListAddressWhitelistResponse(String[] address_list) {
        this.address_list = address_list;
    }

    public String[] getAddress_list() {
        return address_list;
    }

    public void setAddress_list(String[] address_list) {
        this.address_list = address_list;
    }
}