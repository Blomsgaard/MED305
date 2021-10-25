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
    //An arraylist for the users joined and their Threads
    private ArrayList<UserThread> users;
    //Counts the numbers of clients
    private int numberOfClient = 0;

    //Booleans to handle the different stages of the game
    private boolean connected = true;
    private boolean lobby = false;


    public static void main(String[] args) {
        Server server = new Server();
        server.initiateLobby();
    }

    public void initiateLobby() {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                System.out.println("Server has started at:" + new Date() + '\n');

                while (connected) {
                    Socket connectToClient = serverSocket.accept();
                    numberOfClient++;

                    //Displays information about the connected clients
                    System.out.println("Client has connected at:" + new Date() + '\n');
                    System.out.println("Total number of client connected: " + numberOfClient + '\n');

                    DataInputStream dataFromUser = new DataInputStream(connectToClient.getInputStream());
                    DataOutputStream dataToUser = new DataOutputStream(connectToClient.getOutputStream());

                    lobby = dataFromUser.readBoolean();

                    while (lobby) {
                        System.out.println("Lobby has been created");

                        UserThread newUser = new UserThread(connectToClient, "Multithreaded Server");
                        users.add(newUser);

                        // = dataFromUser.readBoolean();

                    }

                }


            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    public void startGame(){
        boolean start = true;

        if(start){

            for(UserThread userThread : users){
                    userThread.sendMessage("Game has started!");
            }
            new Thread(new HandleAGame()).start();

        }
    }

}
