package org.example;

import lombok.Getter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class Encoder {

    @Getter private static String dataFromMap;

    public Encoder() {
        dataFromMap="";
    }

    private String conbertOneObj(Object obj){
        if (obj instanceof Number || obj instanceof Boolean){
            return obj.toString();
        }
        else if (obj instanceof String){
            return "\""+obj+"\"";
        }
        else{
            return "";
        }
    }

    String fromMap(Map<String, Object> mapData){
         dataFromMap = "{";

        for (Map.Entry<String, Object> entry : mapData.entrySet()) {
            dataFromMap += "\""+entry.getKey()+"\":";
            Object val = entry.getValue();

            if (entry.getValue() instanceof HashMap<?,?>) {
                dataFromMap += fromMap((HashMap<String,Object>) entry.getValue());
            }
            else if (entry.getValue() instanceof ArrayList<?>){
                dataFromMap += "[";
                int iterNum = ((ArrayList<?>) entry.getValue()).size();
                for (int i = 0; i < iterNum; i++) {
                    dataFromMap+= conbertOneObj(((ArrayList<?>) entry.getValue()).get(i));
                    if(i != iterNum-1){
                        dataFromMap+=",";
                    }
                }
                dataFromMap += "]";
            }
            else{
                dataFromMap+= conbertOneObj(entry.getValue());
            }
            dataFromMap += ",";
        }
        dataFromMap = dataFromMap.substring(0,dataFromMap.length()-1);
        dataFromMap += "}";

        //System.out.println("Data from encoder"+dataFromMap);
        return dataFromMap;
    }

    String fromClass(Map<String, Object> mapData){
        dataFromMap = "{";

        for (Map.Entry<String, Object> entry : mapData.entrySet()) {
            dataFromMap += "\""+entry.getKey()+"\":";
            Object val = entry.getValue();

            if (entry.getValue() instanceof HashMap<?,?>) {
                dataFromMap += fromMap((HashMap<String,Object>) entry.getValue());
            }
            else if (entry.getValue() instanceof ArrayList<?>){
                dataFromMap += "[";
                int iterNum = ((ArrayList<?>) entry.getValue()).size();
                for (int i = 0; i < iterNum; i++) {
                    dataFromMap+= conbertOneObj(((ArrayList<?>) entry.getValue()).get(i));
                    if(i != iterNum-1){
                        dataFromMap+=",";
                    }
                }
                dataFromMap += "]";
            }
            else{
                dataFromMap+= conbertOneObj(entry.getValue());
            }
            dataFromMap += ",";
        }
        dataFromMap = dataFromMap.substring(0,dataFromMap.length()-1);
        dataFromMap += "}";

        //System.out.println("Data from encoder"+dataFromMap);
        return dataFromMap;
    }

    public <T> String fromClass(T obj) throws IOException, IllegalAccessException {
        Class<?> clas = obj.getClass();

        String string = "{";

        for (Field field : clas.getDeclaredFields()) {
            field.setAccessible(true);
            string += "\"" + field.getName() + "\":";

            Object value = field.get(obj);

            if (value instanceof String) {
                string += "\"" + value.toString() + "\"";
            }else if (value instanceof ArrayList<?>) {
                string += "[";
                for (int i = 0; i < ((ArrayList<?>) value).size(); i++) {
                    string += conbertOneObj(((ArrayList<?>) value).get(i));
                    if(i!=((ArrayList<?>) value).size()-1){
                        string += ",";
                    }
                }
                string += "]";
            } else if (value != null) {
                string += value.toString();
            } else {
                string += "null";
            }

            string += ",";
        }

        // Remove the trailing comma if the string is not empty
        if (string.length() > 1) {
            string = string.substring(0, string.length() - 1);
        }

        string += "}";

        return string;
    }
    public String fromObjectArr(ArrayList<Object> list) {
        StringBuilder str = new StringBuilder("{");

        for (Object obj : list) {
            if (obj instanceof ArrayList<?>) {
                ArrayList<?> pair = (ArrayList<?>) obj;
                if (pair.size() == 2) {
                    Object key = pair.get(0);
                    Object value = pair.get(1);

                    str.append("\"").append(key.toString()).append("\":");

                    if (value instanceof ArrayList<?>) {
                        str.append("[");
                        for (int i = 0; i < ((ArrayList<?>) value).size(); i++) {
                            str.append(conbertOneObj(((ArrayList<?>) value).get(i)));
                            if(i!=((ArrayList<?>) value).size()-1){
                                str.append(",");
                            }
                        }
                        str.append("]");
                    } else if (value instanceof Map<?, ?>) {
                        str.append(fromMap((Map<String, Object>) value));
                    } else {
                        str.append(conbertOneObj(value));
                    }

                    str.append(",");
                }
            }
        }

        // Удаляем последнюю запятую
        if (str.length() > 1) {
            str.setLength(str.length() - 1);
        }

        str.append("}");
        return str.toString();
    }
}
