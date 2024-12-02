package si.skavtko.entitete;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import si.skavtko.entitete.enums.UserRole;

@Entity
@DiscriminatorValue(UserRole.Values.ACTIVE)
public class ClanActive extends Clan{
    @Basic(optional =  false)
    private String username;
    @JsonIgnore
    @Basic(optional = false)
    private String password;
    @Basic(optional = false)
    @Column(updatable = false)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "master", orphanRemoval = true)
    private Set<ClanPassive> varovanci;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
