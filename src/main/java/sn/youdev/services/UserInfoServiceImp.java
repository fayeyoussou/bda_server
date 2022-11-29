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
import sn.youdev.model.Don;
import sn.youdev.model.InfoPerso;
import sn.youdev.model.Token;
import sn.youdev.model.User;
import sn.youdev.repository.TokenRepo;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserInfoServiceImp implements UserInfoService {
    private final UserService userService;
    private final TokenRepo tokenRepo;

    @Autowired
    public UserInfoServiceImp(UserService userService, TokenRepo tokenRepo) {
        this.userService = userService;
        this.tokenRepo = tokenRepo;
    }

    @Override
    public UserResponsePerso VoirProfilUser(HttpServletRequest request) throws UserNotFoundException {
        User user =userService.getUserByRequest(request);
        UserResponsePerso userResponsePerso = new UserResponsePerso();
        InfoPerso infos = user.getInfoPerso();
        userResponsePerso.setEmail(infos.getEmail());
        userResponsePerso.setLogin(user.getLogin());
        userResponsePerso.setPrenom(infos.getPrenom());
        userResponsePerso.setNom(infos.getNom());
        userResponsePerso.setTelephone(infos.getTelephone());
        userResponsePerso.setImage(infos.getImage().getNom());
        if(infos.getNumeroDonneur() != null) {
            userResponsePerso.setNumeroDonneur(infos.getNumeroDonneur().getNumero());
            userResponsePerso.setGroupeSanguin(infos.getNumeroDonneur().getGroupeSanguin().getGroupe());
        }
        return userResponsePerso;
    }

    @Override
    public DonneurResponse VoirProfilDonneur(HttpServletRequest request) throws UserNotFoundException {
        User user =userService.findUser(userService.getUserByRequest(request).getId());
        return user.getInfoPerso().getNumeroDonneur().getDonneurResponse();
    }

    @Override
    public List<DonResponse> voirMesDons(HttpServletRequest request) throws UserNotFoundException {
        User user =userService.findUser(userService.getUserByRequest(request).getId());
        List<Don> dons = user.getInfoPerso().getNumeroDonneur().getDons();

        List<DonResponse> donResponses = new ArrayList<>();
        if(dons!=null) {
            for (Don don :
                    dons) {
                donResponses.add(don.getDonResponse());
            }
        }
        return donResponses;
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

    @Override
    public String logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        Optional<Token> token = tokenRepo.findByCode(header);
        //        token.ifPresent(tokenRepo::delete);
        if(token.isPresent()) {
            tokenRepo.delete(token.get());
            return "deleted";
        }
        return "token not found";
    }
}
