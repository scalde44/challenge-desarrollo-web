package co.com.sofka.cargame.infra.services;

import co.com.sofka.cargame.usecase.services.GanadorJuegoService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GanadorJuegoQueryService implements GanadorJuegoService {

    private final MongoTemplate mongoTemplate;

    public GanadorJuegoQueryService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<GanadorHistorico> getGanadoresHistorico() {
        return Objects.requireNonNull(mongoTemplate.findAll(GanadorHistorico.class, "juego.PrimerLugarAsignado"));
    }

    public static class GanadorHistorico {

        private String aggregateRootId;
        private JugadorId jugadorId;

        public String getAggregateRootId() {
            return aggregateRootId;
        }

        public void setAggregateRootId(String aggregateRootId) {
            this.aggregateRootId = aggregateRootId;
        }

        public JugadorId getJugadorId() {
            return jugadorId;
        }

        public void setJugadorId(JugadorId jugadorId) {
            this.jugadorId = jugadorId;
        }

        public static class JugadorId {

            private String uuid;

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }
        }
    }
}
