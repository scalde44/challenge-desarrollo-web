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
    public CarrosAsociadosJuego getInformacionJuego(JuegoId juegoId) {
        var lookup = LookupOperation.newLookup()
                .from("carro.CarroCreado")
                .localField("aggregateRootId")
                .foreignField("aggregateParentId")
                .as("carroCreados");

        var aggregation = Aggregation.newAggregation(
                lookup,
                Aggregation.match(where("aggregateRootId").is(juegoId.value()))
        );

        return mongoTemplate.aggregate(aggregation, "juego.JuegoCreado", String.class)
                .getMappedResults().stream()
                .map(body -> new Gson().fromJson(body, CarrosAsociadosJuego.class))
                .findFirst().orElseThrow();
    }

    public static class CarrosAsociadosJuego {
        private String aggregateRootId;
        private Pista pista;
        private List<CarroCreado> carroCreados;

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

        public List<CarroCreado> getCarroCreados() {
            return carroCreados;
        }

        public void setCarroCreados(List<CarroCreado> carroCreados) {
            this.carroCreados = carroCreados;
        }

        public static class CarroCreado {

            private String aggregateParentId;
            private String aggregateRootId;
            private Color color;

            public String getAggregateParentId() {
                return aggregateParentId;
            }

            public void setAggregateParentId(String aggregateParentId) {
                this.aggregateParentId = aggregateParentId;
            }

            public String getAggregateRootId() {
                return aggregateRootId;
            }

            public void setAggregateRootId(String aggregateRootId) {
                this.aggregateRootId = aggregateRootId;
            }

            public Color getColor() {
                return color;
            }

            public void setColor(Color color) {
                this.color = color;
            }

            public static class Color {

                private String value;

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }
            }
        }
    }


}
