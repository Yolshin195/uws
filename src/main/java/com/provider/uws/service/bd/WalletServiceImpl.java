package com.provider.uws.service.bd;

import com.provider.uws.model.Wallet;
import com.provider.uws.repository.WalletRepository;

public class WalletServiceImpl extends BaseEntityServiceImpl<Wallet> {

    public WalletServiceImpl(WalletRepository repository) {
        super(repository);
    }
}
