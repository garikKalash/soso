package am.soso.core.models;

/**
 * Created by Garik Kalashyan on 4/15/2017.
 */
public class Feedback{
    private Integer id;
    private String context;
    private Integer rate;
    private Integer partnerId;
    private Integer clientId;
    private Integer requestId;

    public Feedback(){}

    public Feedback(Integer id, String context, Integer rate,
                    Integer partnerId, Integer clientId,Integer requestId) {
        this.id = id;
        this.context = context;
        this.rate = rate;
        this.partnerId = partnerId;
        this.clientId = clientId;
        this.requestId = requestId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }



}
