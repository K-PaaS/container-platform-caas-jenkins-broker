package model;

import org.openpaas.servicebroker.model.Plan;
import org.openpaas.servicebroker.model.ServiceDefinition;

import java.util.*;

public class ServiceDefinitionModel {

    public static ServiceDefinition getService() {

        return new ServiceDefinition(
                "54e2de61-de84-4b9c-afc3-88d08aadfcb6",
                "jenkins",
                "A delivery pipeline service for application development.provision parameters : parameters {owner : owner}",
                true, // bindable
                false, // updatable
                Arrays.asList(
                        new Plan("2a26b717-b8b5-489c-8ef1-02bcdc445720",
                                "dedicated-vm",
                                "jenkins test",
                                null)),
                Arrays.asList("20G"),
                getMetadata(),
                null,
                null);
    }


    /* Service Metadata */
    private static Map<String,Object> getMetadata() {
        Map<String, Object> sdMetadata = new HashMap<String, Object>();
        sdMetadata.put("displayName", "jenkins Service");
        sdMetadata.put("imageUrl", "");
        sdMetadata.put("longDescription", "K-Paas jenkins Service");
        sdMetadata.put("providerDisplayName", "K-PaaS");
        sdMetadata.put("documentationUrl", "https://k-paas.or.kr");
        sdMetadata.put("supportUrl", "https://k-paas.or.kr");
        return sdMetadata;
    }

    public static List<ServiceDefinition> getCatalog() {
        List<ServiceDefinition> result = new ArrayList<>();
        result.add(getService());
        return result;
    }

}
