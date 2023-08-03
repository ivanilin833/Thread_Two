public class Consumer implements Runnable {
    private final Space space;

    public Consumer(Space space) {
        this.space = space;
    }

    @Override
    public void run() {
        space.get();
    }
}
