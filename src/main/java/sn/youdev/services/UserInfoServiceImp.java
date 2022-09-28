package sn.youdev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sn.youdev.config.Constante;
import sn.youdev.config.error.UserNotFoundException;
import sn.youdev.dto.request.ChangeNameRequest;
import sn.youdev.dto.request.EmailRequest;
import sn.youdev.dto.request.LoginChangeRequest;
import sn.youdev.dto.request.StringRequest;
import sn.youdev.dto.response.*;
import sn.youdev.model.InfoPerso;
import sn.youdev.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Service
public class UserInfoServiceImp implements UserInfoService {
    private final UserService userService;

    @Autowired
    public UserInfoServiceImp(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserResponsePerso VoirProfilUser(HttpServletRequest request) throws UserNotFoundException {
        User user =userService.findUser(userService.getConnected(request).getId());
        UserResponsePerso userResponsePerso = new UserResponsePerso();
        InfoPerso infos = user.getInfoPerso();
        userResponsePerso.setEmail(infos.getEmail());
        userResponsePerso.setLogin(user.getLogin());
        userResponsePerso.setPrenom(infos.getPrenom());
        userResponsePerso.setNom(infos.getNom());
        userResponsePerso.setTelephone(infos.getTelephone());
        userResponsePerso.setImage(Constante.applicationUrl(request)+"/file/"+infos.getImage().getNom());
        return userResponsePerso;
    }

    @Override
    public DonneurResponse VoirProfilDonneur(HttpServletRequest request) {
        return null;
    }

    @Override
    public List<DonResponse> voisMesDons(HttpServletRequest request) {
        return null;
    }

    @Override
    public DonResponse voirDon(HttpServletRequest request, String numero) {
        return null;
    }

    @Override
    public UserReponseToken changeMailRequete(HttpServletRequest request, EmailRequest emailRequest) {
        return null;
    }

    @Override
    public UserReponseToken changePhone(HttpServletRequest request, StringRequest stringWithPhone) {
        return null;
    }

    @Override
    public UserResponse changeName(HttpServletRequest request, ChangeNameRequest requestChange) {
        return null;
    }

    @Override
    public UserResponse changeLogin(HttpServletRequest request, LoginChangeRequest loginChangeRequest) {
        return null;
    }

    @Override
    public UserResponse changeProfilImage(HttpServletRequest request, MultipartFile image) {
        return null;
    }
}
