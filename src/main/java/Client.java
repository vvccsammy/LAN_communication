import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {

        Socket socket = new Socket("47.119.190.35", 6000);
//        Socket socket = new Socket("localhost", 8888);

        Thread thread = new Thread(new ReceiveMessageThread(socket));

        thread.start();

//
//        inputStream.close();

        System.out.println("input messages:");

        while (true){

                String input = new Scanner(System.in).nextLine();

                sendMessages(socket, input);


        }

//        socket.close();


    }

    private  static  void  sendMessages(Socket socket,String message) throws IOException {
        OutputStream outputStream = socket.getOutputStream();

        PrintWriter out = new PrintWriter(outputStream);
        // 如果out有内容，则打印


        out.println(message);
        out.flush();

//        out.close();
    }
}

class ReceiveMessageThread implements Runnable {
    private final Socket socket;

    public ReceiveMessageThread(Socket socket) {

        this.socket = socket;
    }

    @Override
//    重写run方法
    public void run() {
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String message = null;
        while (true) {
            try {
                if (!((message = reader.readLine())!=null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            // 接收服务器消息



//        if (message != null) {
            System.out.println("收到服务器消息: " + message);
        }
    }
}