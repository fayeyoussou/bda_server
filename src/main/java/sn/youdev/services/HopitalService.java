package sn.youdev.services;

import sn.youdev.config.error.CustomArgumentValidationException;
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
    HopitalResponse saveHopital(HopitalRequest request) throws EntityNotFoundException, CustomArgumentValidationException;
    HopitalResponse editHopital(Long id,HopitalRequest request) throws EntityNotFoundException, CustomArgumentValidationException;
    Boolean deleteHopital(Long id) throws EntityNotFoundException;
    List<UserResponse> getEmployees(Long id) throws EntityNotFoundException;
}
