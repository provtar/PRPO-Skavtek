package si.skavtko.entitete;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.NaturalId;

import si.skavtko.entitete.enums.UserRole;

@Entity
@DiscriminatorValue(UserRole.Values.ACTIVE)
public class ClanActive extends Clan{
    @NaturalId
    @Basic(optional =  false)
    private String username;
    @Basic(optional = false)
    private String password;
    @Basic(optional = false)
    @Column(updatable = false)
    private String email;

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
