package org.example;
import java.io.IOException;
import java.lang.String;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.*;

import lombok.Getter;
public class Decoder {

    @Getter private static String data;
    @Getter private static Map<String, Object> dataInMap;
    @Getter private static ArrayList<Object> dataInObject;
    private static FourArray finiteStateMachine;
    private static ArrayList<Integer> doubArr;//states when token is double
    private static ArrayList<Integer> intArr;//states when token is int
    private static ArrayList<Integer> boolArr;//states when token is boolean
    private static ArrayList<Integer> strArr;//states when token is string

    public Decoder() {
        this.dataInMap = new HashMap<>();
        this.dataInObject = new ArrayList<>();

        this.finiteStateMachine = new FourArray();

        String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String lettersAndNum = letters + numbers;

        finiteStateMachine.add(new FourArray.Foure(0,"{", 1, false));
        finiteStateMachine.add(new FourArray.Foure(1,"\"", 2, false));
        finiteStateMachine.add(new FourArray.Foure(2,letters, 3, true));
        finiteStateMachine.add(new FourArray.Foure(3,lettersAndNum, 3, true));
        finiteStateMachine.add(new FourArray.Foure(3,"\"", 4, false));
        finiteStateMachine.add(new FourArray.Foure(4,":", 5, false));
        finiteStateMachine.add(new FourArray.Foure(5,"{", 1, false));
        finiteStateMachine.add(new FourArray.Foure(5,"[", 19, false));
        finiteStateMachine.add(new FourArray.Foure(5,"\"", 6, false));
        finiteStateMachine.add(new FourArray.Foure(5,numbers, 8, true));
        finiteStateMachine.add(new FourArray.Foure(5,"t", 10, true));
        finiteStateMachine.add(new FourArray.Foure(5,"f", 14, true));
        finiteStateMachine.add(new FourArray.Foure(6,"\"", 6, true));//тут ньюант, нужно добавить not
        finiteStateMachine.add(new FourArray.Foure(6,"\"", 7, false));
        finiteStateMachine.add(new FourArray.Foure(7,"}", 38, false));
        finiteStateMachine.add(new FourArray.Foure(7,",", 1, false));
        finiteStateMachine.add(new FourArray.Foure(8,numbers, 8, true));
        finiteStateMachine.add(new FourArray.Foure(8,".", 9, true));
        finiteStateMachine.add(new FourArray.Foure(8,"}", 38, false));
        finiteStateMachine.add(new FourArray.Foure(8,",", 1, false));
        finiteStateMachine.add(new FourArray.Foure(9,numbers, 9, true));
        finiteStateMachine.add(new FourArray.Foure(9,",", 1, false));
        finiteStateMachine.add(new FourArray.Foure(9,"}", 38, false));
        finiteStateMachine.add(new FourArray.Foure(10,"r", 11, true));
        finiteStateMachine.add(new FourArray.Foure(11,"u", 12, true));
        finiteStateMachine.add(new FourArray.Foure(12,"e", 13, true));
        finiteStateMachine.add(new FourArray.Foure(13,",", 1, false));
        finiteStateMachine.add(new FourArray.Foure(13,"}", 38, false));
        finiteStateMachine.add(new FourArray.Foure(14,"a", 15, true));
        finiteStateMachine.add(new FourArray.Foure(15,"l", 16, true));
        finiteStateMachine.add(new FourArray.Foure(16,"s", 17, true));
        finiteStateMachine.add(new FourArray.Foure(17,"e", 18, true));
        finiteStateMachine.add(new FourArray.Foure(18,",", 1, false));
        finiteStateMachine.add(new FourArray.Foure(18,"}", 38, false));
        finiteStateMachine.add(new FourArray.Foure(19,"]", 37, false));
        finiteStateMachine.add(new FourArray.Foure(19,"\"", 20, false));
        finiteStateMachine.add(new FourArray.Foure(19,numbers, 23, true));
        finiteStateMachine.add(new FourArray.Foure(19,"t", 26, false));
        finiteStateMachine.add(new FourArray.Foure(19,"f", 31, false));
        finiteStateMachine.add(new FourArray.Foure(20,"\"", 20, true));//тут тоже not
        finiteStateMachine.add(new FourArray.Foure(20,"\"", 21, false));
        finiteStateMachine.add(new FourArray.Foure(21,",", 22, false));
        finiteStateMachine.add(new FourArray.Foure(21,"]", 37, false));
        finiteStateMachine.add(new FourArray.Foure(22,"\"", 20, false));
        finiteStateMachine.add(new FourArray.Foure(23,numbers, 23, true));
        finiteStateMachine.add(new FourArray.Foure(23,".", 24, true));
        finiteStateMachine.add(new FourArray.Foure(24,numbers, 24, true));
        finiteStateMachine.add(new FourArray.Foure(24,"]", 37, false));
        finiteStateMachine.add(new FourArray.Foure(24,",", 25, false));
        finiteStateMachine.add(new FourArray.Foure(25,numbers, 23, true));
        finiteStateMachine.add(new FourArray.Foure(26,"r", 27, true));
        finiteStateMachine.add(new FourArray.Foure(27,"u", 28, true));
        finiteStateMachine.add(new FourArray.Foure(28,"e", 29, true));
        finiteStateMachine.add(new FourArray.Foure(29,",", 30, false));
        finiteStateMachine.add(new FourArray.Foure(29,"]", 37, false));
        finiteStateMachine.add(new FourArray.Foure(30,"t", 26, true));
        finiteStateMachine.add(new FourArray.Foure(30,"f", 31, true));
        finiteStateMachine.add(new FourArray.Foure(31,"a", 32, true));
        finiteStateMachine.add(new FourArray.Foure(32,"l", 33, true));
        finiteStateMachine.add(new FourArray.Foure(33,"s", 34, true));
        finiteStateMachine.add(new FourArray.Foure(34,"e", 35, true));
        finiteStateMachine.add(new FourArray.Foure(35,",", 36, false));
        finiteStateMachine.add(new FourArray.Foure(35,"]", 37, false));
        finiteStateMachine.add(new FourArray.Foure(36,"t", 26, true));
        finiteStateMachine.add(new FourArray.Foure(36,"f", 31, true));
        finiteStateMachine.add(new FourArray.Foure(37,"}", 38, false));
        finiteStateMachine.add(new FourArray.Foure(37,",", 1, false));
        finiteStateMachine.add(new FourArray.Foure(38,",", 1, false));

        this.boolArr = new ArrayList<>();
        boolArr.add(13);
        boolArr.add(18);
        boolArr.add(29);
        boolArr.add(35);

        this.doubArr = new ArrayList<>();
        doubArr.add(9);
        doubArr.add(24);

        this.intArr = new ArrayList<>();
        intArr.add(8);
        intArr.add(23);

        this.strArr = new ArrayList<>();
        strArr.add(6);
        strArr.add(20);
    }
    private void readText(String str) throws IOException {
        data = str;
        //System.out.println(data);
        data = data.replace("\n", "");
        data = data.replace("\r", "");
        data = data.replace("\t", "");
        //data = data.replace(" ", "");

        System.out.println("Data for decoder:\n"+data);
    }

