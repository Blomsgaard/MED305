import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;

public class HandleAGame implements Runnable{
private ArrayList<UserThread> users;
private Server server;

private int solutionTopCard = 0;
private int problemTopCard = 0;

private int pharaoh;
private boolean game;
private boolean outOfBoundsCheck = true;
private int solutionChosen;
private int solutionWinner;
private DataInputStream dataFromUser;
private DataOutputStream dataToUser;


public HandleAGame(Server server){
    this.server = server;
}

    @Override
    public void run() {
        users = server.getUsers();
        game = true;
        pharaoh = 0;

        //Creates a deck of solution cards and adds them in an arraylist
        ArrayList<SolutionCard> solutionDeck = new ArrayList<>();
        for(SolutionText solutionText : SolutionText.values()){
            solutionDeck.add(new SolutionCard(solutionText.printSolutionText()));
        }

        //Creates a deck of problem cards and adds them in an arraylist
        ArrayList<ProblemCard> problemDeck = new ArrayList<>();
        for(ProblemText problemText : ProblemText.values()){
            problemDeck.add(new ProblemCard(problemText.printProblemText()));
        }

        //Shuffles the two decks
        Collections.shuffle(solutionDeck);
        Collections.shuffle(problemDeck);

        //Issues five SolutionCards to each user
        for(int i = 0; i < users.size(); i++){
            ArrayList<SolutionCard> userHand = new ArrayList<>(5);

            for(int j = 0; j < 5; j++){
                userHand.add(solutionDeck.get(solutionTopCard));
                solutionTopCard++;
            }
            users.get(i).setUserHand(userHand);
            System.out.println(users.get(i).getUserHand());
        }

        //Runs the game in this loop. It continues until a player has received 5 points
        while(game){

                String problem = problemDeck.get(problemTopCard).toString();
                ArrayList<SolutionChosen> solutionsChosen = new ArrayList<>();
                boolean goFurther = true;

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

                for(int i = 0; i < users.size(); i++){
                //The problem is printed to the users who must find a solution
                    if (i != pharaoh ) {

                        users.get(i).sendMessage("Choose a fitting answer to the problem below:");
                        users.get(i).sendMessage(problem);

                        users.get(i).sendMessage("Pick the best solution by its number:");
                            for(int j = 0; j < users.get(i).getUserHand().size(); j++){
                            users.get(i).sendMessage(j + ": " + users.get(i).getUserHand().get(j).toString());
                        }
                    }
                //The problem is presented to the pharaoh to read it out loud
                    else {
                        users.get(pharaoh).sendMessage("You are the pharaoh!");
                        users.get(pharaoh).sendMessage(problem);

                     }
                }



            while(goFurther) {
                //For every user that must choose a solution, the server wait to get the index value of that solution,
                //and adds them in a temp array, so they can be viewed by the pharaoh
                for (int i = 0; i < users.size(); i++) {
                    if (i != pharaoh) {
                        try {
                            outOfBoundsCheck = true;
                            while(outOfBoundsCheck) {
                                //Wait for the client to send the index value for the card chosen
                                solutionChosen = users.get(i).receiveInt();
                                // if the value from the user isn't between 0-4 (the amount of cards)
                                if (solutionChosen > 4) {
                                    users.get(i).sendMessage("Please enter a value ranging from 0-4");
                                }
                                else{
                                    outOfBoundsCheck = false;
                                }
                            }
                            SolutionCard solution = users.get(i).getUserHand().get(solutionChosen);
                            solutionsChosen.add(new SolutionChosen(solution, i));


                            //Removes the card chosen from the player hand
                            users.get(i).removeCard(solutionChosen);

                            //Checks if all the users have chosen a solution and stops the loop if they have
                            if(solutionsChosen.size() >= users.size()-1){
                                goFurther = false;
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        catch (IndexOutOfBoundsException e){

                        }
                    }
                }
            }

            //The problems chosen are displayed to all players
            for(int i = 0; i < users.size(); i++){
                if (i != pharaoh ) {
                    users.get(i).sendMessage("Wait for the Pharaoh to pick the winner");
                    users.get(i).sendMessage(problem);

                    for(int j = 0; j < solutionsChosen.size(); j++){
                        users.get(i).sendMessage(solutionsChosen.get(j).getSolutionCard().toString());
                    }
                }
                //The problem is presented to the pharaoh to read it out loud
                else {
                    users.get(pharaoh).sendMessage("Chose the best solution to the problem");
                    for(int j = 0; j < solutionsChosen.size(); j++) {
                        users.get(pharaoh).sendMessage(j + ": " + solutionsChosen.get(j).getSolutionCard().toString());
                    }
                    users.get(pharaoh).sendMessage("Press the number matching the best solution");
                }
            }

            try {
                outOfBoundsCheck = true;
                while(outOfBoundsCheck) {
                    //Waits for the pharaoh to choose a winner and receives the chosen cards index value
                    solutionWinner = users.get(pharaoh).receiveInt();
                    // if the value from the pharaoh isn't between 0 and the amount of players
                    if(solutionWinner > users.size()-1){
                        users.get(pharaoh).sendMessage("Please enter a value ranging from 0-" + (solutionsChosen.size()-1));
                    }
                    else{
                        outOfBoundsCheck = false;
                    }
                }

                server.sendToAll("The winner is: ");
                server.sendToAll(solutionsChosen.get(solutionWinner).getUser() + ": " + solutionsChosen.get(solutionWinner).getSolutionCard().toString());
                users.get(solutionsChosen.get(solutionWinner).getUser()).increasePoints();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Print a scoreboard
            for(int i = 0; i < users.size(); i++){
                try {
                    users.get(i).sendScoreboard();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //Give the players new solution cards
            for(int i = 0; i < users.size(); i++){
                //boolean stopPrint;
                if (i != pharaoh){
                    users.get(i).addCard(solutionDeck.get(solutionTopCard));
                    solutionTopCard++;
                    //If the last card of the deck is drawn, the deck is shuffled and a new top card is drawn
                    if(solutionTopCard >= solutionDeck.size()){
                        Collections.shuffle(solutionDeck);
                        solutionTopCard = 0;
                    }
                }
            }

            //End round check
            for(int i = 0; i < users.size(); i++){
                int score = users.get(i).getPoints();
                //checks if a player has 5 points and in that case the game ends
                if(score >= 5){

                    server.sendToAll("\n" + "Game Over");
                    try {
                    users.get(i).sendScoreboard();
                    } catch (IOException e) {
                    e.printStackTrace();
                    }
                    //server.sendToAll(users.get(i).getUsername() + ": " + users.get(i).getPoints());
                    server.sendToAll(("The winner of the game is:" + users.get(i).getUsername() ));
                    server.sendToAll("Thanks for playing!");
                    game = false;
                }
            }
            //A new problemCard is chosen and a new pharaoh is assigned
            problemTopCard++;

            //A new pharaoh is chosen
            if(pharaoh < users.size() - 1) {
                pharaoh++;
            }
            else{
                pharaoh = 0;
            }

        }
    }
}
