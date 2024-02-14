package client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import proto.Idm;
import proto.LoginServiceGrpc;

public class LoginServiceClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 50051;

        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext() // For simplicity, using plaintext communication. In production, consider securing with TLS.
                .build();

        // Create a stub for your service
        LoginServiceGrpc.LoginServiceBlockingStub blockingStub = LoginServiceGrpc.newBlockingStub(channel);

        // Replace the following lines with your actual gRPC calls
        /*Idm.RegisterUserReply registerUserReply = blockingStub.registerUser(Idm.RegisterUserRequest.newBuilder()
                .setUsername("alex")
                .setPassword("metin2")
                .build());
        System.out.println(registerUserReply.getMessage());*/
        Idm.AuthenticationReply authenticationReply = blockingStub.authenticate(Idm.AuthenticationRequest.newBuilder()
                .setUsername("alex")
                .setPassword("parola")
                .build());
        System.out.println("Authentication Reply: " + authenticationReply.getMessage());
      /*  Idm.ValidateTokenReply validateTokenReply = blockingStub.validateToken(Idm.ValidateTokenRequest.newBuilder()
                .setToken("eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjUwMDUxL2F1dGhlbnRpY2F0ZSIsInN1YiI6IjYiLCJleHAiOjE3MDU5NjI0ODUsImp0aSI6ImJmMTc0YjAwLTJmYTktNDE0Mi1iMTJiLTYxOTZkMzc5NWQwZSIsInJvbGUiOiJQYWNpZW50In0.UuCfgTKyCEpmk6YNmmndNCtAhxYXGr2hcCYPb6AT648")
                .build());
        System.out.println("Authentication Reply: " + validateTokenReply.getId() + " " + validateTokenReply.getRole());*/

        /*Idm.DestroyTokenReply destroyTokenReply = blockingStub.destroyToken(Idm.DestroyTokenRequest.newBuilder()
                .setToken("eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjUwMDUxL2F1dGhlbnRpY2F0ZSIsInN1YiI6IjEiLCJleHAiOjE3MDUwOTIwNDUsImp0aSI6IjA0MWQyOTYxLTgxMjUtNGM2NC1hZGNhLTMxY2QwZmEzNDdlNyIsInJvbGUiOiJBZG1pbiJ9.2NdFTDRDaH0THiML_6Vpmhw1uh3zAwtHd58t60GOeI4")
                .build());
        System.out.println("Authentication Reply: " + destroyTokenReply.getMessage());*/

        // Close the channel
        channel.shutdown();
    }
}
