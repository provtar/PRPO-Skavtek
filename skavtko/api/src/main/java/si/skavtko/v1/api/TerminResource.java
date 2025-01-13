package si.skavtko.v1.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
import si.skavtko.dto.TerminKratkoDTO;
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

    @GET
    @Operation(summary = "Pridobi termine v obmocju",
        description = "Mu poves zacetni in koncni cas")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Vrnjeni termini", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TerminDTO.class)))
        })
    public Response getResource(
        @Parameter(description = "Od katerega casa urejas", example = "2024-12-03T10:53:46")
        @QueryParam("od")
        String datumOdISO8601,
        @Parameter(description = "Do katerega casa urejas", example = "2024-12-03T10:53:46")
        @QueryParam("do")
        String datumDoISO8601,
        @Parameter(description = "Id clana, kateremu pripadajo termini")
        @QueryParam("clanId")
        Long clanId
        // @Parameter(description = "Podatki terminov, ki jih zelis dobiti")
        // TerminDTO data
        ) {

            LocalDateTime datumOd =LocalDateTime.parse(datumOdISO8601, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LocalDateTime datumDo =LocalDateTime.parse(datumDoISO8601, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        List<TerminDTO> termini = terminZrno.getVsebovaniTermini(datumOd, datumDo, clanId);
        
        return Response.status(Status.CREATED).entity(termini).build();
        // return Response.ok(data).build();
    }

    @POST
    @Path("/single")
    @Operation(summary = "Ustvari termin",
        description = "Mu poves zacetni in koncni cas")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Ustvaril si nov termin", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TerminDTO.class)))
        })
    public Response addResource(
        @Parameter(description = "Podatki termina, ki ga ustvarjas")
        TerminDTO data) {

        TerminDTO novTermin = terminZrno.novTermin(data);
        
        return Response.status(Status.CREATED).entity(novTermin).build();
        // return Response.ok(data).build();
    }

    @POST
    @Operation(summary = "Posodobi termine",
        description = "Mu poves zacetni in koncni cas ter nove termine, ki jih zelis ustvariti")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Ustvaril si nov termin", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = TerminDTO.class)))
        })
    public Response updateResources(
        @Parameter(description = "Od katerega casa urejas", example = "2024-12-03T10:53:46")
        @QueryParam("od")
        String datumOdISO8601,
        @Parameter(description = "Do katerega casa urejas", example = "2024-12-03T10:53:46")
        @QueryParam("do")
        String datumDoISO8601,
        @Parameter(description = "Id clana, kateremu pripadajo termini")
        @QueryParam("clanId")
        Long clanId,
        @Parameter(description = "Seznam novih terminov")
        List<TerminDTO> noviTermini) {

            LocalDateTime datumOd =LocalDateTime.parse(datumOdISO8601, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LocalDateTime datumDo =LocalDateTime.parse(datumDoISO8601, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        List<TerminDTO> ustvarjeniTermini = terminZrno.posodobiTermine(datumOd, datumDo, clanId, noviTermini);
        
        return Response.status(Status.CREATED).entity(ustvarjeniTermini).build();
        // return Response.ok(noviTermini).build();
    }

}
