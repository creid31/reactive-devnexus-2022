package com.cecilipoole.reactiveexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;

@Component
public class EventEmitter {
    private final Flux<RegistrantChange> changes;
    private static final Logger logger = LoggerFactory.getLogger(EventEmitter.class);
    private final GuestRepository guestRepository;

    public EventEmitter(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;

        changes = Flux.interval(Duration.ofMinutes(1))
                .map(l -> Instant.now().minus(Duration.ofMinutes(1)))
                .concatMap(this::loadChanges)
                .publish()
                .refCount(1, Duration.ofSeconds(30));
    }

    private Flux<RegistrantChange> loadChanges(Instant after) {
        return Flux.concat(loadCreates(after), loadUpdates(after));
    }

    private Flux<RegistrantChange> loadUpdates(Instant after) {
        logger.debug("Loading changes: updates");
        return Flux.fromStream(guestRepository.findByUpdatedAt(after)
                .stream()
                .map(guest -> new RegistrantChange(RegistrantStatus.UPDATED,
                        guest.getId())));
    }

    private Flux<RegistrantChange> loadCreates(Instant after) {
        logger.debug("Loading changes: creates");
        return Flux.fromStream(guestRepository.findByCreatedAt(after).stream())
                .map(guest -> new RegistrantChange(RegistrantStatus.CREATED,
                        guest.getId()));
    }

    public Flux<RegistrantChange> changes() {
        logger.debug("Returning changes");
        return changes;
    }
}
