package is.flores.exchange.orderservice;

import io.quarkus.grpc.GrpcService;
import io.smallrye.mutiny.Uni;
import is.flores.exchange.grpc.orderservice.OrderService;
import is.flores.exchange.grpc.orderservice.SubmitOrderRequest;
import is.flores.exchange.grpc.orderservice.SubmitOrderResponse;

@GrpcService
public class OrderServiceResource implements OrderService {

    @Override
    public Uni<SubmitOrderResponse> submitOrder(SubmitOrderRequest request) {
        return Uni.createFrom().item(SubmitOrderResponse.newBuilder().setId("1").build());
    }
}
