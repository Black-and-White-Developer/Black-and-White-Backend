package bw.growingcode.code.dto;

public class FeedbackDTO {
    private String readabilityImprovement; // 가독성 향상 코드
    private String performanceImprovement; // 성능 향상 코드

    public String getReadabilityImprovement() {
        return readabilityImprovement;
    }

    public void setReadabilityImprovement(String readabilityImprovement) {
        this.readabilityImprovement = readabilityImprovement;
    }

    public String getPerformanceImprovement() {
        return performanceImprovement;
    }

    public void setPerformanceImprovement(String performanceImprovement) {
        this.performanceImprovement = performanceImprovement;
    }
}
