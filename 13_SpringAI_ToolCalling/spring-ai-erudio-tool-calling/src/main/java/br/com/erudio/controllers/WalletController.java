package br.com.erudio.controllers;

import br.com.erudio.tools.StockTools;
import br.com.erudio.tools.WalletTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
public class WalletController {

    private final ChatClient chatClient;
    private final StockTools stockTools;
    private final WalletTools walletTools;

    public WalletController(ChatClient.Builder chatClientBuilder,
                            StockTools stockTools,
                            WalletTools walletTools) {

        this.chatClient = chatClientBuilder
            .defaultAdvisors(new SimpleLoggerAdvisor())
            .build();

        this.stockTools = stockTools;
        this.walletTools = walletTools;
    }

    @GetMapping("/wallet")
    String calculateWalletValue() {
        PromptTemplate template = new PromptTemplate("""
            What’s the current value in dollars of my wallet based on the latest stock daily prices?
            To improve readability, add tables and line breaks when deemed necessary.
            """);

        return this.chatClient.prompt(template.create(
            ToolCallingChatOptions.builder()
                .toolNames("numberOfShares", "latestStockPrices")
                .build()))
            .call().content();
    }

    @GetMapping("/with-tools")
    String calculateWalletValueWithTools() {
        PromptTemplate template = new PromptTemplate("""
            What’s the current value in dollars of my wallet based on the latest stock daily prices?
            To improve readability, add tables and line breaks when deemed necessary.
            """);

        return this.chatClient.prompt(template.create())
            .tools(stockTools, walletTools)
            .call()
            .content();
    }
}
