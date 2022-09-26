package sn.youdev.services;

import org.springframework.stereotype.Service;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.config.error.EntreeException;
import sn.youdev.config.error.TestException;
import sn.youdev.dto.request.DonneurRequest;
import sn.youdev.dto.request.LoginRequest;
import sn.youdev.dto.request.StringRequest;
import sn.youdev.dto.response.DonneurResponse;
import sn.youdev.model.Donneur;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public interface DonneurService {
    Donneur findDonneur(String numero) throws EntityNotFoundException;
    DonneurResponse findByNumero(String numero) throws EntityNotFoundException;
    List<DonneurResponse> findAll();
    DonneurResponse saveDonneur(DonneurRequest donneurRequest) throws EntityNotFoundException, EntreeException, TestException;
    DonneurResponse editDonneur(String numero,DonneurRequest donneurRequest) throws EntityNotFoundException;
    Boolean deleteDonneur(String numero) throws EntityNotFoundException;
    Map<String, Object> saveWithExist(Long idInfo,String groupe) throws EntityNotFoundException, TestException;
    Map<String, Object> saveExistDonneur(String numero, StringRequest request, HttpServletRequest httpServletRequest) throws EntityNotFoundException;
}
