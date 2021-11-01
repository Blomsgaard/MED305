public class SolutionChosen {
    private int user;
    private SolutionCard solutionCard;

    public SolutionChosen(SolutionCard solutionCard, int user){
        this.solutionCard = solutionCard;
        this.user = user;
    }

    public int getUser() {
        return user;
    }

    public SolutionCard getSolutionCard() {
        return solutionCard;
    }
}
