package am.soso.core.models;

import java.math.BigDecimal;

public class Address {
    private Integer partnerId;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String address;

    public Address(){

    }

    public Address(Integer partnerId, BigDecimal longitude, BigDecimal latitude, String address) {
        this.partnerId = partnerId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
