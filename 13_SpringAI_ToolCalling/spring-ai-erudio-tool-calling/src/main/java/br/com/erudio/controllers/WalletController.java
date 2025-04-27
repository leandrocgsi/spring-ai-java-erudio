package br.com.erudio.controllers;

import br.com.erudio.tools.StockTools;
import br.com.erudio.tools.WalletTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @GetMapping("/highest-day/{days}")
    String calculateHighestWalletValue(@PathVariable int days) {
        PromptTemplate template = new PromptTemplate("""
            On which day during last {days} days my wallet had the highest value in dollars based on the historical daily stock prices?
            To improve readability, add tables and line breaks when deemed necessary.
            """);

        return this.chatClient.prompt(template.create(Map.of("days", days)))
                .tools(stockTools, walletTools)
                .call()
                .content();
    }
}