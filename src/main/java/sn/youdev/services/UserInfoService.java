package sn.youdev.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sn.youdev.config.error.UserNotFoundException;
import sn.youdev.dto.request.ChangeNameRequest;
import sn.youdev.dto.request.EmailRequest;
import sn.youdev.dto.request.LoginChangeRequest;
import sn.youdev.dto.request.StringRequest;
import sn.youdev.dto.response.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface UserInfoService {
    UserResponsePerso VoirProfilUser(HttpServletRequest request) throws UserNotFoundException;
    DonneurResponse VoirProfilDonneur(HttpServletRequest request);
    List<DonResponse> voisMesDons(HttpServletRequest request);
    DonResponse voirDon(HttpServletRequest request,String numero);
    UserReponseToken changeMailRequete(HttpServletRequest request, EmailRequest emailRequest);
    UserReponseToken changePhone(HttpServletRequest request,StringRequest stringWithPhone);
    UserResponse changeName(HttpServletRequest request, ChangeNameRequest requestChange);
    UserResponse changeLogin(HttpServletRequest request, LoginChangeRequest loginChangeRequest);
    UserResponse changeProfilImage(HttpServletRequest request, MultipartFile image);
}
