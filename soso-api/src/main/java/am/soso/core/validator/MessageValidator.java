package am.soso.core.validator;

import am.soso.core.models.MessageDto;
import am.soso.core.service.CommonDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.validation.constraints.NotNull;

/**
 * Created by Garik Kalashyan on 22-Feb-18.
 */

@Component
public class MessageValidator {

    @Autowired
    private CommonDataService commonDataService;


    public void validate(@NotNull MessageDto newMessageDto, @NotNull String lang, Errors errors){
        if(hasEmptyContent(newMessageDto)){
            MessageDto messageDtoFromDb = commonDataService.getMessageById(78);
            if(lang.compareToIgnoreCase("hay") == 0){
                errors.reject("wrong_content", messageDtoFromDb.getHay());
            }else if(lang.compareToIgnoreCase("eng") == 0){
                errors.reject("wrong_content", messageDtoFromDb.getEng());
            }
        }
    }

    private boolean hasEmptyContent(MessageDto newMessageDto){
        return  (newMessageDto.getEng() == null || newMessageDto.getEng().trim().equals(""))
                || (newMessageDto.getHay() == null || newMessageDto.getHay().trim().equals(""));
    }

    public void setCommonDataService(CommonDataService commonDataService){
        this.commonDataService = commonDataService;
    }
}
