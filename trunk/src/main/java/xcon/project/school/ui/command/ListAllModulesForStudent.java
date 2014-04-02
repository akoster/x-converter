package xcon.project.school.ui.command;

import java.util.Scanner;

import xcon.project.school.model.Module;
import xcon.project.school.model.Student;
import xcon.project.school.service.EnrollmentService;

public class ListAllModulesForStudent implements CommandExecutor {

	@Override
	public void execute(EnrollmentService enrollmentService, Scanner scan) {
		Student student = enrollmentService.findStudent(scan.nextLine().trim());
		if (student == null) {
			System.out.println("Student not found");
		} else {
			for (Module m : enrollmentService.getModules(student)) {
				System.out.print(m + " ");
			}
		}
	}

}
