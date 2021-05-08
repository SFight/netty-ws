package com.kkb.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author      bookask
 * @date        2021/5/8 下午5:00
 * @description 服务端
 */
public class SomeServer {

    public static void main(String[] args) {
        // 创建一个group，用于处理客户端连接请求
        NioEventLoopGroup parentGroup = new NioEventLoopGroup();
        // 创建一个group，用于处理客户端连接上server后的后续请求
        NioEventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            // bootstrap用于初始化channel
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 指定要使用的两个group
            bootstrap.group(parentGroup, childGroup)
                    // 指定要创建的channel的类型
                    .channel(NioServerSocketChannel.class)
                    // 指定要使用的处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // 初始化channel的方法
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // channel一旦创建完毕，其就会同时绑定一个pipline
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 添加编码器
                            pipeline.addLast(new StringEncoder());
                            // 添加解码器
                            pipeline.addLast(new StringDecoder());
                            // 添加自定义的处理器
                            pipeline.addLast(new SomeServerHandler());
                        }
                    });

            // 创建channel，绑定到指定的主机（hostName，port）
            // sync()将异步变同步
            ChannelFuture future = bootstrap.bind(8888).sync();
            System.out.println("服务器888已经启动。。。。。。");
            // 当channel被关闭后，会触发closeFuture()的执行，去完成一些收尾工作
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 将两个group进行优雅关闭
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }

    }
}
