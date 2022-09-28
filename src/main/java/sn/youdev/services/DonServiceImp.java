package sn.youdev.services;

import sn.youdev.dto.request.DonRequest;
import sn.youdev.dto.request.ResultatRequest;
import sn.youdev.dto.response.DonResponse;
import sn.youdev.dto.response.ResultatResponse;

import java.util.List;

public class DonServiceImp implements DonService {
    @Override
    public List<DonResponse> listAllDon() {
        return null;
    }

    @Override
    public List<DonResponse> listByDonneur(String numero) {
        return null;
    }

    @Override
    public DonResponse getDon(String numero) {
        return null;
    }

    @Override
    public ResultatResponse seeResultat(String numero) {
        return null;
    }

    @Override
    public DonResponse saveDon(DonRequest donRequest) {
        return null;
    }

    @Override
    public Object saveResult(ResultatRequest request) {
        return null;
    }

    @Override
    public DonResponse editDon(DonRequest request) {
        return null;
    }

    @Override
    public DonResponse deleteDon(DonRequest request) {
        return null;
    }

    @Override
    public ResultatResponse editResultat(ResultatRequest request) {
        return null;
    }
}
