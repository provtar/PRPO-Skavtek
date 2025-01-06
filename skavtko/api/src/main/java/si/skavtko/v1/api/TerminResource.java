package si.skavtko.v1.api;

import java.time.LocalDateTime;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import si.skavtko.dto.TerminDTO;
import si.skavtko.zrna.TerminZrno;

@Path("/termini")
@Tag(name = "Upravljanje s termini",
    description = "CRUD terminov, jih isces, jih dobis, jih ustvaris, jih posodobis")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class TerminResource {

    @Inject
    TerminZrno terminZrno;

    @POST
    @Operation(summary = "Ustvari termin",
        description = "Mu poves zacetni in koncni cas")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Ustvaril si nov termin", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TerminDTO.class)))
        })
    @Path("/termin")
    public Response addResource(
        @Parameter(description = "Podatki termina, ki ga ustvarjas")
        TerminDTO data) {

        TerminDTO novTermin = terminZrno.novTermin(data);
        
        return Response.status(Status.CREATED).entity(novTermin).build();
    }

    @POST
    @Operation(summary = "Posodobi termine",
        description = "Mu poves zacetni in koncni cas ter nove termine, ki jih zelis ustvariti")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Ustvaril si nov termin", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TerminDTO.class)))
        })
    @Path("/termin")
    public Response updateResources(
        @Parameter(description = "Od katerega casa urejas")
        LocalDateTime datumOd,
        @Parameter(description = "Do katerega casa urejas")
        LocalDateTime datumDo,
        @Parameter(description = "Id clana, kateremu pripadajo termini")
        long idClan,
        @Parameter(description = "Seznam novih terminov")
        List<TerminDTO> noviTermini) {

        List<TerminDTO> ustvarjeniTermini = terminZrno.posodobiTermine(datumOd, datumDo, idClan, noviTermini);
        
        return Response.status(Status.CREATED).entity(ustvarjeniTermini).build();
    }

}
