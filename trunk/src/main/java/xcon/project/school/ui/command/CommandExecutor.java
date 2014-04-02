package xcon.project.school.ui.command;

import java.util.Scanner;

import xcon.project.school.service.EnrollmentService;

public interface CommandExecutor {
	void execute(EnrollmentService enrollmentService, Scanner scan);
}
