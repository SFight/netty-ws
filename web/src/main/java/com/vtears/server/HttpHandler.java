package com.vtears.server;

import com.vtears.http.Request;
import com.vtears.http.Response;
import com.vtears.servlets.MyServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;

/**
 * @author          vtears
 * @date            2021-05-09 17:53:44
 * @description     自定义http处理器
 */
public class HttpHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest r = (HttpRequest)msg;
            Request request = new Request(ctx, r);
            Response response = new Response(ctx, r);

            MyServlet.class.newInstance().doGet(request, response);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
