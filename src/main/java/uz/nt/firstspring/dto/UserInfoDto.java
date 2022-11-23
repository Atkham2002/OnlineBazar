package uz.nt.firstspring.dto;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Set;

@Data
@ToString
@JsonIgnoreProperties(value = {"password", "username"})
@JsonFilter("UserInfoFilter")
public class UserInfoDto implements UserDetails {
    private Long id;
    private String firstName;
    private String lastName;
//    @JsonIgnore
    private String username;
//    @JsonIgnore
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
