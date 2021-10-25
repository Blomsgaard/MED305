import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class UserThread implements Runnable{
    private Socket clientSocket = null;
    private String serverText = "";
    private DataInputStream dataFromUser;
    private DataOutputStream dataToUser;

    //Constructor for the class
    public UserThread(Socket clientSocket, String serverText){
        this.clientSocket = clientSocket;
        this.serverText = serverText;
    }

    @Override
    public void run() {
        try{
            //System.out.println("A client has been connected at:" + new Date() + '\n');
            boolean connected = true;

            dataFromUser = new DataInputStream(clientSocket.getInputStream());
            dataToUser = new DataOutputStream(clientSocket.getOutputStream());
            boolean test = true;

            while(connected){

                String name = dataFromUser.readUTF();
                System.out.println(name);


                /*//Stuff happens here that makes the program work
                ArrayList<Game> gameList = new ArrayList<>();

                 boolean gameCreated = false;
                 gameCreated = dataFromUser.readBoolean();

                 if(gameCreated = true){
                     gameList.add(new Game(dataFromUser.readUTF()));

                     //Sends the gameNames created back to the user
                     for (int i = 0; i < gameList.size(); i++){
                         dataToUser.writeUTF(gameList.get(i).getGameName());
                     }

                 }*/

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){
        try {
            dataToUser.writeUTF(message);
            dataToUser.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
