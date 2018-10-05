package am.soso.core.validator;

import am.soso.core.models.MessageDto;
import am.soso.core.models.Request;
import am.soso.core.service.CommonDataService;
import am.soso.core.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class ReserveValidator {

    private final CommonDataService commonDataService;

    private final PartnerService partnerService;

    @Autowired
    public ReserveValidator(CommonDataService commonDataService, PartnerService partnerService) {
        this.commonDataService = commonDataService;
        this.partnerService = partnerService;
    }

    public void validateReserve(Request request, String lang, Errors errors) {
        if (request.getStatus() != 2 && (request.getDuration() == null || request.getDuration() <= 0)) {
            MessageDto messageDto = commonDataService.getMessageByGlobkey("invalidduration");
            if (lang.compareToIgnoreCase("hay") == 0) {
                errors.reject("isWrongDuration", messageDto.getHay());
            } else {
                errors.reject("isWrongDuration", messageDto.getEng());
            }
            return;
        }
        if (request.getDuration() != null && partnerService.getCrossedRequest(request) != null) {
            MessageDto messageDto = commonDataService.getMessageByGlobkey("crossedrequestduration");
            if (lang.compareToIgnoreCase("hay") == 0) {
                errors.reject("crossedrequestduration", messageDto.getHay());
            } else {
                errors.reject("crossedrequestduration", messageDto.getEng());
            }
        }
    }
}
