package main;

import java.util.Date;

public class ExceptionInfo {
	private Exception e;
	private String hostname;
	private String user;
	private Date date;
	
	public ExceptionInfo(Exception e, String hostname, String user, Date date) {
		super();
		this.e = e;
		this.hostname = hostname;
		this.user=user;
		this.date = date;
	}

	public Exception getE() {
		return e;
	}

	public String getHostname() {
		return hostname;
	}

	public String getUser() {
		return user;
	}

	public Date getDate() {
		return date;
	}
	
}
