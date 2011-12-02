package xcon.school.ui.command;

import java.util.Scanner;

import xcon.school.service.EnrollmentService;

public interface CommandExecutor {
	void execute(EnrollmentService enrollmentService, Scanner scan);
}
