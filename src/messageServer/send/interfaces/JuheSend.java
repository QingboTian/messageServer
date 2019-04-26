package messageServer.send.interfaces;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import messageServer.entity.ClassInfo;
import messageServer.entity.Weather;

/**
 * 聚合数据提供的发送信息的接口实现
 * @author tqb
 *
 */
public class JuheSend implements SendInterface {

	@Override
	public void msgSend(Weather w, List<ClassInfo> list) {
		Properties p = new Properties();
		try {
			p.load(this.getClass().getClassLoader().getResourceAsStream("msg_config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuffer sb = new StringBuffer();
		// 处理课程信息
		if(list.size()!=0){
			for (int i = 0; i < list.size(); i++) {
				if (i != list.size() - 1){
					sb.append(list.get(i).getTimeofday()).append(list.get(i).getClassname()).append("、");
				}else{
					sb.append(list.get(i).getTimeofday()).append(list.get(i).getClassname());
				}
			}
		}else{
			sb.append("无课程信息,祝你周末愉快");
		}
		
		String mobile = p.getProperty("mobile");
		String tpl_id = p.getProperty("tpl_id");
		String key = p.getProperty("key");
		String url = p.getProperty("url");
		
		HashMap<String,String> map = new HashMap<>();
		map.put("mobile", mobile);
		map.put("tpl_id", tpl_id);
		map.put("key", key);
		String t = w.getResult().getFuture().get(0).getTemperature();
		String low_tem = "null";
		String high_tem = "null";
		if (t != null && !t.trim().equals("")) {
			String[] split = t.split("\\/");
			low_tem = split[0];
			high_tem = split[1];
		}
		LocalDate ld = LocalDate.now();
		String date = ld.getYear() + "-" + (ld.getMonthValue()) + "-" + ld.getDayOfMonth();
		map.put("tpl_value", "#city#="+w.getResult().getCity()
				+"&"+"#date#="+date
				+"&"+"#info#="+w.getResult().getRealtime().getInfo()
				+"&"+"#low_tem#="+low_tem
				+"&"+"#high_tem#="+high_tem
				+"&"+"#curr_tem#="+w.getResult().getRealtime().getTemperature()
				+"&"+"#humidity#="+w.getResult().getRealtime().getHumidity()
				+"&"+"#power#="+w.getResult().getRealtime().getPower()
				+"&"+"#aqi#="+w.getResult().getRealtime().getAqi()
				+"&"+"#classinfo#="+sb.toString());
		try {
			net(url,map,"GET");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String net(String strUrl, Map<String,String> params,String method) throws Exception {
		final String DEF_CHATSET = "UTF-8";
		final int DEF_CONN_TIMEOUT = 30000;
		final int DEF_READ_TIMEOUT = 30000;
		String userAgent =  "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			if(method==null || method.equals("GET")){
				strUrl = strUrl+"?"+urlencode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if(method==null || method.equals("GET")){
				conn.setRequestMethod("GET");
			}else{
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			if (params!= null && method.equals("POST")) {
				try {
					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
					out.writeBytes(urlencode(params));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}

	//将map型转为请求参数型
	public static String urlencode(Map<String,String> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<String,String> i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue()+"","UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
