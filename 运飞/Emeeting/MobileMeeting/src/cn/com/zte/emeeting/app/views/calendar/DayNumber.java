package cn.com.zte.emeeting.app.views.calendar;

public class DayNumber {
	private int year;
	private int month;
	private String day;
	private int week;
	private String chinaDayString;

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		if (month < 0) {
			this.year -= 1;
			this.month = 12;
			return;
		}

		if (month > 12) {
			this.year += 1;
			this.month = month - 12;
			return;
		}
		this.month = month;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public String getChinaDayString() {
		return chinaDayString;
	}

	public void setChinaDayString(String chinaDayString) {
		this.chinaDayString = chinaDayString;
	}
}
