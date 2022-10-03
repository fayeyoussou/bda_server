package sn.youdev.services;

import sn.youdev.config.error.ArgumentValidationExption;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.dto.request.HopitalRequest;
import sn.youdev.dto.response.HopitalResponse;
import sn.youdev.dto.response.UserResponse;
import sn.youdev.model.Hopital;

import java.util.List;

public interface HopitalService {
    Hopital getHopital(Long id) throws EntityNotFoundException;
    HopitalResponse GetById(Long id) throws EntityNotFoundException;
    List<HopitalResponse> listHopital();
    HopitalResponse saveHopital(HopitalRequest request) throws EntityNotFoundException, ArgumentValidationExption;
    HopitalResponse editHopital(Long id,HopitalRequest request) throws EntityNotFoundException, ArgumentValidationExption;
    Boolean deleteHopital(Long id) throws EntityNotFoundException;
    List<UserResponse> getEmployees(Long id) throws EntityNotFoundException;
}
