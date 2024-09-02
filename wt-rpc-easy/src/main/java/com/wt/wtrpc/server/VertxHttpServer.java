package com.wt.wtrpc.server;


import io.vertx.core.Vertx;

public class VertxHttpServer implements HttpServer {

    @Override
    public void doStart(int port) {
        //创建Vert.x 实例
        Vertx vertx = Vertx.vertx();
        //创建Http服务器
        io.vertx.core.http.HttpServer server = vertx.createHttpServer();
//        server.requestHandler(request ->{
//            //处理HTTP请求
//            System.out.println("Receiced request: "+ request.method()+" "+request.uri());
//            //发送HTTP响应
//            request.response()
//                    .putHeader("content-type","text/plain")
//                    .end("Hello from Vert.x HTTP server!");
//        });
        //启动HTTP 服务器并监听指定端口

        server.requestHandler(new HttpServerHandler());

        server.listen(port,result ->{
           if (result.succeeded()){
               System.out.println("Server is now listening on port"+port);
           }
           else System.err.println("Failed to start server:"+result.cause());
        });
    }
}
