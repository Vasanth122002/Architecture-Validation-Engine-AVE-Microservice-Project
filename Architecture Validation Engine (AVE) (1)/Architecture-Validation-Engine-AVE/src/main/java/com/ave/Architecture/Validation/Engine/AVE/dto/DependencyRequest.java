package com.ave.Architecture.Validation.Engine.AVE.dto;

public class DependencyRequest {
    private Long requesterId;
    private Long providerId;

    public Long getRequesterId() { return requesterId; }
    public void setRequesterId(Long requesterId) { this.requesterId = requesterId; }
    public Long getProviderId() { return providerId; }
    public void setProviderId(Long providerId) { this.providerId = providerId; }
}
