package xcon.school.ui.command;

import java.util.Scanner;

import xcon.school.model.Module;
import xcon.school.service.EnrollmentService;

public class ListAllModules implements CommandExecutor {

	@Override
	public void execute(EnrollmentService enrollmentService, Scanner scan) {
		for (Module module : enrollmentService.listModules()) {
			System.out.print(module + " ");
		}
	}

}
