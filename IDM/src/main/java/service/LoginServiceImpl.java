package service;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.SignedJWT;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.jsonwebtoken.*;
import models.UserModel;
import proto.Idm;
import proto.LoginServiceGrpc;
import repositories.BlacklistedTokensRepository;
import repositories.UserRepository;
import utils.RoleEnum;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class LoginServiceImpl extends LoginServiceGrpc.LoginServiceImplBase {

    private final String secretKey = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
    @Override
    public void authenticate(Idm.AuthenticationRequest request, StreamObserver<Idm.AuthenticationReply> responseObserver) {

        UserModel userModel = UserRepository.authenticateUser(request.getUsername(),request.getPassword());

        if(userModel != null){
            String issuer = "http://localhost:50051/authenticate";
            long expirationTimeMillis = System.currentTimeMillis() + 3600000;

            String jwtId = UUID.randomUUID().toString();

            String token = Jwts.builder()
                    .setIssuer(issuer)
                    .setSubject(userModel.getUid())
                    .setExpiration(new Date(expirationTimeMillis))
                    .setId(jwtId)
                    .claim("role", userModel.getRoleEnum())
                    .signWith(SignatureAlgorithm.HS256, Base64.getDecoder().decode(secretKey))
                    .compact();

            Idm.AuthenticationReply reply = Idm.AuthenticationReply.newBuilder()
                    .setMessage(token)
                    .build();

            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
        else {
            responseObserver.onError(Status.UNAUTHENTICATED.asRuntimeException());
        }
    }

    @Override
    public void registerUser(Idm.RegisterUserRequest request, StreamObserver<Idm.RegisterUserReply> responseObserver) {

        UserRepository.createUser(request.getUsername(),request.getPassword(), RoleEnum.Pacient);

        Idm.RegisterUserReply reply = Idm.RegisterUserReply.newBuilder()
                .setMessage(request.getUsername())
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void validateToken(Idm.ValidateTokenRequest request, StreamObserver<Idm.ValidateTokenReply> responseObserver) {
        String token = request.getToken();

        try {
            JWT jwt = SignedJWT.parse(token);

            JWSVerifier verifier = new MACVerifier(Base64.getDecoder().decode(secretKey));
            if(jwt instanceof SignedJWT){
                SignedJWT signedJWT = (SignedJWT) jwt;
                if(signedJWT.verify(verifier)){
                    Claims claims = getClaims(token);

                    if (claims != null && BlacklistedTokensRepository.verifyToken(token).isEmpty()) {
                        Idm.ValidateTokenReply reply = Idm.ValidateTokenReply.newBuilder()
                                .setId(claims.getSubject())
                                .setRole(claims.get("role", String.class))
                                .build();
                        responseObserver.onNext(reply);
                        responseObserver.onCompleted();
                    } else {
                        responseObserver.onNext(Idm.ValidateTokenReply.newBuilder().build());
                        responseObserver.onCompleted();
                    }
                }
            }
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("An error occurred: " + e.getMessage())
                    .asException());
        }
    }

    private Claims getClaims(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(Base64.getDecoder().decode(secretKey))
                    .build()
                    .parseClaimsJws(token);

            if (claimsJws.getBody().getExpiration().before(new java.util.Date())) {
                BlacklistedTokensRepository.addToken(token);
                return null;
            }

            return claimsJws.getBody();
        } catch (JwtException e) {
            return null;
        }
    }
    @Override
    public void destroyToken(Idm.DestroyTokenRequest request, StreamObserver<Idm.DestroyTokenReply> responseObserver) {

        BlacklistedTokensRepository.addToken(request.getToken());

        Idm.DestroyTokenReply reply = Idm.DestroyTokenReply.newBuilder()
                .setMessage("destroyed")
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    public static void main(String[] args) throws Exception {
        int port = 50051;
        Server server = ServerBuilder.forPort(port)
                .addService(new LoginServiceImpl())
                .build();

        server.start();
        System.out.println("Server started, listening on " + port);

        server.awaitTermination();
    }
}
