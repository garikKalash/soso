package am.soso.core.cache;


import am.soso.core.models.MessageDto;
import am.soso.core.persistance.CommonDataDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Garik Kalashyan on 22-Feb-18.
 */

@Component
public class MessageCacheImpl implements Cache<MessageDto> {
    private final Map<Integer, MessageDto> messageDtoMapById = new HashMap<>();
    private final Map<String, MessageDto> messageDtoMapByGlobkey = new HashMap<>();

    @Autowired
    private CommonDataDAO commonDataDAO;

    @PostConstruct
    private void init() {
        List<MessageDto> messageDtoList = commonDataDAO.getMessages();
        messageDtoList.forEach(messageDto -> {
            messageDtoMapById.put(messageDto.getId(), messageDto);
            messageDtoMapByGlobkey.put(messageDto.getGlobkey(), messageDto);
        });
    }

    @Override
    public MessageDto getById(@NotNull Integer id) {
        return messageDtoMapById.get(id);
    }

    @Override
    public void put(MessageDto messageDto) {
        messageDtoMapById.put(messageDto.getId(), messageDto);
        messageDtoMapByGlobkey.put(messageDto.getGlobkey(), messageDto);
    }

    @Override
    public Map<Integer, MessageDto> loadAll() {
        return messageDtoMapById;
    }

    @Override
    public void putAll(Map<Integer, MessageDto> data) {
        messageDtoMapById.putAll(data);
    }

    @Override
    public void refreshAll() {
        messageDtoMapById.clear();
        messageDtoMapByGlobkey.clear();
        commonDataDAO.getMessages().forEach(messageDto -> {
            messageDtoMapById.put(messageDto.getId(), messageDto);
            messageDtoMapByGlobkey.put(messageDto.getGlobkey(), messageDto);
        });
    }

    @Override
    public boolean remove(Integer id) {
        return messageDtoMapById.remove(id) != null;
    }

    @Override
    public void load(Integer... ids) {
        for (Integer id : ids) {
            MessageDto messageDto = commonDataDAO.getMessageById(id);
            messageDtoMapById.put(messageDto.getId(), messageDto);
            messageDtoMapByGlobkey.put(messageDto.getGlobkey(), messageDto);
        }
    }

    public MessageDto getByGlobkey(String globkey) {
        return messageDtoMapByGlobkey.get(globkey);
    }
}


