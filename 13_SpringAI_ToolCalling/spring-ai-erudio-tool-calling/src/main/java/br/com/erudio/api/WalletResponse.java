package br.com.erudio.api;

import br.com.erudio.entities.Share;

import java.util.List;

public record WalletResponse(List<Share> shares) {}