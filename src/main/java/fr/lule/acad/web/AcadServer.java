package fr.lule.acad.web;

import net.codestory.http.WebServer;

public class AcadServer {

	public static void main(String[] args) {
		new WebServer()
		.configure(routes ->
		routes.add(new GetTasksForMonth()))
		.start();
	}
	
}
