package si.skavtko.entitete;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import si.skavtko.entitete.enums.UserRole;

@Entity
@DiscriminatorValue(UserRole.Values.PASSIVE)
public class ClanPassive extends Clan {
    //Pove kdo ima oblast nad tem userjem, morda boljèe sam premaknit to med permissione, da lahko veò userjev edita
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private ClanActive master;

    //TODO dodat status, arhiviran, aktualen ipd. ni nujno
}
