package am.soso.core.api.controller;

import am.soso.core.models.Client;
import am.soso.core.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin("*")
@Controller
@RequestMapping("client")
public class ClientRequestController {

    @Autowired
    private ClientService clientService;


    @RequestMapping(value = "/clientaccount/{clientid}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<Client> getClientById(@PathVariable(value = "clientid") Integer clientId) {
        Client client = clientService.getClientById(clientId);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }

    @RequestMapping(value = "/addclient", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> addClient(@RequestBody Client client) {
        return new ResponseEntity<>(clientService.addClient(client), HttpStatus.OK);
    }

    @RequestMapping(value = "/clientmaindetails/{clientid}", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Client> getClientMainDetailsById(@PathVariable(value = "clientid") Integer clientId) {
        Client client = clientService.getClientMainDetailsById(clientId);
        return new ResponseEntity<>(client, HttpStatus.OK);
    }
}
