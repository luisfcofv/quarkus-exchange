package is.flores.exchange.gatewayservice;

import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import is.flores.exchange.gatewayservice.models.LoginResponse;
import is.flores.exchange.grpc.userservice.FetchJwtRequest;
import is.flores.exchange.grpc.userservice.FetchJwtResponse;
import is.flores.exchange.grpc.userservice.UserService;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api")
@ApplicationScoped
public class GatewayServiceResource {

    @GrpcClient
    UserService userService;

    @GET
    @Path("/login")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<LoginResponse> getJwt() {
        var uniResponse = userService.fetchJwt(FetchJwtRequest.newBuilder().setName("name").build());
        return uniResponse.onItem().transform(FetchJwtResponse::getJwt).map(LoginResponse::new);
    }
}
