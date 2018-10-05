package am.soso.core.models;

/**
 * Created by Garik Kalashyan on 19-Feb-18.
 */
public class MessageDto {
    private Integer id;
    private String eng;
    private String hay;
    private String globkey;

    public MessageDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getHay() {
        return hay;
    }

    public void setHay(String hay) {
        this.hay = hay;
    }

    public String getGlobkey() {
        return globkey;
    }

    public void setGlobkey(String globkey) {
        this.globkey = globkey;
    }
}
