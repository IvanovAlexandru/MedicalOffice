package com.example.proiectposmongo.configs;

import com.example.proiectposmongo.proto.Idm;
import com.example.proiectposmongo.proto.LoginServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Service;

@Service
public class GrpcClientService {

    public String getUserRole(String token){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        LoginServiceGrpc.LoginServiceBlockingStub blockingStub = LoginServiceGrpc.newBlockingStub(channel);
        Idm.ValidateTokenReply validateTokenReply = blockingStub.validateToken(Idm.ValidateTokenRequest.newBuilder()
                .setToken(token)
                .build());
        channel.shutdown();
        return validateTokenReply.getRole();
    }

    public String getId(String token){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        LoginServiceGrpc.LoginServiceBlockingStub blockingStub = LoginServiceGrpc.newBlockingStub(channel);
        Idm.ValidateTokenReply validateTokenReply = blockingStub.validateToken(Idm.ValidateTokenRequest.newBuilder()
                .setToken(token)
                .build());
        channel.shutdown();
        return validateTokenReply.getId();
    }

    public String getToken(String username,String password){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        LoginServiceGrpc.LoginServiceBlockingStub blockingStub = LoginServiceGrpc.newBlockingStub(channel);
        Idm.AuthenticationReply authenticationReply = blockingStub.authenticate(Idm.AuthenticationRequest.newBuilder()
                .setUsername(username)
                .setPassword(password)
                .build());
        channel.shutdown();
        return authenticationReply.getMessage();
    }

    public String registerUser(String username,String password){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        LoginServiceGrpc.LoginServiceBlockingStub blockingStub = LoginServiceGrpc.newBlockingStub(channel);
        Idm.RegisterUserReply registerUserReply = blockingStub.registerUser(Idm.RegisterUserRequest.newBuilder()
                .setUsername(username)
                .setPassword(password)
                .build());
        channel.shutdown();
        return registerUserReply.getMessage();
    }

    public String destroyToken(String token){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        LoginServiceGrpc.LoginServiceBlockingStub blockingStub = LoginServiceGrpc.newBlockingStub(channel);

        Idm.DestroyTokenReply destroyTokenReply = blockingStub.destroyToken(Idm.DestroyTokenRequest.newBuilder()
                .setToken(token)
                .build());
        return destroyTokenReply.getMessage();
    }
}
