package com.example.demo.disruptor.singleproducermulticonsumer;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.LiteBlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ProducerConsumerMain {

    public static void main(String[] args) throws Exception {

        // 指定 ring buffer字节大小，必需为2的N次方(能将求模运算转为位运算提高效率 )，否则影响性能
        int bufferSize = 1024 * 1024;
        ;
        //固定线程数
        int nThreads = 10;


        // 创建ringBuffer
        RingBuffer<MessageEvent> ringBuffer = RingBuffer.create(ProducerType.SINGLE, MessageEvent::new, bufferSize, new LiteBlockingWaitStrategy());
        SequenceBarrier barriers = ringBuffer.newBarrier();
        // 创建10个消费者来处理同一个生产者发送过来的消息(这10个消费者不重复消费消息)
        MessageEventConsumer[] consumers = new MessageEventConsumer[10];
        for (int i = 0; i < consumers.length; i++) {
            consumers[i] = new MessageEventConsumer("C" + i);
        }
        WorkerPool<MessageEvent> workerPool = new WorkerPool<MessageEvent>(ringBuffer, barriers,
                new ExceptionHandler<MessageEvent>() {
                    @Override
                    public void handleEventException(Throwable ex, long sequence, MessageEvent event) {

                    }

                    @Override
                    public void handleOnStartException(Throwable ex) {

                    }

                    @Override
                    public void handleOnShutdownException(Throwable ex) {

                    }
                }, consumers);
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        workerPool.start(new ThreadPoolExecutor(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()));

        MessageEventProducer producer = new MessageEventProducer("P1", ringBuffer);
        for (int i = 0; i < 20; i++) {
            producer.send(i);
            Thread.sleep(100);
        }
        workerPool.drainAndHalt();
        Thread.sleep(2000);
    }
}
