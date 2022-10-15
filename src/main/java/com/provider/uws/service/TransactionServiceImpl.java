package com.provider.uws.service;

import com.provider.uws.PerformTransactionArguments;
import com.provider.uws.PerformTransactionResult;
import com.provider.uws.model.User;
import com.provider.uws.service.bd.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    UserService userService;

    @Override
    public PerformTransactionResult perform(PerformTransactionArguments arguments) {
        PerformTransactionResult result = new PerformTransactionResult();

        User user = userService.findByUsername(arguments.getUsername());
        if (user != null && user.getPassword().equals(arguments.getPassword())) {

        }

        return result;
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
