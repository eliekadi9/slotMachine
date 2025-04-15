import java.util.Random;

public class SpinRow {
    private Random rand = new Random();

    public String[] spin() {
        String[] result = new String[3];
        for (int i = 0; i < 3; i++) {
            result[i] = Symbols.SYMBOLS[rand.nextInt(Symbols.SYMBOLS.length)];
        }
        return result;
    }
}
