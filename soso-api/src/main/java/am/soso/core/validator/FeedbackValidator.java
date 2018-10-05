package am.soso.core.validator;

import am.soso.core.models.Feedback;
import am.soso.core.models.MessageDto;
import am.soso.core.service.CommonDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class FeedbackValidator {

    private final CommonDataService commonDataService;

    @Autowired
    public FeedbackValidator(CommonDataService commonDataService) {
        this.commonDataService = commonDataService;
    }

    public void validate(Feedback newFeedback, String language, Errors errors) {
        if (isInvalidFeedback(newFeedback)) {
            MessageDto responseEntity = commonDataService.getMessageByGlobkey("invalidfeedback");
            if (language.compareToIgnoreCase("hay") == 0) {
                errors.reject("invalidfeedback", responseEntity.getHay());
            } else if (language.compareToIgnoreCase("eng") == 0) {
                errors.reject("invalidfeedback", responseEntity.getEng());
            }
        }
    }

    private boolean isInvalidFeedback(Feedback newFeedback) {
        return newFeedback.getClientId() == null ||
                newFeedback.getRate() == null ||
                newFeedback.getRequestId() == null ||
                newFeedback.getPartnerId() == null;
    }

}
