package si.skavtko.v1.provider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import com.google.gson.Gson;

import si.skavtko.entitete.Clan;


@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class ClanReader implements MessageBodyReader<Clan> {

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        // TODO Auto-generated method stub
        return type == Clan.class;
    }

    @Override
    public Clan readFrom(Class<Clan> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
            throws IOException, WebApplicationException {
        // TODO Auto-generated method stub
        try(BufferedReader br =new BufferedReader(new InputStreamReader(entityStream))){
            Gson gson = new Gson();
            Clan data = gson.fromJson(br, Clan.class);
            return data;
        }catch (Exception e) {
            // TODO: handle exception
            System.out.println("Napaka v ClanProviderju: " + e.getClass().getCanonicalName());
            return null;
        }
    }
    
}
