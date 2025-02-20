# Langchain4j Playground

This project demonstrates various features and capabilities of the Langchain4j library through practical examples. It
serves as a playground for testing different LLM (Large Language Model) functionalities.

## Features

### 1. Simple Chat

- Basic chat implementation using LLM
- Demonstrates direct interaction with the language model
- Includes response length constraints (5 words)

### 2. Service-based Chat

- Implements chat functionality using a service-oriented approach
- Shows how to structure LLM interactions in a service layer

### 3. Chat with Memory

- Demonstrates conversation history management
- Maintains context across multiple messages
- Uses MessageWindowChatMemory with 10-message capacity

### 4. Tools Integration

- Shows how to extend LLM capabilities with custom tools
- Includes example of animal color identification tool
- Demonstrates tool annotation and parameter handling

### 5. RAG (Retrieval-Augmented Generation)

Includes multiple RAG implementations:

#### Easy RAG

- In-memory embedding store
- Simple document ingestion and retrieval
- Specialized for fairy tale animal information

#### Milvus RAG

- Integration with Milvus vector database
- Persistent storage for embeddings
- Supports both basic and structured outputs
- Uses Nomic embeddings

### 6. Voice Recognition

- Audio processing capabilities
- Converts speech to text
- Supports MP3 file processing

## API Endpoints

- `/simple-chat` - Basic chat interface
- `/service-chat` - Service-based chat
- `/history-chat` - Chat with conversation history
- `/tools-chat` - Chat with tool integration
- `/easy-rag/insert` - Document ingestion for in-memory RAG
- `/easy-rag/search` - RAG-based search
- `/milvus-rag/insert` - Document ingestion for Milvus
- `/milvus-rag/search` - Milvus-based search
- `/milvus-rag-structured/insert` - Structured document ingestion
- `/milvus-rag-structured/search` - Structured search with Milvus
- `/recognize-voice` - Voice recognition endpoint

## Technologies Used

- Kotlin
- Langchain4j
- Ktor
- Milvus Vector Database
- Nomic Embeddings
- Llama Model Integration

## Project Structure

The project is organized into:

- Test classes (`T*.kt`) - Demonstrate functionality
- Service implementations (`*Service.kt`) - Core business logic
- API endpoints (`t*.kt`) - HTTP endpoints
- Domain-specific implementations for different features

## Getting Started

1. Ensure you have Java 21 installed
2. Set up Milvus if using RAG features
3. Configure your LLM credentials
4. Run the application
5. Use the provided test cases as examples

## Note

This is a demonstration project intended to showcase Langchain4j capabilities. Each feature is implemented as a separate
module for clarity and educational purposes.