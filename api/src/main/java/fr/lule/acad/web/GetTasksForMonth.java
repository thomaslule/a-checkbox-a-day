package fr.lule.acad.web;

import java.util.ArrayList;
import java.util.List;

import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Prefix;

@Prefix("/api/TasksOfMonth")
public class GetTasksForMonth {

	@Get("/:id")
	public List<Task> Get(int id) {
		List<Task> list = new ArrayList<Task>();
		list.add(new Task(1, "truc 1 du mois " + id, false));
		list.add(new Task(2, "truc 2", true));
		return list;
	}

}
