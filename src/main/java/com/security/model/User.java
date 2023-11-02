package com.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    private String id;

    private String roleId;
    @NotBlank

    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    @Indexed(unique = true)
    private String email;
    @NotBlank
    @Indexed(unique = true)
    private String username;
    @NotBlank
    private String password;

    @DBRef
    private Role roles;
    private Integer status;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return firstName + " " + lastName;
    }
    //private Set<Role> roles = nbew HashSet<>();
//    private List<GrantedAuthority> authorities;


    private boolean accountNonExpired;


    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;


    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();

        Set<GrantedAuthority> authorities = new HashSet<>();
        System.out.println(roles);
        authorities.add(new SimpleGrantedAuthority(roleId));
        return authorities;
    }


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
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

//    public void addRole(Role role) {
//        roles.add(role);
//    }

//for employeeId set in past which is use as userId now it will change as proper userId.
//    @NotBlank
//    private String employeeId;
}
