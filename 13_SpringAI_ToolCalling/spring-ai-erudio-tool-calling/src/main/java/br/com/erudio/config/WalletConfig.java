package br.com.erudio.config;

import br.com.erudio.api.StockRequest;
import br.com.erudio.api.StockResponse;
import br.com.erudio.api.WalletResponse;
import br.com.erudio.repositories.WalletRepository;
import br.com.erudio.services.StockService;
import br.com.erudio.services.WalletService;
import br.com.erudio.tools.StockTools;
import br.com.erudio.tools.WalletTools;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.web.client.RestTemplate;

import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class WalletConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Bean
    @Description("Number of shares for each company in my portfolio")
    public Supplier<WalletResponse> numberOfShares(WalletRepository repository) {
        return new WalletService(repository);
    }

    @Bean
    @Description("Latest Stock Prices")
    public Function<StockRequest, StockResponse> latestStockPrices(){
        return new StockService(restTemplate());
    }
    
    @Bean
    public WalletTools walletTools(WalletRepository repository) {
        return new WalletTools(repository);
    }

    @Bean
    public StockTools stockTools(){
        return new StockTools(restTemplate());
    }
}
