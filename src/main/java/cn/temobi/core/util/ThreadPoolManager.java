package cn.temobi.core.util;

import java.util.Hashtable; 
import java.util.LinkedList; 
import java.util.Queue; 
import java.util.concurrent.ArrayBlockingQueue; 
import java.util.concurrent.Executors; 
import java.util.concurrent.RejectedExecutionHandler; 
import java.util.concurrent.ScheduledExecutorService; 
import java.util.concurrent.ScheduledFuture; 
import java.util.concurrent.ThreadPoolExecutor; 
import java.util.concurrent.TimeUnit; 


public class ThreadPoolManager 
{ 
    private static Hashtable<String,ThreadPoolManager> tb =  new Hashtable<String,ThreadPoolManager>(); 

    // 线程池维护线程的最少数量 
    private final static int CORE_POOL_SIZE = 10; 

    // 线程池维护线程的最大数量 
    private final static int MAX_POOL_SIZE = 50; 

    // 线程池维护线程所允许的空闲时间 单位(毫秒)
    private final static int KEEP_ALIVE_TIME = 2000; 

    // 线程池所使用的缓冲队列大小 
    private final static int WORK_QUEUE_SIZE = 15000; 

    // 请求Request缓冲队列 
   public Queue<Runnable> msgQueue = new LinkedList<Runnable>(); 

    // 访问请求Request缓存的调度线程 
    final Runnable accessBufferThread = new Runnable() 
    { 
        public void run() 
        { 
            // 查看是否有待定请求，如果有，则添加到线程池中 
            if( hasMoreAcquire() ) 
            { 
            //SearchTask task = ( SearchTask ) msgQueue.poll(); 
                threadPool.execute( msgQueue.poll() ); 
            } 
        } 
    }; 

   
    //handler - 由于超出线程范围和队列容量而使执行被阻塞时所使用的处理程序 
    final RejectedExecutionHandler handler = new RejectedExecutionHandler() 
    { 
        public void rejectedExecution( Runnable r, ThreadPoolExecutor executor ) 
        { 
            System.out.println(r+" request 放入队列中重新等待执行 "+r ); 
            msgQueue.offer( r  ); 
        } 
    }; 


    // 管理线程池 
    final ThreadPoolExecutor threadPool = new ThreadPoolExecutor( 
            CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS, 
            new ArrayBlockingQueue( WORK_QUEUE_SIZE ),this.handler); 

    // 调度线程池 
    final ScheduledExecutorService scheduler = Executors 
            .newScheduledThreadPool( 1 ); 

    //定时调度 
    final ScheduledFuture taskHandler = scheduler.scheduleAtFixedRate( 
            accessBufferThread, 0, 1, TimeUnit.SECONDS ); 

    
    
    /** 
     * 根据key取得对应实例 
     * @param key 
     * @return 
     */ 
    public static synchronized ThreadPoolManager getInstance( String key ) 
    { 
    ThreadPoolManager obj = tb.get(key); 
    if( obj == null ) 
    { 
    System.out.println("new thread pool :"+key); 
    obj = new ThreadPoolManager(); 
    tb.put(key, obj); 
    } 
    
        return obj; 
    } 

    private ThreadPoolManager(){} 

    private boolean hasMoreAcquire() 
    { 
        return !msgQueue.isEmpty(); 
    } 

    public void addTask( Runnable task ) 
    { 
        threadPool.execute( task ); 
    } 
} 