package io.github.openaccelerators.mercury.demo;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.strimzi.api.kafka.model.EntityOperatorSpec;
import io.strimzi.api.kafka.model.EntityTopicOperatorSpec;
import io.strimzi.api.kafka.model.EntityUserOperatorSpec;
import io.strimzi.api.kafka.model.Kafka;
import io.strimzi.api.kafka.model.KafkaClusterSpec;
import io.strimzi.api.kafka.model.KafkaSpec;
import io.strimzi.api.kafka.model.ZookeeperClusterSpec;
import io.strimzi.api.kafka.model.listener.arraylistener.GenericKafkaListener;
import io.strimzi.api.kafka.model.listener.arraylistener.KafkaListenerType;
import io.strimzi.api.kafka.model.status.Condition;
import io.strimzi.api.kafka.model.status.KafkaStatus;
import io.strimzi.api.kafka.model.status.Status;
import io.strimzi.api.kafka.model.storage.EphemeralStorage;
import io.strimzi.api.kafka.model.storage.Storage;

@RegisterForReflection(targets = {
        Kafka.class,
        Status.class,
        KafkaSpec.class,
        KafkaStatus.class,
        Condition.class,
        KafkaClusterSpec.class,
        GenericKafkaListener.class,
        Storage.class,
        EphemeralStorage.class,
        ZookeeperClusterSpec.class,
        KafkaListenerType.class,
        EntityOperatorSpec.class,
        EntityTopicOperatorSpec.class,
        EntityUserOperatorSpec.class
})
public class CustomReflection {
}
