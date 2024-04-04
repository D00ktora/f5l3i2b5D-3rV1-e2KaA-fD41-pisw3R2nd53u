package project.dto;

import java.math.BigDecimal;

public class GasPriceInfo {
    private BigDecimal median;
    private BigDecimal min;
    private BigDecimal max;

    public BigDecimal getMedian() {
        return median;
    }

    public GasPriceInfo setMedian(BigDecimal median) {
        this.median = median;
        return this;
    }

    public BigDecimal getMin() {
        return min;
    }

    public GasPriceInfo setMin(BigDecimal min) {
        this.min = min;
        return this;
    }

    public BigDecimal getMax() {
        return max;
    }

    public GasPriceInfo setMax(BigDecimal max) {
        this.max = max;
        return this;
    }
}
