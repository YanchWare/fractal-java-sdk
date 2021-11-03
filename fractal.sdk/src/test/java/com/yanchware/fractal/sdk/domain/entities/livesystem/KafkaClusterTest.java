package com.yanchware.fractal.sdk.domain.entities.livesystem;

import com.yanchware.fractal.sdk.valueobjects.ComponentId;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class KafkaClusterTest {

    @Test
    public void exceptionThrown_when_kafkaCluster_isCreatedWithoutNamespace() {
        var kafkaBuilder = KafkaCluster.builder()
                .withId("azure-kafka")
                .withDescription("Kafka for Azure")
                .withDisplayName("AzureKafka #1");
        assertThatThrownBy(kafkaBuilder::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[KafkaCluster Validation] Namespace has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_kafkaCluster_isCreatedWithoutId() {
        var kafkaBuilder = KafkaCluster.builder()
                .withDescription("Kafka for Azure")
                .withDisplayName("AzureKafka #1")
                .withNamespace("namespace");
        assertThatThrownBy(kafkaBuilder::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "Component id has not been defined and it is required");
    }

    @Test
    public void exceptionThrown_when_kafkaCluster_isCreatedWithEmptyContainerPlatform() {
        var kafkaBuilder = KafkaCluster.builder()
                .withId("azure-kafka")
                .withDescription("Kafka for Azure")
                .withDisplayName("AzureKafka #1")
                .withNamespace("namespace")
                .withContainerPlatform("");
        assertThatThrownBy(kafkaBuilder::build).isInstanceOf(IllegalArgumentException.class).hasMessageContainingAll(
                "[KafkaCluster Validation] ContainerPlatform defined was either empty or blank and it is required");
    }

    @Test
    public void propertiesAreSet_when_kafkaCluster_isProperlyConfigured() {
        var kafkaCluster = KafkaCluster.builder()
                .withId("azure-kafka")
                .withDescription("Kafka for Azure")
                .withDisplayName("AzureKafka #1")
                .withNamespace("namespace")
                .withContainerPlatform("containerPlatform")
                .withKafkaTopics(List.of(
                        KafkaTopic.builder().withId(ComponentId.from("topic")).withDisplayName("kafka-topic").build()))
                .withKafkaUsers(List.of(
                        KafkaUser.builder().withId(ComponentId.from("user-1")).withDisplayName("kafka-user").withTopicReadACL("svcName").build())).build();

        assertThat(kafkaCluster)
                .returns("azure-kafka", from(x -> x.getId().getValue()))
                .returns("Kafka for Azure", from(KafkaCluster::getDescription))
                .returns("AzureKafka #1", from(KafkaCluster::getDisplayName))
                .returns("namespace", from(KafkaCluster::getNamespace))
                .returns("containerPlatform", from(KafkaCluster::getContainerPlatform))
                .returns(null, from(KafkaCluster::getProvider))
                .returns(1, from(x -> x.getKafkaTopics().size()))
                .returns(1, from(x -> x.getKafkaTopics().size()));

        KafkaUser kafkaUser = kafkaCluster.getKafkaUsers().stream().findFirst().get();
        assertThat(kafkaUser)
                .returns("user-1", from(x -> x.getId().getValue()))
                .returns("kafka-user", from(KafkaUser::getDisplayName))
                .returns(null, from(KafkaUser::getProvider))
                .returns(kafkaCluster.getId().getValue(), from(KafkaUser::getClusterName))
                .returns(kafkaCluster.getNamespace(), from(KafkaUser::getNamespace))
                .returns(kafkaCluster.getContainerPlatform(), from(KafkaUser::getContainerPlatform))
                .returns(1, from(x -> x.getAcls().size()));

        assertThat(kafkaUser.getAcls().stream().findFirst().get())
                .returns("read", from(x -> x.getOperation().getId()))
                .returns("svcName", from(x -> x.getResource().getName()))
                .returns("topic", from(x -> x.getResource().getType().getId()))
                .returns("literal", from(x -> x.getResource().getPatternType().getId()));

        assertThat(kafkaCluster.getKafkaTopics().stream().findFirst().get())
                .returns("topic", from(x -> x.getId().getValue()))
                .returns("kafka-topic", from(KafkaTopic::getDisplayName))
                .returns(null, from(KafkaTopic::getProvider))
                .returns(kafkaCluster.getId().getValue(), from(KafkaTopic::getClusterName))
                .returns(kafkaCluster.getNamespace(), from(KafkaTopic::getNamespace))
                .returns(kafkaCluster.getContainerPlatform(), from(KafkaTopic::getContainerPlatform));

    }

}