package am.soso.core.service;

import am.soso.core.cache.MessageCacheImpl;
import am.soso.core.models.MessageDto;
import am.soso.core.models.Service;
import am.soso.core.persistance.CommonDataDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Garik Kalashyan on 3/8/2017.
 */
@Repository
public class CommonDataService {

    @Autowired
    private CommonDataDAO commonDataDAO;

    @Autowired
    private MessageCacheImpl messageCache;


    public List<Service> getServicesByParentId(Integer parentId) {
        return commonDataDAO.getServicesByParent(parentId);
    }

    public List<Service> getServices() {
        return commonDataDAO.getServices();
    }

    public boolean deleteSosoService(Integer serviceId) {
        Service service = commonDataDAO.getServiceById(serviceId);
        boolean isDeleted = false;
        if (service != null) {
            isDeleted = commonDataDAO.deleteServiceWithSubServices(serviceId);
            if (service.getImgpath() != null) {
                return isDeleted && deleteServiceOldLogoFromFiles(getBasePathOfResources() + service.getImgpath());
            }
        }
        return isDeleted;
    }

    private String getBasePathOfResources() {
        return new File(".").getAbsoluteFile().getParentFile().getPath();
    }

    public Integer createSosoService(Service service) {
        return commonDataDAO.createService(service);
    }

    public String getImgPathOfService(Integer serviceId) {

        return commonDataDAO.getImgPathOfService(serviceId);
    }

    public String getImgPathWithId(Integer serviceId) {
        return commonDataDAO.getImgPathWithId(serviceId);
    }

    public Service getServiceById(Integer serviceId) {
        return commonDataDAO.getServiceById(serviceId);
    }

    public boolean deleteServiceOldLogoFromFiles(String oldLogoPath) {
        return new File(oldLogoPath).delete();
    }

    public void updateLogoOfService(Integer serviceId, String path) {
        commonDataDAO.updateLogoOfService(serviceId, path);

    }

    public List<MessageDto> getMessages() {
        return new ArrayList<>(messageCache.loadAll().values());
    }

    public MessageDto getMessageById(Integer id) {
        return messageCache.getById(id);
    }


    public Integer addMessage(MessageDto messageDto) {
        Integer messageId = commonDataDAO.addMessage(messageDto);
        if (messageId != null) {
            messageDto.setId(messageId);
            messageCache.put(messageDto);
        }
        return messageId;
    }

    public boolean deleteMessageById(Integer messageId) {
        if (commonDataDAO.deleteMessageById(messageId) != null) {
            return messageCache.remove(messageId);
        }
        return false;
    }

    public MessageDto getMessageByGlobkey(String globkey) {
        return messageCache.getByGlobkey(globkey);
    }


}
