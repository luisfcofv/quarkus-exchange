package is.flores.exchange.jwtservice;

import io.smallrye.jwt.build.Jwt;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.HashSet;

@Singleton
public class JwtService {
    final String issuer = "is.flores.exchange";

    public String generateJwt() {
        var roles = new HashSet<>(Arrays.asList("admin", "user"));
        return Jwt.issuer(issuer)
                .subject("exchange-jwt")
                .groups(roles)
                .expiresAt(System.currentTimeMillis() + 3600)
                .sign();
    }
}
