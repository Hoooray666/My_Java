import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author: Francis
 * @Date: 2023-05-19
 * @Description: written by xiaoshizi
 */
public class Main_server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(25);
        Socket socket = null;
        String ip;
        int port ;
        while(true){
            try{
                socket = server.accept();
                ip = socket.getInetAddress().getHostAddress();
                port = socket.getPort();
                System.out.println("ip is :"+ip);
                System.out.println("port is :"+port);
                Receive_Thread thread = new Receive_Thread(socket,ip,port);
                thread.start();
            }catch(Exception e){
                e.printStackTrace();
            }
        }




    }
}
