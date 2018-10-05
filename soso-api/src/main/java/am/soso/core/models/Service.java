package am.soso.core.models;

/**
 * Created by Garik Kalashyan on 3/8/2017.
 */
public class Service {
    private Integer id;

    private String hay;

    private String eng;

    private String imgpath;

    private Integer parentid;

    private String globkey;

    public Service(){}

    public Service(Integer id,
                   String serviceName_arm,
                   String eng,
                   String imgpath,
                   Integer parentid,
                   String globkey) {
        this.id = id;
        this.hay = serviceName_arm;
        this.eng = eng;
        this.imgpath = imgpath;
        this.parentid = parentid;
        this.globkey = globkey;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getHay() {
        return hay;
    }

    public void setHay(String hay) {
        this.hay = hay;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getGlobkey() {
        return globkey;
    }

    public void setGlobkey(String globkey) {
        this.globkey = globkey;
    }
}
