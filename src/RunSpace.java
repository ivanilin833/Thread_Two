import java.util.ArrayList;
import java.util.List;

public class RunSpace {
    public static void main(String[] args) {
        Space space = new Space();

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            threads.add(new Thread(new Producer(space)));
        }
        Thread threadLog = new Thread(new Consumer(space));
        for (Thread th : threads) {
            th.start();
        }
        threadLog.start();
        for (Thread th : threads) {
            try {
                th.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        threadLog.interrupt();
        space.outToConsole();

    }
//    private static void outToConsole(Space space) {
//        Result result = getResult();
//        space.sizeToFreq.remove(result.key());
//        System.out.printf("""
//                Самое частое количество повторений %s (встретилось %s раз)
//                Другие размеры:
//                """, result.key(), result.maxValue());
//        space.sizeToFreq.forEach((k, v) -> System.out.printf("- %s (%s раз)\n", k, v));
//    }
//    public Result getResult() {
//        Integer maxValue = sizeToFreq.values()
//                .stream()
//                .max(Integer::compare).get();
//
//        Integer key = sizeToFreq.keySet()
//                .stream()
//                .filter(k -> maxValue.equals(sizeToFreq.get(k)))
//                .findFirst().get();
//        Space.Result result = new Space.Result(maxValue, key);
//        return result;
//    }
//
//    record Result(Integer maxValue, Integer key) {
//    }
}
