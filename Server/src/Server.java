import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class Server {
    private static int port = 6969;


    public static void main(String[] args) {



        new Thread( () -> {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                System.out.println("Server has started at:" + new Date() + '\n');

                //Counts the numbers of clients
                int numberOfClient = 0;


                while(true){
                    Socket connectToClient = serverSocket.accept();
                    numberOfClient++;
                    boolean lobby = false;

                    //Displays information about the connected clients
                    System.out.println("Client has connected at:" + new Date() + '\n');
                    System.out.println("Total number of client connected: " + numberOfClient + '\n');

                    DataInputStream dataFromUser = new DataInputStream(connectToClient.getInputStream());
                    DataOutputStream dataToUser = new DataOutputStream(connectToClient.getOutputStream());

                    lobby = dataFromUser.readBoolean();

                    while(lobby) {

                        System.out.println("Lobby has been created");

                        new Thread(
                                new ServerRunnable(connectToClient, "Multithreaded Server")
                        ).start();

                    }



                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();

    }


}
