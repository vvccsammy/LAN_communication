import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) throws IOException {
        //ServerSocket serverSocket = new ServerSocket(8888);
ServerSocket serverSocket = new ServerSocket(8888, 50, InetAddress.getByName("127.0.0.1"));

        //接收服客户端连接
        Socket socket = serverSocket.accept();

        System.out.println("服务器创建成功");

//        启动线程负责发消息
        SendMessageThread sendMessageThread = new SendMessageThread(socket);
        Thread thread = new Thread(sendMessageThread);
//        启动线程
        thread.start();

        OutputStream outputStream = socket.getOutputStream();

        PrintWriter out = new PrintWriter(outputStream);

        out.println("你好，我是server");

        out.flush();

        //关闭PrintWriter对象，同时关闭输出流outputStream对象
//        out.close();

//        System.out.println("received messages");
        while (true){
            receiveMessages(socket);
        }



//        socket.close();


    }

    private static  void  receiveMessages(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        // 接收服务器消息

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String message = reader.readLine();
//        if (message != null) {
            System.out.println("收到客户端消息: " + message);
//        }
    }

    }

    // 接口是用于标准化行为的，而类是用来封装、创建实体或者创建抽象事物的
class SendMessageThread implements Runnable {
    private  final  Socket socket;

    public SendMessageThread(Socket socket){

        this.socket = socket;
    }

    @Override
//    重写run方法
    public void run() {


        // 从控制台读取输入
        while (true){
            System.out.println("输入消息：");
            String input = new Scanner(System.in).nextLine();
            OutputStream outputStream = null;
            try {
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            PrintWriter out = new PrintWriter(outputStream);
            // 如果out有内容，则打印


            out.println(input);
            out.flush();
        }
    }
}