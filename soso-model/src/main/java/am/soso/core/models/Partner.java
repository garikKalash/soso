package am.soso.core.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Garik Kalashyan on 3/8/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Partner {
    private Integer id;
    private String name;
    private String telephone;
    private boolean isAdmin;
    private Integer serviceId;
    private String username;
    private String password;
    private String notices;
    private BigDecimal longitude;
    private BigDecimal latitude;
    private String address;
    private Integer imgId;
    private String imgpath;
    private List<PhotoDto> photoDtos;
    private List<Feedback> feedbacks;
    private List<String> followers;
    private List<PartnerServiceDetail> services;
    private Date WorkingStartDate;
    private Date WorkingEndDate;

    private boolean reservable;

    public Partner(Integer id, String telephone, String address) {
        this.id = id;
        this.telephone = telephone;
        this.address = address;
    }
}
