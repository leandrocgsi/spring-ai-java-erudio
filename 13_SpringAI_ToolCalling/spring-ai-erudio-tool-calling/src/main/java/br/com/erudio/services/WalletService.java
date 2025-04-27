package br.com.erudio.services;

import br.com.erudio.api.WalletResponse;
import br.com.erudio.repositories.WalletRepository;

import java.util.function.Supplier;

public class WalletService implements Supplier<WalletResponse> {

    private WalletRepository repository;

    public WalletService(WalletRepository repository) {
        this.repository = repository;
    }

    @Override
    public WalletResponse get() {
        var shares = repository.findAll();
        return new WalletResponse(shares);
    }
}
