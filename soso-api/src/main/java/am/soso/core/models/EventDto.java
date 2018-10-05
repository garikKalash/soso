package am.soso.core.models;

import java.util.Date;

/**
 * Created by Garik Kalashyan on 4/28/2017.
 */
public class EventDto {
    private Integer id;
    private Integer partnerId;
    private Integer clientId;
    private Integer requestId;
    private boolean isSeen;
    private Date seenTime;

    public EventDto(){

    }

    public EventDto(Integer id, Integer partnerId, Integer clientId, Integer requestId, boolean isSeen, Date seenTime) {
        this.id = id;
        this.partnerId = partnerId;
        this.clientId = clientId;
        this.requestId = requestId;
        this.isSeen = isSeen;
        this.seenTime = seenTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public boolean isSeen() {
        return isSeen;
    }

    public void setSeen(boolean seen) {
        isSeen = seen;
    }

    public Date getSeenTime() {
        return seenTime;
    }

    public void setSeenTime(Date seenTime) {
        this.seenTime = seenTime;
    }
}
