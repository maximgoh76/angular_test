package com.max.myserver.bl;

public class Logger {
	public static void writeError (String subject, Throwable ex) {
		System.out.println("--------------------------------------------------");
		System.out.println(subject);
		if (ex!=null) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		System.out.println("--------------------------------------------------");
	}
}
