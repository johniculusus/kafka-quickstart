package org.acme.kafka;

import io.reactivex.Flowable;

import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import javax.enterprise.context.ApplicationScoped;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * A bean producing random prices every 5 seconds.
 * The prices are written to a Kafka topic (prices). The Kafka configuration is specified in the application configuration.
 */
@ApplicationScoped
public class PriceGenerator {
	private Random random = new Random();

    @Outgoing("generated-price")                        
    public Flowable<String> generate() {
        return Flowable.interval(5, TimeUnit.SECONDS)
                .map(tick -> String.valueOf(random.nextInt(100)))
                .onBackpressureDrop();
    }
    
//    @Incoming("prices")
//    @Outgoing("generated-price")
//    public Flowable<String> addOverflow(Flowable<String> in) {
//        return in.onBackpressureDrop();
//    }
}
