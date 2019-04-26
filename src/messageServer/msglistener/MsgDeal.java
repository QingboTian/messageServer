package messageServer.msglistener;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import messageServer.entity.ClassInfo;
import messageServer.entity.Weather;
import messageServer.utils.JsonUtils;

/**
 * 消息处理类 分别为时间，天气，课程
 * 
 * @author tqb
 *
 */
public class MsgDeal {
	
	/**
	 * 获取天气情况
	 * 
	 * @param city
	 *            城市
	 * @param key
	 *            接口key 650ad2ff64e86edbf782bc6418f09ab1
	 */
	public String getWeather(String uri, String city, String key) {
		
		HttpClient http = new DefaultHttpClient();
		HttpGet request = new HttpGet(uri + "?city=" + city + "&key=" + key);
		HttpResponse response = null;
		try {
			response = http.execute(request);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String text = null;
		try {
			text = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	/**
	 * 进行课程表的查询
	 * 
	 * @param date
	 * @return 
	 * @return
	 */
	@Test
	public List<ClassInfo> getClassSchedule() {
		int dayofweek = LocalDate.now().getDayOfWeek().getValue();
		Properties props = new Properties();
		try {
			props.load(this.getClass().getClassLoader().getResourceAsStream("sql.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String jdbcUrl = props.getProperty("jdbcUrl");
		String driver  = props.getProperty("driverClass");
		String user = props.getProperty("user");
		String pwd = props.getProperty("pwd");
		
		Connection conn = null;
		
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(jdbcUrl, user, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String sql = "select * from class where dayof_week = ?";
		ResultSet rs = null;
		PreparedStatement statement = null;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, dayofweek+"");
			rs = statement.executeQuery();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		List<ClassInfo> list = new ArrayList<>();
		
		try {
			while(rs.next()){
				ClassInfo cl = new ClassInfo();
				cl.setDayofweek(rs.getInt("dayof_week"));
				cl.setClassname(rs.getString("classname"));
				cl.setTimeofday(rs.getString("timeofday"));
				list.add(cl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		/*for (ClassInfo c : list) {
			System.out.println(c.getTimeofday() + c.getClassname());
		}*/
		try {
			conn.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/*@Test
	public void tes(){
		LocalDate d =LocalDate.now();
		System.out.println(d.getDayOfWeek().getValue());
	}*/

	/**
	 * 获得Weather
	 * 将接口返回的json字符串封装到Weather中
	 * @return
	 */
	public Weather getweatherStr() {
		Properties props = new Properties();
		try {
			props.load(this.getClass().getClassLoader().getResourceAsStream("weather_config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String uri = props.getProperty("query_uri");
		String city = props.getProperty("city");
		String key = props.getProperty("key");
		String weatherJson = this.getWeather(uri, city, key);
		Weather str = JsonUtils.jsonToPojo(weatherJson, Weather.class);
		return str;
	}
}
