package com.example.pubsubs;

import redis.clients.jedis.Jedis;

import java.io.*;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Jedis jedis = null;

        try{
            jedis = new Jedis();

            Club club = new Club(1, "clubs", "info_pane", new Date(), 2);

            jedis.set("JDK".getBytes(), serialize(club));

            byte[] getByte = jedis.get("JDK".getBytes());

//            printing out the object serialised, with key "JDK".getBytes()
            System.out.println(getByte);

//            Unserialise data
            Object getObject = (Club)unserizlize(getByte);

            if(getObject instanceof Club){
//                System.out.println(getObject);
                System.out.println(getObject.toString());
            }

//            Object getObject = unserizlize(getByte);
//            if (getObject instanceof Club) {
//                System.out.println(getObject);
//                System.out.println(((Club) getObject).getId());
//                System.out.println(((Club) getObject).getName());
//                System.out.println(((Club) getObject).getInfo());
//                System.out.println(((Club) getObject).getCreateDate());
//                System.out.println(((Club) getObject).getRank());
//            }

        }catch (Exception e){
            System.out.println("Exception message: " + e.getMessage());
        }
    }

    private static byte[] serialize(Object object) {
        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            byte[] getByte = byteArrayOutputStream.toByteArray();
            return getByte;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object unserizlize(byte[] binaryByte) {
        ObjectInputStream objectInputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        byteArrayInputStream = new ByteArrayInputStream(binaryByte);
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object obj = objectInputStream.readObject();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
