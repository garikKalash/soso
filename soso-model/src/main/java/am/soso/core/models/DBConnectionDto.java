package am.soso.core.models;

import javax.validation.constraints.NotNull;

/**
 * Created by Garik Kalashyan on 3/9/2017.
 */
public class DBConnectionDto {
    private String driverClassName;
    private String url;
    private String username;
    private String password;


    public DBConnectionDto(@NotNull String driverClassName, @NotNull String url, @NotNull String username, @NotNull String password) {
        this.driverClassName = driverClassName;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @NotNull
    public String getDriverClassName() {
        return driverClassName;
    }

    @NotNull
    public String getUrl() {
        return url;
    }

    @NotNull
    public String getUsername() {
        return username;
    }

    @NotNull
    public String getPassword() {
        return password;
    }


}
