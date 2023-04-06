package is.flores.exchange.gatewayservice;

import io.quarkus.grpc.GrpcClient;
import io.smallrye.mutiny.Uni;
import is.flores.exchange.gatewayservice.models.LoginResponse;
import is.flores.exchange.gatewayservice.models.SubmitOrderHTTPRequest;
import is.flores.exchange.gatewayservice.models.SubmitOrderHTTPResponse;
import is.flores.exchange.grpc.orderservice.OrderService;
import is.flores.exchange.grpc.orderservice.SubmitOrderRequest;
import is.flores.exchange.grpc.orderservice.SubmitOrderResponse;
import is.flores.exchange.grpc.userservice.FetchJwtRequest;
import is.flores.exchange.grpc.userservice.FetchJwtResponse;
import is.flores.exchange.grpc.userservice.UserService;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api")
@ApplicationScoped
public class GatewayServiceResource {

    @GrpcClient
    UserService userService;

    @GrpcClient
    OrderService orderService;

    @GET
    @Path("/login")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<LoginResponse> getJwt() {
        var fetchJwtRequest = FetchJwtRequest.newBuilder().setName("name").build();
        var uniResponse = userService.fetchJwt(fetchJwtRequest);
        return uniResponse.onItem().transform(FetchJwtResponse::getJwt).map(LoginResponse::new);
    }

    @POST
    @Path("/order")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<SubmitOrderHTTPResponse> submitOrder(SubmitOrderHTTPRequest httpRequest) {
        var submitOrderRequest = SubmitOrderRequest.newBuilder()
                .setQuantity(httpRequest.quantity())
                .setSymbol(httpRequest.symbol())
                .build();
        var uniResponse = orderService.submitOrder(submitOrderRequest);
        return uniResponse
                .onItem()
                .transform(SubmitOrderResponse::getId)
                .map(SubmitOrderHTTPResponse::new);
    }
}
