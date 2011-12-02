/**
 * 
 */
package xcon.school.ui;

import xcon.school.ui.command.CommandExecutor;
import xcon.school.ui.command.EnrollStudentForModule;
import xcon.school.ui.command.Exit;
import xcon.school.ui.command.ListAllModules;
import xcon.school.ui.command.ListAllModulesForStudent;
import xcon.school.ui.command.ListAllStudents;
import xcon.school.ui.command.ListAllStudentsForModule;

public enum MenuCommand {

	LIST_ALL_STUDENTS(1, "List all students", ListAllStudents.class),

	LIST_ALL_MODULES(2, "List all modules", ListAllModules.class),

	LIST_STUDENTS_FOR_MODULE(3, "List students in module",
			ListAllStudentsForModule.class),

	LIST_MODULES_FOR_STUDENT(4, "List modules for student",
			ListAllModulesForStudent.class),

	ENROLL_STUDENT_FOR_MODULE(5, "Enroll student in module",
			EnrollStudentForModule.class),

	EXIT(6, "Exit", Exit.class);

	private int menuIndex;
	private String description;
	private Class<? extends CommandExecutor> executorClass;

	MenuCommand(int menuIndex, String description,
			Class<? extends CommandExecutor> executorClass) {
		this.menuIndex = menuIndex;
		this.description = description;
		this.executorClass = executorClass;
	}

	public static MenuCommand forMenuIndex(int menuIndex) {
		for (MenuCommand command : values()) {
			if (command.menuIndex == menuIndex) {
				return command;
			}
		}
		return null;
	}

	public int getMenuIndex() {
		return menuIndex;
	}

	public String getDescription() {
		return description;
	}

	public Class<? extends CommandExecutor> getExecutorClass() {
		return executorClass;
	}
}