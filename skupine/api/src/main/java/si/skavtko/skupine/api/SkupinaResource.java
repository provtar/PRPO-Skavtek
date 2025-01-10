package si.skavtko.skupine.api;

import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import si.skavtko.skupine.storitve.dto.ClanSkupineDTO;
import si.skavtko.skupine.storitve.dto.SkupinaClanaDTO;
import si.skavtko.skupine.storitve.dto.SkupinaDTO;
import si.skavtko.skupine.storitve.zrna.SkupinaZrno;

@Path("/skupine")
@Tag(name = "Upravljanje s Skupinami",
    description = "CRUD skupini, dolocis jim podatke, pa se clane dodajas v skupino")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SkupinaResource {

    @Inject
    SkupinaZrno skupinaZrno;

    @GET
    @Operation(summary = "Poisce skupino po id-ju",
        description = "Imas id skupine, poisces skupino")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Dobil si iskano skupino", content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = SkupinaDTO.class))),
        @ApiResponse(responseCode = "404", description = "Ni skupine s tem id-jem")
    })
    @Path("/{id}")
    public Response getResourceById(
        @Parameter(description = "Id skupine, ki jo isces", example = "6")
        @PathParam("id") Long id){
        SkupinaDTO skupina = skupinaZrno.getSkupina(id);
        if(skupina == null)return Response.status(Status.NOT_FOUND).build();
        
        return Response.ok(skupina).build();
    }

    @GET
    @Operation(summary = "Pridobivanje calanov skupine",
        description = "Imas id skupine, poisces njene clane")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Dobil si clane skupine", content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ClanSkupineDTO.class)))),
            // @ApiResponse(responseCode = "404", description = "Nobena skupin ni imela tega id-ja")
        })
    @Path("/{id}/clani")
    public Response getClaniSkupine (
        @Parameter(description = "Id skupine, katere clane isces", example = "6")
        @PathParam("id") Long id){
        List<ClanSkupineDTO> clani = skupinaZrno.getClaniPoSkupini(id);
        

        
        // if(clani.size() == 0){
        //     return Response.status(Status.NO_CONTENT).entity(clani).build();
        // }

        return Response.ok(clani).build();
    }

    @GET
    @Operation(summary = "Pridobi skupine, v katerih je clan",
        description = "Imas id clana, poisces skupine, v katere je bil vpleten")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Dobil si skupine v katerih je clan", content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = SkupinaDTO.class)))),
            // @ApiResponse(responseCode = "404", description = "Noben clan ni imel tega id-ja")
        })
    public Response getId(
        @Parameter(description = "Id clana, za katerga ces znat v katerih skupinah je", example = "Peter")
        @QueryParam("clanId") Long clanId){
        List<SkupinaClanaDTO> skupine = skupinaZrno.getSkupinePoClanu(clanId);
        
        // if(skupine.size() == 0){
        //     return Response.status(Status.NO_CONTENT).entity(skupine).build();
        // }

        return Response.ok(skupine).build();
    }



    //Clane se doda posebej s PUT metodo, lahko s eposlje listo
    @POST
    @Operation(summary = "Ustvari novo skupino",
        description = "Ustvarjena skupina je brez clanov")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Posodobljen je bil clan", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = SkupinaDTO.class)))
        })
    public Response dodajSkupino(
        @Parameter(description = "Podatki o skupini, ki jo ustvaris")
        SkupinaDTO data){
        SkupinaDTO novaSkupina = skupinaZrno.novaSkupina(data);
        
        return Response.status(Status.CREATED).entity(novaSkupina).build();
    }

    @PUT
    @Operation(summary = "Posodobi podatke o skupini",
        description = "Posljes podatke posodobljene skupine")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Posodobljena je bila skupina", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = SkupinaDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nobena skupina ni imela tega id-ja")
        })
    public Response posodobiSkupino(
        @Parameter(description = "Podatki o skupini, ki jo posodabljas")
        SkupinaDTO skupina){
        SkupinaDTO posodobljenaSkupina = skupinaZrno.posodobiSkupino(skupina);
        
        return Response.ok(posodobljenaSkupina).build();
    }

    @PUT
    @Operation(summary = "Doda enega clana v skupino",
        description = "Posljes id skupine, id clana, pa server nrdi svoje")
    @Path("/{id}/clani/{clanId}")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Dodal si clana v skupino", content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = ClanSkupineDTO.class))),
        @ApiResponse(responseCode = "404", description = "Noben clan ali skupina nima tega id-ja")
    })
    public Response dodajClanaVSkupino(
        @Parameter(description = "Id skupine, v katero dodajas clana", example = "6")
        @PathParam("id") Long skupinaId,
        @Parameter(description = "Id clana, ki ga v skupino dodas", example = "3")
        @PathParam("clanId") Long clanId){
        ClanSkupineDTO cs = skupinaZrno.dodajClana(skupinaId, clanId);
        
        return Response.ok(cs).build();
    }

    @PUT
    @Operation(summary = "Doda clane v skupino",
        description = "V skupino vstavi vse clane, ki so v seznamu")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Ddanih je bilo nekaj clanov", content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = ClanSkupineDTO.class))))
        })
    @Path("/{id}/clani")
    public Response dodajClaneVSkupino(
        @Parameter(description = "Id skupine v katero dodas clane", example = "5")
        @PathParam("id") Long skupinaId, 
        @Parameter(description = "Seznam id-jev clanov, ki jih zelis dodati v skupino", example = "[1, 2, 3]")
        List<Long> clani){
        Set<ClanSkupineDTO> cs = skupinaZrno.dodajClane(skupinaId, clani);
        
        return Response.ok(cs).build();
    }

    @DELETE
    @Operation(summary = "Zbrise skupino",
        description = "Si se navelical ene skupine? Zbrisi jo in bos imel vec fraj cajta.")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "Unicena je bila skupina", content = @Content())
        })
    @Path("/{id}")
    public Response deleteResource(
        @Parameter(description = "Id skupine, katere se znebis", example = "7")
        @PathParam("id") Long id){
        skupinaZrno.deleteSkupino(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @DELETE
    @Operation(summary = "Zbrise clane skupine",
        description = "Si se navelical nekaj clanov? Stran jih vrzi iz skupine.")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "Nekaj manj clanov je zdaj v skupini", content = @Content())
        })
    @Path("/{id}/clani")
    public Response zbrisiClaneSkupine(
        @Parameter(description = "Id skupine, katerih clanov se znebis", example = "7")
        @PathParam("id") Long id,
        @Parameter(description = "Seznam clanov, ki se jih znebis") List<Long> clanId){
        skupinaZrno.deleteClaneSkupine(id, clanId);
        return Response.status(Status.NO_CONTENT).build();
    }

    @DELETE
    @Operation(summary = "Zbrise enega clana skupine",
        description = "Si se navelical nekaj clanov? Stran jih crzi iz skupine.")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "Nekaj manj clanov je zdaj v skupini", content = @Content())
        })
    @Path("/{id}/clani/{clanId}")
    public Response zbrisiClanaSkupine(
        @Parameter(description = "Id skupine, katerih clanov se znebis", example = "7")
        @PathParam("id") Long id,
        @Parameter(description = "Seznam clan, ki se ga znebis")@PathParam("clanId") Long clanId){
        skupinaZrno.deleteClanaSkupine(id, clanId);
        return Response.status(Status.NO_CONTENT).build();
    }
    
    // TODO delete za clane skupine
}