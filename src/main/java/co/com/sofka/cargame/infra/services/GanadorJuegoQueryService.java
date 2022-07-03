package co.com.sofka.cargame.infra.services;

import co.com.sofka.cargame.usecase.services.GanadorJuegoService;
import com.google.gson.Gson;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.LookupOperation;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class GanadorJuegoQueryService implements GanadorJuegoService {

    private final MongoTemplate mongoTemplate;

    public GanadorJuegoQueryService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<GanadorHistorico> getGanadoresHistorico() {
        var lookup = LookupOperation.newLookup()
                .from("juego.JugadorCreado")
                .localField("jugadorId")
                .foreignField("jugadorId")
                .as("ganador");

        var aggregation = Aggregation.newAggregation( lookup);

        return mongoTemplate.aggregate(aggregation, "juego.PrimerLugarAsignado", String.class)
                .getMappedResults().stream()
                .map(body -> new Gson().fromJson(body, GanadorHistorico.class))
                .collect(Collectors.toList());

        
    }

    public static class GanadorHistorico {

        private String aggregateRootId;


        private List<JugadorCreado> ganador;

        public String getAggregateRootId() {
            return aggregateRootId;
        }

        public void setAggregateRootId(String aggregateRootId) {
            this.aggregateRootId = aggregateRootId;
        }


        public List<JugadorCreado> getGanador() {
            return ganador;
        }

        public void setGanador(List<JugadorCreado> ganador) {
            this.ganador = ganador;
        }



        public static class JugadorCreado{
            private Nombre nombre;
            private Color color;
            private JugadorId jugadorId;

            public Nombre getNombre() {
                return nombre;
            }

            public void setNombre(Nombre nombre) {
                this.nombre = nombre;
            }

            public Color getColor() {
                return color;
            }

            public void setColor(Color color) {
                this.color = color;
            }

            public JugadorId getJugadorId() {
                return jugadorId;
            }

            public void setJugadorId(JugadorId jugadorId) {
                this.jugadorId = jugadorId;
            }
        }

        public static class Nombre{

            private String value;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        public static class Color{
            private String value;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
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
