package forkJoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * https://stackoverflow.com/questions/35120181/how-to-avoid-threadlocal-corruption-from-forkjoin-continuation
 *
 * This question is NOT about how to use a ThreadLocal. My question is about the side effect of the ForkJoinPool continuation of ForkJoinTask.compute() which breaks the ThreadLocal contract.
 * In a ForkJoinTask.compute(), I pull an arbitrary static ThreadLocal.
 * The value is some arbitrary stateful object but not stateful beyond the end of the compute() call. In other words, I prepare the threadlocal object/state, use it, then dispose.
 * In principle you would put that state in the ForkJoinTasK, but just assume this thread local value is in a 3rd party lib I cannot change. Hence the static threadlocal, as it is a resource that all tasks instances will share.
 * I anticipated, tested and proved that simple ThreadLocal gets initialized only once, of course. This means that due to thread continuation beneath the ForkJoinTask.join() call, my compute() method can get called again before it even exited. This exposes the state of the object being used on the previous compute call, many stackframes higher.
 * How do you solve that undesirable exposure issue?
 *
 * The only way I currently see is to ensure new threads for every compute() call, but that defeats the F/J pool continuation and could dangerously explode the thread count.
 *
 * Isn't there something to do in the JRE core to backup TL that changed since the first ForkJoinTask and revert the entire threadlocal map as if every task.compute is the first to run on the thread?
 *
 * This question is not related at all to the linked question. My question is formulated precisely on the side effect of the ForkJoinPool continuation which break the ThreadLocal contract. – user2023577
 *
 * I don't believe so. Not in general and specially not for the ForkJoinTask where tasks are expected to be pure functions on isolated objects.
 *
 * Sometimes it is possible to change the order of the task to fork and join at the beginning and before the own task's work. That way the subtask will initialize and dispose the thread-local before returning. If that is not possible, maybe you can treat the thread-local as a stack and push, clear, and restore the value around each join.
 *
 * Share
 * Follow
 * answered Jan 31 '16 at 23:18
 *
 * m4ktub
 * 2,86611 gold badge1111 silver badges1616 bronze badges
 * I believe that a fork join pool thread should take the snapshot and push aside the threadlocals because it is his choice to perform re-entry on a task.compute(). Such re-entry is not guaranteed on all tasks, therefore there is no reason for dev to anticipate and program such protection of threadlocals at every point of his code, and to be denied using a 3rd party lib because it doesn't have such protection yet. This overlook is blocking the development of functional programming style code that would otherwise be done easily. – user2023577 Feb 8 '16 at 20:28
 */
public class test {
    static AtomicInteger nextId = new AtomicInteger();
    static long T0 = System.currentTimeMillis();
    static int N_THREADS = 5;
    static final ThreadLocal<StringBuilder> myTL = ThreadLocal.withInitial(StringBuilder::new);

    static void log(Object msg) {
        System.out.format("%09.3f %-10s %s%n", 0.001 * (System.currentTimeMillis() - T0), Thread.currentThread().getName(), " : "+msg);
    }

    public static void main(String[] args) throws Exception {
        ForkJoinPool p = new ForkJoinPool(
                N_THREADS,
                pool -> {
                    int id = nextId.incrementAndGet(); //count new threads
                    log("new FJ thread "+ id);
                    ForkJoinWorkerThread t = new ForkJoinWorkerThread(pool) {/**/};
                    t.setName("My FJThread "+id);
                    return t;
                },
                Thread.getDefaultUncaughtExceptionHandler(),
                false
        );

        LowercasingTask t = new LowercasingTask("ROOT", 3);
        p.invoke(t);

        int nt = nextId.get();
        log("number of threads was "+nt);
        if(nt > N_THREADS)
            log(">>>>>>> more threads than prescribed <<<<<<<<");
    }

    static class LowercasingTask extends RecursiveTask<String> {
        String name;
        int level;
        public LowercasingTask(String name, int level) {
            this.name = name;
            this.level = level;
        }

        @Override
        protected String compute() {
            StringBuilder sbtl = myTL.get();
            String initialValue = sbtl.toString();
            if(!initialValue.equals(""))
                log("!!!!!! BROKEN ON START!!!!!!! value = " + initialValue);

            sbtl.append(":START");

            if(level>0) {
                log(name + ": compute level " + level);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<LowercasingTask> tasks = new ArrayList<>();
                for(int i=1; i<=2; i++) {
                    LowercasingTask lt = new LowercasingTask(name+"."+i, level-1);
                    tasks.add(lt);
                    lt.fork();
                }

                for (LowercasingTask task : tasks) { //this can lead to compensation threads due to l1.join() method running lifo task lN
                    //for(int i=tasks.size()-1; i>=0; i--) { //this usually has the lN.join() method running task lN, without compensation threads.
                    task.join();
                }

                log(name+": returning from joins");

            }

            sbtl.append(":END");

            String val = sbtl.toString();
            if(!val.equals(":START:END"))
                log("!!!!!! BROKEN AT END !!!!!!! value = "+val);

            sbtl.setLength(0);
            return "done";
        }
    }

}
