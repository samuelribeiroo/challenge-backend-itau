package desafio.itau.springboot.services;

import desafio.itau.springboot.dto.StatisticsDTO;
import desafio.itau.springboot.dto.TransactionDTO;
import desafio.itau.springboot.interfaces.IStatistic;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class StatisticService implements IStatistic {
    private TransactionService service;

    public StatisticService(TransactionService service) {
        this.service = service;
    }

    @Override
    public StatisticsDTO getLastMinuteMetrics() {
        OffsetDateTime lastMinuteTransaction = OffsetDateTime.now().minusSeconds(60);

        List<TransactionDTO> filteredTransactions = service.getRecentTransactions()
                .stream()
                .filter(t -> t.getDataHora().isAfter(lastMinuteTransaction))
                .toList();


        DoubleSummaryStatistics statistics = filteredTransactions.stream()
                .mapToDouble(dto -> dto.getValor().doubleValue())
                .summaryStatistics();

        double min = statistics.getCount() > 0 ? statistics.getMin() : 0.0;
        double max = statistics.getCount() > 0 ? statistics.getMax() : 0.0;

        return  new StatisticsDTO(
                statistics.getCount(),
                statistics.getSum(),
                statistics.getAverage(),
                min,
                max);
    }

    @Override
    public StatisticsDTO getCustomMinuteMetrics(int seconds) {
        OffsetDateTime customTime = OffsetDateTime.now().minusSeconds(seconds);

        List<TransactionDTO> filteredTransactions = service.getRecentTransactions()
                .stream()
                .filter(t -> t.getDataHora().isAfter(customTime))
                .toList();


        DoubleSummaryStatistics statistics = filteredTransactions.stream()
                .mapToDouble(dto -> dto.getValor().doubleValue())
                .summaryStatistics();

        double min = statistics.getCount() > 0 ? statistics.getMin() : 0.0;
        double max = statistics.getCount() > 0 ? statistics.getMax() : 0.0;

        return  new StatisticsDTO(
                statistics.getCount(),
                statistics.getSum(),
                statistics.getAverage(),
                min,
                max);
    }
}
