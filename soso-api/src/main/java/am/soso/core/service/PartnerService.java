package am.soso.core.service;

import am.soso.core.models.*;
import am.soso.core.persistance.PartnerDAO;
import am.soso.core.utility.GeoCalculator;
import am.soso.core.utility.UnitType;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Garik Kalashyan on 3/8/2017.
 */

@Repository
public class PartnerService {


    @Autowired
    private PartnerDAO partnerDAO;

    public Integer addPartner(Partner partner) {
        return partnerDAO.addPartner(partner);
    }

    public Partner getPartnerById(Integer partnerId) {
        return partnerDAO.getPartnerById(partnerId);
    }

    public List<Feedback> getFeedbacks(Integer partnerId) {
        return partnerDAO.loadFeedbacksByPartnerId(partnerId);
    }


    public Integer signInPartner(String telephone, String password) {
        return partnerDAO.signin(telephone, password);
    }

    public List<Partner> getAllPartners() {
        return partnerDAO.getAllPartners();
    }

    public Partner getPartnerByTelephone(String telephone) {
        return partnerDAO.getPartnerByTelephone(telephone);
    }

    public Partner getPartnerByUsername(String username) {
        return partnerDAO.getPartnerByUsername(username);
    }


    public boolean deletePhotoFromFiles(String oldLogoPath) {
        return new File(oldLogoPath).delete();
    }

    public void updatePartnerLogo(Integer newlogoId, Integer partnerId) {
        partnerDAO.updateLogosrcPathOfPartner(partnerId, newlogoId);
    }

    public void saveEditedMainInfo(Integer partnerId, String editedTelephone) {
        partnerDAO.saveEditedMainInfoOfPartner(partnerId, editedTelephone);
    }

    public List<Partner> getPartnersByServiceId(Integer serviceId) {
        return partnerDAO.getPartnersByServiceId(serviceId);
    }

    public void saveEditedAddress(Integer partnerId, BigDecimal latitude, BigDecimal lotitude, String address) {
        partnerDAO.saveEditedAddress(partnerId, latitude, lotitude, address);
    }

    public void saveEditedNotice(Integer partnerId, String notice) {
        partnerDAO.saveEditedNotice(partnerId, notice);
    }

    public Integer savePhotoToPartnier(Integer partnerId, String imgPath) {
        return partnerDAO.addPhotoToPartnier(partnerId, imgPath);
    }

    public List<PhotoDto> getPhotosByParentId(Integer partnerId) {
        return partnerDAO.loadPhotosByPartnerId(partnerId);
    }

    public String getPhotoById(Integer photoId) {
        return partnerDAO.getPhotoById(photoId);
    }

    public Integer deletePhotoById(Integer photoId) {
        return partnerDAO.deletePhotoById(photoId);
    }

    public Integer deleteReservationById(Integer reserveId) {
        return partnerDAO.deleteReservationById(reserveId);
    }

    public List<Request> getReservationsByClientId(Integer clientId, Integer status) {
        return partnerDAO.getReservationsByClientId(clientId, status);
    }

    public List<Request> getReservationsByPartnerId(Integer partnierId, Integer status) {
        return partnerDAO.getReservationsByPartnerId(partnierId, status);
    }

    public void updateReserve(Request request) {

        request.getStartTime().setTime(request.getStartTime().getTime() + 4 * 60 * 1000);
        partnerDAO.updateReservation(request);
    }


    public Integer addReservation(Request request) {
        request.getStartTime().setTime(request.getStartTime().getTime() + 4 * 60 * 1000);
        return partnerDAO.addReservation(request);
    }

    public void deleteFollowerByClientPartnerId(Integer clientId, Integer partnerId) {
        partnerDAO.deleteFollowerByClientPartnerId(clientId, partnerId);
    }

    public Integer addFollowerToPartnier(Integer partnerId, Integer clientId) {
        return partnerDAO.addFollowerToPartnier(partnerId, clientId);
    }

    public List<Follower> getFollowersByPartnerId(Integer partnerId) {
        return partnerDAO.getFollowersByPartnerId(partnerId);
    }

