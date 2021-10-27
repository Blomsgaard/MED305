import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.io.Serializable;

public class HandleAGame implements Runnable{
private ArrayList<UserThread> users;
private Server server;
private int topCard = 0;

public HandleAGame(Server server){
    this.server = server;
}

    @Override
    public void run() {
        users = server.getUsers();

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

        for(int i = 0; i < users.size(); i++){
            ArrayList<SolutionCard> userHand = new ArrayList<>(5);
            for(int j = 0; j < 5; j++){
                userHand.add(solutionDeck.get(topCard));
                topCard++;
            }
            users.get(i).setUserHand(userHand);
            /*
            try {
                users.get(i).sendUserHand();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }

    }


}
