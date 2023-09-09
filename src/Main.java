import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static int amountText = 10_00;
    public static BlockingQueue<String> textA = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> textB = new ArrayBlockingQueue<>(100);
    public static BlockingQueue<String> textC = new ArrayBlockingQueue<>(100);

    public static void main(String[] args) {

        new Thread(() -> {
            for (int i = 0; i < amountText; i++) {
                try {
                    textA.put(generateText("abc", 100_000));
                    textB.put(generateText("abc", 100_000));
                    textC.put(generateText("abc", 100_000));
                } catch (InterruptedException e) {
                    return;
                }
            }
        }).start();

        new Thread(() -> {
            MaxABC<String, Integer> MaxABC;
            try {
                MaxABC = getMax(textA, 'a');
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Текст с максимальным количеством букв a ("
                    + MaxABC.getCount() + "):\n" + MaxABC.getText() + "\n");
        }).start();

        new Thread(() -> {
            MaxABC<String, Integer> MaxABC;
            try {
                MaxABC = getMax(textB, 'b');
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Текст с максимальным количеством букв b ("
                    + MaxABC.getCount() + "):\n" + MaxABC.getText() + "\n");
        }).start();

        new Thread(() -> {
            MaxABC<String, Integer> MaxABC;
            try {
                MaxABC = getMax(textC, 'c');
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Текст с максимальным количеством букв c ("
                    + MaxABC.getCount() + "):\n" + MaxABC.getText() + "\n");
        }).start();
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static MaxABC<String, Integer> getMax(BlockingQueue<String> maxABC, char abc) throws InterruptedException {
        MaxABC<String, Integer> maxTextABC = new MaxABC<>();
        maxTextABC.setCount(0);
        for (int i = 0; i < amountText; i++) {
            int maxInText = 0;
            String textABC = maxABC.take();
            char[] text = textABC.toCharArray();
            for (char s : text) {
                if (s == abc) {
                    maxInText++;
                }
            }
            if (maxInText > maxTextABC.getCount()) {
                maxTextABC.setCount(maxInText);
                maxTextABC.setText(textABC);
            }
        }
        return maxTextABC;
    }
}