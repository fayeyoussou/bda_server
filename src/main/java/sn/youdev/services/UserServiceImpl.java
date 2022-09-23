package sn.youdev.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sn.youdev.config.Constante;
import sn.youdev.config.error.EntreeException;
import sn.youdev.config.error.RoleNotFoundException;
import sn.youdev.config.error.TokenNotFoundException;
import sn.youdev.config.error.UserNotFoundException;
import sn.youdev.dto.request.*;
import sn.youdev.dto.response.UserReponseToken;
import sn.youdev.dto.response.UserResponse;
import sn.youdev.model.InfoPerso;
import sn.youdev.model.Role;
import sn.youdev.model.Token;
import sn.youdev.model.User;
import sn.youdev.repository.InfoRepo;
import sn.youdev.repository.RoleRepo;
import sn.youdev.repository.TokenRepo;
import sn.youdev.repository.UserRepo;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepo repo;
    private final InfoRepo infoRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepo tokenRepo;

    @Autowired
    public UserServiceImpl(UserRepo repo, InfoRepo infoRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder, TokenRepo tokenRepo) {
        this.repo = repo;
        this.infoRepo = infoRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepo = tokenRepo;
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
        if(!Objects.equals(registerRequest.getPassword(), registerRequest.getConfirmation())) throw new EntreeException("mot de passe non conforme");
        if(repo.existsByLogin(registerRequest.getLogin().toLowerCase())) throw new EntreeException("le login existe deja");
        if(repo.existsByInfoPerso_Email(registerRequest.getEmail().toLowerCase())) throw new EntreeException("le mail existe deja");
        if(infoRepo.findByEmailIgnoreCase(registerRequest.getEmail()).isPresent()) throw new EntreeException("1");
        if(infoRepo.findByCin(registerRequest.getCin()).isPresent()) throw  new EntreeException("2");
        if (infoRepo.findByTelephone(registerRequest.getTelephone()).isPresent()) throw  new EntreeException("3");
    }

    @Override
    public UserReponseToken saveUser(RegisterRequest  registerRequest, HttpServletRequest httpServletRequest) throws EntreeException, UserNotFoundException, RoleNotFoundException {
        verifyEntry(registerRequest);
        InfoPerso infoPerso = new InfoPerso();
        infoPerso.setInfoPerso(registerRequest);
        infoRepo.save(infoPerso);
        User user = new User();
        user.setLogin(registerRequest.getLogin());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setInfoPerso(infoPerso);
        repo.save(user);
        editRoleUser(user.getId(),registerRequest.getRoles());
        Token token = new Token((byte) 0,user);
        tokenRepo.save(token);
        return new UserReponseToken(user.getResponse(),Constante.applicationUrl(httpServletRequest)+"/api/auth/enable/"+token.getCode());
    }

    @Transactional
    @Override
    public UserReponseToken enableUser(String tokenCode) throws TokenNotFoundException, EntreeException {
        Token token = tokenRepo.findByCode(tokenCode).orElseThrow(()->new TokenNotFoundException("token not found"));
        if (token.getType()!= (byte)0) throw new EntreeException("not the correct type of token");
        User user = token.getUser();
        if(user.isEnabled()) throw new EntreeException("utilisateur deja active");
        user.setEnabled(true);
        UserResponse userResponse = user.getResponse();
        Token tokenAccess = new Token((byte)1,user);
        tokenRepo.save(tokenAccess);
        return new UserReponseToken(userResponse,tokenAccess.getCode());
    }

    @Override
    public UserResponse saveUserWithExistInfo(UserRequest userRequest) {
        return null;
    }

//    @Override
//    public UserResponse saveUserWithExistInfo(RegisterRequest registerRequest) throws EntreeException {
//        verifyEntry(registerRequest);
//
//        return null;
//    }

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
        findUser(id).setNonLocked(false);
        return true;
    }

    @Transactional
    @Override
    public UserResponse editRoleUser(Long id, List<String> roles) throws UserNotFoundException, RoleNotFoundException {
        User user = findUser(id);
        List<Role> roleArrayList = new ArrayList<>();
        for (String role:roles
             ) {
            roleArrayList.add(roleRepo.findByNomIgnoreCase(role).orElseThrow(()->new RoleNotFoundException("role "+role+" not found")));
        }
        user.setRoles(roleArrayList);
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
        return user.getResponse();

    }

    @Override
    public String passwordResetRequest(String email,HttpServletRequest request) throws UserNotFoundException {
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByLoginOrInfoPerso_Email(username,username).orElseThrow(()->new UsernameNotFoundException("utilisateur non trouve"));
    }

}
