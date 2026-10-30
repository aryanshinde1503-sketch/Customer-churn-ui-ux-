package com.churnprediction.app.dto;

/**
 * DTO containing all dashboard statistics for rendering.
 */
public class DashboardStatsDto {

    private long totalCustomers;
    private long activeCustomers;
    private long churnedCustomers;
    private double churnRate;
    private long highRiskCustomers;
    private double averageTenure;
    private double averageMonthlyCharges;
    private long totalPredictions;
    private long highRiskPredictions;
    private long mediumRiskPredictions;
    private long lowRiskPredictions;

    public DashboardStatsDto() {
    }

    public DashboardStatsDto(long totalCustomers, long activeCustomers, long churnedCustomers,
                             double churnRate, long highRiskCustomers, double averageTenure,
                             double averageMonthlyCharges, long totalPredictions,
                             long highRiskPredictions, long mediumRiskPredictions,
                             long lowRiskPredictions) {
        this.totalCustomers = totalCustomers;
        this.activeCustomers = activeCustomers;
        this.churnedCustomers = churnedCustomers;
        this.churnRate = churnRate;
        this.highRiskCustomers = highRiskCustomers;
        this.averageTenure = averageTenure;
        this.averageMonthlyCharges = averageMonthlyCharges;
        this.totalPredictions = totalPredictions;
        this.highRiskPredictions = highRiskPredictions;
        this.mediumRiskPredictions = mediumRiskPredictions;
        this.lowRiskPredictions = lowRiskPredictions;
    }

    // Getters and Setters
    public long getTotalCustomers() { return totalCustomers; }
    public void setTotalCustomers(long totalCustomers) { this.totalCustomers = totalCustomers; }

    public long getActiveCustomers() { return activeCustomers; }
    public void setActiveCustomers(long activeCustomers) { this.activeCustomers = activeCustomers; }

    public long getChurnedCustomers() { return churnedCustomers; }
    public void setChurnedCustomers(long churnedCustomers) { this.churnedCustomers = churnedCustomers; }

    public double getChurnRate() { return churnRate; }
    public void setChurnRate(double churnRate) { this.churnRate = churnRate; }

    public long getHighRiskCustomers() { return highRiskCustomers; }
    public void setHighRiskCustomers(long highRiskCustomers) { this.highRiskCustomers = highRiskCustomers; }

    public double getAverageTenure() { return averageTenure; }
    public void setAverageTenure(double averageTenure) { this.averageTenure = averageTenure; }

    public double getAverageMonthlyCharges() { return averageMonthlyCharges; }
    public void setAverageMonthlyCharges(double averageMonthlyCharges) { this.averageMonthlyCharges = averageMonthlyCharges; }

    public long getTotalPredictions() { return totalPredictions; }
    public void setTotalPredictions(long totalPredictions) { this.totalPredictions = totalPredictions; }

    public long getHighRiskPredictions() { return highRiskPredictions; }
    public void setHighRiskPredictions(long highRiskPredictions) { this.highRiskPredictions = highRiskPredictions; }

    public long getMediumRiskPredictions() { return mediumRiskPredictions; }
    public void setMediumRiskPredictions(long mediumRiskPredictions) { this.mediumRiskPredictions = mediumRiskPredictions; }

    public long getLowRiskPredictions() { return lowRiskPredictions; }
    public void setLowRiskPredictions(long lowRiskPredictions) { this.lowRiskPredictions = lowRiskPredictions; }

    public int getChurnRatePercent() {
        return (int) Math.round(churnRate * 100);
    }

    public long getRetentionPercent() {
        return Math.round((1 - churnRate) * 100);
    }

    // Builder
    public static DashboardStatsDtoBuilder builder() {
        return new DashboardStatsDtoBuilder();
    }

    public static class DashboardStatsDtoBuilder {
        private long totalCustomers;
        private long activeCustomers;
        private long churnedCustomers;
        private double churnRate;
        private long highRiskCustomers;
        private double averageTenure;
        private double averageMonthlyCharges;
        private long totalPredictions;
        private long highRiskPredictions;
        private long mediumRiskPredictions;
        private long lowRiskPredictions;

        public DashboardStatsDtoBuilder totalCustomers(long totalCustomers) { this.totalCustomers = totalCustomers; return this; }
        public DashboardStatsDtoBuilder activeCustomers(long activeCustomers) { this.activeCustomers = activeCustomers; return this; }
        public DashboardStatsDtoBuilder churnedCustomers(long churnedCustomers) { this.churnedCustomers = churnedCustomers; return this; }
        public DashboardStatsDtoBuilder churnRate(double churnRate) { this.churnRate = churnRate; return this; }
        public DashboardStatsDtoBuilder highRiskCustomers(long highRiskCustomers) { this.highRiskCustomers = highRiskCustomers; return this; }
        public DashboardStatsDtoBuilder averageTenure(double averageTenure) { this.averageTenure = averageTenure; return this; }
        public DashboardStatsDtoBuilder averageMonthlyCharges(double averageMonthlyCharges) { this.averageMonthlyCharges = averageMonthlyCharges; return this; }
        public DashboardStatsDtoBuilder totalPredictions(long totalPredictions) { this.totalPredictions = totalPredictions; return this; }
        public DashboardStatsDtoBuilder highRiskPredictions(long highRiskPredictions) { this.highRiskPredictions = highRiskPredictions; return this; }
        public DashboardStatsDtoBuilder mediumRiskPredictions(long mediumRiskPredictions) { this.mediumRiskPredictions = mediumRiskPredictions; return this; }
        public DashboardStatsDtoBuilder lowRiskPredictions(long lowRiskPredictions) { this.lowRiskPredictions = lowRiskPredictions; return this; }

        public DashboardStatsDto build() {
            return new DashboardStatsDto(totalCustomers, activeCustomers, churnedCustomers, churnRate,
                highRiskCustomers, averageTenure, averageMonthlyCharges, totalPredictions,
                highRiskPredictions, mediumRiskPredictions, lowRiskPredictions);
        }
    }
}
