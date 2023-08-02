import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            threads.add(new Thread(() -> updateMap(lenValue(generateRoute("RLRFR", 100)))));
        }

        for (Thread thread : threads) thread.start();
        for (Thread thread : threads) thread.join();

        outToConsole();
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

    private static void updateMap(Integer value) {
        synchronized (sizeToFreq) {
            if (sizeToFreq.containsKey(value)) {
                sizeToFreq.put(value, sizeToFreq.get(value) + 1);
            } else {
                sizeToFreq.put(value, 1);
            }
        }
    }

    private static void outToConsole() {
        Integer maxValue = sizeToFreq.values()
                .stream()
                .max(Integer::compare).get();

        Integer key = sizeToFreq.keySet()
                .stream()
                .filter(k -> maxValue.equals(sizeToFreq.get(k)))
                .findFirst().get();
        sizeToFreq.remove(key);
        System.out.printf("""
                Самое частое количество повторений %s (встретилось %s раз)
                Другие размеры:
                """, key, maxValue);
        sizeToFreq.forEach((k, v) -> System.out.printf("- %s (%s раз)\n", k, v));
    }
}
