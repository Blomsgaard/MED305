import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class ServerRunnable implements Runnable{
    private Socket clientSocket = null;
    private String serverText = "";

    //Constructor for the class
    public ServerRunnable(Socket clientSocket, String serverText){
        this.clientSocket = clientSocket;
        this.serverText = serverText;
    }

    @Override
    public void run() {
        try{
            System.out.println("A client has been connected at:" + new Date() + '\n');
            boolean connected = true;

            DataInputStream dataFromUser = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dataToUser = new DataOutputStream(clientSocket.getOutputStream());

            while(connected){
                //Stuff happens here that makes the program work
                ArrayList<Game> gameList = new ArrayList<>();

                 boolean gameCreated = false;
                 gameCreated = dataFromUser.readBoolean();

                 if(gameCreated = true){
                     gameList.add(new Game(dataFromUser.readLine()));


                 }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
