package com.security.constants;

public enum ApiEnum {

    SERVER_NAME("/localhost:");

    private final String server;

    ApiEnum(String server) {
        this.server = server;
    }

    public String getAPi() {
        return server;
    }
}

//    localhost:1010/app/leave-request
//            }
