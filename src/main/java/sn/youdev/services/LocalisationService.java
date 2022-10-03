package sn.youdev.services;

import sn.youdev.config.error.EntityNotFoundException;
import sn.youdev.dto.request.LocalisationRequest;
import sn.youdev.model.Localisation;

public interface LocalisationService {
    Object getLocalisationList();
    Localisation getLocalisation(Long id) throws EntityNotFoundException;
    Object addLocalisation(LocalisationRequest request);
    Object editLocalisation(Long id,LocalisationRequest request) throws EntityNotFoundException;
    Object deleteLocalisation(Long id) throws EntityNotFoundException;
}
