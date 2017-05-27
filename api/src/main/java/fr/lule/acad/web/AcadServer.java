package fr.lule.acad.web;

import fr.lule.acad.event.ITaskEvent;
import fr.lule.acad.projection.TaskList;
import fr.lule.acad.store.InMemoryEventStore;
import fr.lule.acad.stream.EventsBus;
import net.codestory.http.WebServer;

public class AcadServer {

	public static void main(String[] args) {
		
		InMemoryEventStore<ITaskEvent> taskEventStore = new InMemoryEventStore<ITaskEvent>();
		TaskList list = new TaskList(taskEventStore.getAllEvents());
		EventsBus bus = new EventsBus(taskEventStore);
		bus.subscribe(list);
		
		new WebServer()
		.configure(routes -> {
			routes.add(new TasksController(list, bus, taskEventStore));
		})
		.start();
	}
	
}
