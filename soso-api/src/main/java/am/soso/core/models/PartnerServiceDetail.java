package am.soso.core.models;

/**
 * Created by Garik Kalashyan on 4/27/2017.
 */
public class PartnerServiceDetail {
    private Integer id;
    private Integer serviceId;
    private Integer partnerId;
    private Integer defaultduration;
    private Integer price;

    public PartnerServiceDetail() {
    }

    public PartnerServiceDetail(Integer id, Integer serviceId, Integer partnerId, Integer defaultduration, Integer price) {
        this.id = id;
        this.serviceId = serviceId;
        this.partnerId = partnerId;
        this.defaultduration = defaultduration;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getDefaultduration() {
        return defaultduration;
    }

    public void setDefaultduration(Integer defaultduration) {
        this.defaultduration = defaultduration;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
