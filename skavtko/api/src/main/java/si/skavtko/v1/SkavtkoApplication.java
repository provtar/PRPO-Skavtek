package si.skavtko.v1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.annotations.Contact;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

@SwaggerDefinition(basePath = "/v1",
    host = "http://localhost:8080",
    info = @Info(
        description = "API za aplikacijio Skavtek",
        version = "v1.0.0",
        title = "SkavtkoAPI",
        contact = @Contact(name = "Peter", email = "ps11915@student.uni-lj.si")
    ),
    consumes = {"application/json"},
    produces = {"application/json"},
    schemes = {SwaggerDefinition.Scheme.HTTP}
    )
@ApplicationPath("/v1")
public class SkavtkoApplication extends Application {

}
