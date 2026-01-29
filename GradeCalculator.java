import java.util.Map;

public interface GradeCalculator {
    double calculateAverage(Map<String, Double> subjectGrades);
    String calculateGrade(double average);
}