package com.xiushow.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OioServer {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		ServerSocket server = new ServerSocket(10010);
		System.out.println("server start...");
		while (true) {
			// 获取套接字（阻塞）
			final Socket socket = server.accept();
			System.out.println("a new client in..");
			cachedThreadPool.execute(new Runnable() {
				public void run() {
					handler(socket);
				}
			});
		}
	}
	
	public static void handler(Socket socket) {
		try {
			InputStream in = socket.getInputStream();
			byte[] bytes = new byte[1024];
			int read = -1;
			// 读取内容（阻塞）
			while ((read = in.read(bytes)) != -1) {
				System.out.println(new String(bytes, 0, read));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
