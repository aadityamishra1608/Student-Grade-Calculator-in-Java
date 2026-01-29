public class Main {
    public static void main(String[] args) {
        // Simply launch the GUI - test students will be added automatically
        javax.swing.SwingUtilities.invokeLater(() -> {
            new GradeManagerGUI();
        });
    }
}