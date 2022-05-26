package com.banking.account.cmd.infrastructure;
import com.banking.account.cmd.domain.EventStoreRepository;
import com.banking.cqrs.core.events.BaseEvent;
import com.banking.cqrs.core.events.EventModel;
import com.banking.cqrs.core.infrastructure.EventStore;
import lombok.AllArgsConstructor;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AccountEventStore implements EventStore {

    private EventStoreRepository eventStoreRepository;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        List<EventModel> eventstream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if(expectedVersion != -1 && eventstream.get(eventstream.size()-1).getVersion() != expectedVersion){
            throw new ConcurrencyFailureException("not event macht");
        }
        var version = expectedVersion;
        for(var event: events){
            version++;
            event.setVersion(version);
        }
    }

    @Override
    public List<BaseEvent> getEvent(String aggregateId) {
        return null;
    }
}
