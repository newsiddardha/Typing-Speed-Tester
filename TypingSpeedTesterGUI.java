import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.Timer;

public class TypingSpeedTesterGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TypingFrame().setVisible(true));
    }

    static class TypingFrame extends JFrame {
        private JTextArea sentenceArea = new JTextArea(3,50);
        private JTextField typingField = new JTextField(50);
        private JLabel timerLabel = new JLabel("Time: 0s");
        private JLabel wpmLabel = new JLabel("WPM: 0");
        private JButton startBtn = new JButton("Start Test");

        private String[] sentences = {
            "The quick brown fox jumps over the lazy dog.",
            "Java programming is both fun and powerful.",
            "Artificial intelligence is transforming technology rapidly.",
            "Consistency and practice make perfect typing skills.",
            "Swing allows us to create GUI applications in Java."
        };

        private String currentSentence = "";
        private long startTime;
        private Timer timer;

        public TypingFrame(){
            super("Typing Speed Tester");
            setSize(800,400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            getContentPane().setBackground(Color.WHITE);
            setLayout(new BorderLayout(10,10));

            // Sentence display
            sentenceArea.setLineWrap(true);
            sentenceArea.setWrapStyleWord(true);
            sentenceArea.setEditable(false);
            sentenceArea.setFont(new Font("Serif",Font.BOLD,18));
            sentenceArea.setBackground(new Color(240,240,240));
            sentenceArea.setForeground(Color.BLACK);
            sentenceArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),"Type this sentence:",0,0,new Font("SansSerif",Font.BOLD,12),Color.BLACK));

            JScrollPane sentenceScroll = new JScrollPane(sentenceArea);

            // Typing field
            typingField.setFont(new Font("Serif",Font.PLAIN,16));
            typingField.setBackground(Color.WHITE);
            typingField.setForeground(Color.BLACK);

            // Bottom panel for buttons and stats
            JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
            bottomPanel.setBackground(Color.WHITE);
            startBtn.setBackground(new Color(200,200,200));
            startBtn.setForeground(Color.BLACK);
            startBtn.setFocusPainted(false);
            startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            startBtn.addMouseListener(new java.awt.event.MouseAdapter(){
                public void mouseEntered(java.awt.event.MouseEvent evt){ startBtn.setBackground(new Color(150,200,250)); }
                public void mouseExited(java.awt.event.MouseEvent evt){ startBtn.setBackground(new Color(200,200,200)); }
            });

            bottomPanel.add(startBtn);
            bottomPanel.add(timerLabel);
            bottomPanel.add(wpmLabel);

            add(sentenceScroll, BorderLayout.NORTH);
            add(typingField, BorderLayout.CENTER);
            add(bottomPanel, BorderLayout.SOUTH);

            startBtn.addActionListener(e -> startTest());

            typingField.addActionListener(e -> finishTest());
        }

        private void startTest(){
            Random rand = new Random();
            currentSentence = sentences[rand.nextInt(sentences.length)];
            sentenceArea.setText(currentSentence);
            typingField.setText("");
            typingField.setEditable(true);
            typingField.requestFocus();

            startTime = System.currentTimeMillis();

            if(timer!=null) timer.stop();
            timer = new Timer(1000, e -> {
                long elapsed = (System.currentTimeMillis()-startTime)/1000;
                timerLabel.setText("Time: "+elapsed+"s");
            });
            timer.start();

            wpmLabel.setText("WPM: 0");
        }

        private void finishTest(){
            typingField.setEditable(false);
            if(timer!=null) timer.stop();
            String typed = typingField.getText().trim();
            long elapsedSecs = (System.currentTimeMillis()-startTime)/1000;
            int wordsTyped = typed.split("\\s+").length;
            int wpm = elapsedSecs>0 ? (int)((wordsTyped*60)/elapsedSecs) : wordsTyped;

            boolean correct = typed.equals(currentSentence);

            JOptionPane.showMessageDialog(this,
                    String.format("Typing completed!\nCorrect: %s\nTime: %ds\nWPM: %d", correct ? "Yes" : "No", elapsedSecs, wpm));
            wpmLabel.setText("WPM: "+wpm);
        }
    }
}
