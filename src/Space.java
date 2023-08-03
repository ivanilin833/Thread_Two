import java.util.HashMap;
import java.util.Map;

public class Space {
    public final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    public boolean isPut = true;

    public void put(Integer key) {
        synchronized (sizeToFreq) {
            while (!isPut) {
                try {
                    sizeToFreq.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if (sizeToFreq.containsKey(key)) {
                sizeToFreq.put(key, sizeToFreq.get(key) + 1);
            } else {
                sizeToFreq.put(key, 1);
            }
            isPut = false;
            sizeToFreq.notifyAll();
        }
    }

    public void get() {
        while (!Thread.interrupted()) {
            synchronized (sizeToFreq) {
                while (isPut) {
                    try {
                        sizeToFreq.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }

                Result result = getResult();
                System.out.printf("Самое частое количество на данный момент повторений %s (встретилось %s раз)%n",
                        result.key(), result.maxValue());
                isPut = true;
                sizeToFreq.notifyAll();
            }
        }
    }

    private Result getResult() {
        Integer maxValue = sizeToFreq.values()
                .stream()
                .max(Integer::compare).get();

        Integer key = sizeToFreq.keySet()
                .stream()
                .filter(k -> maxValue.equals(sizeToFreq.get(k)))
                .findFirst().get();
        return new Result(maxValue, key);
    }

    public void outToConsole() {
        Result result = getResult();
        sizeToFreq.remove(result.key());
        System.out.printf("""
                Самое частое количество повторений %s (встретилось %s раз)
                Другие размеры:
                """, result.key(), result.maxValue());
        sizeToFreq.forEach((k, v) -> System.out.printf("- %s (%s раз)\n", k, v));
    }

    private record Result(Integer maxValue, Integer key) {
    }
}
