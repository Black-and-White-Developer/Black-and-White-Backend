package bw.growingcode.code.dto;

import java.util.List;

public class CommentDTO {
    private List<String> classComments; // 클래스별 주석
    private List<String> functionComments; // 함수별 주석
    private List<String> lineComments; // 줄별 주석

    // Getter 및 Setter 메서드
    public List<String> getClassComments() {
        return classComments;
    }

    public void setClassComments(List<String> classComments) {
        this.classComments = classComments;
    }

    public List<String> getFunctionComments() {
        return functionComments;
    }

    public void setFunctionComments(List<String> functionComments) {
        this.functionComments = functionComments;
    }

    public List<String> getLineComments() {
        return lineComments;
    }

    public void setLineComments(List<String> lineComments) {
        this.lineComments = lineComments;
    }
}
