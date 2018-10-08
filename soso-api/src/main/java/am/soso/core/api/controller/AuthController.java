package am.soso.core.api.controller;

import am.soso.core.api.validator.PartnerValidator;
import am.soso.core.models.Partner;
import am.soso.core.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Garik.Kalashyan on 12/31/2016.
 */
@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/authenticate")
public class AuthController {

    @Autowired
    private PartnerValidator partnerValidator;

    @Autowired
    private PartnerService partnerService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping(value = "/logout")
    public String logout() {
        return "logout";
    }

    @PostMapping(value = "/signinpartner", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signin(@RequestBody Partner partner,
                                 @RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language,
                                 Errors errors) throws IOException {
        partnerValidator.validateForSignin(partner, language, errors);

        if (!errors.hasErrors()) {

            Partner loadedPartner = partnerService.getPartnerByTelephone(partner.getTelephone());
            if (loadedPartner != null) {
                if (passwordEncoder.matches(partner.getPassword(), loadedPartner.getPassword())) {
                    return new ResponseEntity(loadedPartner, HttpStatus.OK);
                } else {
                    return new ResponseEntity("Incorrect details", HttpStatus.UNAUTHORIZED);
                }

            } else {
                return new ResponseEntity(-1, HttpStatus.NO_CONTENT);
            }
        } else {
            return new ResponseEntity(constructMapFromErrors(errors), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(value = "/signuppartner", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerPartner(@RequestBody Partner partner,
                                          @RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language,
                                          Errors errors) {
        partnerValidator.validateNewPartner(partner, language, errors);
        if (!errors.hasErrors()) {
            Integer newPartnerId = partnerService.addPartner(partner);
            return new ResponseEntity(newPartnerId, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(constructMapFromErrors(errors), HttpStatus.BAD_REQUEST);
        }
    }


    private Map<String, String> constructMapFromErrors(Errors errors) {
        Map<String, String> errorsMap = new HashMap<>();
        errors.getAllErrors().forEach(objectError -> errorsMap.put(objectError.getCode(), objectError.getDefaultMessage()));
        return errorsMap;
    }

}
