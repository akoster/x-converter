package xcon.project.school.ui.command;

import java.util.Scanner;

import xcon.project.school.model.Module;
import xcon.project.school.model.Student;
import xcon.project.school.service.EnrollmentService;

public class ListAllStudentsForModule implements CommandExecutor {

	@Override
	public void execute(EnrollmentService enrollmentService, Scanner scan) {
		String moduleName = scan.nextLine().trim();
		Module module = enrollmentService.findModule(moduleName);
		if (module == null) {
			System.out.println("Module not found");
		} else {
			for (Student student : enrollmentService.getStudents(module)) {
				System.out.print(student + " ");
			}
		}
	}
}
