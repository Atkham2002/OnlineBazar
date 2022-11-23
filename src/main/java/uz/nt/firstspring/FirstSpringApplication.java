package uz.nt.firstspring;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FirstSpringApplication {

	public static void main(String[] args) throws JsonProcessingException {
		SpringApplication.run(FirstSpringApplication.class, args);
//		SpringApplication application = new SpringApplication(FirstSpringApplication.class,args);
//		application.setAdditionalProfiles(args[0]);
//		System.getProperties().setProperty("server.port",args[1]);
//
//		application.run(args);

//		String json = "{\n" +
//				"    \"username\":\"Pilot\",\n" +
//				"    \"password\":\"UKPilot\",\n" +
//				"    \"unknown_field\":\"123\"\n" +
//				"}";
//
//		ObjectMapper mapper = new ObjectMapper();
//		ObjectReader reader = mapper.readerFor(LoginDTO.class);
//		LoginDTO loginDTO = reader.readValue(json);
//
//		System.out.println(loginDTO);
	}


}
