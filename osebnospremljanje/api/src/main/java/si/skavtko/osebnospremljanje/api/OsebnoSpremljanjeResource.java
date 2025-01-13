package si.skavtko.osebnospremljanje.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import si.skavtko.osebnospremljanje.dto.OsebnoSpremljanjeDTO;
import si.skavtko.osebnospremljanje.zrna.OsebnoSpremljanjeZrno;

@Path("/osebno-spremljanje")
@Tag(name = "Upravljanje s zapisi OS",
    description = "Dobis zapise za clana, pa jih upravljas")
@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OsebnoSpremljanjeResource {
    
        @Inject
        OsebnoSpremljanjeZrno osebnoSpremljanjeZrno;
    
        @GET
        @Operation(summary = "Poisce vse anotacije OS za enega clana",
            description = "Pogldeas vse OS anotacije")
        @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Dobil si nekaj OS", content = @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = OsebnoSpremljanjeDTO.class)))),
            // @ApiResponse(responseCode = "404", description = "Ni clana s tem id-jem")
        })
        public Response getOS(
            @Parameter(description = "Id clan, za katerega isces OS", example = "6")
            @QueryParam("clanId") Long clanId){
                List<OsebnoSpremljanjeDTO> zapiski = osebnoSpremljanjeZrno.findOsebnoSpremljanje(clanId);
    
            return Response.ok(zapiski).build();
        }
    
        @POST
        @Operation(summary = "Ustvari nov zapis OS",
            description = "Podas mu parametre")
            @ApiResponses( value = {
                @ApiResponse(responseCode = "201", description = "Ustvaril si nov zapis", content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = OsebnoSpremljanjeDTO.class)))
            })
        public Response addResource(
            @Parameter(description = "Podatki zapisa")
            OsebnoSpremljanjeDTO data){
    
            OsebnoSpremljanjeDTO novoZapis = osebnoSpremljanjeZrno.createOsebnoSpremljanje(data);
            
            return Response.status(Status.CREATED).entity(novoZapis).build();
        }
    
        @PUT
        @Operation(summary = "Uredi zapis OS",
            description = "Slovnicna napaka? Posodobi zapis")
            @ApiResponses( value = {
                @ApiResponse(responseCode = "200", description = "Posodobil si zapis", content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = OsebnoSpremljanjeDTO.class))),
                @ApiResponse(responseCode = "404", description = "Noben zapis ni imel tega id-ja")
            })
        public Response updateResource(
            @Parameter(description = "Novi podatki o za zapis")
            OsebnoSpremljanjeDTO data){
                OsebnoSpremljanjeDTO res = null;
                try {
                    res = osebnoSpremljanjeZrno.updateOsebnoSpremljanje(data);                
                } catch (NoResultException e) {
                    return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
                }
            return Response.ok(res).build();
        }
    
        @DELETE
        @Operation(summary = "Zbrise zapis",
            description = "Zapis se vidi, da ni bil toliko pomemben")
            @ApiResponses( value = {
                @ApiResponse(responseCode = "204", description = "Zbrisal si zapis", content = @Content())
            })
        @Path("/{id}")
        public Response deleteResource(
            @Parameter(description = "Id zapisa, ki ga brises", example = "14")
            @PathParam("id") Long id){
            osebnoSpremljanjeZrno.removeOsebnoSpremljanje(id);
            return Response.status(Status.NO_CONTENT).build();
    
        }

        @POST
        @Path("/panic")
        @Hidden
        public Response scalingTest(){
            ArrayList<Long> list = new ArrayList<>();
            Random rnd = new Random(System.nanoTime());
            for(int i = 0; i < 100000; i++){
                list.add(rnd.nextLong());
            }
            System.out.println("ARRAY INITILIZED");
            boolean ordered = false;
            int poskus = 0;
            while (!ordered) {
                ordered = true;
                Collections.shuffle(list);
                for (int i = 0; i < list.size() - 1; i++) {
                        if (list.get(i) > list.get(i + 1))ordered =  false;
                }
                if(!ordered)System.out.println("Not ordered, poskus: " + ++poskus);
            }
            for(int i = 0; i < 100; i++) System.out.println(list.get(i));
            return Response.ok("List je urejen, tako zgleda").build();
        }

        @GET
        @Path("/liveness")
        @Hidden
        public Response livenessTest(){
            if(osebnoSpremljanjeZrno.checkDBconnection()){
                return Response.ok().build();
            }
            else return Response.status(Status.SERVICE_UNAVAILABLE).build();
        }
}
