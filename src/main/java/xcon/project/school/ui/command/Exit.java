package xcon.project.school.ui.command;

import java.util.Scanner;

import xcon.project.school.service.EnrollmentService;

public class Exit implements CommandExecutor {

	@Override
	public void execute(EnrollmentService enrollmentService, Scanner scan) {
		System.out.println("Goodbye!");
	}

}
