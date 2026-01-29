import java.util.*;

public class GradeManager implements GradeCalculator {
    private final List<Student> students;
    private final String[] SUBJECTS = {"AMT-1", "ADSA", "DBMS", "AT", "OE"};
    
    public GradeManager() {
        students = new ArrayList<>();
    }
    
    // Add a student
    public void addStudent(Student student) {
        students.add(student);
    }
    
    // Remove a student
    public boolean removeStudent(String id) {
        return students.removeIf(student -> student.getId().equals(id));
    }
    
    // Get all students
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }
    
    // Find student by ID
    public Student findStudent(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }
    
    // Get available subjects
    public String[] getSubjects() {
        return SUBJECTS;
    }
    
    // Implement interface methods
    @Override
    public double calculateAverage(Map<String, Double> subjectGrades) {
        if (subjectGrades.isEmpty()) return 0;
        
        double sum = 0;
        int count = 0;
        
        for (Double grade : subjectGrades.values()) {
            if (grade != null) {
                sum += grade;
                count++;
            }
        }
        
        return count > 0 ? sum / count : 0;
    }
    
    @Override
    public String calculateGrade(double average) {
        if (average >= 90) return "A";
        else if (average >= 80) return "B";
        else if (average >= 70) return "C";
        else if (average >= 60) return "D";
        else return "F";
    }
    
    // Get student statistics
    public String getStudentStatistics(Student student) {
        double average = calculateAverage(student.getSubjectGrades());
        String grade = calculateGrade(average);
        
        StringBuilder sb = new StringBuilder();
        sb.append("══════════════════════════════════════════\n");
        sb.append("STUDENT DETAILS\n");
        sb.append("══════════════════════════════════════════\n");
        sb.append(String.format("Name: %s\n", student.getName()));
        sb.append(String.format("ID: %s\n", student.getId()));
        sb.append("\n");
        sb.append("SUBJECT GRADES:\n");
        sb.append("──────────────────────────────────────────\n");
        
        for (String subject : SUBJECTS) {
            Double subjectGrade = student.getGrade(subject);
            String gradeText = (subjectGrade != null) ? String.format("%.2f", subjectGrade) : "Not entered";
            sb.append(String.format("• %-8s: %s\n", subject, gradeText));
        }
        
        sb.append("\n");
        sb.append("OVERALL PERFORMANCE:\n");
        sb.append("──────────────────────────────────────────\n");
        sb.append(String.format("Average: %.2f\n", average));
        sb.append(String.format("Grade: %s\n", grade));
        sb.append(String.format("Pointer: %.2f\n", average));
        sb.append("══════════════════════════════════════════\n");
        
        return sb.toString();
    }
    
    // Get all students formatted
    public String getAllStudentsFormatted() {
        if (students.isEmpty()) {
            return "No students found in the system!";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("══════════════════════════════════════════════════════════\n");
        sb.append("                       ALL STUDENTS                       \n");
        sb.append("══════════════════════════════════════════════════════════\n\n");
        
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            double average = calculateAverage(student.getSubjectGrades());
            String grade = calculateGrade(average);
            
            sb.append(String.format("STUDENT %d:\n", i + 1));
            sb.append("──────────────────────────────────────────\n");
            sb.append(String.format("Name: %s\n", student.getName()));
            sb.append(String.format("ID: %s\n", student.getId()));
            sb.append(String.format("Average: %.2f\n", average));
            sb.append(String.format("Grade: %s\n", grade));
            sb.append(String.format("Pointer: %.2f\n", average));
            
            // Show subjects with grades
            sb.append("Subjects: ");
            boolean first = true;
            for (String subject : SUBJECTS) {
                Double subjectGrade = student.getGrade(subject);
                if (subjectGrade != null) {
                    if (!first) sb.append(", ");
                    sb.append(String.format("%s: %.2f", subject, subjectGrade));
                    first = false;
                }
            }
            sb.append("\n\n");
        }
        
        sb.append("══════════════════════════════════════════════════════════\n");
        sb.append(String.format("Total Students: %d\n", students.size()));
        sb.append("══════════════════════════════════════════════════════════\n");
        
        return sb.toString();
    }
    
    // Method to get top scorers for each subject
    public String getTopScorers() {
        if (students.isEmpty()) {
            return "No students found in the system!";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("══════════════════════════════════════════════════════════\n");
        sb.append("                     TOP SCORERS                         \n");
        sb.append("══════════════════════════════════════════════════════════\n\n");
        
        for (String subject : SUBJECTS) {
            sb.append("Subject: ").append(subject).append("\n");
            sb.append("──────────────────────────────────────────\n");
            
            // Get all students who have grades in this subject
            List<StudentScore> subjectScores = new ArrayList<>();
            for (Student student : students) {
                Double grade = student.getGrade(subject);
                if (grade != null) {
                    subjectScores.add(new StudentScore(student, grade));
                }
            }
            
            // Sort by grade in descending order
            subjectScores.sort((a, b) -> Double.compare(b.getGrade(), a.getGrade()));
            
            // Display top 3
            if (subjectScores.isEmpty()) {
                sb.append("  No grades entered for this subject\n");
            } else {
                int count = Math.min(3, subjectScores.size());
                for (int i = 0; i < count; i++) {
                    StudentScore score = subjectScores.get(i);
                    sb.append(String.format("  %d. %-20s (ID: %-8s) : %.2f\n", 
                        i + 1, 
                        score.getStudent().getName(),
                        score.getStudent().getId(),
                        score.getGrade()));
                }
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
    
    // Helper class for storing student-score pairs
    private static class StudentScore {
        private final Student student;
        private final double grade;
        
        public StudentScore(Student student, double grade) {
            this.student = student;
            this.grade = grade;
        }
        
        public Student getStudent() { return student; }
        public double getGrade() { return grade; }
    }
}