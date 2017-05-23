package fr.lule.acad.web;

import fr.lule.acad.projection.TaskList;
import fr.lule.acad.stream.EventsBus;
import fr.lule.acad.stream.MemoryEventStream;
import net.codestory.http.WebServer;

public class AcadServer {

	public static void main(String[] args) {
		
		MemoryEventStream stream = new MemoryEventStream();
		TaskList list = new TaskList(stream.getHistory());
		EventsBus bus = new EventsBus(stream);
		bus.subscribe(list);
		
		new WebServer()
		.configure(routes -> {
			routes.add(new TasksController(list, bus));
		})
		.start();
	}
	
}
