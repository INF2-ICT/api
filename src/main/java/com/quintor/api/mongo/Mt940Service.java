package com.quintor.api.mongo;

import org.springframework.stereotype.Service;

@Service
public class Mt940Service {
    private final Mt940Repository mt940Repository;

    public Mt940Service(Mt940Repository mt940Repository) {
        this.mt940Repository = mt940Repository;
    }

    public mt940 insertMt940(mt940 mt940) {
        return mt940Repository.insert(mt940);
    }
}