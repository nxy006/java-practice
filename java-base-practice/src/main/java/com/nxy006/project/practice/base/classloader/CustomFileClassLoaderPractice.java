package com.nxy006.project.practice.base.classloader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

public class CustomFileClassLoaderPractice {
    protected static final Logger logger = LogManager.getLogger();

    @SuppressWarnings("ConfusingArgumentToVarargsMethod")
    public static void main(String[] args) {
        String className = "com.nxy006.project.practice.base.classloader.HelloWorld";
        try {
            Class<?> c = new CustomFileClassLoader().loadClass(className);
            Object o = c.newInstance();
            Method sayMethod = c.getDeclaredMethod("say", null);
            sayMethod.invoke(o, null);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static class CustomFileClassLoader extends ClassLoader {
        private static final String BASE_FILE_PATH = "classloader/";

        @Override
        public Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                return defineClass(name, data, 0, data.length);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassNotFoundException();
            }
        }

        private byte[] loadByte(String name) throws Exception {
            String className = name.substring(name.lastIndexOf('.') + 1);
            URL url = CustomFileClassLoader.class.getClassLoader().getResource(BASE_FILE_PATH + className + ".klass");
            if (url == null) {
                logger.error("loadByte failed, file not exist, path: " + BASE_FILE_PATH + className + ".class");
                throw new FileNotFoundException();
            }
            FileInputStream fis = new FileInputStream(url.getFile());

            byte[] data = new byte[fis.available()];
            fis.read(data);
            fis.close();
            return data;
        }
    }

}
