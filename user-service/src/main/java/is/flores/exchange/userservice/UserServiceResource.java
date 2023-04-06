package is.flores.exchange.userservice;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import is.flores.exchange.grpc.userservice.FetchJwtRequest;
import is.flores.exchange.grpc.userservice.FetchJwtResponse;
import is.flores.exchange.grpc.userservice.UserService;

import javax.inject.Inject;

@GrpcService
public class UserServiceResource implements UserService {

    @Inject
    JwtService jwtService;

    @Override
    public Uni<FetchJwtResponse> fetchJwt(FetchJwtRequest request) {
        String jwt = jwtService.generateJwt();
        return Uni.createFrom().item(() -> FetchJwtResponse.newBuilder().setJwt(jwt).build());
    }
}
