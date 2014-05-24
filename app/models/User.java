package models;

import javax.persistence.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.data.format.*;
import play.data.validation.*;
import play.db.jpa.JPA;

/**
 */
@Entity
@Table(name = "user_login")
public class User {

	@Id
	@Constraints.Required
	@Formats.NonEmpty
	public String email;

	@Constraints.Required
	public String nome;

	@Constraints.Required
	public String senha;

	private static final Logger LOGGER = LoggerFactory.getLogger(User.class);

	public static User findByEmail(String email) {
		LOGGER.info("teste_logger");
		return (User) JPA.em()
				.createQuery("select u from User u where u.email = ? ")
				.setParameter(1, email)
				.getSingleResult();

	}

	public static User authenticate(String email, String password) {
		
		return (User) JPA.em()
				.createQuery("select u from User u where u.email = ? and u.senha = ?")
				.setParameter(1, email)
				.setParameter(2, password)
				.getSingleResult();
	}

	public String toString() {
		return "User(" + email + ")";
	}

}
