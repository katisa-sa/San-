package ejercicios;

import java.util.List;

import entidades.Student;
import entidades.University;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ConsultaHQL {
	/**
	 * Realizamos la consulta:  Lista el nombre completo de los alumnos junto con el 
	 * nombre de sus universidades.
	 */
	public static void main(String[] args) {


		// crea factory y entityManager
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("ud4");
		EntityManager entityManager = factory.createEntityManager();
			
		try {			
		
			System.out.println("Borrando un nuevo Student y en cascada su Tuition asociado");
			
			String jpql = " from Student s\r\n"
					+ "join s.university u ";
	        
			List <Student> studentList = entityManager.createQuery(jpql).getResultList();
			
			// comienza la transacci�n
			entityManager.getTransaction().begin();
		
			// Imprime la lista
			imprimir(studentList);
			
			// hace commit de la transaccion
			entityManager.getTransaction().commit();
					
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
			entityManager.close();
		}
		
	}
	//creamos un metodo que lea los nombres de los estudiantes y la universidad
	private static void imprimir(List <Student> studentList) {
		for (Student student : studentList) {
			University university = student.getUniversity();
			System.out.printf ("Nombre: %s %s, Universidad: %s%n", student.getFirstName(), student.getLastName(), university.getName());
		}
	
}
}
