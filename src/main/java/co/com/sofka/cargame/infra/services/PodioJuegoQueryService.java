package co.com.sofka.cargame.infra.services;

import co.com.sofka.cargame.domain.juego.values.JuegoId;
import co.com.sofka.cargame.usecase.services.PodioJuegoService;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class PodioJuegoQueryService implements PodioJuegoService {

    private final MongoTemplate mongoTemplate;

    public PodioJuegoQueryService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public JuegoFinalizadoRecord.PodioJuego getPodioPor(JuegoId juegoId) {
        var query = new Query(where("aggregateRootId").is(juegoId.value()));
        return Objects.requireNonNull(mongoTemplate.findOne(query, JuegoFinalizadoRecord.class, "juego.JuegoFinalizado"))
                .getPodio();

    }

    public static class JuegoFinalizadoRecord {
        private String type;

        private PodioJuego podio;

        public PodioJuego getPodio() {
            return podio;
        }

        public void setPodio(PodioJuego podio) {
            this.podio = podio;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public static class PodioJuego {
            private Jugador primerLugar;
            private Jugador segundoLugar;
            private Jugador tercerLugar;

            public Jugador getPrimerLugar() {
                return primerLugar;
            }

            public void setPrimerLugar(Jugador primerLugar) {
                this.primerLugar = primerLugar;
            }

            public Jugador getSegundoLugar() {
                return segundoLugar;
            }

            public void setSegundoLugar(Jugador segundoLugar) {
                this.segundoLugar = segundoLugar;
            }

            public Jugador getTercerLugar() {
                return tercerLugar;
            }

            public void setTercerLugar(Jugador tercerLugar) {
                this.tercerLugar = tercerLugar;
            }

            public static class Jugador {

                private Nombre nombre;
                private Color color;
                private Integer puntos;

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

                public Integer getPuntos() {
                    return puntos;
                }

                public void setPuntos(Integer puntos) {
                    this.puntos = puntos;
                }

                public static class Nombre {

                    private String value;

                    public String getValue() {
                        return value;
                    }

                    public void setValue(String value) {
                        this.value = value;
                    }
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
}
