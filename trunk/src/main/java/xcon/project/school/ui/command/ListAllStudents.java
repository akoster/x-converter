package xcon.project.school.ui.command;

import java.util.Scanner;

import xcon.project.school.model.Student;
import xcon.project.school.service.EnrollmentService;

public class ListAllStudents implements CommandExecutor {

	@Override
	public void execute(EnrollmentService enrollmentService, Scanner scan) {
		for (Student student : enrollmentService.listStudents()) {
			System.out.print(student + " ");
		}
	}

}
