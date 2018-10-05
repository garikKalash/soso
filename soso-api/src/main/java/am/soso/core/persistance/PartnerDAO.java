package am.soso.core.persistance;

import am.soso.core.models.*;
import am.soso.core.service.BaseSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Garik Kalashyan on 3/8/2017.
 */

@Repository
public class PartnerDAO {
    private final String GET_PARTNER_BY_ID_QUERY = "SELECT * FROM public.Partner WHERE id= :id";
    private final String GET_PARTNER_BY_TELEPHONE_QUERY = "SELECT * FROM public.Partner WHERE telephone= :telephone";
    private final String GET_PARTNER_BY_USERNAME_QUERY = "SELECT * FROM public.Partner WHERE username= :username";
    private final String GET_PHOTO_BY_ID_QUERY = "SELECT image_path FROM public.f_partnerimages WHERE id= :id";
    private final String DELETE_PHOTO_BY_ID_QUERY = "DELETE FROM public.f_partnerimages WHERE id= :id";
    private final String DELETE_RESERVATION_BY_ID_QUERY = "DELETE FROM public.f_partnerrequests WHERE id= :id";
    private final String GET_PARTNERS_BY_SERVICE_ID_QUERY = "SELECT * FROM public.Partner WHERE serviceid= :serviceid";
    private final String GET_FEEDBACKS_PARTNER_BY_ID_QUERY = "SELECT * FROM public.f_partnerfeedbacks WHERE partnerid = :partnerid";
    private final String GET_RESERVATIONS_BY_PARTNER_ID_QUERY = "SELECT * FROM public.f_partnerrequests WHERE partnerid = :partnerid AND status = :status";
    private final String GET_RESERVATIONS_BY_CLIENT_ID_QUERY = "SELECT * FROM public.f_partnerrequests WHERE clientid = :clientid AND status = :status";
    private final String GET_IMAGES_PARTNER_BY_ID_QUERY = "SELECT * FROM public.f_partnerimages WHERE partner_id = :partnerid";
    private final String SIGN_IN_PARTNER_QUERY = "SELECT id FROM public.Partner WHERE password = :password AND telephone = :telephone";
    private final String UPDATE_LOGO_SRC_PATH_OF_PARTNER_QUERY = "UPDATE public.Partner SET imgid = :imgid WHERE id = :id";
    private final String UPDATE_TELEPHONE_OF_PARTNER_QUERY = "UPDATE public.Partner SET telephone = :telephone  WHERE id = :id";
    private final String UPDATE_LONG_ADDRESS_OF_PARTNER_QUERY = "UPDATE public.Partner SET address = :address,longitude = :longitude, latitude = :latitude  WHERE id = :id";
    private final String UPDATE_NOTICE_OF_PARTNER_QUERY = "UPDATE public.Partner SET notices = :notice  WHERE id = :id";
    private final String UPDATE_RESERVATION_OF_PARTNER_QUERY = "UPDATE public.f_partnerrequests SET clientid = :clientid, partnerid = :partnerid, starttime = :starttime, description = :description, status = :status, responsetext = :responsetext, duration = :duration, serviceid = :serviceid   WHERE id = :id";
    private final String UPDATE_SERVIC_DETAIL_FOR_PARTNER_OF_PARTNER_QUERY = "UPDATE public.f_partnersubservices SET serviceid = :serviceid, defaultduration = :defaultduration, price = :price, partnerid = :partnerid   WHERE id = :id";
    private final String LOAD_ALL_PARTNERS_QUERY = "SELECT * FROM public.Partner";
    private final String LOAD_ALL_FOLLOWERS_BY_PARTNER_ID_QUERY = "SELECT * FROM public.f_partnerfollowers WHERE partnerid = :partnerid";
    private final String LOAD_ALL_FOLLOWERS_BY_CLIENT_ID_QUERY = "SELECT * FROM public.f_partnerfollowers WHERE clientid = :clientid";
    private final String DELETE_FOLLOWER_BY_CLIENT_PARTNER_ID_QUERY = "DELETE FROM public.f_partnerfollowers WHERE clientid = :clientid AND partnerid = :partnerid";
    private final String GET_FOLLOWER_BY_ID_QUERY = "DELETE FROM public.f_partnerfollowers WHERE id = :id";
    private final String GET_PARTNERS_BY_SERVICE_SEARCH_ID_QUERY = "SELECT * FROM public.partner WHERE serviceid = :_serviceid AND username LIKE %:_searchterm%";


    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    public NamedParameterJdbcOperations getNamedParameterJdbcOperations() {
        return namedParameterJdbcOperations;
    }

