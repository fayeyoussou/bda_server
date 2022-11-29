package sn.youdev.services;

import sn.youdev.config.error.CustomArgumentValidationException;
import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.config.error.EntreeException;
import sn.youdev.config.error.UserNotFoundException;
import sn.youdev.dto.request.ArticleRequest;
import sn.youdev.dto.request.DonRequest;
import sn.youdev.dto.request.JourneeRequest;
import sn.youdev.dto.request.StringRequest;
import sn.youdev.dto.response.ArticleResponse;
import sn.youdev.dto.response.DonResponse;
import sn.youdev.dto.response.JourneeResponse;
import sn.youdev.model.Journee;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface JourneeService {
    List<JourneeResponse> listJournee();
    List<DonResponse> listDonJournee(Long id) throws EntityNotFoundException;
    ArticleResponse articleJournee(Long id) throws EntityNotFoundException;
    JourneeResponse addJournee(JourneeRequest requete) throws UserNotFoundException;
    List<DonResponse> addDonJournee(Long id, DonRequest request) throws CustomArgumentValidationException, EntityNotFoundException;
    ArticleResponse addArticleToJournee(Long id, ArticleRequest request,HttpServletRequest httpServletRequest) throws UserNotFoundException, IOException, EntityNotFoundException;
    Journee getJournee(Long id) throws EntityNotFoundException;
    JourneeResponse getJourneeById(Long id) throws EntityNotFoundException;
    JourneeResponse addCntsComment(Long id, StringRequest request) throws EntityNotFoundException;
    JourneeResponse addOrgaComment(Long id, HttpServletRequest httpServletRequest,StringRequest request) throws EntityNotFoundException, UserNotFoundException, EntreeException;
}
