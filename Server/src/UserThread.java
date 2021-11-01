import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class UserThread extends Thread implements java.io.Serializable{
    private Socket clientSocket = null;
    private String serverText = "";
    private String username;
    private Server server;
    private DataInputStream dataFromUser;
    private DataOutputStream dataToUser;
    private ObjectOutputStream objectToUser;

    private boolean startCheck;
    private boolean readyCheck;
    private int points = 0;
    private ArrayList<SolutionCard> userHand = new ArrayList<SolutionCard>(5);

    //Constructor for the class
    public UserThread(Server server, Socket clientSocket, String name){
        this.clientSocket = clientSocket;
        this.server = server;
        this.username = name;
    }

    @Override
    public void run() {
        try{
            //System.out.println("A client has been connected at:" + new Date() + '\n');
            boolean connected = true;
            startCheck = true;

            dataFromUser = new DataInputStream(clientSocket.getInputStream());
            dataToUser = new DataOutputStream(clientSocket.getOutputStream());
            boolean test = true;

            System.out.println("Nu kører det");
            while(connected){

                //String name = dataFromUser.readUTF();
                //System.out.println(name);

                 /*  readyCheck = true;
			while (readyCheck) {
				String clientMessage = dataFromUser.readUTF();
				if(clientMessage.equalsIgnoreCase("ready")) {
					server.sendToAll(username + " is ready", this);
					readyCheck = false;
					server.startGame();
				}

			}*/
                
                while(startCheck){
                    System.out.println("Wuppa");

                    boolean start = dataFromUser.readBoolean();
                    if(start){
                        System.out.println("Game has started");
                        //server.sendToAll("Game has started");
                        startCheck = false;
                        server.startGame();
                    }
                }
                
             


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

    //A method for sending messages to a user by sending a String
    public void sendMessage(String message){
        try {
            dataToUser = new DataOutputStream(clientSocket.getOutputStream());
            dataToUser.writeUTF(message);
            dataToUser.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendCard(String card){
        try {
            dataToUser.writeUTF(card);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPoints() {
        return points;
    }

    public String getUsername() {
        return username;
    }

    public void increasePoints(){
        points++;
    }


    public ArrayList<SolutionCard> getUserHand() {
        return userHand;
    }

    //Sets the user hand by receiving an array of solutioncards
    public void setUserHand(ArrayList<SolutionCard> userHand) {
        this.userHand = userHand;
    }

    public void sendUserHand() throws IOException {
        //Takes to Text from each solution card from the users hand and sends it to the client
        for(int i = 0; i < userHand.size(); i++) {
            dataToUser.writeUTF(userHand.get(i).toString());
        }

    }

    public void removeCard(int index){
        userHand.remove(index);
    }

    public int receiveInt() throws IOException {
        dataFromUser = new DataInputStream(clientSocket.getInputStream());
        int intReceived = dataFromUser.readInt();
        return intReceived;
    }

    public boolean isReadyCheck() {
		return readyCheck;
	}

}
