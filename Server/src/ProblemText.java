public enum ProblemText {
    A("Maybe she's born with it, maybe it's ____."),
    B("Old McDonald had a _____, Ia ia ohh"),
    C("I'm going on a cleanse this week. Nothing but kale juice and ____."),
    D("I got 99 problems but ____ ain't one."),
    E("Hey guys, welcome to Chili's! Would you like to start the night off right with ____?"),
    F("What will always get you laid?"),
    G("A romantic, candlelit dinner would be incomplete without ____."),
    H("White people like ____."),
    I("What's that smell?"),
    J("I get by with a little help from ____."),
    K("Airport security guidelines now prohibit ____ on airplanes."),
    L("The school trip was completely ruined by ____."),
    M("Oi! Show us ____!"),
    N("Science will never explain ____."),
    O("During his midlife crisis, my dad got really into ____."),
    P("Money can't buy me love, but it can buy me ____."),
    Q("Future historians will agree that ____ marked the beginning of America's decline."),
    R("When I am a billionaire, I shall erect a 20-meter statue to commemorate ____."),
    S("When all else fails, I can always masturbate to ____."),
    T("My plan for world domination begins with ____."),
    U("What left this stain on my couch?"),
    V("Introducing X-treme Baseball! It's like baseball, but with ____!"),
    W("Well if you'll excuse me, gentlemen, I have a date with ____."),
    X("Just once I'd like to hear you say \"Thanks, Mom. Thanks for ____.\""),
    Y("Click Here for ____!!!"),
    Z("Just saw this upsetting video! Please retweet!! #stop ____"),
    AA("In the new Disney Channel Original Movie, Hannah Montana struggles with ____ for the first time."),
    AB("I'm not going to lie. I despise ____. There, I said it."),
    AC("All classes today are canceled due to ____."),
    AD("Excuse me, waiter. Could you take this back? This soup tastes like ____."),
    AE("It's beginning to look a lot like ____."),
    AF("____: Achievement unlocked."),
    AG("My body, my voice! ____, my choice!"),
    AH("Donald Trump's first act as president was to outlaw ____."),
    AI("You can't wait forever. It's time to talk to your doctor about ____."),
    AJ("What's wrong with these gorillas?"),
    AK("You say tomato, I say ____."),
    AL("America is hungry. America needs ____."),
    AM("Nothing says \"I love you\" like ____.");
   // AN(""),
   // AO(""),
   // AP(""),
   // AQ(""),
   // AR(""),
   // AS(""),
   // AT(""),
   // AU(""),
   // AV(""),
   // AW(""),
   // AX(""),
   // AY(""),
   // AZ("");




    private final String problemText;

    //Constructor for the solutiontexts
    private ProblemText(String problemText){
        this.problemText = problemText;
    }

    //A method to acces the String for of the list and not the label
    public String printProblemText(){
        return problemText;
    }

}
