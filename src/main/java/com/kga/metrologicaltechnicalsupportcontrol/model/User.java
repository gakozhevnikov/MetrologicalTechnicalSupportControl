package com.kga.metrologicaltechnicalsupportcontrol.model;

import com.kga.metrologicaltechnicalsupportcontrol.HasId;
import com.kga.metrologicaltechnicalsupportcontrol.util.ERole;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class User implements HasId, UserDetails {

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    @Column (nullable = false)
    private String name;
    @Column (nullable = false)
    private String lastName;
    @Column (nullable = true)
    private String userName;
    @Column (unique = true)
    private String email;
    @Column
    private String company;
    @Column
    private String jobTitle;
    @Column
    private String password;

    @ElementCollection(targetClass = ERole.class)
    @CollectionTable (name = "user_name", joinColumns = @JoinColumn(name = "user_id"))
    private Set<ERole> roles = new HashSet<>();

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public User() {
    }

    public User(Long id, String name, String lastName, String userName, String email, String company,
                String jobTitle, String password, Set<ERole> roles, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.company = company;
        this.jobTitle = jobTitle;
        this.password = password;
        setRoles(roles);
        this.authorities=authorities;
    }

    public User(Long id, String userName,  String password,
                Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.userName = userName;
        this.authorities = authorities;
        this.password = password;
    }

    /*@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }*/
    /**Security*/
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
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

    public void setRoles(Collection<ERole> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(ERole.class) : EnumSet.copyOf(roles);
    }
}
