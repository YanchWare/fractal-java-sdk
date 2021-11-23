package com.yanchware.fractal.sdk.domain.entities.livesystem.caas;

import com.yanchware.fractal.sdk.domain.entities.Component;
import com.yanchware.fractal.sdk.services.contracts.livesystemcontract.dtos.ProviderType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.yanchware.fractal.sdk.utils.CollectionUtils.isBlank;
import static com.yanchware.fractal.sdk.valueobjects.ComponentType.KAFKA;

@Getter
@Setter(AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class KafkaCluster extends CaaSMessageBrokerImpl implements LiveSystemComponent {
    private Collection<KafkaTopic> kafkaTopics;
    private Collection<KafkaUser> kafkaUsers;

    protected KafkaCluster() {
        kafkaTopics = new ArrayList<>();
        kafkaUsers = new ArrayList<>();
    }

    public static KafkaClusterBuilder builder() {
        return new KafkaClusterBuilder();
    }

    public static class KafkaClusterBuilder extends Builder<KafkaCluster, KafkaClusterBuilder> {

        @Override
        protected KafkaCluster createComponent() {
            return new KafkaCluster();
        }

        @Override
        protected KafkaClusterBuilder getBuilder() {
            return this;
        }

        public KafkaClusterBuilder withNamespace(String namespace) {
            component.setNamespace(namespace);
            return builder;
        }

        public KafkaClusterBuilder withContainerPlatform(String containerPlatform) {
            component.setContainerPlatform(containerPlatform);
            return builder;
        }

        public KafkaClusterBuilder withKafkaTopic(KafkaTopic topic) {
            return withKafkaTopics(List.of(topic));
        }

        public KafkaClusterBuilder withKafkaTopics(List<KafkaTopic> topics) {
            if (isBlank(topics)) {
                return builder;
            }

            if (component.getKafkaTopics() == null) {
                component.setKafkaTopics(new ArrayList<>());
            }

            topics.forEach(topic -> extractTopicInfo(topic, component));
            component.getKafkaTopics().addAll(topics);
            return builder;
        }

        public KafkaClusterBuilder withKafkaUser(KafkaUser user) {
            return withKafkaUsers(List.of(user));
        }

        public KafkaClusterBuilder withKafkaUsers(List<KafkaUser> users) {
            if (isBlank(users)) {
                return builder;
            }

            if (component.getKafkaUsers() == null) {
                component.setKafkaUsers(new ArrayList<>());
            }

            users.forEach(user -> extractUserInfo(user, component));
            component.getKafkaUsers().addAll(users);
            return builder;
        }

        @Override
        public KafkaCluster build() {
            component.setType(KAFKA);
            return super.build();
        }
    }

    @Override
    public void extractInfo(ProviderType provider, Component from) {
        setProvider(provider);
        setContainerPlatform(from.getId().getValue());
        getDependencies().add(from.getId());
        getKafkaUsers().forEach(user -> {
            user.setContainerPlatform(from.getId().getValue());
            user.setProvider(provider);
        });
        getKafkaTopics().forEach(user -> {
            user.setContainerPlatform(from.getId().getValue());
            user.setProvider(provider);
        });
    }

    @Override
    public Collection<String> validate() {
        Collection<String> errors = super.validate();

        kafkaTopics.stream()
                .map(KafkaTopic::validate)
                .forEach(errors::addAll);
        kafkaUsers.stream()
                .map(KafkaUser::validate)
                .forEach(errors::addAll);

        return errors;
    }

    private static void extractUserInfo(KafkaUser user, KafkaCluster cluster) {
        user.setContainerPlatform(cluster.getContainerPlatform());
        user.setClusterName(cluster.getId().getValue());
        user.setNamespace(cluster.getNamespace());
        user.setProvider(cluster.getProvider());
        user.getDependencies().add(cluster.getId());
    }

    private static void extractTopicInfo(KafkaTopic topic, KafkaCluster cluster) {
        topic.setContainerPlatform(cluster.getContainerPlatform());
        topic.setClusterName(cluster.getId().getValue());
        topic.setNamespace(cluster.getNamespace());
        topic.setProvider(cluster.getProvider());
        topic.getDependencies().add(cluster.getId());
    }
}
