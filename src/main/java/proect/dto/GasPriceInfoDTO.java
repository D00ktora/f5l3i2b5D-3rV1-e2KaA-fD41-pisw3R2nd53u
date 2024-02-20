package proect.dto;

import java.math.BigDecimal;

public class GasPriceInfoDTO {
    private BigDecimal median;
    private BigDecimal min;
    private BigDecimal max;

    public BigDecimal getMedian() {
        return median;
    }

    public GasPriceInfoDTO setMedian(BigDecimal median) {
        this.median = median;
        return this;
    }

    public BigDecimal getMin() {
        return min;
    }

    public GasPriceInfoDTO setMin(BigDecimal min) {
        this.min = min;
        return this;
    }

    public BigDecimal getMax() {
        return max;
    }

    public GasPriceInfoDTO setMax(BigDecimal max) {
        this.max = max;
        return this;
    }
}
