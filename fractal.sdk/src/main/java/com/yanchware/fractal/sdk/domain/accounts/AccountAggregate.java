package com.yanchware.fractal.sdk.domain.accounts;

import com.yanchware.fractal.sdk.domain.accounts.service.AccountsService;
import com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService;
import com.yanchware.fractal.sdk.domain.accounts.service.dtos.PersonalResourceGroupResponse;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Setter(AccessLevel.PROTECTED)
public class AccountAggregate {

    private final AccountsService service;

    public record PersonalResourceGroup(String shortName, String displayName) {
    }

    private final List<PersonalResourceGroup> personalResourceGroups = new ArrayList<>();

    public AccountAggregate(RestAccountsService restAccountsService) {
        this.service = restAccountsService;
    }

    public void addPersonalResourceGroup(String shortName, String displayName) {
        int idx = -1;
        for (int i = 0; i < personalResourceGroups.size(); i++) {
            if (personalResourceGroups.get(i).shortName().equals(shortName)) {
                idx = i;
                break;
            }
        }
        if (idx >= 0) {
            var existing = personalResourceGroups.get(idx);
            if (!existing.displayName().equals(displayName)) {
                personalResourceGroups.set(idx, new PersonalResourceGroup(shortName, displayName));
            }
            return;
        }
        personalResourceGroups.add(new PersonalResourceGroup(shortName, displayName));
    }


    public void createOrUpdate() throws InstantiatorException {
        if (personalResourceGroups.isEmpty()) {
            log.info("No personal resource groups to manage.");
            return;
        }

        int created = 0;
        for (var rg : personalResourceGroups) {
            var shortName = rg.shortName();
            var displayName = rg.displayName();

            PersonalResourceGroupResponse existing = service.getByShortName(shortName);

            if (existing == null) {
                log.info("Creating Personal Resource Group [shortName: '{}', displayName: '{}']", shortName, displayName);
                var response = service.upsertPersonalResourceGroup(shortName, displayName);
                log.debug("Upserted Personal Resource Group id: {}", response.Id());
                created++;
            } else {
                log.info("Personal Resource Group [shortName: '{}'] already exists. Skipping.", shortName);
            }
        }
        log.info("Created {} new Personal Resource Group(s).", created);
    }
}
