package main;

import java.util.List;

import messageServer.entity.ClassInfo;
import messageServer.entity.Weather;
import messageServer.msglistener.MsgDeal;
import messageServer.send.interfaces.JuheSend;
import messageServer.send.interfaces.SendInterface;

public class Main {
	public static void main(String[] args) {
		MsgDeal md = new MsgDeal();
		Weather str = md.getweatherStr();
		List<ClassInfo> list = md.getClassSchedule();
		SendInterface send = new JuheSend();
		send.msgSend(str, list);
	}
}
