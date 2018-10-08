package am.soso.core.api.controller;

import am.soso.core.api.validator.MessageValidator;
import am.soso.core.api.validator.ServiceValidator;
import am.soso.core.models.MessageDto;
import am.soso.core.models.Service;
import am.soso.core.service.CommonDataService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Garik Kalashyan on 3/4/2017.
 */

@CrossOrigin("*")
@Controller
@RequestMapping("commonData")
public class CommonDataController {

    private static final String RELATIVE_PATH_FOR_UPLOADS = File.separatorChar + "work" + File.separatorChar + "soso-common-data-service-uploads" + File.separatorChar;


    @Autowired
    private final CommonDataService commonDataService;

    @Autowired
    private ServiceValidator serviceValidator;

    @Autowired
    private MessageValidator messageValidator;

    @Autowired
    public CommonDataController(CommonDataService commonDataService) {
        this.commonDataService = commonDataService;
    }

    @PostConstruct
    public void init() {
        serviceValidator.setCommonDataService(commonDataService);
    }

    @RequestMapping(value = "/getSosoServices/{parentId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List> getServices(@PathVariable("parentId") Integer parentId,
                                            @RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language,
                                            HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        List<Service> sosoServicesList = commonDataService.getServicesByParentId(parentId);
        return new ResponseEntity<>(sosoServicesList, HttpStatus.OK);
    }


