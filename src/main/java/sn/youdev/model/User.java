package sn.youdev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sn.youdev.dto.response.UserResponse;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor @Table(name = "users")
public class User implements UserDetails {
    @Id
    @Column(name = "user_id")
    private Long id;
    @Column(nullable = false,unique = true)
    private String login;
    @Column(nullable = false)
    private String password;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private List<Role> roles;
    @OneToOne(optional = false,fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "user_id")
    private InfoPerso infoPerso;
    private boolean enabled = false;
    private boolean nonLocked = true;
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private List<Token> tokens;


    public User(String login, String password, List<Role> roles, InfoPerso infoPerso) {
        this.login = login;
        this.password = password;
        this.roles = roles;
        this.infoPerso = infoPerso;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(this.roles != null){
            for (Role role: this.roles
                 ) {
                authorities.add(new SimpleGrantedAuthority(role.getNom()));
            }
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.nonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
    public UserResponse getResponse(){
        return new UserResponse(this.id,this.login, infoPerso.getPrenom(),infoPerso.getNom());
    }
}
