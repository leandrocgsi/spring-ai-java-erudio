package br.com.erudio.repositories;

import br.com.erudio.entities.Share;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Share, Long> {}