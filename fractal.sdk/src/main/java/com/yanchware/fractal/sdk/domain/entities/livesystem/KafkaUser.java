package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.domain.entities.blueprint.caas.CaaSKafkaUser;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.domain.entities.livesystem.ACLOperation.READ;
import static com.yanchware.fractal.sdk.domain.entities.livesystem.KafkaACLType.TOPIC;
import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
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

        public KafkaUserBuilder withAcl(KafkaACL acl) {
            return withAcls(List.of(acl));
        }

        public KafkaUserBuilder withAcls(List<KafkaACL> acls) {
            if (isBlank(acls)) {
                return builder;
            }

            if (component.getAcls() == null) {
                component.setAcls(new ArrayList<>());
            }

            component.getAcls().addAll(acls);
            return builder;
        }

        public KafkaUserBuilder withTopicReadACL(String serviceName) {
            return withAcl(KafkaACL.builder().
                    resource(KafkaResource.builder().
                            type(TOPIC).
                            name(serviceName).
                            patternType(KafkaACLPatternType.LITERAL).build()).
                    operation(READ).build());
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
            return super.build();
        }
    }
}
