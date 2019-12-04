package com.zhu.aqs.test;

import com.google.common.util.concurrent.Monitor;

import java.io.PrintWriter;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPrintData {

    private static Integer limit = 100;
    private static Integer cur = 1;
    private static Object lock =new Object();

    private static Lock reentrantLock = new ReentrantLock();
    private static Condition conditionA = reentrantLock.newCondition();
    private static Condition conditionB = reentrantLock.newCondition();
    private static Condition conditionC = reentrantLock.newCondition();

    private static Semaphore s1= new Semaphore(1);
    private static Semaphore s2= new Semaphore(0);
    private static Semaphore s3= new Semaphore(0);

    private static Monitor monitor = new Monitor();
    private static int signal = 1;


    static class PrintThread extends Thread{
        @Override
        public void run() {
            while(cur<=limit){
                synchronized (lock){
                    System.out.println(Thread.currentThread().getName()+": "+cur++);
                    lock.notify();
                    try {
                        //当cur=limit时，由另一个线程打印最后一个数，
                        // 当前线程仍然需要wait,交由另一个线程打印最后一个数后唤醒自己，当前线程退出循环结束线程
                        //cur>limit时，说明当前线程已经打印了最后一个数，不能wait，因为此时已经没有人来唤醒它了。
                        if(cur<=limit){
                            lock.wait();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(Thread.currentThread().getName()+": end");

        }
    }

    static class PrintAThread extends Thread{
        @Override
        public void run() {
            for(int i=1;i<10;i++){
                reentrantLock.lock();
                    System.out.println(Thread.currentThread().getName()+": A");
                    conditionB.signal();
                    try {
                            conditionA.await();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                reentrantLock.unlock();
            }
            System.out.println(Thread.currentThread().getName()+": end");
        }
    }
    static class PrintBThread extends Thread{
        @Override
        public void run() {

            for(int i=1;i<10;i++){
                reentrantLock.lock();
                System.out.println(Thread.currentThread().getName()+": B");
                conditionC.signal();
                try {
                    if(i<9){
                        conditionB.await();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                reentrantLock.unlock();
            }
            System.out.println(Thread.currentThread().getName()+": end");
        }
    }
    static class PrintCThread extends Thread{
        @Override
        public void run() {

            for(int i=1;i<10;i++){
                reentrantLock.lock();
                System.out.println(Thread.currentThread().getName()+": C");
                conditionA.signal();
                try {
                    if(i<9){
                        conditionC.await();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                reentrantLock.unlock();
            }
            System.out.println(Thread.currentThread().getName()+": end");
        }
    }

    static class PrintA extends Thread{
        public void run(){
            for(int i=0;i<10;i++){
                try {
                    s1.acquire();
                    System.out.println(Thread.currentThread().getName()+": A");
                    s2.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    static class PrintB extends Thread{
        public void run(){
            for(int i=0;i<10;i++){
                try {
                    s2.acquire();
                    System.out.println(Thread.currentThread().getName()+": B");
                    s3.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    static class PrintC extends Thread{
        public void run(){
            for(int i=0;i<10;i++){
                try {
                    s3.acquire();
                    System.out.println(Thread.currentThread().getName()+": C");
                    s1.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class PrintMonitor extends Thread{
        private String print;
        private int acceptSignal;
        private int sendSignal;

        public PrintMonitor(String print, int acceptSignal, int sendSignal) {
            this.print = print;
            this.acceptSignal = acceptSignal;
            this.sendSignal = sendSignal;
        }

        @Override
        public void run() {
            for(int i=0;i<10;i++){
                try {
                    //有锁且条件满足否则等待
                    monitor.enterWhen(new Monitor.Guard(monitor) {
                        @Override
                        public boolean isSatisfied() {
                            return signal==acceptSignal;
                        }
                    });
                    System.out.println(Thread.currentThread().getName()+": "+print);
                    signal=sendSignal;
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    monitor.leave();
                }
            }
        }
    }
    public static void main(String[] args) {
        new PrintMonitor("A",1,2).start();
        new PrintMonitor("B",2,3).start();
        new PrintMonitor("C",3,1).start();
    }

}
