package com.yanchware.fractal.sdk.domain.accounts;

import com.yanchware.fractal.sdk.domain.accounts.service.AccountsService;
import com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter(AccessLevel.PROTECTED)
public class AccountAggregate {

    private final AccountsService service;

    public AccountAggregate(RestAccountsService restAccountsService) {
        this.service = restAccountsService;
    }

    public void createOrUpdateResourceGroup(String Shortname, String DisplayName) throws InstantiatorException {
        service.upsertPersonalResourceGroup(Shortname, DisplayName);
    }

    public void setAccount(AccountAggregate accountAggregate) {

    }
}
