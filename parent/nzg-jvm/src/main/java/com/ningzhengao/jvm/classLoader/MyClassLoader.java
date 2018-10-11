package com.ningzhengao.jvm.classLoader;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) {
        String myPath = "D:\\mycode\\javaProject\\parent\\nzg-jvm\\" + name.replace(".", "/") + ".class";
        System.out.println(myPath);
        byte[] cLassBytes = null;
        Path path = null;
        try {
            path = Paths.get(myPath);
            cLassBytes = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Class clazz = defineClass("com.ningzhengao.jvm.classLoader." + name, cLassBytes, 0, cLassBytes.length);
        return clazz;
    }

    public static void main(String[] args) {
        try {
            System.out.println(Thread.currentThread().getContextClassLoader().getResource("./com/ningzhengao/jvm/classLoader/MyTestClass.class"));
            MyClassLoader classLoader = new MyClassLoader();
            Class<?>c = classLoader.findClass("MyTestClass");
            //获取实例
            Object instance = c.newInstance();
            //获取方法
            Method method = c.getDeclaredMethod("show");
            //执行实例的方法
            method.invoke(instance);

            new MyTestClass().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

