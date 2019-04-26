package messageServer.entity;

import java.util.Date;

public class Future {
	private Date date;
    private String temperature;
    private String weather;
    private Wid wid;
    private String direct;
    public void setDate(Date date) {
         this.date = date;
     }
     public Date getDate() {
         return date;
     }

    public void setTemperature(String temperature) {
         this.temperature = temperature;
     }
     public String getTemperature() {
         return temperature;
     }

    public void setWeather(String weather) {
         this.weather = weather;
     }
     public String getWeather() {
         return weather;
     }

    public void setWid(Wid wid) {
         this.wid = wid;
     }
     public Wid getWid() {
         return wid;
     }

    public void setDirect(String direct) {
         this.direct = direct;
     }
     public String getDirect() {
         return direct;
     }
}
