package am.soso.core.models;

/**
 * Created by Garik Kalashyan on 4/26/2017.
 */
public class Follower {
    private Integer id;
    private Integer clientId;
    private Integer partnerId;

    public Follower() {
    }

    public Follower(Integer id, Integer clientId, Integer partnerId) {
        this.id = id;
        this.clientId = clientId;
        this.partnerId = partnerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }
}
