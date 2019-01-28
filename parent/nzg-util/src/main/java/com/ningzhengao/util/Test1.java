package com.ningzhengao.util;

public class Test1 {
    private Test1(){
        throw new RuntimeException();
    }
    public static Test1 t=new Test1();
    public  static void a(){
        System.out.println(1);
    }
}
