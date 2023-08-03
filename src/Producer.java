import java.util.Random;

public class Producer implements Runnable {
    private final Space space;

    public Producer(Space space) {
        this.space = space;
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    private static Integer lenValue(String text) {
        return countMatches(text);
    }

    private static int countMatches(String str) {
        if (isEmpty(str)) {
            return 0;
        }
        int count = 0;
        int idx = 0;
        while ((idx = str.indexOf("R", idx)) != -1) {
            count++;
            idx += 1;
        }
        return count;
    }

    private static boolean isEmpty(final String s) {
        return s == null || s.length() == 0;
    }

    @Override
    public void run() {
        space.put(lenValue(generateRoute("RLRFR", 100)));
    }

}
