package xcon.project.school.ui.command;

import java.util.Scanner;

import xcon.project.school.model.Module;
import xcon.project.school.service.EnrollmentService;

public class ListAllModules implements CommandExecutor {

	@Override
	public void execute(EnrollmentService enrollmentService, Scanner scan) {
		for (Module module : enrollmentService.listModules()) {
			System.out.print(module + " ");
		}
	}

}
