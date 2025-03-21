package dev.danvega.reviews;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ReviewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewsApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(ChatClient.Builder builder) {
		return args -> {
			var client = builder
					.defaultOptions(ChatOptions.builder().temperature(0.0d).build())
					.build();

			var system = new SystemMessage("You are an assistant that classifies tweet sentiment as Positive, Negative, or Neutral.");
			var tweets = new UserMessage( """
					Tweet: "I love sunny days!"
					Sentiment: Positive
					
					Tweet: "I lost my wallet today."
					Sentiment: Negative
					
					Tweet: "Just had lunch."
					Sentiment: Neutral
					
					Tweet: "Excited for my trip tomorrow!"
					Sentiment:
					""");

			String response = client.prompt(new Prompt(List.of(system, tweets)))
					.call()
					.content();

			System.out.println(response);


		};
	}

}
