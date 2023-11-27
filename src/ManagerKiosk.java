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
        String[] categories = {"커피", "라떼", "에이드/스무디", "티"};

        // 고객 모드로 전환하는 버튼 추가
        JButton switchModeButton = new JButton("관리자");
        switchModeButton.setBackground(Color.ORANGE); // 버튼 배경을 주황색으로 설정
        switchModeButton.setForeground(Color.WHITE); // 버튼 글자를 흰색으로 설정
        categoryPanel.add(switchModeButton);

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

        // '자세히 보기' 버튼에 대한 이벤트 리스너 추가
        detailsButton.addActionListener(e -> showSalesDetails());


        // 프레임에 메인 패널 추가
        add(mainPanel);
        setVisible(true);
    }

    // 각 카테고리 별 메뉴 패널 생성
    private JPanel createMenuPanel(String category) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        // 각 카테고리별 실제 메뉴 이름 정의
        String[] menuItems;
        switch (category) {
            case "커피":
                menuItems = new String[]{"아메리카노", "헤이즐넛 아메리카노", "카푸치노", "에스프레소", "카페모카"};
                break;
            case "라떼":
                menuItems = new String[]{"바닐라 라떼", "카페 라떼", "녹차 라떼", "딸기 라떼"};
                break;
            case "에이드/스무디":
                menuItems = new String[]{"레몬에이드", "딸기스무디", "블루베리스무디", "망고스무디", "딸기바나나주스"};
                break;
            case "티":
                menuItems = new String[]{"아이스티", "밀크티", "얼그레이", "캐모마일", "유자차", "허니자몽밀크티", "흑당밀크티"};;
                break;
            default:
                menuItems = new String[]{"메뉴 1", "메뉴 2", "메뉴 3", "메뉴 4", "메뉴 5"};
        }

        // 메뉴 항목 별 패널 생성
        for (String menuItem : menuItems) {
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
    private void showSalesDetails() {
        JFrame detailsFrame = new JFrame("매출 상세 정보");
        detailsFrame.setSize(500, 400);
        detailsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 새 창만 닫히게 설정

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());

        // 메뉴와 판매 수량을 표시할 패널
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 2)); // 2열 그리드 레이아웃

        String[] categories = {"커피", "라떼", "프라페", "디저트"};
        for (String category : categories) {
            String[] menuItems = getMenuItems(category);
            for (String item : menuItems) {
                menuPanel.add(new JLabel(item));
                menuPanel.add(new JLabel("판매 수량: " + getSalesCount(item))); // 임시 판매 수량
            }
        }

        // 하루 매출을 표시할 레이블
        JLabel totalSalesLabel = new JLabel("하루 매출: " + getDailySales()); // 임시 하루 매출
        totalSalesLabel.setHorizontalAlignment(JLabel.CENTER);

        detailsPanel.add(new JScrollPane(menuPanel), BorderLayout.CENTER);
        detailsPanel.add(totalSalesLabel, BorderLayout.SOUTH);

        detailsFrame.add(detailsPanel);
        detailsFrame.setVisible(true);
    }
    private String[] getMenuItems(String category) {
        // 각 카테고리별 메뉴 아이템을 배열로 반환
        switch (category) {
            case "커피":
                return new String[]{"아메리카노", "에스프레소", "카푸치노", "카페라떼", "모카"};
            case "라떼":
                return new String[]{"바닐라 라떼", "카라멜 라떼", "말차 라떼", "고구마 라떼", "허니 라떼"};
            case "프라페":
                return new String[]{"모카 프라페", "카라멜 프라페", "민트 초콜릿 프라페", "자몽 프라페", "딸기 프라페"};
            case "디저트":
                return new String[]{"치즈 케이크", "타르트", "마카롱", "머핀", "크루아상"};
            default:
                return new String[]{};
        }
    }
    private int getSalesCount(String item) {
        // 메뉴 아이템별 판매 수량을 반환 (임시 데이터)
        return (int) (Math.random() * 100); // 임의의 판매 수량
    }

    private String getDailySales() {
        // 하루 매출 금액을 반환 (임시 데이터)
        return "100,000원";
    }

    public static void main(String[] args) {
        new ManagerKiosk();
    }
}
