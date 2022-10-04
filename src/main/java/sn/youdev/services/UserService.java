package sn.youdev.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sn.youdev.config.error.*;
import sn.youdev.dto.request.*;
import sn.youdev.dto.response.*;
import sn.youdev.model.User;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    User getUserByRequest(HttpServletRequest request) throws UserNotFoundException;
    User findUser(Long id) throws UserNotFoundException;
    UserResponse findById (Long id) throws UserNotFoundException;
    UserReponseToken saveUser(RegisterRequest registerRequest, MultipartFile image, HttpServletRequest httpServletRequest) throws EntreeException, UserNotFoundException, RoleNotFoundException, IOException;
    UserReponseToken enableUser(String token) throws TokenNotFoundException, EntreeException;

    List<UserResponse> findAllUser() throws UserNotFoundException;
    UserResponse editUser (Long id, EditUserRequest editUserRequest) throws UserNotFoundException, EntreeException;
    Boolean blockUser (Long id) throws UserNotFoundException;
    UserResponse addBanqueRole(Long id,Long banque_id) throws RoleNotFoundException, UserNotFoundException, EntreeException, BanqueNotFoundException;
    UserResponse addCntsRole(Long id) throws RoleNotFoundException, UserNotFoundException, EntreeException;
    UserResponse addDoctorRole(Long id,Long hopital) throws UserNotFoundException, EntityNotFoundException, RoleNotFoundException, EntreeException;
    UserResponse removeRole(Long id,String role) throws UserNotFoundException, RoleNotFoundException, EntreeException;
    UserResponse changePassword(Long id, ChangePasswordRequest changePasswordRequest) throws EntreeException, UserNotFoundException;
    UserResponse passwordReset(String token, ResetPasswordRequest resetPasswordRequest) throws TokenNotFoundException, EntreeException;
    String passwordResetRequest (String email,HttpServletRequest request) throws UserNotFoundException;

    User findByCredentials(String username) throws UserNotFoundException;
    UserReponseToken getConnected(HttpServletRequest request);
}