    public Map<String, Object> dataToMap(String dataToConvert, Map<String, Object> map)  {
        String stringToConvert = dataToConvert;
        String token = "";
        int state = 0; //curent state
        boolean correctFlag = true; //file correctness flag

        //flags for arrays [...]
        boolean array = false;
        boolean arrayIsReady = false;

        ////flags for arrays {...}
        boolean subSection = false;
        boolean subSectionIsReady = false;
        int subLevel = 0;

        String key = "";
        Object value = null;
        ArrayList<Object> arrayList = new ArrayList<>();

        while(!stringToConvert.isEmpty() && correctFlag){
            correctFlag = false;

            char symbol = stringToConvert.charAt(0);
            stringToConvert = stringToConvert.substring(1);
            boolean needToAddSymb = false; //is it nessasary to add char to token flag
            int nextState = 0;


            for (int i = 0; i < finiteStateMachine.size; i++) {
                if (finiteStateMachine.get(i).getCurrentState() == state){
                    nextState = finiteStateMachine.get(i).getNextState();

                    if (state == 5 && nextState == 1 && symbol=='{'){
                        subSection = true;
                        subLevel++;
                    }
                    else if (subSection && symbol=='}'){
                        subLevel--;
                        if(subLevel==0){
                            subSection = false;
                            subSectionIsReady = true;
                        }
                    }

                    if (symbol=='['){
                        array = true;
                    }
                    else if (array && symbol==']'){
                        array = false;
                        arrayIsReady = true;
                        needToAddSymb = false;
                    }

                    if ((state == 6 && nextState == 6) || (state == 20 && nextState == 20)){
                        if(finiteStateMachine.get(i).getValue().indexOf(symbol) == -1){
                            if(finiteStateMachine.get(i).isWritable() || subSection) {
                                token += symbol;
                                needToAddSymb = true;
                            }
                            correctFlag = true;
                            break;
                        }
                    }
                    else{
                        if(finiteStateMachine.get(i).getValue().indexOf(symbol) != -1 || (subSection && symbol=='}')){
                            if(finiteStateMachine.get(i).isWritable() || subSection) {
                                token += symbol;
                                needToAddSymb = true;
                            }
                            correctFlag = true;
                            break;
                        }
                    }
                }
            }
            if(correctFlag){
                //System.out.println(token);
                if (!needToAddSymb && (token != "" || arrayIsReady)){
                    if (subSectionIsReady){
                        String subData = token+"}";
                        //System.out.println("Sub data: "+subData);

                        HashMap<String,Object> subMap = new HashMap<>();
                        this.dataToMap(subData, subMap);

                        map.put(key, subMap);
                        subSectionIsReady = false;
                        arrayIsReady = false;
                        token="";
                        key="";
                    }
                    else{
                        if(key == ""){
                            key = token;
                            token = "";
                        }
                        else{
                            if(boolArr.indexOf(state)!=-1){
                                value = Boolean.valueOf(token);
                            }
                            else if(intArr.indexOf(state)!=-1){
                                value = Integer.parseInt(token);
                            }
                            else if(doubArr.indexOf(state)!=-1){
                                value = Double.parseDouble(token);
                            }
                            else{
                                value = token;
                            }
                            token = "";

                            if (!array && !arrayIsReady){
                                map.put(key, value);

                                ArrayList<Object> pair = new ArrayList<>();
                                pair.add(key);
                                pair.add(value);
                                dataInObject.add(pair);

                                key = "";
                            }
                            else if (array){
                                arrayList.add(value);

                                //System.out.println("value for array- "+value);
                            }

                            else if (arrayIsReady){
                                map.put(key, new ArrayList<>(arrayList));

                                ArrayList<Object> pair = new ArrayList<>();
                                pair.add(key);
                                pair.add(new ArrayList<>(arrayList));
                                dataInObject.add(pair);

                                //System.out.println("key for array - "+key);
                                arrayList.clear();
                                key = "";
                                arrayIsReady = false;
                            }

                        }
                    }

                }
            }
            state = nextState;
        }

        if(state == 38 && correctFlag){
            //System.out.println("File was decoded successfuly!");
        }
        else{
            map = null;
            //System.out.println("File is incorrect, and can't be decoded!");
        }
        return map;
    }