    public Integer addPartner(Partner item) {
        String createUserQuery = "SELECT addpartner ( :_name,:_telephone,:_isAdmin,:_serviceId,:_username,:_password,:_reservable,:_working_start_date,:_working_end_date)";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("_name", item.getName());
        paramMap.put("_telephone", item.getTelephone());
        paramMap.put("_isAdmin", false);
        paramMap.put("_serviceId", item.getServiceId());
        paramMap.put("_username", item.getUsername());
        paramMap.put("_password", BaseSecurity.getEncodedVersion(item.getPassword()));
        paramMap.put("_reservable", item.isReservable());
        paramMap.put("_working_start_date", item.isReservable());
        paramMap.put("_working_end_date", item.isReservable());

        return getNamedParameterJdbcOperations().queryForObject(createUserQuery, paramMap, Integer.class);

    }

    public Integer addPhotoToPartnier(Integer partnerId, String imgPath) {
        String addPhotoQuery = "SELECT addphototopartnier ( :partnerId, :imgPath)";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("partnerId", partnerId);
        paramMap.put("imgPath", imgPath);


        return getNamedParameterJdbcOperations().queryForObject(addPhotoQuery, paramMap, Integer.class);

    }


    public Integer addServiceToPartnier(Integer partnerId, Integer serviceId, Integer defaultduration, Integer price) {
        String addPhotoQuery = "SELECT addservicedetailtopartnier ( :serviceId, :defaultduration, :price, :partnerId)";

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("partnerId", partnerId);
        paramMap.put("serviceId", serviceId);
        paramMap.put("defaultduration", defaultduration);
        paramMap.put("price", price);

        return getNamedParameterJdbcOperations().queryForObject(addPhotoQuery, paramMap, Integer.class);

    }

