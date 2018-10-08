package am.soso.core.dao;

import am.soso.core.models.Client;
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
public class ClientDAO {
    private final String GET_CLIENT_BY_ID_QUERY = "SELECT * FROM public.Client WHERE id= :id";
    private final String GET_CLIENT_BY_DETAILS_QUERY = "SELECT id FROM public.Client WHERE telephone= :telephone AND password = :password";


    @Autowired
    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    public NamedParameterJdbcOperations getNamedParameterJdbcOperations() {
        return namedParameterJdbcOperations;
    }


    public Client getClientById(Integer partnerId) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("id", partnerId);
            return getNamedParameterJdbcOperations().queryForObject( GET_CLIENT_BY_ID_QUERY, paramMap, new BeanPropertyRowMapper<>(Client.class));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }


    public Client getClientMainDetailsById(Integer partnerId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", partnerId);
        return getNamedParameterJdbcOperations().queryForObject("SELECT name,telephone FROM public.client WHERE id= :id", paramMap, new BeanPropertyRowMapper<>(Client.class));
    }

    public Integer addClient(Client item) {
        try {
            String createUserQuery = "SELECT addclient ( :fname, :telephone)";
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("fname", item.getName());
            paramMap.put("telephone", item.getTelephone());
            return getNamedParameterJdbcOperations().queryForObject(createUserQuery, paramMap, Integer.class);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }

    }

    public List<Client> getClientByTelephone(String telephone) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("telephone", telephone);
            return getNamedParameterJdbcOperations().query("SELECT * FROM public.Client WHERE telephone= :telephone", paramMap, new BeanPropertyRowMapper<>(Client.class));
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public Integer signinClient(String telephone, String password) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("telephone", telephone);
            paramMap.put("password", BaseSecurity.getEncodedVersion(password));
            return getNamedParameterJdbcOperations().queryForObject(GET_CLIENT_BY_DETAILS_QUERY, paramMap, Integer.class);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }
}
