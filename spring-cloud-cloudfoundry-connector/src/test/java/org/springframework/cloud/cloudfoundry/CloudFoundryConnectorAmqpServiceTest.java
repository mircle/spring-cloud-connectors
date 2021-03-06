package org.springframework.cloud.cloudfoundry;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Test;
import org.springframework.cloud.service.ServiceInfo;
import org.springframework.cloud.service.common.AmqpServiceInfo;

/**
 * 
 * @author Ramnivas Laddad
 *
 */
public class CloudFoundryConnectorAmqpServiceTest extends AbstractCloudFoundryConnectorTest {
	@Test
	public void rabbitServiceCreationWithTags() {
		when(mockEnvironment.getEnvValue("VCAP_SERVICES"))
			.thenReturn(getServicesPayload(
					getRabbitServicePayloadWithTags("rabbit-1", hostname, port, username, password, "q-1", "vhost1"),
					getRabbitServicePayloadWithTags("rabbit-2", hostname, port, username, password, "q-2", "vhost2")));

		List<ServiceInfo> serviceInfos = testCloudConnector.getServiceInfos();
		assertServiceFoundOfType(serviceInfos, "rabbit-1", AmqpServiceInfo.class);
		assertServiceFoundOfType(serviceInfos, "rabbit-2", AmqpServiceInfo.class);
	}

	@Test
	public void rabbitServiceCreationWithoutTags() {
		when(mockEnvironment.getEnvValue("VCAP_SERVICES"))
				.thenReturn(getServicesPayload(
						getRabbitServicePayloadWithoutTags("rabbit-1", hostname, port, username, password, "q-1", "vhost1"),
						getRabbitServicePayloadWithoutTags("rabbit-2", hostname, port, username, password, "q-2", "vhost2")));

		List<ServiceInfo> serviceInfos = testCloudConnector.getServiceInfos();
		assertServiceFoundOfType(serviceInfos, "rabbit-1", AmqpServiceInfo.class);
		assertServiceFoundOfType(serviceInfos, "rabbit-2", AmqpServiceInfo.class);
	}

	@Test
	public void rabbitServiceCreationNoLabelNoTags() {
		when(mockEnvironment.getEnvValue("VCAP_SERVICES"))
			.thenReturn(getServicesPayload(
					getRabbitServicePayloadNoLabelNoTags("rabbit-1", hostname, port, username, password, "q-1", "vhost1"),
					getRabbitServicePayloadNoLabelNoTags("rabbit-2", hostname, port, username, password, "q-2", "vhost2")));

		List<ServiceInfo> serviceInfos = testCloudConnector.getServiceInfos();
		assertServiceFoundOfType(serviceInfos, "rabbit-1", AmqpServiceInfo.class);
		assertServiceFoundOfType(serviceInfos, "rabbit-2", AmqpServiceInfo.class);
	}

	@Test
	public void rabbitServiceCreationNoLabelNoTagsSecure() {
		when(mockEnvironment.getEnvValue("VCAP_SERVICES"))
			.thenReturn(getServicesPayload(
					getRabbitServicePayloadNoLabelNoTagsSecure("rabbit-1", hostname, port, username, password, "q-1", "vhost1"),
					getRabbitServicePayloadNoLabelNoTagsSecure("rabbit-2", hostname, port, username, password, "q-2", "vhost2")));

		List<ServiceInfo> serviceInfos = testCloudConnector.getServiceInfos();
		assertServiceFoundOfType(serviceInfos, "rabbit-1", AmqpServiceInfo.class);
		assertServiceFoundOfType(serviceInfos, "rabbit-2", AmqpServiceInfo.class);
	}

	private String getRabbitServicePayloadWithoutTags(String serviceName,
													  String hostname, int port,
													  String user, String password, String name,
													  String vHost) {
		return getRabbitServicePayload("test-rabbit-info-with-label-no-tags.json", serviceName,
				hostname, port, user, password, name, vHost);
	}

	private String getRabbitServicePayloadNoLabelNoTags(String serviceName,
														String hostname, int port,
														String user, String password, String name,
														String vHost) {
		return getRabbitServicePayload("test-rabbit-info-no-label-no-tags.json", serviceName,
				hostname, port, user, password, name, vHost);
	}

	private String getRabbitServicePayloadNoLabelNoTagsSecure(String serviceName,
														String hostname, int port,
														String user, String password, String name,
														String vHost) {
		return getRabbitServicePayload("test-rabbit-info-no-label-no-tags-secure.json", serviceName,
				hostname, port, user, password, name, vHost);
	}

	private String getRabbitServicePayloadWithTags(String serviceName,
												   String hostname, int port,
												   String user, String password, String name,
												   String vHost) {
		return getRabbitServicePayload("test-rabbit-info.json", serviceName,
				hostname, port, user, password, name, vHost);
	}

	private String getRabbitServicePayload(String filename, String serviceName,
										   String hostname, int port,
										   String user, String password, String name,
										   String vHost) {
		String payload = readTestDataFile(filename);
		payload = payload.replace("$serviceName", serviceName);
		payload = payload.replace("$hostname", hostname);
		payload = payload.replace("$port", Integer.toString(port));
		payload = payload.replace("$user", user);
		payload = payload.replace("$pass", password);
		payload = payload.replace("$name", name);
		payload = payload.replace("$virtualHost", vHost);

		return payload;
	}
	
}
