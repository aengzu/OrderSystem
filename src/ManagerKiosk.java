import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ManagerKiosk extends JFrame {

    private JPanel soldOutPanel; // 품절 표시된 항목을 보여줄 패널
    private JTextField salesField; // 매출 금액을 보여줄 텍스트 필드

    public ManagerKiosk() {
        setTitle("매니저 전용 키오스크");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 메인 패널 설정
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 카테고리 패널 설정
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] categories = {"커피", "라떼", "프라페", "디저트"};
        mainPanel.add(categoryPanel, BorderLayout.NORTH);

        // 카드 레이아웃 패널 설정
        JPanel cardPanel = new JPanel(new CardLayout());
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        // 각 카테고리에 대한 패널 추가
        for (String category : categories) {
            cardPanel.add(createMenuPanel(category), category);
        }

        // 카테고리 버튼 설정 및 액션 리스너 추가
        for (String category : categories) {
            JButton button = new JButton(category);
            button.addActionListener(e -> {
                CardLayout cl = (CardLayout) (cardPanel.getLayout());
                cl.show(cardPanel, category);
            });
            categoryPanel.add(button);
        }

        // 품절 표시 패널 설정
        soldOutPanel = new JPanel();
        soldOutPanel.setLayout(new BoxLayout(soldOutPanel, BoxLayout.Y_AXIS));
        JLabel soldOutLabel = new JLabel("품절된 메뉴");
        soldOutLabel.setFont(new Font(soldOutLabel.getFont().getName(), Font.BOLD, 16));
        soldOutPanel.add(soldOutLabel);
        JScrollPane scrollPane = new JScrollPane(soldOutPanel);
        scrollPane.setPreferredSize(new Dimension(200, 200));
        mainPanel.add(scrollPane, BorderLayout.EAST);

        // 매출 관련 패널 설정
        JPanel salesPanel = new JPanel(new BorderLayout());
        JLabel salesLabel = new JLabel("하루 매출");
        salesField = new JTextField("0원");
        salesField.setEditable(false);
        JButton detailsButton = new JButton("자세히 보기");
        salesPanel.add(salesLabel, BorderLayout.NORTH);
        salesPanel.add(salesField, BorderLayout.CENTER);
        salesPanel.add(detailsButton, BorderLayout.SOUTH);
        mainPanel.add(salesPanel, BorderLayout.SOUTH);

        // 프레임에 메인 패널 추가
        add(mainPanel);
        setVisible(true);
    }

    // 각 카테고리 별 메뉴 패널 생성
    private JPanel createMenuPanel(String category) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        for (int i = 1; i <= 5; i++) {
            String menuItem = category + " 메뉴 " + i;
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.add(new JLabel(menuItem), BorderLayout.CENTER);

            JButton soldOutButton = new JButton("품절 표시");
            soldOutButton.setBackground(Color.RED);
            soldOutButton.setForeground(Color.WHITE);
            soldOutButton.addActionListener(new SoldOutActionListener(menuItem, soldOutButton));
            itemPanel.add(soldOutButton, BorderLayout.SOUTH);

            panel.add(itemPanel);
        }
        return panel;
    }

    // 품절 상태를 토글하는 액션 리스너
    private class SoldOutActionListener implements ActionListener {
        private String item;
        private JButton button;

        public SoldOutActionListener(String item, JButton button) {
            this.item = item;
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (button.getText().equals("품절 표시")) {
                button.setText("품절 해제");
                button.setBackground(Color.GRAY);
                addSoldOutItem(item);
            } else {
                button.setText("품절 표시");
                button.setBackground(Color.RED);
                removeSoldOutItem(item);
            }
        }
    }

    // 품절 항목 추가
    private void addSoldOutItem(String item) {
        soldOutPanel.add(new JLabel(item));

        soldOutPanel.revalidate();
        soldOutPanel.repaint();
    }

    // 품절 항목 제거
    private void removeSoldOutItem(String item) {
        for (Component comp : soldOutPanel.getComponents()) {
            if (comp instanceof JLabel && ((JLabel) comp).getText().equals(item)) {
                soldOutPanel.remove(comp);
                break;
            }
        }
        soldOutPanel.revalidate();
        soldOutPanel.repaint();
    }

    public static void main(String[] args) {
        new ManagerKiosk();
    }
}
