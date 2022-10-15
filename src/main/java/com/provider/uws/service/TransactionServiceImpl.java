package com.provider.uws.service;

import com.provider.uws.GenericParam;
import com.provider.uws.PerformTransactionArguments;
import com.provider.uws.PerformTransactionResult;
import com.provider.uws.model.*;
import com.provider.uws.service.bd.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    UserService userService;

    @Autowired
    CustomerService customerService;

    @Autowired
    ProviderService providerService;

    @Autowired
    WalletService walletService;

    @Autowired
    TransactionEntityService transactionEntityService;

    @Override
    @Transactional
    public PerformTransactionResult perform(PerformTransactionArguments arguments) {
        PerformTransactionResult result = new PerformTransactionResult();
        Customer customer = null;
        Provider provider = null;
        Wallet wallet = null;

        if (!isAuth(arguments.getUsername(), arguments.getPassword())) {
            result.setErrorMsg("The username or password you entered is incorrect");
            result.setStatus(401);
            return result;
        }

        Optional<Provider> providerOptional = providerService.findByServiceId(arguments.getServiceId());
        if (providerOptional.isEmpty()) {
            result.setErrorMsg("Service not found");
            result.setStatus(400);
            return result;
        }
        provider = providerOptional.get();

        List<GenericParam> paramList = arguments.getParameters();
        Optional<GenericParam> phoneOptional = getPhone(paramList);
        if (phoneOptional.isPresent()) {
            Optional<Customer> customerOptional = customerService.findByPhone(
                    phoneOptional.get().getParamValue()
            );
            if (customerOptional.isEmpty()) {
                result.setErrorMsg("Customer not found");
                result.setStatus(400);
                return result;
            }
            customer = customerOptional.get();
        }

        if (customer != null ) {
            Optional<Wallet> walletOptional = walletService.findByProviderAndCustomer(provider, customer);
            if (walletOptional.isEmpty()) {
                result.setErrorMsg("Wallet not found");
                result.setStatus(400);
                return result;
            }
            wallet = walletOptional.get();
        }

        Transaction transaction = Transaction.builder()
                .wallet(wallet)
                .transactionId(arguments.getTransactionId())
                .amount(arguments.getAmount())
                .balanceBeforeSurgery(wallet.getBalance())
                .balanceAfterSurgery(wallet.getBalance() + arguments.getAmount())
                .transactionType(TransactionTypeEnum.CREDIT.getValue())
                .build();

        transaction = transactionEntityService.save(transaction);

        wallet.setBalance(transaction.getBalanceAfterSurgery());

        walletService.update(wallet);

        result.setStatus(200);

        return result;
    }

    private Optional<GenericParam> getPhone(List<GenericParam> paramList) {
        return paramList.stream()
                .filter(p -> p.getParamKey().equals("phone"))
                .findAny();
    }

    private boolean isAuth(String username, String password) {
        User user = userService.findByUsername(username);
        if (user != null && user.getActive() && user.getPassword().equals(password)) {
            return true;
        }

        return false;
    }

    private static boolean isValidLuhn(String value) {
        int sum = Character.getNumericValue(value.charAt(value.length() - 1));
        int parity = value.length() % 2;
        for (int i = value.length() - 2; i >= 0; i--) {
            int summand = Character.getNumericValue(value.charAt(i));
            if (i % 2 == parity) {
                int product = summand * 2;
                summand = (product > 9) ? (product - 9) : product;
            }
            sum += summand;
        }
        return (sum % 10) == 0;
    }
}