    public Integer updateServiceDetailForPartner(PartnerServiceDetail partnerServiceDetail) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", partnerServiceDetail.getId());
        paramMap.put("partnerid", partnerServiceDetail.getPartnerId());
        paramMap.put("serviceid", partnerServiceDetail.getServiceId());
        paramMap.put("defaultduration", partnerServiceDetail.getDefaultduration());
        paramMap.put("price", partnerServiceDetail.getPrice());
        return getNamedParameterJdbcOperations().update(UPDATE_SERVIC_DETAIL_FOR_PARTNER_OF_PARTNER_QUERY, paramMap);
    }

    public Integer setRatedFlagTrue(Integer requestId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("requestid", requestId);
        return getNamedParameterJdbcOperations().update("UPDATE public.f_partnerrequests SET israted = TRUE WHERE id = :requestid", paramMap);
    }

    public List<PartnerServiceDetail> getAllServicesByPartner(Integer partnerId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("partnerid", partnerId);
        return getNamedParameterJdbcOperations().query("SELECT * FROM public.f_partnersubservices WHERE partnerid = :partnerid ", paramMap, new BeanPropertyRowMapper<>(PartnerServiceDetail.class));
    }

    public Integer deletePartnerService(Integer itemId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", itemId);
        return getNamedParameterJdbcOperations().update("DELETE FROM public.f_partnersubservices WHERE id = :id", paramMap);
    }


    public List<Partner> getAllPartners() {
        return getNamedParameterJdbcOperations().query(LOAD_ALL_PARTNERS_QUERY, new BeanPropertyRowMapper<>(Partner.class));
    }


    public Integer signin(String telephone, String password) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("telephone", telephone);
            paramMap.put("password", BaseSecurity.getEncodedVersion(password));
            return getNamedParameterJdbcOperations().queryForObject(SIGN_IN_PARTNER_QUERY, paramMap, Integer.class);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public void updateLogosrcPathOfPartner(Integer partnerId, Integer imgid) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", partnerId);
        paramMap.put("imgid", imgid);
        getNamedParameterJdbcOperations().update(UPDATE_LOGO_SRC_PATH_OF_PARTNER_QUERY, paramMap);
    }

    public void saveEditedMainInfoOfPartner(Integer partnerId, String editedTelephone) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", partnerId);
        if (editedTelephone != null && !editedTelephone.isEmpty()) {
            paramMap.put("telephone", editedTelephone);
            getNamedParameterJdbcOperations().update(UPDATE_TELEPHONE_OF_PARTNER_QUERY, paramMap);
        }
    }

    public void saveEditedAddress(Integer partnerId, BigDecimal latitude, BigDecimal lotitude, String address) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", partnerId);
        paramMap.put("address", address);
        paramMap.put("latitude", latitude);
        paramMap.put("longitude", lotitude);
        getNamedParameterJdbcOperations().update(UPDATE_LONG_ADDRESS_OF_PARTNER_QUERY, paramMap);

    }

    public void saveEditedNotice(Integer partnerId, String notice) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", partnerId);
        paramMap.put("notice", notice);
        getNamedParameterJdbcOperations().update(UPDATE_NOTICE_OF_PARTNER_QUERY, paramMap);

    }

    public Partner getPartnerById(Integer partnerId) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", partnerId);
            return getNamedParameterJdbcOperations().queryForObject(GET_PARTNER_BY_ID_QUERY, paramMap, new BeanPropertyRowMapper<>(Partner.class));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Partner getPartnerByTelephone(String telephone) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("telephone", telephone);
            return getNamedParameterJdbcOperations().queryForObject(GET_PARTNER_BY_TELEPHONE_QUERY, paramMap, new BeanPropertyRowMapper<>(Partner.class));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }


    public Partner getPartnerByUsername(String username) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("username", username);
            return getNamedParameterJdbcOperations().queryForObject(GET_PARTNER_BY_USERNAME_QUERY, paramMap, new BeanPropertyRowMapper<>(Partner.class));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }


    public Partner getPartnerMainDetailsById(Integer partnerId) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", partnerId);
            return getNamedParameterJdbcOperations().queryForObject("SELECT name,telephone,address,serviceid,imgid FROM public.Partner WHERE id= :id", paramMap, new BeanPropertyRowMapper<>(Partner.class));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public List<Feedback> loadFeedbacksByPartnerId(Integer partnerId) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("partnerid", partnerId);
            return getNamedParameterJdbcOperations().query(GET_FEEDBACKS_PARTNER_BY_ID_QUERY, paramMap, new BeanPropertyRowMapper<>(Feedback.class));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public List<PhotoDto> loadPhotosByPartnerId(Integer partnerId) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("partnerid", partnerId);
            return getNamedParameterJdbcOperations().query(GET_IMAGES_PARTNER_BY_ID_QUERY, paramMap, new BeanPropertyRowMapper<>(PhotoDto.class));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public List<Partner> getPartnersByServiceId(Integer serviceId) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("serviceid", serviceId);
            return getNamedParameterJdbcOperations().query(GET_PARTNERS_BY_SERVICE_ID_QUERY, paramMap, new BeanPropertyRowMapper<>(Partner.class));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public List<Partner> getPartnersByServiceTermId(Integer serviceId, String term) {
        try {
            StringBuilder builder = new StringBuilder("SELECT * FROM public.partner WHERE serviceid =")
                    .append(serviceId)
                    .append(" AND username LIKE ")
                    .append("'%")
                    .append(term)
                    .append("%'");

            return getNamedParameterJdbcOperations().query(builder.toString(), new BeanPropertyRowMapper<>(Partner.class));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public String getPhotoById(Integer photoId) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", photoId);
            return getNamedParameterJdbcOperations().queryForObject(GET_PHOTO_BY_ID_QUERY, paramMap, String.class);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Integer deletePhotoById(Integer photoId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", photoId);
        return getNamedParameterJdbcOperations().update(DELETE_PHOTO_BY_ID_QUERY, paramMap);
    }

    public List<Request> getAllRequests() {
        try {
            return getNamedParameterJdbcOperations().query("SELECT * FROM f_partnerrequests WHERE status = 1", new BeanPropertyRowMapper<>(Request.class));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }


    public Integer addReservation(Request request) {
        try {
            String addReservationQuery = "SELECT addreservetopartnier ( :clientid, :partnerid, :starttime, :description, :status, :responsetext, :duration, :serviceid)";

            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("clientid", request.getClientId());
            paramMap.put("partnerid", request.getPartnerId());
            paramMap.put("starttime", request.getStartTime());
            paramMap.put("description", request.getDescription());
            paramMap.put("status", request.getStatus());
            paramMap.put("responsetext", request.getResponseText());
            paramMap.put("duration", request.getDuration());
            paramMap.put("serviceid", request.getServiceId());

            return getNamedParameterJdbcOperations().queryForObject(addReservationQuery, paramMap, Integer.class);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public List<Request> getReservationsByPartnerId(Integer partnerId, Integer status) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("partnerid", partnerId);
            paramMap.put("status", status);
            return getNamedParameterJdbcOperations().query(GET_RESERVATIONS_BY_PARTNER_ID_QUERY, paramMap, new BeanPropertyRowMapper<>(Request.class));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public List<Request> getReservationsByClientId(Integer clientId, Integer status) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("clientid", clientId);
            paramMap.put("status", status);
            return getNamedParameterJdbcOperations().query(GET_RESERVATIONS_BY_CLIENT_ID_QUERY, paramMap, new BeanPropertyRowMapper<>(Request.class));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Integer deleteReservationById(Integer reserveId) {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", reserveId);
        return getNamedParameterJdbcOperations().update(DELETE_RESERVATION_BY_ID_QUERY, paramMap);
    }

    public void updateReservation(Request request) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", request.getId());
        paramMap.put("clientid", request.getClientId());
        paramMap.put("partnerid", request.getPartnerId());
        paramMap.put("starttime", request.getStartTime());
        paramMap.put("description", request.getDescription());
        paramMap.put("status", request.getStatus());
        paramMap.put("responsetext", request.getResponseText());
        paramMap.put("duration", request.getDuration());
        paramMap.put("serviceid", request.getServiceId());
        getNamedParameterJdbcOperations().update(UPDATE_RESERVATION_OF_PARTNER_QUERY, paramMap);
    }


    public void deleteFollowerByClientPartnerId(Integer clientId, Integer partnerId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("clientid", clientId);
        paramMap.put("partnerid", partnerId);
        getNamedParameterJdbcOperations().update(DELETE_FOLLOWER_BY_CLIENT_PARTNER_ID_QUERY, paramMap);
    }

    public Integer addFollowerToPartnier(Integer partnerId, Integer clientId) {
        try {
            String addFollower = "SELECT addfollower ( :partnerId, :clientId)";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("partnerId", partnerId);
            paramMap.put("clientId", clientId);
            return getNamedParameterJdbcOperations().queryForObject(addFollower, paramMap, Integer.class);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public List<Follower> getFollowersByPartnerId(Integer partnerId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("partnerid", partnerId);
        return getNamedParameterJdbcOperations().query(LOAD_ALL_FOLLOWERS_BY_PARTNER_ID_QUERY, paramMap, new BeanPropertyRowMapper<>(Follower.class));
    }

    public List<Follower> getFollowersByClientId(Integer clientId) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("clientid", clientId);
            return getNamedParameterJdbcOperations().query(LOAD_ALL_FOLLOWERS_BY_CLIENT_ID_QUERY, paramMap, new BeanPropertyRowMapper<>(Follower.class));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Follower getFollowerById(Integer id) {
        try {
            Map<String, Object> paramMap = new HashMap();
            paramMap.put("id", id);
            return getNamedParameterJdbcOperations().queryForObject(GET_FOLLOWER_BY_ID_QUERY, paramMap, new BeanPropertyRowMapper<>(Follower.class));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Integer addFeedbackToPartner(Feedback feedback) {
        try {
            String addFeedbackQuery = "SELECT addfeedback (:_context, :_rate, :_partner_id, :_client_id, :_requestid)";
            Map<String, Object> paramMap = new HashMap();
            paramMap.put("_context", feedback.getContext());
            paramMap.put("_rate", feedback.getRate());
            paramMap.put("_partner_id", feedback.getPartnerId());
            paramMap.put("_client_id", feedback.getClientId());
            paramMap.put("_requestid", feedback.getRequestId());
            return getNamedParameterJdbcOperations().queryForObject(addFeedbackQuery, paramMap, Integer.class);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

}
