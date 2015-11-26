package by;

import java.util.*;

/**
 * User: Alexey.Koyro
 */
public class TestConcurrency
{
    private static int resultSum = 0;

    public static void main(String[] args)
    {
//        int source[] = {1,2,4,5,6,7,8,2};
//        CountDownLatcher countDownLatcher = new CountDownLatcher();
//        SumTask sumTask = new SumTask(source, countDownLatcher, 1);
//        SumTask sumTask1 = new SumTask(source, countDownLatcher, 2);
//        SumTask sumTask2 = new SumTask(source, countDownLatcher, 3);
//        countDownLatcher.await();
//        System.out.println(Thread.currentThread().getName());
//        List<Integer> dogs = new ArrayList<Integer>();
        Container container = new Container();
//        container.add(dogs);
//        Animal animal = new Animal();
        List<String> strings = new ArrayList<String>();

        EnumSet.allOf(ComSystem.class);


    }
    

    static <E extends Animal> void reduce(List<E> list)
    {

    }
    
    public static class SumTask implements Runnable
    {
        private int [] source;
        private CountDownLatcher latcher;
        private int num;

        public SumTask(int[] source, CountDownLatcher latcher, int num)
        {
            this.source = source;
            this.latcher = latcher;
            this.num = num;
            latcher.registerTask(this);
        }

        @Override
        public void run()
        {
            int result = 0;
            for (int aSource : source)
            {
                result += aSource;
            }
            putResult(result);
            System.out.println("Executing:" + num);
        }
    }

    public static void putResult(int result)
    {
        resultSum += result;        
    }

    public static class CountDownLatcher
    {
        private final List<Thread> tasks = new ArrayList<Thread>();
        private int count=0;
        
        public void registerTask(Runnable runnable)
        {
            tasks.add(new Thread(runnable, String.valueOf(count++)));
        }

        public void await()
        {
            for (Thread task : tasks)
            {
                task.start();
            }
            for (Thread task : tasks)
            {
                try
                {
                    task.join();
                }
                catch (InterruptedException e) { }
            }
        }

    }
}
