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
            readyCheck = true;

            dataFromUser = new DataInputStream(clientSocket.getInputStream());
            dataToUser = new DataOutputStream(clientSocket.getOutputStream());
            boolean test = true;

            while(connected){
                while(readyCheck){
                    boolean start = dataFromUser.readBoolean();
                    if(start){
                        //server.sendToAll("Game has started");
                        readyCheck = false;
                        server.startGame();
                    }
                }
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

    public void increasePoints(){
        points++;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name){
        this.username = name;
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

    public void addCard(SolutionCard solutionCard){
        userHand.add(solutionCard);
    }

    public int receiveInt() throws IOException {
        dataFromUser = new DataInputStream(clientSocket.getInputStream());
        int intReceived = dataFromUser.readInt();
        return intReceived;
    }

    public boolean isReadyCheck() {
		return readyCheck;
	}

    public void sendBoolean(boolean b) throws IOException {
        dataToUser = new DataOutputStream(clientSocket.getOutputStream());
        dataToUser.writeBoolean(b);
    }

    //Method for sending playernames to the client
    public void sendPlayerNames(){
        try {
            dataToUser = new DataOutputStream(clientSocket.getOutputStream());

            ArrayList<String> playerNames = new ArrayList<>();
            for(int i = 0; i < server.getUsers().size(); i++){
                playerNames.add(server.getUsers().get(i).getUsername());
            }
            //Sents all the playernames
            for(int i = 0; i < playerNames.size(); i++){
             dataToUser.writeUTF("\t" + playerNames.get(i));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendScoreboard() throws IOException {

        dataToUser = new DataOutputStream(clientSocket.getOutputStream());

        sendMessage("Scoreboard: ");
        for(int i = 0; i < server.getUsers().size(); i++){
            dataToUser.writeUTF(server.getUsers().get(i).getUsername() + ": " + server.getUsers().get(i).getPoints());
        }
    }
}
