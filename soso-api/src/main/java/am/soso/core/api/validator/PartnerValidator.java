package am.soso.core.api.validator;


import am.soso.core.models.Address;
import am.soso.core.models.MessageDto;
import am.soso.core.models.Partner;
import am.soso.core.service.CommonDataService;
import am.soso.core.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class PartnerValidator {

    @Autowired
    private CommonDataService commonDataService;

    @Autowired
    private PartnerService partnerService;

    public void validateMainInfo(Partner partner, String language, Errors errors) {
        if (isInvalidMainInfo(partner)) {
            addMessageToErrors("invalidmaininfo", language, errors);
        }

    }

    public void validateForSignin(Partner partner, String language, Errors errors) {
        if (partner.getTelephone() == null || partner.getTelephone().isEmpty()) {
            addMessageToErrors("shorttelephone", language, errors);

        }
        if (partner.getPassword() == null || partner.getPassword().isEmpty() || partner.getPassword().length() < 3) {
            addMessageToErrors("invalidpassword", language, errors);
        }
    }

    public void validateEditedAddress(Address address, String language, Errors errors) {
        if (isInvalidEditedAddress(address)) {
            addMessageToErrors("invalideditedaddress", language, errors);
        }

    }

    public void validateNewPartner(Partner partner, String language, Errors errors) {
        if (partner.getTelephone() == null || partner.getTelephone().isEmpty()) {
            addMessageToErrors("invalidphone", language, errors);
        } else if (partnerService.getPartnerByTelephone(partner.getTelephone()) != null) {
            addMessageToErrors("doubletelephonenumber", language, errors);
        }

        if (partner.getPassword() == null || partner.getPassword().isEmpty() || partner.getPassword().length() < 3) {
            addMessageToErrors("invalidpassword", language, errors);
        }

        if (partner.getUsername() == null || partner.getUsername().isEmpty() || partner.getUsername().length() < 3) {
            addMessageToErrors("invalidusername", language, errors);
        } else if (partnerService.getPartnerByUsername(partner.getUsername()) != null) {
            addMessageToErrors("doubleusernameerror", language, errors);
        }

        if (partner.getName() == null || partner.getName().isEmpty()) {
            addMessageToErrors("invalidpartnername", language, errors);
        }
        if (partner.getServiceId() == null) {
            addMessageToErrors("invalidserviceforpartner", language, errors);
        }

    }

    private void addMessageToErrors(String globKey, String language, Errors errors) {
        MessageDto messageDto = commonDataService.getMessageByGlobkey(globKey);
        if (language.compareToIgnoreCase("hay") == 0) {
            errors.reject(globKey, messageDto.getHay());
        } else if (language.compareToIgnoreCase("eng") == 0) {
            errors.reject(globKey, messageDto.getEng());
        }


    }

    private boolean isInvalidMainInfo(Partner partner) {
        return partner.getTelephone().isEmpty() || partner.getAddress().isEmpty();
    }


    private boolean isInvalidEditedAddress(Address address) {
        return address.getLongitude() == null ||
                address.getLatitude() == null ||
                address.getAddress().isEmpty();
    }


}
