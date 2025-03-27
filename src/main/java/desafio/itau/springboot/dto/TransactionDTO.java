package desafio.itau.springboot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class TransactionDTO {

    @DecimalMin(value =  "0.0", inclusive = true)
    @NotNull
    private BigDecimal valor;


    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "America/Sao_Paulo")
    private OffsetDateTime dataHora;

    public TransactionDTO() {}

    public TransactionDTO(BigDecimal valor, OffsetDateTime dataHora) {
        this.valor = valor;
        this.dataHora = dataHora;
    }


    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public OffsetDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(OffsetDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
