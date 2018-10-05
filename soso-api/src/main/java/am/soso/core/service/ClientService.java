package am.soso.core.service;

import am.soso.core.models.Client;
import am.soso.core.persistance.ClientDAO;
import am.soso.core.validator.ClientDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Garik Kalashyan on 3/8/2017.
 */

@Repository
public class ClientService {


    private final ClientDAO clientDAO;

    @Autowired
    public ClientService(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public Client getClientById(Integer clientId) {
        return clientDAO.getClientById(clientId);
    }

    public Integer addClient(Client client) {

        //todo to be implemented
        return 0;
    }

    public Integer signinClient(String telephone, String password) {
        return clientDAO.signinClient(telephone, password);
    }

    public Client getClientMainDetailsById(Integer clientId) {
        return clientDAO.getClientById(clientId);
    }

    public List<Client> getClientByTelephone(String telephone) {
        return clientDAO.getClientByTelephone(telephone);
    }
}
