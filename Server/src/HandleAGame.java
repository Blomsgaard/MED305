import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;

public class HandleAGame implements Runnable{
private ArrayList<UserThread> users;
private Server server;
private int solutionTopCard = 0;
private int problemTopCard = 0;
private int zhar;
private boolean game;
private DataInputStream dataFromUser;


public HandleAGame(Server server){
    this.server = server;
}

    @Override
    public void run() {
        users = server.getUsers();
        game = true;
        zhar = 0;

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

        //Printing of the deck to check if it works
        //for(int i = 0; i < problemDeck.size();i++)
          //  System.out.println(problemDeck.get(i).toString());

        //Printing of the deck to check if it works
        //for(int i = 0; i < solutionDeck.size();i++)
          //  System.out.println(solutionDeck.get(i).toString());

        //Issues five SolutionCards to each user
        for(int i = 0; i < users.size(); i++){
            ArrayList<SolutionCard> userHand = new ArrayList<>(5);

            for(int j = 0; j < 5; j++){
                userHand.add(solutionDeck.get(solutionTopCard));
                solutionTopCard++;
            }
            users.get(i).setUserHand(userHand);
            System.out.println(users.get(i).getUserHand());

            try {
                users.get(i).sendUserHand();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        server.sendToAll("Printing is working again!");
        //commit Test


        while(game){
            String problem = problemDeck.get(problemTopCard).toString();
            ArrayList<SolutionCard> solutionsChosen = new ArrayList<>();
            boolean goFurther = true;

            for(int i = 0; i < users.size(); i++){
                //The problem is printed to the users who must find a solution
                if (i != zhar ) {
                    users.get(i).sendMessage("Choose a fitting answer to the problem below:");
                    users.get(i).sendMessage(problem);

                    users.get(i).sendMessage("Pick the best solution by its number:");
                    for(int j = 0; j < users.get(i).getUserHand().size(); j++){
                        users.get(i).sendMessage(j + ": " + users.get(i).getUserHand().get(j).toString());
                    }

                }
                //The problem is presented to the zhar to read it out loud
                else {
                    users.get(zhar).sendMessage("You are the zhar!");
                    users.get(zhar).sendMessage(problem);
                }
            }

            while(goFurther) {
                //For every user that must choose a solution, the server wait to get the index value of that solution,
                //and adds them in a temp array, so they can be viewed by the zhar
                for (int i = 0; i < users.size(); i++) {
                    if (i != zhar) {
                        try {
                            System.out.println("Test");
                            int solutionChosen = users.get(i).receiveInt();
                            System.out.println("Test");
                            SolutionCard solution = users.get(i).getUserHand().get(solutionChosen);
                            solutionsChosen.add(solution);

                            if(solutionsChosen.size() < users.size()-1){
                                goFurther = false;
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            server.sendToAll(problem);
            for(int i = 0; i < solutionsChosen.size(); i++) {
                server.sendToAll(i + ": " + solutionsChosen.get(i).toString());
            }
            System.out.println(solutionsChosen);

            game = false;

        }
    }


}
