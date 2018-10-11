package com.ningzhengao.jvm.classLoader;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 自定义classloader,实现热部署
 */
public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) {
        String myPath = Thread.currentThread().getContextClassLoader().getResource("./") + name.replace(".", "/") + ".class";
        System.out.println(myPath);
        byte[] cLassBytes = null;
        Path path = null;
        try {
            path = Paths.get(new URI(myPath));
            cLassBytes = Files.readAllBytes(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Class clazz = defineClass(name, cLassBytes, 0, cLassBytes.length);
        return clazz;
    }

    public static void main(String[] args) {
        try {
            //初次编译
            new MyTestClass().show();
            System.out.println("-----------");
            //实时编译
            while (true) {
                MyClassLoader classLoader = new MyClassLoader();
                Class<?> c = classLoader.findClass("com.ningzhengao.jvm.classLoader.MyTestClass");
                //获取实例
                Object instance = c.newInstance();
                //获取方法
                Method method = c.getDeclaredMethod("show");
                //执行实例的方法
                method.invoke(instance);
                Thread.sleep(3000L);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

