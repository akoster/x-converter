package xcon.school.ui.command;

import java.util.Scanner;

import xcon.school.model.Student;
import xcon.school.service.EnrollmentService;

public class ListAllStudents implements CommandExecutor {

	@Override
	public void execute(EnrollmentService enrollmentService, Scanner scan) {
		for (Student student : enrollmentService.listStudents()) {
			System.out.print(student + " ");
		}
	}

}
