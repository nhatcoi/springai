# Spring AI Chat Application

1. **Clone and run:**
   ```bash
   ./mvnw spring-boot:run
   ```

2. **Swagger UI:**
   ```
   http://localhost:8888/swagger-ui/index.html
   ```

3. **Test API:**
   ```bash
   # Chat test
   curl "http://localhost:8888/api/chat/test?message=Hello!"
   
   # Chat with conversation
   curl -X POST http://localhost:8888/api/chat/message \
     -H "Content-Type: application/json" \
     -d '{"message": "How are you?", "conversationId": "test-123"}'
   ```

## API Endpoints

- `POST /api/chat/message` - Send message to chat conversation
- `GET /api/chat/test` - Test message
- `DELETE /api/chat/conversation/{id}` - Delete conversation
- `GET /api/chat/conversation/{id}/history` - Get conversation history

## Swagger UI

 **Interactive API Testing**: http://localhost:8888/swagger-ui/index.html

