package si.skavtko.srecanja.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
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

import si.skavtko.srecanja.dto.SrecanjeDTO;
import si.skavtko.srecanja.zrna.SrecanjeZrno;

@Path("/srecanja")
@Tag(name = "Upravljanje s srecanji",
    description = "CRUD srecanj, jih isces, jih dobis, jih ustvaris, jih posodobis")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SrecanjeResource {

    @Inject
    SrecanjeZrno srecanjeZrno;

    @GET
    @Operation(summary = "Poisce srecanje na podlagi parametrov",
        description = "Si clan in isces srecanja v katerih si, al pa zelis videti srecanja, katerih se kaka skupina udelezi")
    @ApiResponses( value = {
        @ApiResponse(responseCode = "200", description = "Dobil si neakj srecanj", content = @Content(mediaType = "application/json",
        array = @ArraySchema(schema = @Schema(implementation = SrecanjeDTO.class)))),
        // @ApiResponse(responseCode = "404", description = "Ni bilo dobljenih srecanj")
    })
    public Response get(
        @Parameter(description = "Id skupine, za katero isces srecanja", example = "6")
        @QueryParam("s") Long skupinaId,
        // @Parameter(description = "Id clana, za katerega isces srecanja", example = "2")
        // @QueryParam("c") Long clanId,
        @Parameter(description = "Datum od katerega naprej se zacne srecanje", example = "2024-12-03T10:53:46")
        @QueryParam("od") String datumOd, 
        @Parameter(description = "Datum do katerega se srecanje konca", example = "2024-12-03T10:53:46")
        @QueryParam("do") String datumDo){
            LocalDateTime dod = null;
            if(datumOd != null) dod = LocalDateTime.parse(datumOd, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            LocalDateTime ddo = null;
            if(datumDo != null) ddo =LocalDateTime.parse(datumDo, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        List<SrecanjeDTO> srecanja = srecanjeZrno.getSrecanjaPoClanuInSkupini(
            // clanId, 
            skupinaId, dod, ddo);

        // if(srecanja.size() == 0){
        //     return Response.status(Status.NO_CONTENT).entity(srecanja).build();
        // }

        return Response.ok(srecanja).build();
    }

    @GET
    @Operation(summary = "Dobi srecajne po id-ju",
        description = "Das id, ti vrne srecanje")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Dobil si srecanje", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = SrecanjeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nobeno srecanje nima tega id-ja")
        })
    @Path("/{id}")
    public Response getResourceById(
        @Parameter(description = "Id srecanja, ki ga isces", example = "13")
        @PathParam("id") Long id){
        try{
            SrecanjeDTO srecanje = srecanjeZrno.getSrecanje(id);
            return Response.ok(srecanje).build();
        }catch(NoResultException nre){
            return Response.status(Status.NOT_FOUND).build();
        }catch(Exception e){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }

    @POST
    @Operation(summary = "Mu pove, da se je ustvarilo belezenje prisotnosti",
        description = "Mu poves za kteri id so bile ustvarjene prisotnosti")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Uspelo", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = SrecanjeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nobeno srecanje nima tega id-ja")
        })
    @Path("/{id}/belezi")
    public Response belezi(
        @Parameter(description = "Id srecanja, ki ima prisotnosti", example = "13")
        @PathParam("id") Long id){
        try{
            Boolean res = srecanjeZrno.belezi(id);
            if(res) return Response.ok().build();
            else return Response.status(Status.BAD_REQUEST).build();
        }catch(NoResultException nre){
            return Response.status(Status.NOT_FOUND).build();
        }catch(Exception e){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e).build();
        }
    }

    @POST
    @Operation(summary = "Ustvari srecanje",
        description = "Mu poves skupino, mu poves kaksno srecanje")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Ustvaril si novo srecanje", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = SrecanjeDTO.class)))
        })
    public Response addResource(
        @Parameter(description = "Podatki srecanja, ki ga ustvarjas")
        SrecanjeDTO data){

        SrecanjeDTO novoSrecanje = srecanjeZrno.novoSrecanje(data, data.getIdSkupine());
        
        return Response.status(Status.CREATED).entity(novoSrecanje).build();
    }

    @PUT
    @Operation(summary = "Posodobi srecanje",
        description = "Slovnicna napaka? Posodobi srecanje")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Posodobljeno je bilo srecanje", content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = SrecanjeDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nobeno srecanje ni imelo tega id-ja")
        })
    public Response updateResource(
        @Parameter(description = "Novi podatki o srecanju")
        SrecanjeDTO data){
            SrecanjeDTO res = null;
            try {
                res = srecanjeZrno.posodobiSrecanje(data);                
            } catch (NoResultException e) {
                return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
            }
        return Response.ok(res).build();
    }

    @DELETE
    @Operation(summary = "Zbrise srecanje",
        description = "Se nocete vec srecat? Zbrisi srecanje in pozabi nanj")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "Uniceno je bilo srecanje", content = @Content())
        })
    @Path("/{id}")
    public Response deleteResource(
        @Parameter(description = "Id srecanja, ki je odpadlo", example = "14")
        @PathParam("id") Long id){
        srecanjeZrno.deleteSrecanje(id);
        return Response.status(Status.NO_CONTENT).build();

    }

}