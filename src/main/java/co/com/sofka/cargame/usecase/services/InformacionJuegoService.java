package co.com.sofka.cargame.usecase.services;

import co.com.sofka.cargame.domain.juego.values.JuegoId;
import co.com.sofka.cargame.infra.services.InformacionJuegoQueryService;

public interface InformacionJuegoService {

    public InformacionJuegoQueryService.CarrosAsociadosJuego getInformacionJuego(JuegoId juegoId);
}
