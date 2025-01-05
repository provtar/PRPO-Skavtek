package si.skavtko.v1.api;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.swagger.v3.oas.annotations.tags.Tag;
import si.skavtko.zrna.TerminZrno;

@Path("/termini")
@Tag(name = "Upravljanje s termini",
    description = "CRUD srecanj, jih isces, jih dobis, jih ustvaris, jih posodobis")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class TerminResource {

    @Inject
    TerminZrno terminZrno;

    
}
