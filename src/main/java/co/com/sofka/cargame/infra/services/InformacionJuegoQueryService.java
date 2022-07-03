package co.com.sofka.cargame.infra.services;

import co.com.sofka.cargame.domain.juego.values.JuegoId;
import co.com.sofka.cargame.domain.juego.values.Pista;
import co.com.sofka.cargame.usecase.services.InformacionJuegoService;
import com.google.gson.Gson;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class InformacionJuegoQueryService implements InformacionJuegoService {

    private final MongoTemplate mongoTemplate;

    public InformacionJuegoQueryService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public JugadoresAsociadosJuego getInformacionJuego(JuegoId juegoId) {
        var lookup = LookupOperation.newLookup()
                .from("juego.JugadorCreado")
                .localField("aggregateRootId")
                .foreignField("aggregateRootId")
                .as("jugadores");

        var aggregation = Aggregation.newAggregation(
                lookup,
                Aggregation.match(where("aggregateRootId").is(juegoId.value()))
        );

        return mongoTemplate.aggregate(aggregation, "juego.JuegoCreado", String.class)
                .getMappedResults().stream()
                .map(body -> new Gson().fromJson(body, JugadoresAsociadosJuego.class))
                .findFirst().orElseThrow();
    }

    public static class JugadoresAsociadosJuego {
        private String aggregateRootId;
        private Pista pista;
        private List<JugadorCreado> jugadores;

        public String getAggregateRootId() {
            return aggregateRootId;
        }

        public void setAggregateRootId(String aggregateRootId) {
            this.aggregateRootId = aggregateRootId;
        }

        public Pista getPista() {
            return pista;
        }

        public void setPista(Pista pista) {
            this.pista = pista;
        }


        public static class JugadorCreado {

        }
    }


}
