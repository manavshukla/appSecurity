package com.security.constants;

public enum EndPointEnum {
    CREATE_API("/createLeave");
    private final String api;

    EndPointEnum(String api) {
        this.api = api;
    }

    public String getAPi() {
        return api;
    }
}
