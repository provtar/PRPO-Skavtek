package si.skavtko.srecanja.provider;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import si.skavtko.srecanja.dto.SrecanjeDTO;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class SrecanjeWriter implements MessageBodyWriter<SrecanjeDTO> {

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        
        return type == SrecanjeDTO.class;
    }

    @Override
    public void writeTo(SrecanjeDTO t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
            MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream)
            throws IOException, WebApplicationException {
            
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                
            entityStream.write(objectMapper.writer().without(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writeValueAsString(t).getBytes());
            entityStream.flush();
            entityStream.close();
        
    }


}