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

	/*@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			// Obtener el repositorio de ArticleFeedback
			ArticleFeedbackRepository feedbackRepository = ctx.getBean(ArticleFeedbackRepository.class);

			// Crear una instancia de ArticleFeedback con datos hardcodeados
			ArticleFeedback feedback = ArticleFeedback.builder()
					.articleId("123")
					.userId("user123")
					.comment("Me gustó mucho el artículo")
					.liked(true)
					.status("COMPLETADO")
					.createdAt(new Date())
					.build();

			// Guardar la instancia en la base de datos
			feedbackRepository.save(feedback);

			System.out.println("Feedback hardcodeado guardado en la base de datos.");
		};
	}*/

}
