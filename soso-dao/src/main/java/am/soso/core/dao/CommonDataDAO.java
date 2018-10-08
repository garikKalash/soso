package am.soso.core.dao;

import am.soso.core.models.MessageDto;
import am.soso.core.models.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Garik Kalashyan on 3/8/2017.
 */

@Repository
public class CommonDataDAO {
    private final String QUERY_FOR_LOADING_SERVICES = "SELECT * FROM public.C_Service WHERE parentid = :parentid";
    private final String GET_SERVICE_BY_ID = "SELECT * FROM public.c_service WHERE id=:_id";
    private final String UPDATE_LOGO_SRC_PATH_OF_SERVICE_QUERY = "UPDATE public.c_service SET imgpath = :imgpath WHERE id = :id";

    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    public NamedParameterJdbcOperations getNamedParameterJdbcOperations() {
        return namedParameterJdbcOperations;
    }

    public Integer createService(Service item) {
        String createUserQuery = "SELECT addservice ( :_servicename_arm, :_parentid ,:_servicename_eng)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("_servicename_arm", item.getHay());
        paramMap.put("_parentid",item.getParentid());
        paramMap.put("_servicename_eng", item.getEng());
        return getNamedParameterJdbcOperations().queryForObject(createUserQuery, paramMap, Integer.class);
    }

    public boolean deleteServiceWithSubServices(Integer serviceId) {
        try {
            String deleteServiceQuery = "SELECT deleteservice ( :_serviceId)";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("_serviceId", serviceId);
            getNamedParameterJdbcOperations().update(deleteServiceQuery, paramMap);
            return true;
        }catch (EmptyResultDataAccessException e){
            return false;
        }
    }


    public List<Service> getServicesByParent(Integer parentId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("parentid", parentId);
        return getNamedParameterJdbcOperations().query(QUERY_FOR_LOADING_SERVICES, paramMap, new BeanPropertyRowMapper<>(Service.class));

    }

    public List<Service> getServices() {
        return getNamedParameterJdbcOperations().query("SELECT * FROM public.c_service", new BeanPropertyRowMapper<>(Service.class));
    }

    public List<MessageDto> getMessages(){
        List<MessageDto> messageDtos = getNamedParameterJdbcOperations().query("SELECT * FROM public.c_messages",new BeanPropertyRowMapper<>(MessageDto.class));
        List<MessageDto> serviceNames = getNamedParameterJdbcOperations().query("SELECT * FROM public.c_service", new BeanPropertyRowMapper<>(MessageDto.class));
        messageDtos.addAll(serviceNames);
        return messageDtos;
    }

    public MessageDto getMessageById(Integer id){
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("messageId", id);
        return getNamedParameterJdbcOperations().queryForObject("Select * FROM public.c_messages WHERE id=:messageId",paramMap,new BeanPropertyRowMapper<>(MessageDto.class));
    }

    public Integer addMessage(MessageDto messageDto) {
        String addMessageQuery = "SELECT addmessage ( :_eng, :_hay)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("_eng", messageDto.getEng());
        paramMap.put("_hay",messageDto.getHay());
        return getNamedParameterJdbcOperations().queryForObject(addMessageQuery, paramMap, Integer.class);
    }

    public Integer deleteMessageById(Integer messageId) {
        String deleteMessageQuery = "SELECT deletemessage ( :_messageId)";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("_messageId", messageId);
        return getNamedParameterJdbcOperations().queryForObject(deleteMessageQuery, paramMap, Integer.class);

    }

    public String getImgPathOfService(Integer serviceId) {
       try {
           Map<String, Object> paramMap = new HashMap<>();
           paramMap.put("serviceid", serviceId);
           return getNamedParameterJdbcOperations().queryForObject("SELECT imgpath FROM c_service WHERE id=:serviceid", paramMap, String.class);
       }catch (EmptyResultDataAccessException ex){
           return null;
       }
    }

    public String getImgPathWithId(Integer id) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", id);
            return getNamedParameterJdbcOperations().queryForObject("SELECT path FROM c_commonimages WHERE id=:id", paramMap, String.class);
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Service getServiceById(Integer serviceId) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("_id", serviceId);
            return getNamedParameterJdbcOperations().queryForObject(GET_SERVICE_BY_ID, params, new BeanPropertyRowMapper<>(Service.class));
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public void updateLogoOfService(Integer serviceId, String path) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", serviceId);
        paramMap.put("imgpath", path);
        getNamedParameterJdbcOperations().update(UPDATE_LOGO_SRC_PATH_OF_SERVICE_QUERY, paramMap);

    }

}
