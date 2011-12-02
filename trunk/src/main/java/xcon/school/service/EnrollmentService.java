package xcon.school.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import xcon.school.model.Module;
import xcon.school.model.Student;

public class EnrollmentService {

	private final Map<Module, List<Student>> enrollments = new HashMap<Module, List<Student>>();

	/**
	 * @return true if the module was added, false if it already existed
	 */
	public boolean addModule(Module module) {
		boolean newModule = !enrollments.keySet().contains(module);
		if (newModule) {
			enrollments.put(module, new ArrayList<Student>());
		}
		return newModule;
	}

	/**
	 * @return true if the student was enrolled, false if the student was
	 *         already enrolled
	 */
	public boolean enroll(Student student, Module module) {
		List<Student> moduleStudents = getStudents(module);
		if (moduleStudents == null) {
			throw new IllegalArgumentException("Module not found");
		}
		boolean newModuleStudent = !moduleStudents.contains(student);
		if (newModuleStudent) {
			moduleStudents.add(student);
		}
		return newModuleStudent;
	}

	public final List<Student> getStudents(Module module) {
		return enrollments.get(module);
	}

	public List<Module> getModules(Student student) {
		List<Module> studentModules = new ArrayList<Module>();
		for (Entry<Module, List<Student>> entry : enrollments.entrySet()) {
			if (entry.getValue().contains(student)) {
				studentModules.add(entry.getKey());
			}
		}
		return studentModules;
	}

	public Set<Module> listModules() {
		return enrollments.keySet();
	}

	public final Set<Student> listStudents() {
		Set<Student> students = new HashSet<Student>();
		for (List<Student> moduleStudents : enrollments.values()) {
			students.addAll(moduleStudents);
		}
		return students;
	}

	public Module findModule(String name) {
		for (Module module : enrollments.keySet()) {
			if (name.equalsIgnoreCase(module.getName())) {
				return module;
			}
		}
		return null;
	}

	public Student findStudent(String name) {
		for (Student student : listStudents()) {
			if (name.equalsIgnoreCase(student.getName())) {
				return student;
			}
		}
		return null;
	}
}
