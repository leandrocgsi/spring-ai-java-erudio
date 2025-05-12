package br.com.erudio.tools;

import br.com.erudio.entities.Share;
import br.com.erudio.repositories.WalletRepository;
import org.springframework.ai.tool.annotation.Tool;

import java.util.List;

public class WalletTools {

    private WalletRepository repository;

    public WalletTools(WalletRepository repository) {
        this.repository = repository;
    }

    @Tool(description = "Number of shares for each company in my wallet")
    public List<Share> getNumberOfShares(){
        return repository.findAll();
    }
}
