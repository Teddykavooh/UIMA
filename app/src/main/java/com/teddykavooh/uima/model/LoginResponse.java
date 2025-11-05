package com.teddykavooh.uima.model;

public class LoginResponse {
    private String message;
    private boolean success;
    private int code;
    private LoginData data;

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public LoginData getData() {
        return data;
    }

    // Inner class for the 'data' field(key)
    public static class LoginData {
        private int id;
        private String name;
        private String email;
        private String updated_at;
        private String created_at;
        private String access_token;

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public String getCreated_at() {
            return created_at;
        }

        public String getAccess_token() {
            return access_token;
        }
    }
}
