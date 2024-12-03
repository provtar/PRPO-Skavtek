package si.skavtko.v1.api;



import java.util.ArrayList;

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

import com.google.gson.Gson;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import si.skavtko.entitete.Clan;
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

    //sem bi spadala se avtentikacija, se doda kasneje


    //je smiselno dodat moùnost izpisa vseh clanov? boljse
    //TODO spremenit al karkoli, ne ve,m ce je klic sploh se aktualen
    @GET
    @Operation(summary = "Iskanje po imenu",
        description = "Velikokrat imamo veliko clanov v skupini in jih ni lahko dobiti, zato jih moremo poisati po imenu in priimku, lahko isces samo po imenu, samo po priimku, po obojem ali pa sam dobis vse clane")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Bil je dobljen vsaj en clan", content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Clan.class))),
        @ApiResponse(responseCode = "404", description = "Noben clan ne ustreza kriterijem poizvedbe")

    })
    public Response getId(
        @Parameter(description = "ime", example = "Peter")
        @QueryParam("ime") String ime,
        @Parameter(example = "Klepec") 
        @QueryParam("priimek") String priimek){
        ArrayList<Clan> clani = (ArrayList<Clan>) clanZrno.getClan(ime, priimek);

        if(clani.size() == 0){
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(clani).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Iskanje clana po id-ju",
        description = "Vsakotolko si zmislis eno random stevilko, pa se vprasas kateri osebi pripada")
    public Response getResourceById(
        @PathParam("id") Long id){
        Clan result = clanZrno.getClan(id);
        if(result == null)return Response.status(Status.NOT_FOUND).build();
        
        return Response.ok(result).build();
    }



    // Doda novega clana
    @POST
    @Operation(summary = "Ustvarjanje clana",
        description = "Priporocam se, vstavljajte samo resnicne podatke")
    public Response addResource(Clan data){
        Clan ustvarjen = clanZrno.dodajClana(data);
        
        return Response.ok(ustvarjen).build();
    }

    @PUT
    @Operation(summary = "Posodabljanje clana",
        description = "Neaktualne podatke posodobi v aktualne")
    //Clan data kliće providerja, ki sprova prebrati telo requesta, providaer je v mapi provider
    public Response updateResource(Clan data){
        
        Clan updated = clanZrno.posodobiClan(data);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Brisanje clana",
        description = "Ce ti je kdo antipaticen, ga zbrises")
    public Response deleteResource(@PathParam("id") Long id){
        clanZrno.deleteClan(id);
        return Response.status(Status.NO_CONTENT).build();

    }

}
