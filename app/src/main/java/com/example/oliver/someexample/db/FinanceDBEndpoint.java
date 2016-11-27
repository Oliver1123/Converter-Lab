package com.example.oliver.someexample.db;

import com.example.oliver.someexample.models.pojo.CurrencyPOJO;
import com.example.oliver.someexample.models.pojo.FinanceSnapshotPOJO;
import com.example.oliver.someexample.models.pojo.OrganizationPOJO;

import java.util.List;
import java.util.Map;

/**
 * Created by zolotar on 26/11/16.
 */

public interface FinanceDBEndpoint {

    void insertRegions(Map<String, String> regions);
    void insertCities(Map<String, String> cities);
    void insertOrganizations(List<OrganizationPOJO> organizationsList);
    void insertCurrenciesData(String orgID, Map<String, CurrencyPOJO> currencies, String data);
    void insertFinanceSnapshot(FinanceSnapshotPOJO financeSnapshot);
}
