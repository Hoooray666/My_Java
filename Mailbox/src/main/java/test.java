import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: Francis
 * @Date: 2023-05-19-2:11
 * @Description: written by xiaoshizi
 */
public class test {
    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(8080)){    //将服务端创建在端口8080上
            System.out.println("正在等待客户端连接...");
            Socket socket = server.accept();
            System.out.println("客户端已连接，IP地址为："+socket.getInetAddress().getHostAddress());
            InputStream in = socket.getInputStream();  //通过
            System.out.println("接收到客户端数据：");
            while (true){
                int i = in.read();
                if(i == -1) break;
                System.out.print((char) i);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
