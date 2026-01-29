import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class GradeManagerGUI extends JFrame {
    private final GradeManager gradeManager;
    private JTextArea displayArea;
    private JTextField nameField, idField;
    private JTextField[] gradeFields;
    
    public GradeManagerGUI() {
        gradeManager = new GradeManager();
        initializeGUI();
    }
    
    // Custom panel with warm cream gradient background
    class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            
            // Warm Cream Theme
            Color topColor = new Color(255, 253, 245);    // Warm white
            Color bottomColor = new Color(255, 248, 230); // Soft cream
            
            GradientPaint gradient = new GradientPaint(
                0, 0, topColor,                    // Top - warm white
                0, getHeight(), bottomColor        // Bottom - soft cream
            );
            
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
    
    private void initializeGUI() {
        setTitle("Student Grade Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 800);
        setLocationRelativeTo(null);
        
        // Use gradient panel as main panel
        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Header with warm cream theme styling
        JLabel headerLabel = new JLabel("STUDENT GRADE MANAGEMENT SYSTEM", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        headerLabel.setForeground(new Color(120, 80, 40)); // Warm brown
        headerLabel.setBorder(BorderFactory.createEmptyBorder(15, 0, 25, 0));
        headerLabel.setOpaque(false);
        
        // Create left panel for inputs and buttons
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setOpaque(false); // Transparent background
        
        // Input panel with warm styling
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        inputPanel.setBackground(new Color(255, 252, 240, 240)); // Semi-transparent warm white
        inputPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 210, 190)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        inputPanel.setPreferredSize(new Dimension(400, 320));
        
        inputPanel.add(createStyledLabel("Student Name:"));
        nameField = createStyledTextField();
        inputPanel.add(nameField);
        
        inputPanel.add(createStyledLabel("Student ID:"));
        idField = createStyledTextField();
        inputPanel.add(idField);
        
        // Subject grades
        String[] subjects = gradeManager.getSubjects();
        gradeFields = new JTextField[subjects.length];
        
        for (int i = 0; i < subjects.length; i++) {
            inputPanel.add(createStyledLabel(subjects[i] + ":"));
            gradeFields[i] = createStyledTextField();
            inputPanel.add(gradeFields[i]);
        }
        
        // Button panel with warm styling
        JPanel buttonPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        buttonPanel.setBackground(new Color(255, 250, 235, 220)); // Very light warm transparent
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 210, 190)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        buttonPanel.setPreferredSize(new Dimension(400, 220));
        
        JButton addButton = createStyledButton("Add Student");
        JButton viewButton = createStyledButton("View All");
        JButton findButton = createStyledButton("Find Student");
        JButton removeButton = createStyledButton("Remove Student");
        JButton topScorersButton = createStyledButton("Top Scorers");
        JButton clearButton = createStyledButton("Clear");
        JButton exitButton = createStyledButton("Exit");
        
        addButton.addActionListener(this::addStudent);
        viewButton.addActionListener(this::viewAllStudents);
        findButton.addActionListener(this::findStudent);
        removeButton.addActionListener(this::removeStudent);
        topScorersButton.addActionListener(this::showTopScorers);
        clearButton.addActionListener(e -> clearFields());
        exitButton.addActionListener(e -> System.exit(0));
        
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(findButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(topScorersButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);
        
        // Add input and button panels to left panel
        leftPanel.add(inputPanel, BorderLayout.NORTH);
        leftPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Display area with warm styling
        JPanel displayPanel = new JPanel(new BorderLayout());
        displayPanel.setBackground(new Color(255, 252, 240, 240)); // Semi-transparent warm
        displayPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 210, 190)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        displayArea.setBackground(new Color(255, 253, 245)); // Warm white
        displayArea.setForeground(new Color(80, 60, 40)); // Warm brown text
        displayArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        displayArea.setCaretColor(new Color(160, 120, 80)); // Warm brown cursor
        
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(210, 200, 180)));
        scrollPane.setPreferredSize(new Dimension(450, 600));
        scrollPane.getViewport().setBackground(new Color(255, 253, 245));
        
        // Style the scroll bars
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        displayPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Add components to main panel
        mainPanel.add(headerLabel, BorderLayout.NORTH);
        
        // Center panel with left and right sections
        JPanel centerPanel = new JPanel(new BorderLayout(20, 0));
        centerPanel.setOpaque(false); // Transparent background
        centerPanel.add(leftPanel, BorderLayout.WEST);
        centerPanel.add(displayPanel, BorderLayout.CENTER);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(new Color(120, 80, 40)); // Warm brown text
        return label;
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBackground(new Color(255, 255, 250)); // Warm white
        field.setForeground(new Color(80, 60, 40)); // Warm brown
        field.setCaretColor(new Color(160, 120, 80)); // Warm brown cursor
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 180, 150)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        
        // Add focus effect
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(180, 140, 100), 2),
                    BorderFactory.createEmptyBorder(6, 8, 6, 8)
                ));
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 180, 150)),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
                ));
            }
        });
        
        return field;
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(new Color(180, 140, 100)); // Warm brown
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(160, 120, 80)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        // Modern hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(160, 120, 80));
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(140, 100, 60)),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(180, 140, 100));
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(160, 120, 80)),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)
                ));
            }
        });
        
        return button;
    }
    
    private void addStudent(ActionEvent e) {
        try {
            String name = nameField.getText().trim();
            String id = idField.getText().trim();
            
            if (name.isEmpty() || id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter name and ID!");
                return;
            }
            
            Map<String, Double> subjectGrades = new HashMap<>();
            String[] subjects = gradeManager.getSubjects();
            
            for (int i = 0; i < subjects.length; i++) {
                String gradeText = gradeFields[i].getText().trim();
                if (!gradeText.isEmpty()) {
                    double grade = Double.parseDouble(gradeText);
                    if (grade < 0 || grade > 100) {
                        JOptionPane.showMessageDialog(this, "Grades must be between 0-100!");
                        return;
                    }
                    subjectGrades.put(subjects[i], grade);
                }
            }
            
            if (subjectGrades.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter at least one grade!");
                return;
            }
            
            Student student = new Student(name, id, subjectGrades);
            gradeManager.addStudent(student);
            
            displayArea.setText("Student added successfully!\n");
            displayArea.append(gradeManager.getStudentStatistics(student));
            clearFields();
            
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for grades!");
        }
    }
    
    private void viewAllStudents(ActionEvent e) {
        java.util.List<Student> students = gradeManager.getAllStudents();
        
        if (students.isEmpty()) {
            displayArea.setText("No students found in the system!");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("══════════════════════════════════════════════════════════\n");
        sb.append("                       ALL STUDENTS                       \n");
        sb.append("══════════════════════════════════════════════════════════\n\n");
        
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            sb.append(gradeManager.getStudentStatistics(student)).append("\n\n");
        }
        
        sb.append("Total Students: ").append(students.size()).append("\n");
        displayArea.setText(sb.toString());
    }
    
    private void findStudent(ActionEvent e) {
        String id = JOptionPane.showInputDialog(this, "Enter Student ID to find:");
        
        if (id == null) {
            return;
        }
        
        id = id.trim();
        if (id.isEmpty()) {
            displayArea.setText("Please enter a valid Student ID!");
            return;
        }
        
        Student student = gradeManager.findStudent(id);
        if (student != null) {
            displayArea.setText(gradeManager.getStudentStatistics(student));
        } else {
            displayArea.setText("Student not found with ID: " + id);
        }
    }
    
    private void removeStudent(ActionEvent e) {
        String id = JOptionPane.showInputDialog(this, "Enter Student ID to remove:");
        
        if (id == null || id.trim().isEmpty()) {
            return;
        }
        
        id = id.trim();
        if (gradeManager.removeStudent(id)) {
            displayArea.setText("Student with ID " + id + " removed successfully!");
            clearFields();
        } else {
            displayArea.setText("Student not found with ID: " + id);
        }
    }
    
    private void clearFields() {
        nameField.setText("");
        idField.setText("");
        for (JTextField field : gradeFields) {
            field.setText("");
        }
    }
    
    // Top Scorers method
    private void showTopScorers(ActionEvent e) {
        List<Student> students = gradeManager.getAllStudents();
        
        if (students.isEmpty()) {
            displayArea.setText("No students found in the system!");
            return;
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("══════════════════════════════════════════════════════════\n");
        sb.append("                     TOP SCORERS                         \n");
        sb.append("══════════════════════════════════════════════════════════\n\n");
        
        String[] subjects = gradeManager.getSubjects();
        
        for (String subject : subjects) {
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
        
        displayArea.setText(sb.toString());
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