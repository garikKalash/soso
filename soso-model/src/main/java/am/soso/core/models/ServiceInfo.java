package am.soso.core.models;

/**
 * Created by Garik Kalashyan on 3/12/2017.
 */
public class ServiceInfo {
    private String url;
    private String serviceUniqueName;
    private Integer serviceId;
    private DBConnectionDto dbConnectionMetaData;

    public ServiceInfo(String url, String serviceUniqueName, DBConnectionDto dbConnectionMetaData,Integer serviceId) {
        this.url = url;
        this.serviceUniqueName = serviceUniqueName;
        this.serviceId = serviceId;
        this.dbConnectionMetaData = dbConnectionMetaData;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getServiceUniqueName() {
        return serviceUniqueName;
    }

    public void setServiceUniqueName(String serviceUniqueName) {
        this.serviceUniqueName = serviceUniqueName;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public DBConnectionDto getDbConnectionMetaData() {
        return dbConnectionMetaData;
    }

    public void setDbConnectionMetaData(DBConnectionDto dbConnectionMetaData) {
        this.dbConnectionMetaData = dbConnectionMetaData;
    }
}
