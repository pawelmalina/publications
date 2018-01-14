package com.malina.auth;

import com.malina.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by pawel on 12.01.18.
 */
@Component
@Scope(value = "session",  proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessionState {

    private User currentUser;

    public void login(User user){
        this.currentUser = user;
    }

    public void logout(){
        this.currentUser = null;
    }

    public boolean isAuthenticated(){
        return currentUser != null;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
