package com.chung.product.mydocument;

import com.amazonaws.client.builder.AwsClientBuilder;

public abstract class MyTextractSuper implements MyTextract{


    private String serviceEndpoint;
    private String signingRegion;

    public abstract String getText(String document, String bucket);

    public abstract String getTextType();

    public AwsClientBuilder.EndpointConfiguration getEndPoint(){
        AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder.EndpointConfiguration(
                                                                    this.serviceEndpoint, this.signingRegion);

        return endpoint;

    }
    public String getServiceEndpoint() {
        return serviceEndpoint;
    }

    public void setServiceEndpoint(String serviceEndpoint) {
        this.serviceEndpoint = serviceEndpoint;
    }

    public String getSigningRegion() {
        return signingRegion;
    }

    public void setSigningRegion(String signingRegion) {
        this.signingRegion = signingRegion;
    }
}
