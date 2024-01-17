package ejercicios;

import entidades.Course;
import entidades.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class UD04_TareaEvaluativa4c {

	/**
	 * 1. ManyToMany bidireccional entre entidades Student y Course
	 * Crea un nuevo curso y a�ade un alumno al curso 
	 */
	public static void main(String[] args) {

		// crea sessionFactory y session
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ud4");
		EntityManager entityManager = factory.createEntityManager();
		
		try {			
			// crea un objeto Student y Course
			System.out.println("Creando un nuevo curso y a�adiendo un alumno...");
			
			Student student = entityManager.find(Student.class, 9);
			Course course = createCourse();
						
			student.getCourses().add(course);
			course.getStudents().add(student);//asociaci�n bidireccional para mantener la coherencia en ambos lados
			
											
			// comienza la transacci�n
			entityManager.getTransaction().begin();
			
			// guarda el objeto Student y el curso
			System.out.println("Guardando el curso...");
						
			entityManager.persist(course);
			
			// hace commit de la transaccion
			entityManager.getTransaction().commit();	
			
			// Inicia una nueva transacci�n y recupera el curso de la base de datos para verificar los estudiantes asociados.
			// Esta parte est� comentada temporalmente para evitar operaciones adicionales de base de datos durante la demostraci�n.
			// Si necesitas verificar que la relaci�n ManyToMany se ha establecido correctamente, puedes descomentar estas l�neas.
			// session.beginTransaction();
			// Course dbCourse= (Course) session.get(Course.class, course.getId());
			// System.out.println(dbCourse.getStudents().iterator().next().getLastName());
			
			System.out.println("Hecho!");
		}
		catch ( Exception e ) {
			// rollback ante alguna excepci�n
			System.out.println("Realizando Rollback");
			entityManager.getTransaction().rollback();
			e.printStackTrace();
		}
		finally {
			entityManager.close();
			factory.close();
		}
	}
	private static Course createCourse() {
		Course tempCourse = new Course();
				
		tempCourse.setName("Bases de datos");
		tempCourse.setCredits(6);
		return tempCourse;		
	}
}




