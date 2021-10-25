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

    //Booleans to handle the different stages of the game
    private boolean connected = true;
    private boolean lobby = true;


    public static void main(String[] args) {
        Server server = new Server();
        server.initiateLobby();
    }

    public void initiateLobby() {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                System.out.println("Server has started at:" + new Date() + '\n');
                users = new ArrayList<>();

                while (lobby) {
                    Socket connectToClient = serverSocket.accept();


                    //Displays information about the connected clients
                    System.out.println("Client has connected at:" + new Date() + '\n');

                    UserThread newUser = new UserThread(this, connectToClient);
                    users.add(newUser);
                    newUser.start();

                    System.out.println("Total number of client connected: " + users.size() + '\n');

                    //DataInputStream dataFromUser = new DataInputStream(connectToClient.getInputStream());
                    //DataOutputStream dataToUser = new DataOutputStream(connectToClient.getOutputStream());

                }


            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    public ArrayList<UserThread> getUsers() {
        return users;
    }

    public void sendToAll(String message){
        for(int i = 0; i < users.size(); i++){
            users.get(i).sendMessage(message);
        }
    }

    public void startGame(){
        boolean lobby = false;
        boolean start = true;

        if(start){

            sendToAll("Game has started!");

            new Thread(new HandleAGame(this)).start();

        }
    }

}
