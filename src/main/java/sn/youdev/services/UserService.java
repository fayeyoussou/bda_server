package sn.youdev.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import sn.youdev.config.error.EntreeException;
import sn.youdev.config.error.RoleNotFoundException;
import sn.youdev.config.error.TokenNotFoundException;
import sn.youdev.config.error.UserNotFoundException;
import sn.youdev.dto.request.*;
import sn.youdev.dto.response.*;
import sn.youdev.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    User findUser(Long id) throws UserNotFoundException;
    UserResponse findById (Long id) throws UserNotFoundException;
    UserReponseToken saveUser(RegisterRequest registerRequest, HttpServletRequest httpServletRequest) throws EntreeException, UserNotFoundException, RoleNotFoundException;
    UserReponseToken enableUser(String token) throws TokenNotFoundException, EntreeException;
    UserResponse saveUserWithExistInfo(UserRequest userRequest);
    List<UserResponse> findAllUser() throws UserNotFoundException;
    UserResponse editUser (Long id, EditUserRequest editUserRequest) throws UserNotFoundException, EntreeException;
    Boolean blockUser (Long id) throws UserNotFoundException;
    UserResponse editRoleUser(Long id, List<String> roles) throws UserNotFoundException, RoleNotFoundException;
    UserResponse changePassword(Long id, ChangePasswordRequest changePasswordRequest) throws EntreeException, UserNotFoundException;
    UserResponse passwordReset(String token, ResetPasswordRequest resetPasswordRequest) throws TokenNotFoundException, EntreeException;
    String passwordResetRequest (String email,HttpServletRequest request) throws UserNotFoundException;

    User findByCredentials(String username) throws UserNotFoundException;

    UserReponseToken getConnected(HttpServletRequest request);
}
