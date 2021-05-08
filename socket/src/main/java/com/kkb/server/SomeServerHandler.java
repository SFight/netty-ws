package com.kkb.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author      bookask
 * @date        2021/5/8 下午5:18
 * @description 自定义服务端处理器，用于处理来自于客户端的数据
 */
public class SomeServerHandler extends ChannelInboundHandlerAdapter {

    // 一种回调方法：当client将数据写入到channel并发送到server后，server端就会触发该方法的执行

    /**
     *
     * @param ctx           其代表当前处理器（其实它是当前处理器所封装的一个节点）
     * @param msg           就是client端发送来的数据
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 输出client的地址与发送来的数据
        System.out.println(ctx.channel().remoteAddress() + ", " + msg);
        // 向client发送一个随机的UUID
        ctx.channel().writeAndFlush("from server: " + UUID.randomUUID());
        TimeUnit.MICROSECONDS.sleep(500);
    }

    // 一旦在服务端发生一场，就会触发该方法的执行
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 关闭channel
        ctx.close();
    }
}
