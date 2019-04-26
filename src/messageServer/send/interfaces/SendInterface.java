package messageServer.send.interfaces;

import java.util.List;

import messageServer.entity.ClassInfo;
import messageServer.entity.Weather;

public interface SendInterface {
	// 发送消息
	public void msgSend(Weather w,List<ClassInfo> list);
}
