package am.soso.core.models;

/**
 * Created by Garik Kalashyan on 5/4/2017.
 */
public class PhotoDto {
    private Integer id;
    private Integer partner_id;
    private String image_path;

    public PhotoDto() {
    }

    public PhotoDto(Integer id, Integer partner_id, String image_path) {
        this.id = id;
        this.partner_id = partner_id;
        this.image_path = image_path;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPartner_id() {
        return partner_id;
    }

    public void setPartner_id(Integer partner_id) {
        this.partner_id = partner_id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
