package ru.teamscore.java23.springdata.storage;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import ru.teamscore.java23.springdata.storage.entities.Item;
import ru.teamscore.java23.springdata.storage.entities.Storage;
import ru.teamscore.java23.springdata.storage.repos.ItemRepository;
import ru.teamscore.java23.springdata.storage.repos.StorageRepository;

@SpringBootApplication
public class StorageApplication {

	public static void main(String[] args) {
		SpringApplication.run(StorageApplication.class, args);
	}

	@Bean
	public static CommandLineRunner populateItems(ItemRepository repository) {
		return args -> {
			repository.save(new Item("ААА000111", "Шерсть"));
			repository.save(new Item("ААА000112", "Брюки"));
			repository.save(new Item("БВГ000113", "Ванна"));
			repository.save(new Item("LAA000113", "Бампер"));
			repository.save(new Item("PАИ000095", "Бензин"));
			System.out.println("Mock items populated");
		};
	}

	@Bean
	public static CommandLineRunner populateStorages(StorageRepository repository) {
		return args -> {
			repository.save(new Storage("000000001", "Склад 1", "ул. Ленина, 5"));
			repository.save(new Storage("000000002", "Склад 2", "пгт Светлый Путь"));
			System.out.println("Mock storages populated");
		};
	}

	@Bean
	public static ModelMapper getModelMapper() {
		var modelMapper = new ModelMapper();
		return modelMapper;
	}
}