    public Map<String, Object> toMap(String str) throws IOException {
        data = "";
        this.dataInMap = new HashMap<>();
        this.dataInObject = new ArrayList<>();
        //тут еще дата в class

        readText(str);
        return dataToMap(data, dataInMap);
    }

    public ArrayList<Object> toObjectArr(String file) throws IOException {
        toMap(file);

        for (Map.Entry<String, Object> entry : dataInMap.entrySet()) {
            dataInObject.add(entry.getValue());
        }
        return dataInObject;
    }

    public <T> T toClass(String str, Class<T> clas) throws IOException {
        toMap(str);
        return convertToClass(dataInMap, clas);
    }

    public <T> T convertToClass(Map<String,Object> map, Class<T> clas) throws IOException {
        // Populate the dataInMap from the file

        T obj = null;
        try {
            obj = clas.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        if (obj != null) {
            for (Map.Entry<String, Object> entry : dataInMap.entrySet()) {
                try {
                    Field field = clas.getDeclaredField(entry.getKey());
                    field.setAccessible(true);
                    if(obj instanceof Class<?>){
                        convertToClass((Map<String, Object>) entry.getValue(), (Class<T>) obj);
                    }
                    else{
                        field.set(obj, entry.getValue());
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj;
    }

    public ArrayList<Object> convertToObjList(String str) throws IOException {
        toMap(str);
        return dataInObject;
    }
}

