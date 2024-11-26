package com.utn.frm.DiazJIgnacio.ArticleFeedbackMS;

import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.domain.ArticleFeedback;
import com.utn.frm.DiazJIgnacio.ArticleFeedbackMS.repository.ArticleFeedbackRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class ArticleFeedbackMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArticleFeedbackMsApplication.class, args);
			System.out.println("Estoy funcionando");

	}


}
