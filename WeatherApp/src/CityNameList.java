import java.io.*;
import java.util.*;

import com.alibaba.fastjson.JSONArray;

public class CityNameList implements java.io.Serializable {
    private static final long serialVersionUID = -308160735333114289L;
    private ArrayList city;
    private ArrayList nation;


    /**
     * The default constructor comes to instantiate the two blank ArrayList for both
     * city and its corresponded nation.
     */
    public CityNameList() {
        city = new ArrayList();
        nation = new ArrayList();
    }

    public String readfile(String fileName) throws FileNotFoundException {
        String jsonStr = "";
        try {
            File jsonFile = new File(fileName);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonStr = sb.toString();
//            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
//            String name = jsonObject.getString("name");
//            return name;
            return jsonStr;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public void load() throws FileNotFoundException {
        CityNameList cityNameList = new CityNameList();
        String input = cityNameList.readfile("onlyCityAndNation.list.json");
        List<Map<String,String>> listObjectFir = (List<Map<String,String>>) JSONArray.parse(input);
        for(Map<String,String> mapList : listObjectFir){
            city.add(mapList.get("City"));
            nation.add(mapList.get("Nation"));
        }
    }


    public boolean contains(String name) {
        return this.city.contains(name);
    }

    @SuppressWarnings("unchecked") public Iterator<String> search(String input, String nations) throws FileNotFoundException {
        CityNameList cityNameList = new CityNameList();
        String list = cityNameList.readfile("onlyCityAndNation.list.json");
        List<Map<String,String>> listObjectFir = (List<Map<String,String>>) JSONArray.parse(list);
        for(Map<String,String> mapList : listObjectFir){
            city.add(mapList.get("City"));
            nation.add(mapList.get("Nation"));
        }
        input = input.substring(0,1).toUpperCase() + input.substring(1);
        nations = nations.toUpperCase();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < city.size()/2; i++) {
            if ((city.get(i).toString().startsWith(input)) && ((nation.get(i).toString()).equals(nations)) && !arrayList.contains(city.get(i).toString().startsWith(input))){
                arrayList.add(city.get(i).toString()+"  ----  "+nation.get(i).toString());
            }
        }
        Iterator<String> showCity = arrayList.iterator();
        return showCity;
    }
    public Iterator<String> search(String input) throws FileNotFoundException {
        CityNameList cityNameList = new CityNameList();
        String list = cityNameList.readfile("onlyCityAndNation.list.json");
        List<Map<String,String>> listObjectFir = (List<Map<String,String>>) JSONArray.parse(list); //用map来存取其中的每一对数据
        for(Map<String,String> mapList : listObjectFir){
            //对其中每一对存取过的数据在进行读取
            city.add(mapList.get("City"));
            nation.add(mapList.get("Nation"));
        }
        input = input.substring(0,1).toUpperCase() + input.substring(1);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < city.size()/2; i++) {
            if ((city.get(i).toString().startsWith(input)) && !arrayList.contains(city.get(i).toString().startsWith(input))){ //判断搜取城市是否符合数据库内的要求
                arrayList.add(city.get(i).toString()+"  ----  "+nation.get(i).toString()); // 将符合要求的数据进行打印
            }
        }
        Iterator<String> showCity = arrayList.iterator();
        return showCity;
    }


    public void getCity() throws FileNotFoundException {
        String a = "";
        Iterator<String> itCity = this.search("Wuh", "Cn");
        while (itCity.hasNext()) {
            a+=itCity.next();
        }
        System.out.println(a);
    }

    public static void main(String[] args) throws FileNotFoundException {
        CityNameList cityNameList = new CityNameList();
        cityNameList.getCity();
    }

}
