package sn.youdev.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import sn.youdev.config.Constante;
import sn.youdev.config.CustomValidator;
import sn.youdev.config.error.*;
import sn.youdev.dto.request.*;
import sn.youdev.dto.response.UserReponseToken;
import sn.youdev.dto.response.UserResponse;
import sn.youdev.model.*;
import sn.youdev.repository.*;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepo repo;
    private final InfoRepo infoRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepo tokenRepo;
    private final BanqueRepo banqueRepo;
    private final BanqueUserRepo banqueUserRepo;
    private final HopitalRepo hopitalRepo;
    private final MedecinHopitalRepo medecinHopitalRepo;
    private final FileRepo fileRepo;


    @Autowired
    public UserServiceImpl(
            UserRepo repo,
            InfoRepo infoRepo,
            RoleRepo roleRepo,
            PasswordEncoder passwordEncoder,
            TokenRepo tokenRepo,
            BanqueRepo banqueRepo, BanqueUserRepo banqueUserRepo, HopitalRepo hopitalRepo, MedecinHopitalRepo medecinHopitalRepo, FileRepo fileRepo) {
        this.repo = repo;
        this.infoRepo = infoRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepo = tokenRepo;
        this.banqueRepo = banqueRepo;
        this.banqueUserRepo = banqueUserRepo;
        this.hopitalRepo = hopitalRepo;
        this.medecinHopitalRepo = medecinHopitalRepo;
        this.fileRepo = fileRepo;
    }

    @Override
    public User getUserByRequest(HttpServletRequest request) throws UserNotFoundException {
        String header = request.getHeader("Authorization");
        return tokenRepo.findByCode(header).orElseThrow(()->new UserNotFoundException("user not found")).getUser();
    }

    @Override
    public User findUser(Long id) throws UserNotFoundException {
        Optional<User> user =  repo.findById(id);
        if (user.isEmpty()) throw new UserNotFoundException("user not found");
        return user.get();
    }


    @Override
    public UserResponse findById(Long id) throws UserNotFoundException {
        return findUser(id).getResponse();
    }
    private void verifyEntry(RegisterRequest registerRequest) throws EntreeException {
        if(repo.existsByLogin(registerRequest.getLogin().toLowerCase())) throw new EntreeException("le login existe deja");
        if(repo.existsByInfoPerso_Email(registerRequest.getEmail().toLowerCase())) throw new EntreeException("le mail existe deja");
        if(infoRepo.findByEmailIgnoreCase(registerRequest.getEmail()).isPresent()) throw new EntreeException("1");
        if(infoRepo.findByCin(registerRequest.getCin()).isPresent()) throw  new EntreeException("2");
        if (infoRepo.findByTelephone(registerRequest.getTelephone()).isPresent()) throw  new EntreeException("3");
    }

    @Transactional
    @Override
    public String saveUser(RegisterRequest  registerRequest, MultipartFile image) throws EntreeException, IOException {
        verifyEntry(registerRequest);
        InfoPerso infoPerso = new InfoPerso();
        infoPerso.setInfoPerso(registerRequest);
        User user = new User();
        user.setLogin(registerRequest.getLogin());
        String password = RandomStringUtils.randomAlphanumeric(7);
        user.setPassword(passwordEncoder.encode(password));
        user.setInfoPerso(infoPerso);
        File file = new File(Constante.createFile(image));
        fileRepo.save(file);
        Token token = new Token(user);
        infoPerso.setImage(file);
        infoRepo.save(infoPerso);
        repo.save(user);
        file.setOwner(user);
        tokenRepo.save(token);
        System.out.println(token.getCode()+"/"+password);
        return "code envoye par mail avec mot de passe";
    }

    @Transactional
    @Override
    public UserReponseToken enableUser(String tokenCode) throws TokenNotFoundException, EntreeException {
        Token token = tokenRepo.findByCode(tokenCode).orElseThrow(()->new TokenNotFoundException("token not found"));
        if (token.getType()!= (byte)0) throw new EntreeException("not the correct type of token");
        User user = token.getUser();
        if(user.isEnabled()) throw new EntreeException("utilisateur deja active");
        user.setEnabled(true);
        tokenRepo.delete(token);
        UserResponse userResponse = user.getResponse();
        Token tokenAccess = new Token((byte)1,user);
        tokenRepo.save(tokenAccess);
        return new UserReponseToken(userResponse,tokenAccess.getCode());
    }

    @Override
    public List<UserResponse> findAllUser() throws UserNotFoundException {
        List<UserResponse> userResponses = new ArrayList<>();
        List<User> users = repo.findAllByNonLockedTrue();
        if(users.isEmpty()) throw new UserNotFoundException("user list is empty");
        for (User user:
             users) {
                userResponses.add(user.getResponse());
        }
        return userResponses;
    }

    @Transactional
    @Override
    public UserResponse editUser(Long id, EditUserRequest editRequest) throws UserNotFoundException, EntreeException {
        User user = findUser(id);
        if(!passwordEncoder.matches(editRequest.getPassword(),user.getPassword())) throw new EntreeException("mot de passe incorrecte");
        user.setLogin(editRequest.getLogin());
        user.getInfoPerso().setPrenom(editRequest.getPrenom());
        user.getInfoPerso().setNom(editRequest.getNom());
        return user.getResponse();
    }

    @Transactional
    @Override
    public Boolean blockUser(Long id) throws UserNotFoundException {
        User user = findUser(id);
        user.setNonLocked(false);
        tokenRepo.deleteAll(user.getTokens());
        return true;
    }

    @Transactional
    @Override
    public UserResponse addBanqueRole(Long id, Long banque_id) throws RoleNotFoundException, UserNotFoundException, EntreeException, BanqueNotFoundException {
        try {
            Role role = roleRepo.findByNomIgnoreCase("banque").orElseThrow(() -> new RoleNotFoundException("role banque found"));
            User user = findUser(id).addRoleToUser(role);
            Banque banque = banqueRepo.findById(banque_id).orElseThrow(() -> new BanqueNotFoundException("banque not found"));
            BanqueUser banqueUser = new BanqueUser(banque, user);
            banqueUserRepo.save(banqueUser);
            return user.getResponse();
        }catch (Exception e){
            log.error(e.getClass().getSimpleName(),e);
            throw new EntreeException(e.getMessage());
        }
    }

    @Transactional
    @Override
    public UserResponse addCntsRole(Long id) throws RoleNotFoundException, UserNotFoundException, EntreeException {
        Role role = roleRepo.findByNomIgnoreCase("cnts").orElseThrow(()->new RoleNotFoundException("role banque found"));
        return findUser(id).addRoleToUser(role).getResponse();
    }
    @Transactional
    @Override
    public UserResponse addDoctorRole(Long id, Long hopitalId) throws UserNotFoundException, EntityNotFoundException, RoleNotFoundException, EntreeException {
        User user = findUser(id);
        if(hopitalId!=0){
            Hopital hopital = hopitalRepo.findById(hopitalId).orElseThrow(()->new EntityNotFoundException("hôpital not found"));
            MedecinHopital medecinHopital = new MedecinHopital(hopital,user);
            medecinHopitalRepo.save(medecinHopital);
        }
        Role role = roleRepo.findByNomIgnoreCase("medecin").orElseThrow(()->new RoleNotFoundException("role banque found"));
        return findUser(id).addRoleToUser(role).getResponse();

    }

    @Override
    public UserResponse removeRole(Long id, String roleName) throws UserNotFoundException, RoleNotFoundException, EntreeException {
        User user = findUser(id);
        Role role = roleRepo.findByNomIgnoreCase(roleName).orElseThrow(()->new RoleNotFoundException("role "+roleName+" non trouvee"));
        user.deleteRoleFromUser(role);
        if(Objects.equals(roleName, "banque")){
            BanqueUser banqueUser = banqueUserRepo.findByUser(user).orElse(null);
            if(banqueUser != null){
                banqueUserRepo.delete(banqueUser);
            }
        } else if (Objects.equals(roleName, "medecin")) {
            MedecinHopital medecinHopital = medecinHopitalRepo.findByUser(user).orElse(null);
            if (medecinHopital!=null){
                medecinHopitalRepo.delete(medecinHopital);
            }
        }
        return user.getResponse();
    }


    @Transactional
    @Override
    public UserResponse changePassword(Long id, ChangePasswordRequest changePasswordRequest) throws EntreeException, UserNotFoundException {
        if(!Objects.equals(changePasswordRequest.getNewPassword(), changePasswordRequest.getConfirmation())) throw new EntreeException("mot de passe non conforme");
        User user = findUser(id);
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        return user.getResponse();
    }

    @Transactional
    @Override
    public UserResponse passwordReset(String token, ResetPasswordRequest resetPasswordRequest) throws TokenNotFoundException, EntreeException {
        if (!Objects.equals(resetPasswordRequest.getNewPassword(), resetPasswordRequest.getConfirmation())) throw new EntreeException("mot de passe non conforme");
        Token token1 = tokenRepo.findByCode(token).orElseThrow(()->new TokenNotFoundException("token not found"));
        if(token1.getType()!= (byte)2) throw new EntreeException("not the correct type of token");
        User user = token1.getUser();
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        tokenRepo.delete(token1);
        return user.getResponse();
    }

    @Override
    public String passwordResetRequest(String email,HttpServletRequest request) throws UserNotFoundException {
        System.out.println(email);
        System.out.println("dadsasa");
        User user = infoRepo.findByEmailIgnoreCase(email).orElseThrow(()->new UserNotFoundException("user not found")).getUser();
        Token token = new Token((byte) 2,user);
        tokenRepo.save(token);
        return Constante.applicationUrl(request)+"/api/auth/reset/"+token.getCode();
    }

    @Override
    public User findByCredentials(String username) throws UserNotFoundException {
        return repo.findByLoginOrInfoPerso_Email(username,username).orElseThrow(()->new UserNotFoundException("user not found"));
    }



    @Override
    public UserReponseToken loginUser(LoginChangeRequest request) throws CustomArgumentValidationException {
        CustomValidator validator = new CustomValidator();
        Map<String,String> errors = validator.validate(request);
        if(errors.size()>0) throw new CustomArgumentValidationException(errors);
        User user = repo.findByLoginOrInfoPerso_Email(request.getLogin(),request.getLogin()).orElse(null);
        if(user == null || !passwordEncoder.matches(request.getPassword(),user.getPassword())) {
            errors.put("login", "login ou password erroné");
            throw new CustomArgumentValidationException(errors);
        }
        Token token = new Token((byte) 1,user);
        tokenRepo.save(token);
        return new UserReponseToken(user.getResponse(),token.getCode(),token.getExpiration());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByLoginOrInfoPerso_Email(username,username).orElseThrow(()->new UsernameNotFoundException("utilisateur non trouve"));
    }

}
