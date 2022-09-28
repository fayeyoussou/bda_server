package sn.youdev.services;

import org.springframework.stereotype.Service;
import sn.youdev.dto.request.DonRequest;
import sn.youdev.dto.request.ResultatRequest;
import sn.youdev.dto.response.DonResponse;
import sn.youdev.dto.response.ResultatResponse;

import java.util.List;

@Service
public interface DonService {
    List<DonResponse> listAllDon();
    List<DonResponse> listByDonneur(String numero);
    DonResponse getDon(String numero);
    ResultatResponse seeResultat(String numero);
    DonResponse saveDon(DonRequest donRequest);
    Object saveResult(ResultatRequest request);
    DonResponse editDon(DonRequest request);
    DonResponse deleteDon(DonRequest request);
    ResultatResponse editResultat(ResultatRequest request);
}
