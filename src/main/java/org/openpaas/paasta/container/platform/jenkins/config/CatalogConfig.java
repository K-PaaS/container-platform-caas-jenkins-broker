package org.openpaas.paasta.container.platform.jenkins.config;

import org.openpaas.servicebroker.model.Catalog;
import org.openpaas.servicebroker.model.Plan;
import org.openpaas.servicebroker.model.ServiceDefinition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.*;

/**
 * Spring boot 구동시 Catalog API 에서 사용하는 Catalog Bean을 생성하는 클래스
 *
 * @author CheolHan Park
 * @version 1.0
 * @since 2019.11.16
 */

@Configuration
public class CatalogConfig {

	@Value("${serviceDefinition.id}")
	String SERVICEDEFINITION_ID;

	@Value("${serviceDefinition.name}")
	String SERVICEDEFINITION_NAME;

	@Value("${serviceDefinition.desc}")
	String SERVICEDEFINITION_DESC;

	@Value("${serviceDefinition.bindable}")
	String SERVICEDEFINITION_BINDABLE_STRING;

	@Value("${serviceDefinition.planupdatable}")
	String SERVICEDEFINITION_PLANUPDATABLE_STRING;

	@Value("${serviceDefinition.bullet.name}")
	String SERVICEDEFINITION_BULLET_NAME;

	@Value("${serviceDefinition.bullet.desc}")
	String SERVICEDEFINITION_BULLET_DESC;

	@Value("${serviceDefinition.plan1.id}")
	String SERVICEDEFINITION_PLAN1_ID;

	@Value("${serviceDefinition.plan1.name}")
	String SERVICEDEFINITION_PLAN1_NAME;

	@Value("${serviceDefinition.plan1.desc}")
	String SERVICEDEFINITION_PLAN1_DESC;

	@Value("${serviceDefinition.plan1.type}")
	String SERVICEDEFINITION_PLAN1_TYPE;

	@Bean
	public Catalog catalog() {
		boolean SERVICEDEFINITION_BINDABLE = true;
		boolean SERVICEDEFINITION_PLANUPDATABLE = false;

		if(SERVICEDEFINITION_BINDABLE_STRING.toUpperCase().trim().equals("TRUE"))
			SERVICEDEFINITION_BINDABLE = true;

		if(SERVICEDEFINITION_PLANUPDATABLE_STRING.toUpperCase().trim().equals("TRUE"))
			SERVICEDEFINITION_PLANUPDATABLE = true;

		return new Catalog(Arrays.asList(
				new ServiceDefinition(
						SERVICEDEFINITION_ID,
						SERVICEDEFINITION_NAME,
						SERVICEDEFINITION_DESC,
						SERVICEDEFINITION_BINDABLE, // bindable
						SERVICEDEFINITION_PLANUPDATABLE, // updatable
						Arrays.asList(
								new Plan(SERVICEDEFINITION_PLAN1_ID,
										SERVICEDEFINITION_PLAN1_NAME,
										SERVICEDEFINITION_PLAN1_DESC,
										getPlanMetadata(SERVICEDEFINITION_PLAN1_TYPE))),
						Arrays.asList(SERVICEDEFINITION_PLAN1_NAME),
						getServiceDefinitionMetadata(),
						null,
						null)));
	}

	/**
	 * Service Definition Metadata 객체를 생성
	 * (Used by Pivotal CF console)
	 * @return Map:String, Object
	 */
	private Map<String, Object> getServiceDefinitionMetadata() {
		Map<String, Object> sdMetadata = new HashMap<String, Object>();
		sdMetadata.put("displayName", "On-Demand Service" + SERVICEDEFINITION_NAME);
		sdMetadata.put("imageUrl", "");
		sdMetadata.put("longDescription", "Paas-TA On-Demand Service" + SERVICEDEFINITION_NAME);
		sdMetadata.put("providerDisplayName", "PaaS-TA");
		sdMetadata.put("documentationUrl", "https://paas-ta.kr");
		sdMetadata.put("supportUrl", "https://paas-ta.kr");
		return sdMetadata;
	}

	/**
	 * Costs, bullets 정보를 포함한 Plan metadata 객체를 생성
	 * @param planType
	 * @return Map:String, Object
	 */
	private Map<String, Object> getPlanMetadata(String planType) {
		Map<String, Object> planMetadata = new HashMap<>();
		planMetadata.put("costs", getCosts(planType));
		planMetadata.put("bullets", getBullets(planType));

		return planMetadata;
	}

	/**
	 * Plan의 Costs 정보를 Map 객체의 리스트 형태로 반환
	 * @param planType
	 * @return Map:String, Object
	 */
	private List<Map<String, Object>> getCosts(String planType) {
		Map<String, Object> costsMap = new HashMap<>();
		Map<String, Object> amount = new HashMap<>();
		costsMap.put("amount", amount);
		costsMap.put("unit", "MONTHLY");
		switch (planType) {
			case "A":
				amount.put("usd", 0.0);
				break;
			case "B":
				amount.put("usd", 10.0);
				break;
			default:
				amount.put("usd", 100.0);
				break;
		}

		return Collections.singletonList(costsMap);
	}

	/**
	 * Plan의 Bullets 정보를 담은 객체를 반환
	 * @param planType
	 * @return List:String
	 */
	private List<String> getBullets(String planType) {
		return Arrays.asList(SERVICEDEFINITION_BULLET_NAME,
				SERVICEDEFINITION_BULLET_DESC);
	}
}