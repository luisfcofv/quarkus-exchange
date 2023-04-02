package is.flores.exchange.orderservice;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Path("/api/order")
@ApplicationScoped
public class OrderServiceResource {

    List<Item> items = new ArrayList<>();

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getItems() {
        return Response.ok(items).build();
    }

    @POST
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addItem(Item newItem) {
        items.add(newItem);
        return Response.ok(items).build();
    }

    @DELETE
    @RolesAllowed({"admin", "user"})
    @Path("{id}")
    public Response deleteItem(@PathParam("id") Long id) {
        items.stream().filter(item -> Objects.equals(item.id(), id)).findFirst().ifPresent(item -> items.remove(item));
        return Response.ok(items).build();
    }
}
