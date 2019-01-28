package com.ningzhengao.util;

public class Test {
    public static void main(String[] args) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Test1.a();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();

        try {
            for(int i=0;i<100;i++){
                System.out.println(i);
                Thread.sleep(1000L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


