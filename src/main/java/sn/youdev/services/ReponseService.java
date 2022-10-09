package sn.youdev.services;

import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.config.error.UserNotFoundException;
import sn.youdev.dto.request.ReponseRequest;
import sn.youdev.dto.response.DemandeResponse;

import javax.servlet.http.HttpServletRequest;

public interface ReponseService {
    DemandeResponse repondre(Long id, ReponseRequest request, HttpServletRequest httpServletRequest) throws EntityNotFoundException, UserNotFoundException;
}
