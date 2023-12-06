import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.List;

public class ManagerKiosk extends JFrame {
    CafeManager cafeManager = new CafeManager();
    FileHandler fileHandler = new FileHandler("CustomerInfo.text"); // 고객 정보를 불러오는 FileHandler 인스턴스
    FileHandler fileHandler2 = new FileHandler("Inventory.txt"); // 재고 정보를 불러오는 FileHandler 인스턴스    // 재고 정보 불러오기
    private JPanel soldOutPanel; // 품절 표시된 항목을 보여줄 패널
    private JTextField salesField; // 매출 금액을 보여줄 텍스트 필드
    Menu menu;
    private List<MenuItem> items;
    private final String CUSTOMER_INFO_FILE_NAME = "CustomerInfo.text";
    private final String INVENTORY_FILE_NAME="Inventory.txt";


    public List<MenuItem> getMenuItems() {
        return this.items;
    }

    public ManagerKiosk() {
        menu = new Menu();
        this.items = this.getInventoryListFromFile();
        setTitle("매니저 전용 키오스크");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // 메인 패널 설정
        JPanel mainPanel = new JPanel(new BorderLayout());

        // 카테고리 패널 설정
        JPanel categoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] categories = {"커피", "라떼", "에이드/스무디", "티"};

        // 고객 모드로 전환하는 버튼 추가
        JButton switchModeButton = new JButton("고객 화면");
        switchModeButton.setBackground(Color.ORANGE); // 버튼 배경을 주황색으로 설정
        switchModeButton.setForeground(Color.WHITE); // 버튼 글자를 흰색으로 설정
        switchModeButton.addActionListener(e -> {
            // 버튼 클릭 시 CafeGUI 인스턴스 생성
            new CafeGUI();
            // 현재 ManagerKiosk 화면을 숨김
            this.setVisible(false);
        });
        categoryPanel.add(switchModeButton);

        mainPanel.add(categoryPanel, BorderLayout.NORTH);

        // 카드 레이아웃 패널 설정

        JPanel cardPanel = new JPanel(new CardLayout());
        mainPanel.add(cardPanel, BorderLayout.CENTER);

        // 각 카테고리에 대한 패널 추가
        cardPanel.add(createMenuPanel(menu.getItems()), "커피");
        cardPanel.add(createMenuPanel(menu.getItems()), "라떼");
        cardPanel.add(createMenuPanel(menu.getItems()), "에이드/스무디");
        cardPanel.add(createMenuPanel(menu.getItems()), "티");

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

        // 고객 정보를 출력하는 버튼 추가
        JButton customerInfoButton = new JButton("고객 정보 출력");
        customerInfoButton.setBackground(Color.BLUE);
        customerInfoButton.setForeground(Color.WHITE);
        customerInfoButton.addActionListener(e -> showAllCustomerInfo());
        categoryPanel.add(customerInfoButton);
    }

    // 각 카테고리 별 메뉴 패널 생성
    private JPanel createMenuPanel(List<MenuItem> menuItems) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        // 메뉴 항목 별 패널 생성
        for (MenuItem menuItem : menuItems) {
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.add(new JLabel(menuItem.getName()), BorderLayout.CENTER);

            JButton soldOutButton = new JButton("품절 처리");
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
        private MenuItem item;
        private JButton button;

        public SoldOutActionListener(MenuItem item, JButton button) {
            this.item = item;
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (button.getText().equals("품절 처리")) {
                button.setText("품절 해제");
                button.setBackground(Color.GRAY);
                cafeManager.markAsSoldOut(item);
                addSoldOutItem(item.getName()); // 품절 항목 추가
            } else {
                button.setText("품절 처리");
                button.setBackground(Color.RED);
                removeSoldOutItem(item.getName()); // 품절 항목 제거
            }
//            // 재고 정보 저장
//            fileHandler2.saveInventory(getMenuItems());
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

        // 메뉴 항목 추가
        addMenuItemsToPanel(menu.getItems(), menuPanel);
        addMenuItemsToPanel(menu.getItems(), menuPanel);
        addMenuItemsToPanel(menu.getItems(), menuPanel);
        addMenuItemsToPanel(menu.getItems(), menuPanel);

        // 하루 매출을 표시할 레이블
        JLabel totalSalesLabel = new JLabel("하루 매출: " + cafeManager.checkDailySales() + "원"); // CafeManager의 메소드 호출
        totalSalesLabel.setHorizontalAlignment(JLabel.CENTER);

        detailsPanel.add(new JScrollPane(menuPanel), BorderLayout.CENTER);
        detailsPanel.add(totalSalesLabel, BorderLayout.SOUTH);

        detailsFrame.add(detailsPanel);
        detailsFrame.setVisible(true);
    }

    // 판매 수량 출력
    private void addMenuItemsToPanel(List<MenuItem> items, JPanel panel) {
        for (MenuItem item : items) {
            panel.add(new JLabel(item.getName()));
            panel.add(new JLabel("판매 수량: " + cafeManager.getSalesCount(item))); // 실제 판매 수량 출력
        }
    }

    // 모든 고객 정보를 출력
    private void showAllCustomerInfo() {
        List<Customer> customers = this.getAllCustomerInfoInFile();
        if (!customers.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Customer customer : customers) {
                sb.append("이름: ").append(customer.getName()).append(", 전화번호: ").append(customer.getPhoneNum()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.toString());
        } else {
            JOptionPane.showMessageDialog(this, "불러올 고객 정보가 없습니다.");
        }
    }

    private List<Customer> getAllCustomerInfoInFile(){
        FileHandler fileHandler = new FileHandler("");
        List<Customer> list = new ArrayList<>();

        String[] splitStr = fileHandler.readFile(CUSTOMER_INFO_FILE_NAME).split("\n");
        for(String str : splitStr){
            String[] parts = str.split(",");
            list.add(new Customer(parts[0], parts[1]));
        }
        return list;
    }

    // 파일에서 재고 정보를 읽어서 List<MenuItem> 객체를 반환
    private List<MenuItem> getInventoryListFromFile(){
        List<MenuItem> items = new ArrayList<>();
        FileHandler fileHandler = new FileHandler(INVENTORY_FILE_NAME);
        String rawString = fileHandler.readFile(INVENTORY_FILE_NAME);

        String[] strings = rawString.split("\n");
        for(String str : strings){
            String[] parts = str.split(",");
            String name = parts[0];
            int price = Integer.parseInt(parts[1]);
            int inventory = Integer.parseInt(parts[2]);
            items.add(new MenuItem(name, price, inventory));
        }
        return items;
    }

    public static void main(String[] args) {
        new ManagerKiosk();
        FileHandler fileHandler = new FileHandler("Inventory.txt");

        Menu menu = new Menu();

        // 프로그램 종료 시 재고 정보 저장
        //Runtime.getRuntime().addShutdownHook(new Thread(() -> fileHandler2.saveInventory(managerKiosk.getMenuItems())));

    }
}