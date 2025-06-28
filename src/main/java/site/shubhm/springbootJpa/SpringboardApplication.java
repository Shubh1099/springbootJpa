package site.shubhm.springbootJpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import site.shubhm.springbootJpa.entities.Student;
import site.shubhm.springbootJpa.services.StudentService;
import site.shubhm.springbootJpa.services.StudentServiceImpl;
import java.util.List;

@SpringBootApplication
public class SpringboardApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringboardApplication.class, args);
		StudentService studentService = context.getBean(StudentServiceImpl.class);

//				INSERT

		Student std = new Student();
		std.setName("vaibhav");
		std.setCity("nwnw");
		std.setEmail("mvs@mail.com");
		std.setGender("Male");
		std.setPassword("asdqwerr");

		boolean status = studentService.addStudentDetails(std);

		if(status){
			System.out.println("Record has been added successfully!");
		}
		else{
			System.out.println("Record not inserted.");
		}

		// SELECT ALL
		List<Student> stdList= studentService.getAllStudentDetails();

		for (Student std1:stdList){
			System.out.println("Id :" + std1.getId());
			System.out.println("Name :" + std1.getName());
			System.out.println("Email :" + std1.getEmail());
			System.out.println("Password :" + std1.getPassword());
			System.out.println("Gender :" + std1.getGender());
			System.out.println("City :" + std1.getCity());

		}

		//  SELECT BY ID


		Student std2 = studentService.getStudentById(21L);

		if(std2!=null){
			System.out.println("Details ::________________ "+std2.getName()+" " + std2.getEmail() + " " + std2.getGender());
		}

		else{
			System.out.println("Student not found!");
		}



		// UPDATE

		boolean updateStatus = studentService.updateDetails(1L,"asdljkhdfkjb");

		if (updateStatus){
			System.out.println("Record updated successfully!");
		}
		else{
			System.out.println("error occured!");
		}



		//DELETE
		boolean deleteStatus = studentService.deleteStudent(2L);

		if (deleteStatus){
			System.out.println("Record deleted successfully!");
		}
		else{
			System.out.println("error occured!");
		}


	}

}
