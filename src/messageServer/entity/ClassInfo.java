package messageServer.entity;

public class ClassInfo {
	private int id;
	private int dayofweek;
	private String timeofday;
	private String classname;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDayofweek() {
		return dayofweek;
	}

	public void setDayofweek(int dayofweek) {
		this.dayofweek = dayofweek;
	}

	public String getTimeofday() {
		return timeofday;
	}

	public void setTimeofday(String timeofday) {
		this.timeofday = timeofday;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

}
