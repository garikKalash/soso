package am.soso.core.validator;

import am.soso.core.models.MessageDto;
import am.soso.core.models.Service;
import am.soso.core.service.CommonDataService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.validation.constraints.NotNull;

/**
 * Created by Garik Kalashyan on 22-Feb-18.
 */

@Component
@Scope("singleton")
public class ServiceValidator {

  private CommonDataService commonDataService;


  public void validate(@NotNull Service newService, @NotNull String lang, Errors errors){
      if(hasEmptyName(newService)){
         MessageDto messageDtoFromDb = commonDataService.getMessageById(79);
         if(lang.compareToIgnoreCase("hay") == 0){
             errors.reject("wrong_content", messageDtoFromDb.getHay());
         }else if(lang.compareToIgnoreCase("eng") == 0){
             errors.reject("wrong_content",messageDtoFromDb.getEng());
         }
     }
     if(!isFilledParentId(newService)){
        errors.reject("parent_service_error","Parent service id should not be null");
     }

  }

  private boolean hasEmptyName(Service newService){
    return  (newService.getHay() == null || newService.getHay().trim().equals(""))
            || (newService.getEng() == null || newService.getEng().trim().equals(""));
  }

  private boolean isFilledParentId(Service newService){
      return newService != null  && newService.getParentid() != null;
  }

  public void setCommonDataService(CommonDataService commonDataService){
      this.commonDataService = commonDataService;
  }

}
