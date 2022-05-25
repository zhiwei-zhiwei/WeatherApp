// author: Zhiwei Cao
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

public class WeatherTree extends RedBlackTree implements java.io.Serializable {
    private static final long serialVersionUID = -6667316626977287667L;
    private String main;
    private Object lon;
    private Object lat;
    private String description;
    private Object temp;
    private Object feels_like;
    private Object temp_min;
    private Object temp_max;
    private Object pressure;
    private Object humidity;
    private Object speed;
    private double visibility;

    public WeatherTree(){
        this.main = main;
        this.lon = lon;
        this.lat = lat;
        this.description = description;
        this.temp = temp;
        this.feels_like = feels_like;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.pressure = pressure;
        this.humidity = humidity;
        this.speed = speed;
        this.visibility = visibility;
    }


    public WeatherTree(String main, Object lon, Object lat, String description, Object temp, Object feels_like, Object temp_min, Object temp_max, Object pressure, Object humidity, Object speed, double visibility) {
        this.main = main;
        this.lon = lon;
        this.lat = lat;
        this.description = description;
        this.temp = temp;
        this.feels_like = feels_like;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.pressure = pressure;
        this.humidity = humidity;
        this.speed = speed;
        this.visibility = visibility;
    }

    public WeatherTree(String main, String description, Object temp, Object temp_min, Object temp_max) {
        this.main = main;
        this.description = description;
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString1() {
        return "All Weather Information : " + "\n" +
                "Weather description = " + main + "\n" +
                "Location longitude = " + lon + "\n" +
                "Location latitude = " + lat + "\n" +
                "Description = " + description + "\n" +
                "Temperature = " + temp + "°F"+ "\n" +
                "Feels like temperature = " + feels_like + "°F"+ "\n" +
                "Minimum temperature = " + temp_min + "°F"+ "\n" +
                "Maximum temperature = " + temp_max + "°F"+ "\n" +
                "Pressure = " + pressure + "Pa" +"\n" +
                "Humidity = " + humidity + "%rh" + "\n" +
                "Wind Speed = " + speed + "m/s" + "\n" +
                "Visibility = " + visibility + "m";
    }

    public String toString2() {
        return "Important Weather Information : " + "\n" +
                "Weather description = " + main + "\n" +
                "Description = " + description + "\n" +
                "Temperature = " + temp + "°F"+ "\n" +
                "Minimum temperature = " + temp_min + "°F"+ "\n" +
                "Maximum temperature = " + temp_max + "°F";
    }

    public String getDetailInfo(String city) throws IOException {
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=4ed47c6b7cdd594bc236d2df723af5b3");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null){
//            System.out.println(inputLine);
            JSONObject jsonObject1 = JSONObject.parseObject(inputLine);
            String weather = jsonObject1.getString("weather");
            double visi = jsonObject1.getDouble("visibility");
            String main_temp = jsonObject1.getString("main");
            List<WeatherTree> list_weather = JSONObject.parseArray(weather, WeatherTree.class);
            String main = list_weather.get(0).getMain();
            String description = list_weather.get(0).getDescription();
            JSONObject jsonObject = JSONObject.parseObject(inputLine);
            Object lon = jsonObject.getJSONObject("coord").get("lon");
            Object lon111 = jsonObject.getJSONObject("coord");
            Object lat = jsonObject.getJSONObject("coord").get("lat");
            Object temp = jsonObject.getJSONObject("main").get("temp");
            Object feel = jsonObject.getJSONObject("main").get("feels_like");
            Object min = jsonObject.getJSONObject("main").get("temp_min");
            Object max = jsonObject.getJSONObject("main").get("temp_max");
            Object pressure = jsonObject.getJSONObject("main").get("pressure");
            Object humid = jsonObject.getJSONObject("main").get("humidity");
            Object speed = jsonObject.getJSONObject("wind").get("speed");
            WeatherTree methodTable = new WeatherTree(main,lon,lat,description,temp,feel,min,max,pressure,humid,speed,visi);
            System.out.println("City: "+city);
            //System.out.println("^^^^^^^^^^^^^"+lon111);
            System.out.println(methodTable.toString1());
        }
        bufferedReader.close();
        return inputLine;
    }

    public String getImportantInfo(String city) throws IOException {
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=4ed47c6b7cdd594bc236d2df723af5b3");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null){
            JSONObject jsonObject1 = JSONObject.parseObject(inputLine);
            String weather = jsonObject1.getString("weather");
            double visi = jsonObject1.getDouble("visibility");
            String main_temp = jsonObject1.getString("main");
            List<WeatherTree> list_weather = JSONObject.parseArray(weather, WeatherTree.class);
            String main = list_weather.get(0).getMain();
            String description = list_weather.get(0).getDescription();
            JSONObject jsonObject = JSONObject.parseObject(inputLine);
            Object temp = jsonObject.getJSONObject("main").get("temp");
            Object min = jsonObject.getJSONObject("main").get("temp_min");
            Object max = jsonObject.getJSONObject("main").get("temp_max");
            WeatherTree methodTable = new WeatherTree(main,description,temp,min,max);
            System.out.println("City: "+city);
            System.out.println(methodTable.toString2());
        }
        bufferedReader.close();
        return inputLine;
    }

    Data data = new Data();

//    public WeatherTree(){
//        try {
//            data.update();
//            for (int i = 0; i < data.getSize(); i++) {
//                this.insert(data.get(i).getSummary());
//            }
//        } catch (IOException e) {
//        }
//    }

    public static void main(String[] args) throws IOException {
        WeatherTree methodTable = new WeatherTree();
//      methodTable.getDetailInfo("London");
        methodTable.getImportantInfo("Shanghai");
        System.out.println(" ");
        String a = "";
        a = methodTable.getDetailInfo("London");
        System.out.println(a);
    }


}