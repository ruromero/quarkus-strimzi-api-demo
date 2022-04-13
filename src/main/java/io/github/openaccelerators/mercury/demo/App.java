package io.github.openaccelerators.mercury.demo;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.fabric8.kubernetes.api.model.OwnerReferenceBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.quarkus.runtime.StartupEvent;
import io.strimzi.api.kafka.model.EntityOperatorSpecBuilder;
import io.strimzi.api.kafka.model.EntityTopicOperatorSpecBuilder;
import io.strimzi.api.kafka.model.Kafka;
import io.strimzi.api.kafka.model.KafkaBuilder;
import io.strimzi.api.kafka.model.KafkaClusterSpecBuilder;
import io.strimzi.api.kafka.model.ZookeeperClusterSpecBuilder;
import io.strimzi.api.kafka.model.listener.arraylistener.GenericKafkaListenerBuilder;
import io.strimzi.api.kafka.model.listener.arraylistener.KafkaListenerType;
import io.strimzi.api.kafka.model.storage.EphemeralStorageBuilder;

import static io.strimzi.api.kafka.model.authentication.KafkaClientAuthenticationPlain.TYPE_PLAIN;
import static io.strimzi.api.kafka.model.authentication.KafkaClientAuthenticationTls.TYPE_TLS;

@ApplicationScoped
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    @Inject
    KubernetesClient client;
    
    void createKafka(@Observes StartupEvent ev) {
        Kafka desiredKafka = new KafkaBuilder()
                .withNewMetadata()
                .withName("the-kafka")
                .withNamespace(client.getNamespace())
                .withLabels(Map.of("managaed-by", "my-operator"))
                .endMetadata()
                .withNewSpec()
                .withEntityOperator(new EntityOperatorSpecBuilder()
                        .withTopicOperator(new EntityTopicOperatorSpecBuilder().build())
                        .build())
                .withKafka(new KafkaClusterSpecBuilder()
                        .withReplicas(1)
                        .withListeners(new GenericKafkaListenerBuilder()
                                        .withName(TYPE_PLAIN)
                                        .withPort(9092)
                                        .withType(KafkaListenerType.INTERNAL)
                                        .withTls(false)
                                        .build(),
                                new GenericKafkaListenerBuilder()
                                        .withName(TYPE_TLS)
                                        .withPort(9093)
                                        .withType(KafkaListenerType.INTERNAL)
                                        .withTls(true)
                                        .build())
                        .withVersion("3.0.0")
                        .withConfig(Map.of(
                                "inter.broker.protocol.version", "v1",
                                "default.replication.factor", "1",
                                "offsets.topic.replication.factor", "1",
                                "transaction.state.log.replication.factor", "1",
                                "transaction.state.log.min.isr", "1",
                                "min.insync.replicas", "1"))
                        .withStorage(new EphemeralStorageBuilder().build())
                        .build())
                .withZookeeper(new ZookeeperClusterSpecBuilder()
                        .withReplicas(1)
                        .withStorage(new EphemeralStorageBuilder().build())
                        .build())
                .endSpec()
                .build();

        desiredKafka.getMetadata().setOwnerReferences(List.of(new OwnerReferenceBuilder()
                .withName("owner-name")
                .withUid(UUID.randomUUID().toString())
                .withKind("somekind")
                .withApiVersion("v1")
                .build()));
        
        LOGGER.info("Generated kafka object: {}", desiredKafka);
        
        client.resources(Kafka.class).createOrReplace(desiredKafka);
    }

}