    public List<Follower> getFollowersByClientId(Integer clientId) {
        return partnerDAO.getFollowersByClientId(clientId);
    }

    public Integer addServiceDetailToPartner(Integer partnerid, Integer serviceId, Integer defaulttime, Integer price) {
        return partnerDAO.addServiceToPartnier(partnerid, serviceId, defaulttime, price);
    }

    public Integer updatePartnerServiceDetail(PartnerServiceDetail partnerServiceDetail) {
        return partnerDAO.updateServiceDetailForPartner(partnerServiceDetail);
    }

    public Integer deletePartnerServiceDetail(Integer itemId) {
        return partnerDAO.deletePartnerService(itemId);
    }

    public List<PartnerServiceDetail> getPartnerServiceDetailsByPartner(Integer itemId) {
        return partnerDAO.getAllServicesByPartner(itemId);
    }

    public Follower getFollowerById(Integer id) {
        return partnerDAO.getFollowerById(id);
    }


    public List<EventDto> getAutoCompletedRequests() {
        List<Request> requests = partnerDAO.getAllRequests();
        List<EventDto> result = new ArrayList<>();
        for (Request request : requests) {
            if (isNotCompleted(request) && needAutoCompleteRequest(request)) {
                request.setStatus(3);
                partnerDAO.updateReservation(request);
                EventDto eventDto = new EventDto(null, request.getPartnerId(), request.getClientId(), request.getId(), false, null);
                result.add(eventDto);
            }
        }
        return result;
    }

    public List<Partner> getPartnersByServiceTermId(Integer serviceId, String term) {
        return partnerDAO.getPartnersByServiceTermId(serviceId, term);
    }

    private boolean isNotCompleted(Request request) {
        return request.getStatus() != 3;
    }

    private boolean needAutoCompleteRequest(Request request) {
        long startTimeInMs = request.getStartTime().getTime();
        Date afterAddingMins = new Date(startTimeInMs + (request.getDuration() * 1000));
        return afterAddingMins.getTime() <= new Date().getTime();
    }

    public Request getCrossedRequest(Request request) {
        long startTimeInMs = request.getStartTime().getTime();
        Date endTime = new Date(startTimeInMs + (request.getDuration() * 1000 * 60));

        for (Request _request : partnerDAO.getReservationsByPartnerId(request.getPartnerId(), 1)) {
            long _startTimeInMs = _request.getStartTime().getTime();
            Date _afterAddingMins = new Date(startTimeInMs + (request.getDuration() * 1000 * 60));

            if ((startTimeInMs < _startTimeInMs && endTime.getTime() > _startTimeInMs)
                    || (startTimeInMs > _startTimeInMs && startTimeInMs < _afterAddingMins.getTime())) {
                return _request;
            }
        }

        return null;
    }


    public Integer addFeedbackToPartner(Feedback feedback) {
        return partnerDAO.addFeedbackToPartner(feedback);
    }

    public Integer addIsRatedFlagToRequest(Integer requestId) {
        return partnerDAO.setRatedFlagTrue(requestId);
    }

    public Partner getPartnerMainDetailsById(Integer partnerId) {
        return partnerDAO.getPartnerMainDetailsById(partnerId);
    }

    public List<Map> getPartnersInGivenRange(Pair<BigDecimal, BigDecimal> myLocation, BigDecimal range, Integer partnerServiceId) {
        List<Partner> partnersByServiceId = partnerDAO.getPartnersByServiceId(partnerServiceId);
        List<Map> list = new ArrayList<>();

        partnersByServiceId.forEach(partner -> {
            double distanceForPartner = GeoCalculator.distance(partner.getLatitude().doubleValue(),
                    partner.getLongitude().doubleValue(),
                    myLocation.getKey().doubleValue(),
                    myLocation.getValue().doubleValue(),
                    UnitType.KILOMETER);
            if (distanceForPartner <= range.doubleValue()) {
                Map<String, Object> partnerDistanceEntry = new HashMap<>();
                partnerDistanceEntry.put("partner", partner);
                partnerDistanceEntry.put("distance", distanceForPartner);
                list.add(partnerDistanceEntry);
            }
        });
        return list;
    }


}
