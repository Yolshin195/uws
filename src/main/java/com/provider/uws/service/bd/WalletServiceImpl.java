package com.provider.uws.service.bd;

import com.provider.uws.model.Wallet;
import com.provider.uws.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletServiceImpl extends BaseEntityServiceImpl<Wallet> implements WalletService {

    public WalletServiceImpl(WalletRepository repository) {
        super(repository);
    }
}
