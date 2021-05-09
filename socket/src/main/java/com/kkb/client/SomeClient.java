package com.kkb.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author      bookask
 * @date        2021/5/8 下午5:27
 * @description 客户端
 */
public class SomeClient {

    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup();

        try {
            // bootstrap用于初始化channel
            // server端使用的是ServerBootstrap
            Bootstrap bootstrap = new Bootstrap();
            // 指定一个group
            // server端指定的是两个group
            bootstrap.group(group)
                    // 指定客户端端口
                    .localAddress(8011)
                    // 指定要创建的channel的类型
                    // server端指定的是NioServerSocketChannel
                    .channel(NioSocketChannel.class)
                    // 指定要使用的处理器
                    // 因为server端有两个group，所以是childHandler
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // channel一旦创建完毕，其就会同时绑定一个pipline
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            // 添加编码器
                            pipeline.addLast(new StringEncoder());
                            // 添加解码器
                            pipeline.addLast(new StringDecoder());
                            // 添加自定义的处理器
                            pipeline.addLast(new SomeClientHandler());
                        }
                    });


            ChannelFuture future = bootstrap.connect("localhost", 8888).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }

    }
}
