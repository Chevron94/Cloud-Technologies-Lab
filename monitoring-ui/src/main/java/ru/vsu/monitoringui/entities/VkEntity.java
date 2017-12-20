package ru.vsu.monitoringui.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Roman on 20.12.2017 21:15.
 */
public class VkEntity {
    @JsonProperty(value = "access_token")
    private String accessToken;

    @JsonProperty(value = "expires_in")
    private Integer expiresIn;

    @JsonProperty(value = "user_id")
    private Integer userId;

    public VkEntity() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "VkEntity{" +
                "accessToken='" + accessToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", userId=" + userId +
                '}';
    }
}
