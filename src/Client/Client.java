package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Socket socket;

        final String IP_ADRESS = "localhost";
        final int PORT = 8189;

        try {
            socket = new Socket(IP_ADRESS, PORT);

            Thread message = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Scanner in = new Scanner(socket.getInputStream());

                        while(true){
                            String str = in.nextLine();
                            System.out.println(str);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            message.start();

            Scanner input = new Scanner(System.in);
            PrintWriter output = new PrintWriter(socket.getOutputStream(),true);

            while(true){
                String str = input.nextLine();

                if(str.equals("/end")){
                    System.out.println("Связь прервана");
                    output.println("Клиент отключился");
                    break;
                }

                System.out.println("Я: " + str);
                output.println("Клиент: " + str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
