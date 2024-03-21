package ru.teamscore.java23.springdata.users;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.teamscore.java23.springdata.users.entity.User;
import ru.teamscore.java23.springdata.users.exception.UserNotExistsException;
import ru.teamscore.java23.springdata.users.exception.UsernameExistsException;
import ru.teamscore.java23.springdata.users.service.UserService;

import java.time.LocalDateTime;

@SpringBootApplication
public class UsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ru.teamscore.java23.springdata.users.UsersApplication.class, args);
	}

	@Bean
	public CommandLineRunner runner(UserService service) {
		return args -> {
			populateMockUsers(service);

			for (var user : service.getUsers(0, "email", false)) {
				System.out.println(user);
			};

			try {
				service.register("alice", "alice@wonderland.uk");
			}
			catch (UsernameExistsException ex) {
				System.err.println(ex.getMessage());
			}

			try {
				service.edit(new User("nonexistinguser", "nonexistinguser@example.com"));
			}
			catch (UserNotExistsException ex) {
				System.err.println(ex.getMessage());
			}

			try {
				var bond = service.getUser("agent007");
				if (bond.isPresent()) {
					bond.get().setPersonalName("Бонд");
					bond.get().setFamilyName("Джеймс Бонд");
					System.out.println(service.edit(bond.get()));
				}
			}
			catch (UserNotExistsException ex) {
				System.err.println(ex.getMessage());
			}

			String userToBan = "VaSyA";
			service.ban(userToBan);
			var vasya = service.getUser(userToBan);
			System.out.print("Забанен: ");
			System.out.println(vasya.get());
		};
	}

	private static void populateMockUsers(UserService service) {
		User newUser;
		newUser = service.register("iliya_muromets", "ilyusha@murom.ru");
		newUser.setPersonalName("Илья");
		newUser.setFamilyName("Муромец");
		newUser.setRegistrationDate(LocalDateTime.of(1562, 3, 1, 8, 32, 02));
		service.edit(newUser);
		newUser = service.register("VaSyA", "v.pupkin@mymail.ru");
		newUser.setPersonalName("Вася");
		newUser.setFamilyName("Пупкин");
		service.edit(newUser);
		newUser = service.register("alice", "alice@yandex.ru");
		newUser.setPersonalName("Алиса");
		service.edit(newUser);
		newUser = service.register("agent007", "007@bond.org");
		newUser = service.register("sales", "sales@example.com");
		for (int i = 0; i < 100; i++) {
			service.register("user" + i, "user_" + i +"@example.com");
		}
	}
}
