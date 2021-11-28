package com.prinfosys.news48.Model;

import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;
    @SerializedName("status")
    private Boolean status;
    @SerializedName("message")
    private String message;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
