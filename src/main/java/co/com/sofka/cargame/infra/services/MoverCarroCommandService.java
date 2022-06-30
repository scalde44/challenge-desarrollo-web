package co.com.sofka.cargame.infra.services;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.cargame.domain.carril.values.CarrilId;
import co.com.sofka.cargame.domain.carro.command.MoverCarroCommand;
import co.com.sofka.cargame.domain.carro.values.CarroId;
import co.com.sofka.cargame.usecase.MoverCarroUseCase;
import co.com.sofka.cargame.usecase.services.MoverCarroService;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.bus.EventBus;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import co.com.sofka.infraestructure.store.StoredEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MoverCarroCommandService implements MoverCarroService {
    private final EventStoreRepository eventStoreRepository;
    private final EventBus eventBus;
    private final MoverCarroUseCase moverCarroUseCase;

    @Autowired
    public MoverCarroCommandService(
            EventStoreRepository eventStoreRepository,
            EventBus eventBus,
            MoverCarroUseCase moverCarroUseCase) {
        this.eventStoreRepository = eventStoreRepository;
        this.eventBus = eventBus;
        this.moverCarroUseCase = moverCarroUseCase;
    }

    @Override
    public void mover(CarroId carroId, CarrilId carrilId) {
        var command = new MoverCarroCommand(carroId, carrilId);
        moverCarroUseCase.addRepository(domainEventRepository());
        UseCaseHandler.getInstance()
                .setIdentifyExecutor(carroId.value())
                .syncExecutor(moverCarroUseCase, new RequestCommand<>(command))
                .orElseThrow()
                .getDomainEvents().forEach(event -> {
            StoredEvent storedEvent = StoredEvent.wrapEvent(event);
            Optional.ofNullable(event.aggregateRootId()).ifPresent(aggregateId -> {
                if (Objects.nonNull(event.getAggregateName()) && !event.getAggregateName().isBlank()) {
                    eventStoreRepository.saveEvent(event.getAggregateName(), aggregateId, storedEvent);
                }
            });
            eventBus.publish(event);
        });
    }

    private DomainEventRepository domainEventRepository() {
        return new DomainEventRepository() {
            @Override
            public List<DomainEvent> getEventsBy(String aggregateId) {
                return eventStoreRepository.getEventsBy("carro", aggregateId);
            }

            @Override
            public List<DomainEvent> getEventsBy(String aggregateName, String aggregateRootId) {
                return eventStoreRepository.getEventsBy(aggregateName, aggregateRootId);
            }
        };
    }

}
