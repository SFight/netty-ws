package com.vtears.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

/**
 * @author          vtears
 * @date            2021-05-09 17:53:19
 * @description     Http返回数据
 */
public class Response {

    private ChannelHandlerContext ctx;

    private HttpRequest r;

    public Response(ChannelHandlerContext ctx, HttpRequest r) {
        this.ctx = ctx;
        this.r = r;
    }

    public void write(String out) throws Exception {
        try {
            if (out == null || out.length() == 0) {
                return;
            }
            // 设置 http协议及请求头信息
            FullHttpResponse response = new DefaultFullHttpResponse(
                    // 设置http版本为1.1
                    HttpVersion.HTTP_1_1,
                    // 设置响应状态码
                    HttpResponseStatus.OK,
                    // 将输出值写出 编码为UTF-8
                    Unpooled.wrappedBuffer(out.getBytes("UTF-8")));
            // 设置连接类型 为 JSON
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/json");
            // 设置请求头长度
            response.headers().set(HttpHeaderNames.CONTENT_LANGUAGE, response.content().readableBytes());
            // 设置超时时间为5000ms
            response.headers().set(HttpHeaderNames.EXPIRES, 5000);
            // 当前是否支持长连接
//            if (HttpUtil.isKeepAlive(r)) {
//                // 设置连接内容为长连接
//                response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
//            }
            ctx.write(response);
        } finally {
            ctx.flush();
            ctx.close();
        }
    }
}
