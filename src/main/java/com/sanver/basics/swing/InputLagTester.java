package com.sanver.basics.swing;

import static com.sanver.basics.utils.Utils.sleep;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InputLagTester extends JFrame {

  /**
   *
   */
  private static final long serialVersionUID = -3188408548830104991L;

  private static LocalDateTime changeTime;
  private static boolean clicked = false;

  public InputLagTester(String title) {
    super(title);
  }

  public static void main(String[] args) {
    var tester = new InputLagTester("Input Lag Tester v0.1");
    var mainPane = tester.getContentPane();
    mainPane.setLayout(new BorderLayout());
    var result = new JLabel("Result:");
    var testPanel = new JPanel();
    testPanel.setBackground(WHITE);
    mainPane.add(result, NORTH);
    mainPane.add(testPanel, CENTER);
    testPanel.setPreferredSize(new Dimension(1920, 1080));
    tester.pack();
    tester.setDefaultCloseOperation(EXIT_ON_CLOSE);
    tester.setVisible(true);
    testPanel.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        Runnable wait = () -> {
          sleep((int) (Math.random() * 1000) + 2500);
          clicked = true;
        };

        if (changeTime == null) {
          new Thread(wait).start();
          return;
        }
        result.setText(Duration.between(changeTime, LocalDateTime.now()).toMillis() + "");
        testPanel.setBackground(WHITE);
        new Thread(wait).start();
      }
    });

    new Thread(() -> {
      while (true) {
        sleep(250);

        if (clicked) {
          testPanel.setBackground(BLACK);
          changeTime = LocalDateTime.now();
          clicked = false;
        }
      }
    }).start();
  }
}
