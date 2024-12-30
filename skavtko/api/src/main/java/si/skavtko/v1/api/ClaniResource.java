package si.skavtko.v1.api;



import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
import si.skavtko.dto.ClanAktivenDTO;
import si.skavtko.dto.ClanDTO;
import si.skavtko.zrna.ClanZrno;

@Path("/clani")
@Tag(name = "Upravljanje s clani",
    description = "CRUD pasivnih clanov")
@ApplicationScoped
 @Consumes(MediaType.APPLICATION_JSON)
 @Produces(MediaType.APPLICATION_JSON)
public class ClaniResource {

    @Inject
    ClanZrno clanZrno;

    @GET
    @Operation(summary = "Iskanje po imenu",
        description = "Velikokrat imamo veliko clanov v skupini in jih ni lahko dobiti, zato jih moremo poisati po imenu in priimku, lahko isces samo po imenu, samo po priimku, po obojem ali pa sam dobis vse clane")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Bil je dobljen vsaj en clan", content = @Content(mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = ClanDTO.class)))),
        @ApiResponse(responseCode = "404", description = "Noben clan ne ustreza kriterijem poizvedbe")

    })
    public Response getId(
        @Parameter(description = "Ime", example = "Peter")
        @QueryParam("ime") String ime,
        @Parameter(description = "Priimek", example = "Klepec") 
        @QueryParam("priimek") String priimek){
        ArrayList<ClanDTO> clani = (ArrayList<ClanDTO>) clanZrno.getClan(ime, priimek);

        if(clani.size() == 0){
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(clani).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Iskanje clana po id-ju",
        description = "Vsakotolko si zmislis eno random stevilko, pa se vprasas kateri osebi pripada")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Bil je dobljen clan", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ClanDTO.class))),
            @ApiResponse(responseCode = "404", description = "Noben clan nima tega id-ja")
    
        })
    public Response getResourceById(
        @Parameter(description = "Id clana, ki ga isces", example = "2")
        @PathParam("id") Long id){
        ClanDTO result = clanZrno.getClan(id);
        if(result == null)return Response.status(Status.NOT_FOUND).build();
        
        return Response.ok(result).build();
    }

    @POST
    @Operation(summary = "Ustvarjanje pasivnega clana",
        description = "Priporocam se, vstavljajte samo resnicne podatke")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "201", description = "Ustvaril si clana", content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = ClanDTO.class)))    
    })
    public Response addResource(
        @Parameter(description = "Pove, kdo upravlja s clanom", required = true)
        @HeaderParam("master") Long id,
        @Parameter(description = "Specificiras celega clana")
        ClanDTO data){
        ClanDTO ustvarjen = clanZrno.dodajClana(data, id);
        
        return Response.status(Status.CREATED).entity(ustvarjen).build();
    }

    @POST
    @Operation(summary = "Ustvarjanje clana",
        description = "Priporocam se, vstavljajte samo resnicne podatke")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "201", description = "Ustvaril si clana", content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = ClanDTO.class))),
        @ApiResponse(responseCode = "400", description = "Email ze v rabi, ali geslo ali email ni bilo podano")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/register")
    public Response addAktivenClan(
        // @Parameter(description = "Pove, kdo upravlja s clanom")@FormParam("ime")String ime,
        // @Parameter(description = "Priimek")@FormParam("priimek")String priimek,
        // @Parameter(description = "Email, mora biti enolicna za aktivnega clana", required = true)@FormParam("email")String email,
        // @Parameter(description = "Geslo, po moznosti varno", required = true)@FormParam("password")String password
        @Parameter(description = "Podatki potrebni za vpis, ni se validacije")
            ClanAktivenDTO data
        ){
        ClanDTO ustvarjen = null;
        try{
            ustvarjen = clanZrno.registrirajClana(data.getIme(), data.getPriimek(), data.getPassword(), data.getEmail());
        }catch(Exception e){
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }        
        return Response.status(Status.CREATED).entity(ustvarjen).build();
    }

    @POST
    @Operation(summary = "Ustvarjanje clana",
        description = "Ce si to res ti, lahko vstopis")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "201", description = "Uspel si se loggat nutr", content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = ClanDTO.class))),
        @ApiResponse(responseCode = "400", description = "Napacen username ali geslo")
    })
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response login(
        @Parameter(description = "Podatki potrebni za vpis, ni se validacije")
            ClanAktivenDTO data
        ){
        ClanDTO user = null;
        try{
            user = clanZrno.login(data.getPassword(), data.getEmail());
        }catch(Exception e){
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }        
        return Response.status(Status.CREATED).entity(user).build();
    }

    @PUT
    @Operation(summary = "Posodabljanje clana",
        description = "Neaktualne podatke posodobi v aktualne")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Posodobljen je bil clan", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ClanDTO.class))),
            @ApiResponse(responseCode = "404", description = "Noben clan ni imel tega id-ja")
        })
    public Response updateResource(
        @Parameter(description = "Napolnes samo polja, ki zelis spremeniti, ostala lahko ostanejo prazna")
        ClanDTO data){
        
        ClanDTO updated = clanZrno.posodobiClan(data);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Brisanje clana",
        description = "Ce ti je kdo antipaticen, ga zbrises")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "Ubil si clana")
    
        })
    public Response deleteResource(
        @Parameter(description = "Id clana", example = "666")
        @PathParam("id") Long id){
        clanZrno.deleteClan(id);
        return Response.status(Status.NO_CONTENT).build();

    }

}
