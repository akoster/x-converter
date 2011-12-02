package xcon.school.ui;

import java.util.Scanner;

import xcon.school.model.Module;
import xcon.school.model.Student;
import xcon.school.service.EnrollmentService;

public class Controller {

	private final EnrollmentService enrollmentService;
	private final Scanner scan;

	public Controller() {
		enrollmentService = new EnrollmentService();
		initialize(enrollmentService);
		scan = new Scanner(System.in);
	}

	private static void initialize(EnrollmentService enrollmentService) {
		Module ufce1 = new Module("UFCE1");
		Module ufce2 = new Module("UFCE2");
		Module ufce3 = new Module("UFCE3");

		enrollmentService.addModule(ufce1);
		enrollmentService.addModule(ufce2);
		enrollmentService.addModule(ufce3);

		Student jane = new Student("jane");
		Student alex = new Student("alex");

		enrollmentService.enroll(jane, ufce1);
		enrollmentService.enroll(jane, ufce3);
		enrollmentService.enroll(alex, ufce1);
		enrollmentService.enroll(alex, ufce2);
	}

	public void run() {

		System.out.println("Welcome to the Student Record System!");
		showUsage();

		MenuCommand command = null;
		while (command != MenuCommand.EXIT) {

			command = getMenuCommand(command);
			if (command != null) {
				System.out.println(command.getDescription());
				executeCommand(command);
			} else {
				System.out.println("That command was not recognized.");
				showUsage();
			}
			System.out.println();
		}
	}

	private void showUsage() {
		System.out.println("Usage:");
		for (MenuCommand menuCommand : MenuCommand.values()) {
			System.out.println(String.format("%s - %s", menuCommand
					.getMenuIndex(), menuCommand.getDescription()));
		}
	}

	private MenuCommand getMenuCommand(MenuCommand selection) {
		try {
			selection = MenuCommand.forMenuIndex(Integer.parseInt(scan
					.nextLine().trim()));
		} catch (NumberFormatException e) {
			selection = null;
		}
		return selection;
	}

	private void executeCommand(MenuCommand command) {
		try {
			command.getExecutorClass().newInstance().execute(enrollmentService,
					scan);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
