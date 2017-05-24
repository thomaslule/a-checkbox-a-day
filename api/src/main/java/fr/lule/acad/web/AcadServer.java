package fr.lule.acad.web;

import fr.lule.acad.projection.TaskList;
import fr.lule.acad.store.MemoryEventStore;
import fr.lule.acad.stream.EventsBus;
import net.codestory.http.WebServer;

public class AcadServer {

	public static void main(String[] args) {
		
		MemoryEventStore store = new MemoryEventStore();
		TaskList list = new TaskList(store.getAllEvents());
		EventsBus bus = new EventsBus(store);
		bus.subscribe(list);
		
		new WebServer()
		.configure(routes -> {
			routes.add(new TasksController(list, bus));
		})
		.start();
	}
	
}
