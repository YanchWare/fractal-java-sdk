package com.yanchware.fractal.sdk.domain.accounts;

import com.yanchware.fractal.sdk.domain.accounts.service.AccountsService;
import com.yanchware.fractal.sdk.domain.accounts.service.RestAccountsService;
import com.yanchware.fractal.sdk.domain.accounts.service.dtos.OrganizationalResourceGroupResponse;
import com.yanchware.fractal.sdk.domain.exceptions.InstantiatorException;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Setter(AccessLevel.PROTECTED)
public class OrganizationAggregate {

  private final AccountsService service;

  public record OrganizationalResourceGroup(UUID organizationId, String shortName, String displayName) {}

  private final List<OrganizationalResourceGroup> organizationalResourceGroups = new ArrayList<>();

  public OrganizationAggregate(RestAccountsService restAccountsService) {
    this.service = restAccountsService;
  }

  public void addOrganizationalResourceGroup(UUID organizationId, String shortName, String displayName) {
    int idx = -1;
    for (int i = 0; i < organizationalResourceGroups.size(); i++) {
      var item = organizationalResourceGroups.get(i);
      if (item.organizationId().equals(organizationId) && item.shortName().equals(shortName)) {
        idx = i;
        break;
      }
    }
    if (idx >= 0) {
      var existing = organizationalResourceGroups.get(idx);
      if (!existing.displayName().equals(displayName)) {
        organizationalResourceGroups.set(idx, new OrganizationalResourceGroup(organizationId, shortName, displayName));
      }
      return;
    }
    organizationalResourceGroups.add(new OrganizationalResourceGroup(organizationId, shortName, displayName));
  }

  public void createOrUpdate() throws InstantiatorException {
    if (organizationalResourceGroups.isEmpty()) {
      log.info("No organizational resource groups to manage.");
      return;
    }

    int created = 0;
    for (var rg : organizationalResourceGroups) {
      var organizationId = rg.organizationId();
      var shortName = rg.shortName();
      var displayName = rg.displayName();

      OrganizationalResourceGroupResponse existing =
          service.getOrganizationalResourceGroupByShortName(organizationId, shortName);

      if (existing == null) {
        log.info("Creating Organizational Resource Group [organizationId: '{}', shortName: '{}', displayName: '{}']",
            organizationId, shortName, displayName);

        var response = service.upsertOrganizationalResourceGroup(organizationId, shortName, displayName);
        if (response != null) {
          log.debug("Upserted Organizational Resource Group id: {}", response.Id());
        }
        created++;
      } else {
        log.info("Organizational Resource Group [organizationId: '{}', shortName: '{}'] already exists. Skipping.",
            organizationId, shortName);
      }
    }
    log.info("Created {} new Organizational Resource Group(s).", created);
  }
}
