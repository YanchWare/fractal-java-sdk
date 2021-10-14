package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSKafkaUser;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.configuration.Constants.DEFAULT_VERSION;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.KAFKA_USER;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class KafkaUser extends CaaSKafkaUser implements LiveSystemComponent {

    private String containerPlatform;
    private String namespace;
    private String clusterName;
    private ProviderType provider;
    private Collection<KafkaACL> acls;

    protected KafkaUser() {
        acls = new ArrayList<>();
    }

    @Override
    public ProviderType getProvider() {
        return provider;
    }

    public static KafkaUserBuilder builder() {
        return new KafkaUserBuilder();
    }

    public static class KafkaUserBuilder extends Builder<KafkaUser, KafkaUserBuilder> {

        //TODO if we agree/enforce these to be passed from KafkaCluster, I would remove them.
        public KafkaUserBuilder containerPlatform(String containerPlatform) {
            component.setContainerPlatform(containerPlatform);
            return builder;
        }

        public KafkaUserBuilder namespace(String namespace) {
            component.setNamespace(namespace);
            return builder;
        }

        public KafkaUserBuilder clusterName(String clusterName) {
            component.setClusterName(clusterName);
            return builder;
        }

        public KafkaUserBuilder acl(KafkaACL acl) {
            if (component.getAcls() == null) {
                component.setAcls(new ArrayList<>());
            }
            if (acl != null) {
                component.getAcls().add(acl);
            }
            return builder;
        }

        public KafkaUserBuilder acls(List<KafkaACL> acls) {
            if (acls == null || acls.isEmpty()) {
                return builder;
            }

            if (component.getAcls() == null) {
                component.setAcls(new ArrayList<>());
            }

            component.getAcls().addAll(acls);
            return builder;
        }

        public KafkaUserBuilder withTopicReadACL(String serviceName) {
            if (component.getAcls() == null) {
                component.setAcls(new ArrayList<>());
            }
            if (!StringUtils.isBlank(serviceName)) {
                component.getAcls().add(KafkaACL.builder().
                        resource(KafkaResource.builder().
                                type("topic").
                                name(serviceName).
                                patternType("literal").build()).
                        operation("read").build());
            }
            return builder;
        }

        @Override
        protected KafkaUser createComponent() {
            return new KafkaUser();
        }

        @Override
        protected KafkaUserBuilder getBuilder() {
            return this;
        }

        @Override
        public KafkaUser build() {
            component.setType(KAFKA_USER);
            component.setVersion(DEFAULT_VERSION);
            return super.build();
        }
    }
}
