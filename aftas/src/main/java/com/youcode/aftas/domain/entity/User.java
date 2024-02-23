package com.youcode.aftas.domain.entity;

import com.youcode.aftas.domain.enums.IdentityDocumentType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class User implements UserDetails {
    @Id
    private Integer num;
    private String name;
    private String familyName;
    private String username;
    private String password;
    private LocalDate accessionDate;
    private String nationality;
    @Column(unique = true)
    private String identityNumber;
    private IdentityDocumentType identityDocument;

    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private List<Hunting> huntingList;

    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private List<Ranking> rankingList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        authorities.addAll(role.getPermissions());
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
