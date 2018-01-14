package com.malina.auth;

import com.malina.model.User;
import com.malina.model.dto.LoginUserDTO;
import com.malina.model.dto.UserDTO;
import com.malina.repositories.UserRepository;
import com.malina.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

/**
 * Created by pawel on 12.01.18.
 */
@CrossOrigin(origins = {"${origin.domain}", "*"})
@RequiredArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    private SessionState state;

    private final String INCORECT_EMAIL_OR_PASS = "Incorect email or password";

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus login(@RequestBody LoginUserDTO loginUser) throws NoSuchAlgorithmException {

        if (state.isAuthenticated()) {
            return HttpStatus.CONFLICT;
        }

        String email = loginUser.getEmail();
        String password = AuthUtils.Sha256(loginUser.getPassword());
        if (email == null || password == null) {
            return HttpStatus.BAD_REQUEST;
        }

        if (userRepository.existsByEmailAndPassword(email, password)) {
            User user = userRepository.getByEmailAndPassword(email, password);
            state.login(user);
        } else {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }



    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus logout() {
        state.logout();
        return HttpStatus.OK;
    }

    @RequestMapping(value = "check", method = RequestMethod.GET)
    @ResponseBody
    public boolean check() {
        return state.isAuthenticated();
    }

    @RequestMapping(value = "logged-user", method = RequestMethod.GET)
    @ResponseBody
    public UserDTO getLoggedUser() {
        if (!state.isAuthenticated()) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.convertToDTO(state.getCurrentUser());
        return userDTO;
    }
}
