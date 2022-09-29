package uz.nt.firstspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

@SpringBootApplication
public class FirstSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstSpringApplication.class, args);
//		SpringApplication application = new SpringApplication(FirstSpringApplication.class,args);
//		application.setAdditionalProfiles(args[0]);
//		System.getProperties().setProperty("server.port",args[1]);
//
//		application.run(args);
	}


}
