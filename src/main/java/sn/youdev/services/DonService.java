package sn.youdev.services;

import org.springframework.stereotype.Service;
import sn.youdev.config.error.CustomArgumentValidationException;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.config.error.EntreeException;
import sn.youdev.dto.request.DonRequest;
import sn.youdev.dto.request.ResultatRequest;
import sn.youdev.dto.response.DonResponse;
import sn.youdev.dto.response.ResultatResponse;
import sn.youdev.model.Don;

import java.util.List;

@Service
public interface DonService {
    Don getDonById(String id) throws EntityNotFoundException;
    List<DonResponse> listAllDon();
    List<DonResponse> listDonDispo();
    List<DonResponse> listByDonneur(String numero) throws EntityNotFoundException;
    DonResponse getDon(String numero) throws EntityNotFoundException;
    ResultatResponse seeResultat(String numero) throws EntityNotFoundException;
    DonResponse saveDon(DonRequest donRequest) throws CustomArgumentValidationException, EntityNotFoundException;
    Object saveResult(ResultatRequest request) throws CustomArgumentValidationException, EntityNotFoundException;
    DonResponse editDon(String numero,DonRequest request) throws CustomArgumentValidationException, EntityNotFoundException;
    DonResponse changeEtat(String numero,String etat) throws EntityNotFoundException;
    ResultatResponse editResultat(ResultatRequest request) throws EntityNotFoundException, CustomArgumentValidationException, EntreeException;
}
