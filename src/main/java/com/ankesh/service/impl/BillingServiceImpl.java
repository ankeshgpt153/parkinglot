package com.ankesh.service.impl;

import com.ankesh.service.BillingService;

public class BillingServiceImpl implements BillingService {
    @Override
    public long calculateBill(int timeDuration) {
        if (timeDuration > 2) {
            return 10 + (10 * (timeDuration-2));
        }
        return 20;
    }
}
