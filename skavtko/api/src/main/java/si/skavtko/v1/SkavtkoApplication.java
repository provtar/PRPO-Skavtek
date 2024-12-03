package si.skavtko.v1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

// import io.swagger.annotations.Contact;
// import io.swagger.annotations.Info;
// import io.swagger.annotations.SwaggerDefinition;

// @SwaggerDefinition(basePath = "/v1",
//     host = "http://localhost:8080",
//     info = @Info(
//         description = "API za aplikacijio Skavtek",
//         version = "v1.0.0",
//         title = "SkavtkoAPI",
//         contact = @Contact(name = "Peter", email = "ps11915@student.uni-lj.si")
//     ),
//     consumes = {"application/json"},
//     produces = {"application/json"},
//     schemes = {SwaggerDefinition.Scheme.HTTP}
//     )
@OpenAPIDefinition(
    info = @Info(title = "Skavtko API", 
    version = "v1.0.0", 
    contact = @Contact(name = "Peter", email = "ps11915@student.uni-lj.si"), 
    license = @License()), 
    servers = @Server(url = "http://localhost:8080/v1"))
@ApplicationPath("/v1")
public class SkavtkoApplication extends Application {

}
