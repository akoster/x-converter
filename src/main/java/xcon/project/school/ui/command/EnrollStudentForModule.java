package xcon.project.school.ui.command;

import java.util.Scanner;

import xcon.project.school.model.Module;
import xcon.project.school.model.Student;
import xcon.project.school.service.EnrollmentService;

public class EnrollStudentForModule implements CommandExecutor {
	@Override
	public void execute(EnrollmentService enrollmentService, Scanner scan) {
		String studentName = scan.nextLine().trim();
		Student student1 = enrollmentService.findStudent(studentName);
		if (student1 == null) {
			student1 = new Student(studentName);
		}
		Module module1 = enrollmentService.findModule(scan.nextLine().trim());
		if (module1 == null) {
			System.out.println("Module not found");
		} else {
			boolean success = enrollmentService.enroll(student1, module1);
			if (!success) {
				System.out.println("Student cannot be enrolled twice");
			}
		}
	}
}
