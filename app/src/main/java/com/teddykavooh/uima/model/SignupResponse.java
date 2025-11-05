package com.teddykavooh.uima.model;

public class SignupResponse {
    private String message;
    private boolean success;
    private int code;
    private SignupData data;

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public SignupData getData() {
        return data;
    }

    // Inner class for the 'data' field(key)
    public static class SignupData {
        private int proceed;
        private String message;

        public int getProceed() {
            return proceed;
        }

        public String getMessage() {
            return message;
        }
    }
}
