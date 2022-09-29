package uz.nt.firstspring.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Set;

@Data
@ToString
public class UserInfoDto implements UserDetails {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Double account;
    private String phoneNumber;
    private Date birthDate;
    private String email;
    private String locale;
    private Set<SimpleGrantedAuthority> authorities;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
