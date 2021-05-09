package com.kkb.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author          vtears
 * @date            2021-05-09 17:04:05
 * @description     自定义客户端处理器，用于处理来自server端的数据
 */
public class SomeClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 输出server的地址与发送来的数据
        System.out.println(ctx.channel().remoteAddress() + ", " + msg);

        ctx.channel().writeAndFlush("from client: " + LocalDateTime.now());

        TimeUnit.MICROSECONDS.sleep(500);
    }

    // 当channel被激活时触发该方法的执行，该方法只会执行一次
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().writeAndFlush("send the first data");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
