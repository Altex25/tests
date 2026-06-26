package org.example.exercice_13.bdd;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

@CucumberContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
public class CucumberSpringConfiguration {
}