    @RequestMapping(value = "/appsososervices", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List> getServices(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        List<Service> sosoServicesList = commonDataService.getServices();
        return new ResponseEntity<>(sosoServicesList, HttpStatus.OK);
    }


    @RequestMapping(value = "/deleteSosoServices/{serviceId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteServiceById(@PathVariable("serviceId") Integer serviceId, HttpServletResponse response) throws IOException {
        return new ResponseEntity<>(commonDataService.deleteSosoService(serviceId), HttpStatus.OK);
    }

    @RequestMapping(value = "/createSosoService", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createService(@RequestBody Service service, @RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language,
                                        HttpServletResponse response, Errors errors) throws IOException {
        response.setCharacterEncoding("UTF-8");
        serviceValidator.validate(service, language, errors);
        if (!errors.hasErrors()) {
            Integer newServiceId = commonDataService.createSosoService(service);
            service.setId(newServiceId);
            return new ResponseEntity<>(service, HttpStatus.OK);
        } else {
            Map<String, String> errorsMap = new HashMap<>();
            errors.getAllErrors().forEach(objectError -> {
                errorsMap.put(objectError.getObjectName(), objectError.getDefaultMessage());
            });
            return new ResponseEntity<>(errorsMap, HttpStatus.PARTIAL_CONTENT);
        }
    }

    @RequestMapping(value = "/servicephoto/{serviceId}", method = RequestMethod.GET)
    public void getPhotoById(@PathVariable(value = "serviceId") Integer serviceId , @RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language , HttpServletResponse response) throws IOException {
        String imgPath = commonDataService.getImgPathOfService(serviceId);
        InputStream imageStream = imgPath != null ? getImageInputStreamByImgPath(getBasePathOfResources() + imgPath) : null;
        if (imageStream != null) {
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            IOUtils.copy(imageStream, response.getOutputStream());
        }
    }

    @RequestMapping(value = "/service/{serviceId}", method = RequestMethod.GET)
    public ResponseEntity<Service> getServiceById(@PathVariable(value = "serviceId") Integer serviceId, HttpServletResponse response) throws IOException {
        Service service = commonDataService.getServiceById(serviceId);
        return new ResponseEntity<>(service, HttpStatus.OK);
    }

    @RequestMapping(value = "/commonphoto/{id}", method = RequestMethod.GET)
    public void getCommonPhotoById(@PathVariable(value = "id") Integer id, @RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language , HttpServletResponse response) throws IOException {
        String imgPath = commonDataService.getImgPathWithId(id);
        InputStream imageStream = imgPath != null ? getImageInputStreamByImgPath(getBasePathOfResources() + imgPath) : null;
        if (imageStream != null) {
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            IOUtils.copy(imageStream, response.getOutputStream());
        }
    }

    @RequestMapping(value = "/uploadserviceimage", method = RequestMethod.POST, consumes = {"multipart/mixed", "multipart/form-data"})
    public String uploadAccountImage(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, @RequestParam("id") Integer serviceId) throws IOException {

        Service service = commonDataService.getServiceById(serviceId);
        System.out.println("***** --> Initializing file with name " + getBasePathOfResources() + RELATIVE_PATH_FOR_UPLOADS + " <--  *****");
        File directory = new File(getBasePathOfResources() + RELATIVE_PATH_FOR_UPLOADS);
        String newLogoPath = null;
        if (directory.exists() && directory.isDirectory()) {
            System.out.println("***** --> Directory is existed " + getBasePathOfResources() + RELATIVE_PATH_FOR_UPLOADS + " <--  *****");
            newLogoPath = RELATIVE_PATH_FOR_UPLOADS + file.getOriginalFilename();
        } else if (directory.mkdirs()) {
            System.out.println("***** --> Creating file with name " + getBasePathOfResources() + RELATIVE_PATH_FOR_UPLOADS + " <--  *****");
            newLogoPath = RELATIVE_PATH_FOR_UPLOADS + file.getOriginalFilename();
        }
        if (newLogoPath != null) {
            if (service.getImgpath() != null) {
                commonDataService.deleteServiceOldLogoFromFiles(service.getImgpath());
            }
            commonDataService.updateLogoOfService(serviceId, newLogoPath);
            System.out.println("***** --> Transfering file with name " + newLogoPath + " <--  *****");
            file.transferTo(new File(getBasePathOfResources() + newLogoPath));
            redirectAttributes.addFlashAttribute("Your account image is changed successfully!");
        } else {
            System.out.println("***** --> New Logo is not created in" + getBasePathOfResources() + RELATIVE_PATH_FOR_UPLOADS + " <--  *****");

        }
        return "redirect:/";

    }


    private InputStream getImageInputStreamByImgPath(String imagePath) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagePath));
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", os);
            return new ByteArrayInputStream(os.toByteArray());
        } catch (IOException e) {
            IOException ex = new IOException("There is problem with reading file " + imagePath);
            ex.initCause(e);
            ex.printStackTrace();
        }
        return null;

    }

    @RequestMapping(value = "/systemmessages", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List> getMessages() {
        return new ResponseEntity<>(commonDataService.getMessages(), HttpStatus.OK);
    }

    @RequestMapping(value = "/systemmessage/{id}", method = RequestMethod.GET)
    public ResponseEntity<MessageDto> getMessageDtoById(@PathVariable(value = "id") Integer id) {
        return new ResponseEntity<>(commonDataService.getMessageById(id), HttpStatus.OK);
    }


    @RequestMapping(value = "/systemmessagebykey/{key}", method = RequestMethod.GET)
    public ResponseEntity<MessageDto> getMessageDtoByGlobKey(@PathVariable(value = "key") String key) {
        return new ResponseEntity<>(commonDataService.getMessageByGlobkey(key), HttpStatus.OK);
    }

    @RequestMapping(value = "/message/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> getMessageById(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language, @PathVariable(value = "id") Integer id) {
        MessageDto messageDto = commonDataService.getMessageById(id);
        String message = language.compareToIgnoreCase("hay") == 0 ? messageDto.getHay() : messageDto.getEng();
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @RequestMapping(value = "/messagebykey/{key}", method = RequestMethod.GET)
        public ResponseEntity<String> getMessageByGlobkey(@RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language, @PathVariable(value = "key") String key) {
            MessageDto messageDto = commonDataService.getMessageByGlobkey(key);
            String message = language.compareToIgnoreCase("hay") == 0 ? messageDto.getHay() : messageDto.getEng();
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

    @RequestMapping(value = "/addmessage", method = RequestMethod.POST)
    public ResponseEntity addMessage(@RequestBody MessageDto messageDto, Errors errors,
                                     @RequestHeader(HttpHeaders.ACCEPT_LANGUAGE) String language) {
        messageValidator.validate(messageDto, language, errors);
        if (!errors.hasErrors()) {
            Integer newMessageId = commonDataService.addMessage(messageDto);
            messageDto.setId(newMessageId);
            return new ResponseEntity<>(messageDto, HttpStatus.OK);
        } else {
            Map<String, String> errorsMap =  new HashMap<>();
            errors.getAllErrors().forEach(objectError -> {
                errorsMap.put(objectError.getObjectName(), objectError.getDefaultMessage());
            });
            return new ResponseEntity<>(errorsMap, HttpStatus.PARTIAL_CONTENT);
        }
    }

    @RequestMapping(value = "/deletesystemmessage/{messageid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deleteMessageById(@PathVariable("messageid") Integer messageId) {
        return new ResponseEntity<>(commonDataService.deleteMessageById(messageId), HttpStatus.OK);
    }


    private String getBasePathOfResources() {
        return new File(".").getAbsoluteFile().getParentFile().getPath();
    }


}
