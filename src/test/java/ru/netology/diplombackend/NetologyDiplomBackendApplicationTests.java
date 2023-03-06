package ru.netology.diplombackend;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.diplombackend.entity.FileEntity;
import ru.netology.diplombackend.repository.FileRepository;

@SpringBootTest
@Testcontainers
@DataJpaTest
class NetologyDiplomBackendApplicationTests {

	@Container
	private static GenericContainer<?> postgres = new GenericContainer("postgres:latest");

	@Autowired
	FileRepository fileRepository;

	@BeforeEach
	void setUp() {
		fileRepository.deleteAll();
		fileRepository.save(new FileEntity("test1.jpg", new byte[10], 100));
		fileRepository.save(new FileEntity("test2.jpg", new byte[10], 100));
		fileRepository.save(new FileEntity("test3.jpg", new byte[10], 100));
	}

	@Test
	void contextLoads() {
		Assertions.assertThat(fileRepository.getFiles(3)).isEqualTo(3);
	}

}
