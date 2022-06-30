package co.com.sofka.cargame.usecase.services;

import co.com.sofka.cargame.infra.services.GanadorJuegoQueryService;

import java.util.List;

public interface GanadorJuegoService {

    List<GanadorJuegoQueryService.GanadorHistorico> getGanadoresHistorico();
}
