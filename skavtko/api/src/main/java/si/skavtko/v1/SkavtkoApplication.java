package si.skavtko.v1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.kumuluz.ee.cors.annotations.CrossOrigin;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
    info = @Info(title = "Skavtko API", 
    version = "v1.0.0", 
    contact = @Contact(name = "Peter", email = "ps11915@student.uni-lj.si"), 
    license = @License()), 
    servers = @Server(url = "http://localhost:8080/v1"))
@ApplicationPath("/v1")
@CrossOrigin(name = "vsi", allowOrigin = "*")
public class SkavtkoApplication extends Application {

}
