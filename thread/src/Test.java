import java.util.*;
import java.util.concurrent.*;


public class Test {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3); // класс при создании которого в качестве аргументов мы передаем число(сколько раз мы должны вызвать метод countDown())
                                                               // чтобы await() Больше не ждал
        ExecutorService executorService = Executors.newFixedThreadPool(3); // создание нескольких потоков
        for (int i = 0; i <  3; i++)
            executorService.submit(new Processor(i, countDownLatch));

                executorService.shutdown();

        for (int i = 0; i < 3; i++) {
            Thread.sleep(1000);
            countDownLatch.countDown();
        }

    }
}
class Processor implements Runnable{
    private  CountDownLatch countDownLatch;
    private int id;

    public Processor(int id,CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Thread with id: " + id + " proceeded");
    }
}

