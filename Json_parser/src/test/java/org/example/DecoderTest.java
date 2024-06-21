package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DecoderTest {
    String str1 = "{\"name\":\"John\",\"isStudent\":true,\"courses\":[\"Math\",\"Science\"],\"age\":30,\"siblings\":" +
            "[\"old brother\"],\"isWorker\":false}";
    String str2 = "{\"food\":[\"apple\",\"banana\",\"orange\"],\"read\":[\"book1\",\"book2\"],\"draw\":\"arts\"}";
    String str3 = "{\"height\":182,\"size\":52}";


    @org.junit.jupiter.api.Test
    void toMap() throws IOException {
        String encMap;
        Map<String, Object> map = new HashMap<>();
        Decoder decoder = new Decoder();
        Encoder encoder = new Encoder();
        CorrectChecker correctChecker = new CorrectChecker();

        System.out.println("Map 1:");
        map = decoder.toMap(str1);
        encMap = encoder.fromMap(map);
        System.out.println("Data after encode:\n" + encMap);
        boolean check1 = correctChecker.isCorrect(str1, encMap);
        System.out.println("The JSON strings are equal: " + check1 +"\n\n");

        System.out.println("Map 2:");
        map = decoder.toMap(str2);
        encMap = encoder.fromMap(map);
        System.out.println("Data after encode:\n" + encMap);
        boolean check2 = correctChecker.isCorrect(str2, encMap);
        System.out.println("The JSON strings are equal: " + check2 +"\n\n");

        System.out.println("Map 3:");
        map = decoder.toMap(str3);
        encMap = encoder.fromMap(map);
        System.out.println("Data after encode:\n" + encMap);
        boolean check3 = correctChecker.isCorrect(str3, encMap);
        System.out.println("The JSON strings are equal: " + check3 +"\n\n");

        assertTrue(check1&&check2&&check3, "tree checks must be true");
    }

    @org.junit.jupiter.api.Test
    void toObjectArr() throws IOException {
        String encObjArr;
        ArrayList<Object> list = new ArrayList<>();
        Decoder decoder = new Decoder();
        Encoder encoder = new Encoder();
        CorrectChecker correctChecker = new CorrectChecker();

        System.out.println("ObjArr 1:");
        list = decoder.toObjectArr(str1);
        encObjArr = encoder.fromObjectArr(list);
        System.out.println("Data after encode:\n" + encObjArr);
        boolean check1 = correctChecker.isCorrect(str1, encObjArr);
        System.out.println("The JSON strings are equal: " + check1 +"\n\n");

        System.out.println("ObjArr 2:");
        list = decoder.toObjectArr(str2);
        encObjArr = encoder.fromObjectArr(list);
        System.out.println("Data after encode:\n" + encObjArr);
        boolean check2 = correctChecker.isCorrect(str2, encObjArr);
        System.out.println("The JSON strings are equal: " + check2 +"\n\n");

        System.out.println("ObjArr 3:");
        list = decoder.toObjectArr(str3);
        encObjArr = encoder.fromObjectArr(list);
        System.out.println("Data after encode:\n" + encObjArr);
        boolean check3 = correctChecker.isCorrect(str3, encObjArr);
        System.out.println("The JSON strings are equal: " + check3 +"\n\n");

        assertTrue(check1&&check2&&check3, "tree checks must be true");
    }

    @org.junit.jupiter.api.Test
    void toClass() throws IOException, IllegalAccessException {
        String encClass;
        Decoder decoder = new Decoder();
        Encoder encoder = new Encoder();
        CorrectChecker correctChecker = new CorrectChecker();
        Person person = new Person();
        Love love = new Love();
        Clothes clothes = new Clothes();


        System.out.println("Class 1:");
        person = decoder.toClass(str1, Person.class);
        encClass = encoder.fromClass(person);
        System.out.println("Data after encode:\n" + encClass);
        boolean check1 = correctChecker.isCorrect(str1, encClass);
        System.out.println("The JSON strings are equal: " + check1 +"\n\n");

        System.out.println("Class 2:");
        love = decoder.toClass(str2, Love.class);
        encClass = encoder.fromClass(love);
        System.out.println("Data after encode:\n" + encClass);
        boolean check2 = correctChecker.isCorrect(str2, encClass);
        System.out.println("The JSON strings are equal: " + check2 +"\n\n");

        System.out.println("Class 3:");
        clothes = decoder.toClass(str3, Clothes.class);
        encClass = encoder.fromClass(clothes);
        System.out.println("Data after encode:\n" + encClass);
        boolean check3 = correctChecker.isCorrect(str3, encClass);
        System.out.println("The JSON strings are equal: " + check3 +"\n\n");

        assertTrue(check1&&check2&&check3, "tree checks must be true");
    }
}