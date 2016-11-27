package com.example.oliver.someexample.db;

import com.example.oliver.someexample.models.pojo.OrganizationPOJOModel;

import java.util.List;
import java.util.Map;

/**
 * Created by zolotar on 26/11/16.
 */

public interface FinanceDBEndpoint {

    void insertRegions(Map<String, String> regions);
    void insertCities(Map<String, String> cities);
    void insertOrganizations(List<OrganizationPOJOModel> organizationsList);
}
