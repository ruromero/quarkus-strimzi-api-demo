package io.github.openaccelerators.mercury.demo;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.strimzi.api.kafka.model.Kafka;
import io.strimzi.api.kafka.model.KafkaClusterSpec;
import io.strimzi.api.kafka.model.KafkaSpec;
import io.strimzi.api.kafka.model.ZookeeperClusterSpec;
import io.strimzi.api.kafka.model.listener.arraylistener.GenericKafkaListener;
import io.strimzi.api.kafka.model.storage.EphemeralStorage;
import io.strimzi.api.kafka.model.storage.Storage;

@RegisterForReflection(targets = {
        Kafka.class,
        KafkaSpec.class,
        KafkaClusterSpec.class,
        GenericKafkaListener.class,
        Storage.class,
        EphemeralStorage.class,
        ZookeeperClusterSpec.class,

})
public class CustomReflection {
}
