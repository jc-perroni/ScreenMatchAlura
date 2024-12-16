package br.com.alura.screenmatch.service.traducao;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConsultaChatGpt {
        @Value("${gpt.token}")
        private String apiToken;
        public String obterTraducao(String texto) {
            System.out.println("Token utilizado "+ apiToken);

            OpenAiService service = new OpenAiService(apiToken);

            CompletionRequest requisicao = CompletionRequest.builder()
                    .model("text-davinci-003")
                    .prompt("traduza o texto a seguir para o português: " + texto)
                    .maxTokens(1000)
                    .temperature(0.7)
                    .build();

            var resposta = service.createCompletion(requisicao);
            return resposta.getChoices().get(0).getText();
        }
    }

