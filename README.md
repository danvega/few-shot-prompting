# Tweet Sentiment Analysis

![Spring AI Banner](https://img.shields.io/badge/Spring%20AI-1.0.0--M6-brightgreen)
![Java 23](https://img.shields.io/badge/Java-23-orange)
![Spring Boot 3.4.3](https://img.shields.io/badge/Spring%20Boot-3.4.3-green)

A lightweight Spring Boot application that classifies tweets as Positive, Negative, or Neutral using OpenAI's language models through Spring AI.

## Overview

This application demonstrates how to integrate Spring AI with OpenAI to build a simple sentiment analysis tool for tweets. It showcases few-shot learning by providing examples of how to classify tweets with different sentiments, then asking the model to classify a new tweet.

## Project Requirements

- Java 23
- Maven 3.6+
- OpenAI API key

## Dependencies

The project uses the following major dependencies:

- **Spring Boot 3.4.3**: Framework for building stand-alone Spring applications
- **Spring Web**: RESTful web services and MVC implementation
- **Spring AI OpenAI**: Spring Boot starter for OpenAI integration
- **Spring AI BOM**: Bill of Materials for Spring AI dependency management

## Getting Started

### Configuration

1. Set up your OpenAI API key as an environment variable:

```bash
export OPENAI_API_KEY=your_openai_api_key_here
```

2. The application is configured to use GPT-4o model by default. This can be modified in `application.properties`:

```properties
spring.ai.openai.chat.options.model=gpt-4o
```

## Running the Application

You can run the application using Maven:

```bash
mvn spring-boot:run
```

The application will execute as a command-line application and output the sentiment classification for the example tweet.

## How It Works

This application demonstrates the power of Spring AI with a few key components:

### ChatClient Builder

The application uses Spring AI's `ChatClient.Builder` to create a client with specific options:

```java
var client = builder
        .defaultOptions(ChatOptions.builder().temperature(0.0d).build())
        .build();
```

Setting the temperature to 0.0 ensures consistent, deterministic responses from the AI model.

### Prompt Construction

The application constructs a prompt with a system message and user message:

```java
var system = new SystemMessage("You are an assistant that classifies tweet sentiment as Positive, Negative, or Neutral.");
var tweets = new UserMessage("""
        Tweet: "I love sunny days!"
        Sentiment: Positive
        
        Tweet: "I lost my wallet today."
        Sentiment: Negative
        
        Tweet: "Just had lunch."
        Sentiment: Neutral
        
        Tweet: "Excited for my trip tomorrow!"
        Sentiment:
        """);
```

The system message establishes the role of the AI, while the user message contains examples (few-shot learning) and the tweet to be classified.

### Making the AI Call

The application sends the prompt to the OpenAI model and receives the classification:

```java
String response = client.prompt(new Prompt(List.of(system, tweets)))
        .call()
        .content();
```

## Example Output

When you run the application, it will classify the sentiment of "Excited for my trip tomorrow!" and output something like:

```
Positive
```

## Extending the Application

You can modify this application to:

1. Accept tweets from user input rather than hardcoded examples
2. Create a REST API endpoint for sentiment analysis
3. Batch process multiple tweets
4. Store classification results in a database
5. Add more complex classification categories

## Core Code Explanation

The heart of this application is the `CommandLineRunner` bean that performs the sentiment analysis:

```java
@Bean
CommandLineRunner commandLineRunner(ChatClient.Builder builder) {
    return args -> {
        // Build the AI client with specific options
        var client = builder
                .defaultOptions(ChatOptions.builder().temperature(0.0d).build())
                .build();

        // Define the system role and examples
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

        // Call the AI and print the response
        String response = client.prompt(new Prompt(List.of(system, tweets)))
                .call()
                .content();

        System.out.println(response);
    };
}
```

## Summary

This project demonstrates a practical application of AI in natural language processing using Spring AI and OpenAI. The sentiment analysis functionality can be adapted and expanded for various text classification tasks.

With just a few lines of code, you can harness the power of large language models to analyze text sentiment in your Java applications.

## Resources

- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/index.html)
- [OpenAI API Documentation](https://platform.openai.com/docs/introduction)
- [Spring Boot Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)