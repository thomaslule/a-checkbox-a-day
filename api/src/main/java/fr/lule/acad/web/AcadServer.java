package fr.lule.acad.web;

import fr.lule.acad.event.ITaskEvent;
import fr.lule.acad.projection.TaskList;
import fr.lule.acad.store.MemoryEventStore;
import fr.lule.acad.stream.EventsBus;
import net.codestory.http.WebServer;

public class AcadServer {

	public static void main(String[] args) {
		
		MemoryEventStore<ITaskEvent> taskEventStore = new MemoryEventStore<ITaskEvent>();
		TaskList list = new TaskList(taskEventStore.getAllEvents());
		EventsBus bus = new EventsBus(taskEventStore);
		bus.subscribe(list);
		
		new WebServer()
		.configure(routes -> {
			routes.add(new TasksController(list, bus));
		})
		.start();
	}
	
}
