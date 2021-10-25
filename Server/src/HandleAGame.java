import java.util.ArrayList;
import java.util.Collections;

public class HandleAGame implements Runnable{


    @Override
    public void run() {

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
        for(int i = 0; i < problemDeck.size();i++)
            System.out.println(problemDeck.get(i).toString());

        //Printing of the deck to check if it works
        for(int i = 0; i < solutionDeck.size();i++)
            System.out.println(solutionDeck.get(i).toString());



    }


}
