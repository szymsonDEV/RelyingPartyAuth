package dev.szymson.relyingpartyauth.domain;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Builder
public class UserDetailsDTO {

    private String username;

    private List<GrantedAuthority> authorities;

    private int registredCredentials;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("USER"));
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public int getRegistredCredentials() {
        return registredCredentials;
    }

    public void setRegistredCredentials(int registredCredentials) {
        this.registredCredentials = registredCredentials;
    }
}
