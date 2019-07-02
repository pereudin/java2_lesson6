import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");
            socket = server.accept();
            System.out.println("Клиент подключился");

            Scanner input = new Scanner(socket.getInputStream());

            Thread message = new Thread(new Runnable() {
                @Override
                public void run() {

                        while(true){
                            String str = input.nextLine();

                            if(str.equals("/end")){
                                System.out.println("Клиент отключился");
                                break;
                            }

                            System.out.println(str);
                        }
                }
            });
            message.start();

            Scanner in = new Scanner(System.in);
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

            while(true){
                String str = in.nextLine();

                if(str.equals("/end")){
                    System.out.println("Связь разорвана");
                    out.println("Сервер разорвал соединение");
                    break;
                }

                System.out.println("Сервер: " + str);
                out.println("Сервер: " + str);
            }



        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
