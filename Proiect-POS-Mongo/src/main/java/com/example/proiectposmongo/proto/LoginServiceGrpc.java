package com.example.proiectposmongo.proto;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */

public final class LoginServiceGrpc {

  private LoginServiceGrpc() {}

  public static final String SERVICE_NAME = "LoginService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<Idm.AuthenticationRequest,
      Idm.AuthenticationReply> getAuthenticateMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "Authenticate",
      requestType = Idm.AuthenticationRequest.class,
      responseType = Idm.AuthenticationReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Idm.AuthenticationRequest,
      Idm.AuthenticationReply> getAuthenticateMethod() {
    io.grpc.MethodDescriptor<Idm.AuthenticationRequest, Idm.AuthenticationReply> getAuthenticateMethod;
    if ((getAuthenticateMethod = LoginServiceGrpc.getAuthenticateMethod) == null) {
      synchronized (LoginServiceGrpc.class) {
        if ((getAuthenticateMethod = LoginServiceGrpc.getAuthenticateMethod) == null) {
          LoginServiceGrpc.getAuthenticateMethod = getAuthenticateMethod = 
              io.grpc.MethodDescriptor.<Idm.AuthenticationRequest, Idm.AuthenticationReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "LoginService", "Authenticate"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Idm.AuthenticationRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Idm.AuthenticationReply.getDefaultInstance()))
                  .setSchemaDescriptor(new LoginServiceMethodDescriptorSupplier("Authenticate"))
                  .build();
          }
        }
     }
     return getAuthenticateMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Idm.RegisterUserRequest,
      Idm.RegisterUserReply> getRegisterUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RegisterUser",
      requestType = Idm.RegisterUserRequest.class,
      responseType = Idm.RegisterUserReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Idm.RegisterUserRequest,
      Idm.RegisterUserReply> getRegisterUserMethod() {
    io.grpc.MethodDescriptor<Idm.RegisterUserRequest, Idm.RegisterUserReply> getRegisterUserMethod;
    if ((getRegisterUserMethod = LoginServiceGrpc.getRegisterUserMethod) == null) {
      synchronized (LoginServiceGrpc.class) {
        if ((getRegisterUserMethod = LoginServiceGrpc.getRegisterUserMethod) == null) {
          LoginServiceGrpc.getRegisterUserMethod = getRegisterUserMethod = 
              io.grpc.MethodDescriptor.<Idm.RegisterUserRequest, Idm.RegisterUserReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "LoginService", "RegisterUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Idm.RegisterUserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Idm.RegisterUserReply.getDefaultInstance()))
                  .setSchemaDescriptor(new LoginServiceMethodDescriptorSupplier("RegisterUser"))
                  .build();
          }
        }
     }
     return getRegisterUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Idm.ValidateTokenRequest,
      Idm.ValidateTokenReply> getValidateTokenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ValidateToken",
      requestType = Idm.ValidateTokenRequest.class,
      responseType = Idm.ValidateTokenReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Idm.ValidateTokenRequest,
      Idm.ValidateTokenReply> getValidateTokenMethod() {
    io.grpc.MethodDescriptor<Idm.ValidateTokenRequest, Idm.ValidateTokenReply> getValidateTokenMethod;
    if ((getValidateTokenMethod = LoginServiceGrpc.getValidateTokenMethod) == null) {
      synchronized (LoginServiceGrpc.class) {
        if ((getValidateTokenMethod = LoginServiceGrpc.getValidateTokenMethod) == null) {
          LoginServiceGrpc.getValidateTokenMethod = getValidateTokenMethod = 
              io.grpc.MethodDescriptor.<Idm.ValidateTokenRequest, Idm.ValidateTokenReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "LoginService", "ValidateToken"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Idm.ValidateTokenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Idm.ValidateTokenReply.getDefaultInstance()))
                  .setSchemaDescriptor(new LoginServiceMethodDescriptorSupplier("ValidateToken"))
                  .build();
          }
        }
     }
     return getValidateTokenMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Idm.DestroyTokenRequest,
      Idm.DestroyTokenReply> getDestroyTokenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DestroyToken",
      requestType = Idm.DestroyTokenRequest.class,
      responseType = Idm.DestroyTokenReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Idm.DestroyTokenRequest,
      Idm.DestroyTokenReply> getDestroyTokenMethod() {
    io.grpc.MethodDescriptor<Idm.DestroyTokenRequest, Idm.DestroyTokenReply> getDestroyTokenMethod;
    if ((getDestroyTokenMethod = LoginServiceGrpc.getDestroyTokenMethod) == null) {
      synchronized (LoginServiceGrpc.class) {
        if ((getDestroyTokenMethod = LoginServiceGrpc.getDestroyTokenMethod) == null) {
          LoginServiceGrpc.getDestroyTokenMethod = getDestroyTokenMethod = 
              io.grpc.MethodDescriptor.<Idm.DestroyTokenRequest, Idm.DestroyTokenReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "LoginService", "DestroyToken"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Idm.DestroyTokenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Idm.DestroyTokenReply.getDefaultInstance()))
                  .setSchemaDescriptor(new LoginServiceMethodDescriptorSupplier("DestroyToken"))
                  .build();
          }
        }
     }
     return getDestroyTokenMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static LoginServiceStub newStub(io.grpc.Channel channel) {
    return new LoginServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static LoginServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new LoginServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static LoginServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new LoginServiceFutureStub(channel);
  }

  /**
   */
  public static abstract class LoginServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void authenticate(Idm.AuthenticationRequest request,
                             io.grpc.stub.StreamObserver<Idm.AuthenticationReply> responseObserver) {
      asyncUnimplementedUnaryCall(getAuthenticateMethod(), responseObserver);
    }

    /**
     */
    public void registerUser(Idm.RegisterUserRequest request,
                             io.grpc.stub.StreamObserver<Idm.RegisterUserReply> responseObserver) {
      asyncUnimplementedUnaryCall(getRegisterUserMethod(), responseObserver);
    }

    /**
     */
    public void validateToken(Idm.ValidateTokenRequest request,
                              io.grpc.stub.StreamObserver<Idm.ValidateTokenReply> responseObserver) {
      asyncUnimplementedUnaryCall(getValidateTokenMethod(), responseObserver);
    }

    /**
     */
    public void destroyToken(Idm.DestroyTokenRequest request,
                             io.grpc.stub.StreamObserver<Idm.DestroyTokenReply> responseObserver) {
      asyncUnimplementedUnaryCall(getDestroyTokenMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAuthenticateMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                Idm.AuthenticationRequest,
                Idm.AuthenticationReply>(
                  this, METHODID_AUTHENTICATE)))
          .addMethod(
            getRegisterUserMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                Idm.RegisterUserRequest,
                Idm.RegisterUserReply>(
                  this, METHODID_REGISTER_USER)))
          .addMethod(
            getValidateTokenMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                Idm.ValidateTokenRequest,
                Idm.ValidateTokenReply>(
                  this, METHODID_VALIDATE_TOKEN)))
          .addMethod(
            getDestroyTokenMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                Idm.DestroyTokenRequest,
                Idm.DestroyTokenReply>(
                  this, METHODID_DESTROY_TOKEN)))
          .build();
    }
  }

  /**
   */
  public static final class LoginServiceStub extends io.grpc.stub.AbstractStub<LoginServiceStub> {
    private LoginServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private LoginServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected LoginServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new LoginServiceStub(channel, callOptions);
    }

    /**
     */
    public void authenticate(Idm.AuthenticationRequest request,
                             io.grpc.stub.StreamObserver<Idm.AuthenticationReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAuthenticateMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void registerUser(Idm.RegisterUserRequest request,
                             io.grpc.stub.StreamObserver<Idm.RegisterUserReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRegisterUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void validateToken(Idm.ValidateTokenRequest request,
                              io.grpc.stub.StreamObserver<Idm.ValidateTokenReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getValidateTokenMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void destroyToken(Idm.DestroyTokenRequest request,
                             io.grpc.stub.StreamObserver<Idm.DestroyTokenReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getDestroyTokenMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class LoginServiceBlockingStub extends io.grpc.stub.AbstractStub<LoginServiceBlockingStub> {
    private LoginServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private LoginServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected LoginServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new LoginServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public Idm.AuthenticationReply authenticate(Idm.AuthenticationRequest request) {
      return blockingUnaryCall(
          getChannel(), getAuthenticateMethod(), getCallOptions(), request);
    }

    /**
     */
    public Idm.RegisterUserReply registerUser(Idm.RegisterUserRequest request) {
      return blockingUnaryCall(
          getChannel(), getRegisterUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public Idm.ValidateTokenReply validateToken(Idm.ValidateTokenRequest request) {
      return blockingUnaryCall(
          getChannel(), getValidateTokenMethod(), getCallOptions(), request);
    }

    /**
     */
    public Idm.DestroyTokenReply destroyToken(Idm.DestroyTokenRequest request) {
      return blockingUnaryCall(
          getChannel(), getDestroyTokenMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class LoginServiceFutureStub extends io.grpc.stub.AbstractStub<LoginServiceFutureStub> {
    private LoginServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private LoginServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected LoginServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new LoginServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Idm.AuthenticationReply> authenticate(
        Idm.AuthenticationRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getAuthenticateMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Idm.RegisterUserReply> registerUser(
        Idm.RegisterUserRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRegisterUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Idm.ValidateTokenReply> validateToken(
        Idm.ValidateTokenRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getValidateTokenMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Idm.DestroyTokenReply> destroyToken(
        Idm.DestroyTokenRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getDestroyTokenMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_AUTHENTICATE = 0;
  private static final int METHODID_REGISTER_USER = 1;
  private static final int METHODID_VALIDATE_TOKEN = 2;
  private static final int METHODID_DESTROY_TOKEN = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final LoginServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(LoginServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_AUTHENTICATE:
          serviceImpl.authenticate((Idm.AuthenticationRequest) request,
              (io.grpc.stub.StreamObserver<Idm.AuthenticationReply>) responseObserver);
          break;
        case METHODID_REGISTER_USER:
          serviceImpl.registerUser((Idm.RegisterUserRequest) request,
              (io.grpc.stub.StreamObserver<Idm.RegisterUserReply>) responseObserver);
          break;
        case METHODID_VALIDATE_TOKEN:
          serviceImpl.validateToken((Idm.ValidateTokenRequest) request,
              (io.grpc.stub.StreamObserver<Idm.ValidateTokenReply>) responseObserver);
          break;
        case METHODID_DESTROY_TOKEN:
          serviceImpl.destroyToken((Idm.DestroyTokenRequest) request,
              (io.grpc.stub.StreamObserver<Idm.DestroyTokenReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class LoginServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    LoginServiceBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Idm.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("LoginService");
    }
  }

  private static final class LoginServiceFileDescriptorSupplier
      extends LoginServiceBaseDescriptorSupplier {
    LoginServiceFileDescriptorSupplier() {}
  }

  private static final class LoginServiceMethodDescriptorSupplier
      extends LoginServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    LoginServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (LoginServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new LoginServiceFileDescriptorSupplier())
              .addMethod(getAuthenticateMethod())
              .addMethod(getRegisterUserMethod())
              .addMethod(getValidateTokenMethod())
              .addMethod(getDestroyTokenMethod())
              .build();
        }
      }
    }
    return result;
  }
}
