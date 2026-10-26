import java.util.*;

public class Q1Project {
    
    private static List<Integer> cards = new ArrayList<>(List.of(11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10));
    private static Random rand = new Random();
    private static Scanner input = new Scanner(System.in);
    private static int[] p;
    private static int[] pAce;
    private static boolean[] pBlack;

    public static void main(String[] args) {
        int deal = 0, dealAce = 0, dealCard = 0;
        System.out.print("How many players? (1-5): ");
        int players = input.nextInt();
        boolean dealBust = false, dealBlack = false;
        
        p = new int[players + 1];
        pAce = new int[players + 1];
        pBlack = new boolean[players+1];

        for (int i = 1; i <= players; i++) {
            int idx1 = rand.nextInt(cards.size());
            int card1 = cards.remove(idx1);
            
            int idx2 = rand.nextInt(cards.size());
            int card2 = cards.remove(idx2);

            System.out.printf("Player %d: %d and %d\n", i, card1, card2);
            p[i] = card1 + card2;
            if (card1 == 11) pAce[i]++;
            if (card2 == 11) pAce[i]++;

            if (p[i] == 21) System.out.println("BLACKJACK!!!!");
        }

        // Dealer logic
        int d1 = cards.remove(rand.nextInt(cards.size()));
        int d2 = cards.remove(rand.nextInt(cards.size()));
        System.out.printf("Dealer shows: %d\n", d1);
        deal = d1 + d2;

        for (int i = 1; i <= players; i++) {
            HitOrStand(i);
        }
        System.out.println("Dealer got a: "+d2);
        while(deal<17){
          dealCard = cards.remove(rand.nextInt(cards.size()));
          deal += dealCard;
          System.out.println("Dealer got a: "+dealCard);
          System.out.println("Dealer total is a: "+deal);
        }
        if(deal>21){
          System.out.println("The dealer busted!");
          dealBust = true;
        } else if (deal==21){
            System.out.println("Dealer got a Blackjack! You Lose :(");
            dealBlack = true;
        }
        else {
          System.out.printf("The dealer had a: %d\n", deal);
          
        }
        for(int i = 1; i <= players; i++){
          if(dealBust&&(p[i]>21)){
            System.out.println("Player "+i+" loses!");
          } else if(dealBust){
            System.out.println("Player "+i+" wins!");
          } else if(p[i]>21){
            System.out.println("Player "+i+" loses!");
          } else if(dealBlack&&pBlack[i]){
            System.out.println("Player "+i+" pushes!");
          }else if(dealBlack){
            System.out.println("Player "+i+" loses!");
          }else if(pBlack[i]){
            System.out.println("Player "+i+" Wins!");
          }else if(p[i]>deal){
            System.out.println("Player "+i+" wins!");
          } else if(p[i]<deal){
            System.out.println("Player "+i+" loses!");
          } else {
            System.out.println("Player "+i+" pushes!");
          }
        }
    }

    static void printTotal(int i) {
        System.out.printf("You have a total of %d (Player %d)\n", p[i], i);
    }

    static boolean CheckForBust(int i) {
        if (p[i] > 21) {
            if (pAce[i] > 0) {
                pAce[i] -= 1;
                p[i] -= 10;
                printTotal(i);
                return false; // Not busted yet
            } else {
                System.out.println("You bust!");
                return true; // Busted
            }
        }
        return false;
    }

    static void HitOrStand(int i) {
        boolean playerBusted = false;
        while (!playerBusted) {
            System.out.printf("Player %d, hit or stand? (h/s): ", i);
            String action = input.next();
            
            if (action.equalsIgnoreCase("h")) {
                int newCard = cards.remove(rand.nextInt(cards.size()));
                p[i] += newCard;
                if (newCard == 11) pAce[i]++;
                System.out.println("You got a " + newCard);
                printTotal(i);
                playerBusted = CheckForBust(i);
            } else {
                break; 
            }
        }
    }
}
