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

import si.skavtko.dto.SkupinaDTO;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class SkupinaReader implements MessageBodyReader<SkupinaDTO>  {


    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        
        return type == SkupinaDTO.class;
    }

    @Override
    public SkupinaDTO readFrom(Class<SkupinaDTO> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
            throws IOException, WebApplicationException {
        
        try(BufferedReader br =new BufferedReader(new InputStreamReader(entityStream))){
            Gson gson = new Gson();
            SkupinaDTO data = gson.fromJson(br, SkupinaDTO.class);
            return data;
        }catch (Exception e) {
            System.out.println("Napaka v ClanProviderju: " + e.getClass().getCanonicalName());
            return null;
        }
    }
    
}
