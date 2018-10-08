package am.soso.core.models;

/**
 * Created by Garik Kalashyan on 4/23/2017.
 */
public class Client {
    public Client(){

    }

    public Client(Integer id, String name, String telephone, String password) {
        this.id = id;
        this.name = name;

        this.telephone = telephone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    private Integer id;
    private String name;
    private String telephone;

}
