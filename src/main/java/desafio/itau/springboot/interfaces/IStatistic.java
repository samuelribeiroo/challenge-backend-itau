package desafio.itau.springboot.interfaces;

import desafio.itau.springboot.dto.StatisticsDTO;

public interface IStatistic {
    StatisticsDTO getLastMinuteMetrics();

    StatisticsDTO getCustomMinuteMetrics(int seconds);
}
