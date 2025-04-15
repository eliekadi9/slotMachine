public class GetPayout {

    public int calculatePayout(String[] result) {
        if (result[0].equals(result[1]) && result[1].equals(result[2])) {
            return 50; // Jackpot
        } else if (result[0].equals(result[1]) || result[1].equals(result[2]) || result[0].equals(result[2])) {
            return 20; // Partial match
        }
        return 0;
    }

    public String getMessage(int payout) {
        if (payout == 50) return "🎉 JACKPOT! You win $50!";
        if (payout == 20) return "💰 Nice! You win $20!";
        return "😞 No win. Try again!";
    }
}
