package kr.kro.simpleboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "kr.kro.simpleboard")
public class SimpleBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleBoardApplication.class, args);
	}

}
