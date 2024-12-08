package dev.gmarchev.flightmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(value = "dev.gmarchev.flightmanager")
public class FlightManagerApplication {

	public static void main(String[] args) {

		SpringApplication.run(FlightManagerApplication.class, args);
	}
}
