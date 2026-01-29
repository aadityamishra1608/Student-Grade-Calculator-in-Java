import java.util.HashMap;
import java.util.Map;

public class Student {
    private String name;
    private String id;
    private Map<String, Double> subjectGrades; // Subject -> Grade mapping
    
    public Student(String name, String id, Map<String, Double> subjectGrades) {
        this.name = name;
        this.id = id;
        this.subjectGrades = subjectGrades;
    }
    
    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        this.subjectGrades = new HashMap<>();
    }
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public Map<String, Double> getSubjectGrades() { return subjectGrades; }
    public void setSubjectGrades(Map<String, Double> subjectGrades) { this.subjectGrades = subjectGrades; }
    
    public void addGrade(String subject, double grade) {
        subjectGrades.put(subject, grade);
    }
    
    public Double getGrade(String subject) {
        return subjectGrades.get(subject);
    }
    
    @Override
    public String toString() {
        return name + " (ID: " + id + ")";
    }
}