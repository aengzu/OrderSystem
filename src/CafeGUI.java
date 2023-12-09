import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class CafeGUI extends JFrame {

    Order order;             // order 객체 : 주문을 전달하기 위한 객체
    Payment payment;
    JButton PurchaseButton;  // 결제 버튼 : 클릭 시 결제 Frame 으로 넘어감.
    JButton ManagerButton;   // 매니저 버튼 : 클릭시 매니저 Frame 으로 넘어감.
    MenuPanel menu_panel;     // 메뉴 패널 : 메뉴 아이템들 존재
    BuyPanel buy_panel;       // 장바구니 패널 : 담은 메뉴 아이템들을 보여주는 패널
    MainPanel main_panel;     // 메인 패널

    JPanel item_panel, total_panel;        // 아이템 패널
    OrderPanel orderitemsPanel;


    JLabel TotalLabel;        // TotalLabel : 총 금액을 나타내는 label


    public CafeGUI() {
        order = new Order();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);

        add(main_panel = new MainPanel());
        //  cPane = getContentPane();
        setVisible(true);
    }

    //메인 패널 : 카테고리패널 + 메뉴패널 + 장바구니패널
    class MainPanel extends JPanel {
        public MainPanel() {

            setLayout(null);
            //메뉴를 선택할 수 있는 패널
            menu_panel = new MenuPanel();

            //스크롤 기능
            menu_panel.setPreferredSize(new Dimension(630,900));
            JScrollPane scroll = new JScrollPane(menu_panel);
            scroll.setBounds(50, 80, 630, 450);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            add(scroll);

            //장바구니, 결제 버튼이 담긴 패널
            add(buy_panel = new BuyPanel());

            //관리자 전용 화면으로 이동하는 버튼
            add(ManagerButton = new JButton("Manager"));
            ManagerButton.setBounds(50, 10, 100, 25);
            ManagerButton.addActionListener(e -> {
                dispose();               //CafeGUI를 닫고
                new ManagerKiosk();      //ManagerKiosk 생성
            });

        }
    }
    //메뉴 선택창 패널
    class MenuPanel extends JPanel {
        Menu menu = new Menu();

        public MenuPanel() {
            setBounds(50, 80, 630, 450);

            setLayout(null);
            int i = 0;
            for (MenuItem menuItem : menu.getItems()) {
                item_panel = new ItemPanel(menuItem);
                if (i < 4) {
                    item_panel.setBounds(10 + i * 150, 20, 140, 200);
                } else if (i < 8) {
                    item_panel.setBounds(10 + (i - 4) * 150, 230, 140, 200);

                } else {
                    item_panel.setBounds(10 + (i - 8) * 150, 450, 140, 200);
                }
                add(item_panel);
                i++;
            }
        }}

    class ItemPanel extends JPanel {
        // TODO : 품절 플래그를 통한 품절 표시 필요
        JLabel item_name, item_price;
        JTextField AmountTextField;
        JButton MinusButton, PlusButton;
        // menuItem 을 파라미터로 넣어주면 그에 대한 아이템 패널을 생성해주는 패널
        // 아이템 패널 : 아이템 이름 + 가격 + 수량칸 + + 버튼 + -버튼
        public ItemPanel(MenuItem menuItem) {
            setSize(140, 150);
            setLayout(null);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            // 아이템의 이름
            item_name = new JLabel(menuItem.getName());
            add(item_name);
            item_name.setBounds(20, 0, 140, 30);

            // 아이템의 가격
            item_price = new JLabel(menuItem.getPrice() + "");
            add(item_price,BorderLayout.CENTER);
            item_price.setBounds(50, 50, 50, 30);

            //아이템의 수량
            AmountTextField = new JTextField(menuItem.getAmount() + "", 50);
            AmountTextField.setEditable(false);
            add(AmountTextField);
            AmountTextField.setBounds(53, 120, 40, 30);

            // 개수 줄이기 버튼
            MinusButton = new JButton("-");
            add(MinusButton);
            MinusButton.setBounds(5, 120, 45, 30);
            MinusButton.addActionListener(e->{
                if(order.getCount(menuItem)>0){
                    // 주문 목록에서 해당 menuItem 의 주문 수량을 -1 하여 수정한다.
                    // 만약 0으로 수정한다면 order 리스트에서 삭제( Order 클래스에 구현함 )
                    order.setItem(menuItem, order.getCount(menuItem)-1);
                    // 메뉴 아이템 선택 개수 텍스트 필드를 order 의 수량으로 setText
                    AmountTextField.setText(Integer.toString(order.getCount(menuItem)));
                    // 전체 금액을 order.calculateTotal() 을 통해 업데이트
                    TotalLabel.setText(Integer.toString(order.calculateTotal())+"  원");
                    // 장바구니 패널을 업데이트
                    orderitemsPanel.updateorder();
                }
            });

            //개수 늘리기 버튼
            PlusButton = new JButton("+");
            add(PlusButton);
            PlusButton.setBounds(95, 120, 45, 30);
            PlusButton.addActionListener(e->{
                // 만약 주문 목록에 같은 아이템 이미 존재할 때
                if(order.getCount(menuItem)>=1){
                    // 주문 목록에서 해당 menuItem 의 주문 수량을 +1 하여 수정
                    order.setItem(menuItem, order.getCount(menuItem)+1);
                    AmountTextField.setText(Integer.toString(order.getCount(menuItem)));
                    // 장바구니 패널 업데이트
                    orderitemsPanel.updateorder();

                }else{
                    // 주문 목록에서 해당 menuItem 의 주문 수량을 +1 하여 add
                    order.addItem(menuItem, order.getCount(menuItem)+1);
                    AmountTextField.setText(Integer.toString(order.getCount(menuItem)));
                    // 장바구니 패널 업데이트
                    orderitemsPanel.updateorder();
                }
                TotalLabel.setText(Integer.toString(order.calculateTotal())+"  원");

            });
        }

    }


        //장바구니와 결제 버튼이 있는 패널
        class BuyPanel extends JPanel {
            public BuyPanel() {
                setSize(200, 500);
                setLocation(710, 25);
                setLayout(new BorderLayout());

                setBorder(BorderFactory.createLineBorder(Color.BLACK));

                //장바구니 글씨 표시
                JLabel label = new JLabel("장바구니");// Border, Label 중 선택
                label.setFont(new Font("Dialog", Font.PLAIN, 30));
                label.setHorizontalAlignment(JLabel.CENTER);
                // label.setBounds(20, 10, 20, 20);
                add(label, BorderLayout.NORTH);

                // TODO: 장바구니 스택 구현 : orderitemsPanel 에 orderList 가 담기고 이를 Buy 패널에 넣는다
                orderitemsPanel = new OrderPanel();
                add(orderitemsPanel, BorderLayout.CENTER);


                // 구매하기 버튼 쪽 패널
                JPanel purchasepanel = new JPanel();
                purchasepanel.setSize(200, 300);
                purchasepanel.setLayout(new BorderLayout());
                total_panel = new TotalPanel();
                // 총액 패널 추가하기
                purchasepanel.add(total_panel, BorderLayout.NORTH);
                //결제 버튼 및 리스너 추가
                PurchaseButton = new JButton("결제하기");
            //  PurchaseButton.setBounds(10, 430, 180, 50);
                PurchaseButton.setSize(180, 50);
                purchasepanel.add(PurchaseButton,BorderLayout.SOUTH);
                add(purchasepanel, BorderLayout.SOUTH);

                // 결제 버튼 클릭 시 . 현재 선택된 아이템
                PurchaseButton.addActionListener(e -> {
                    if (order.calculateTotal() != 0) {
                        //    Information.setText("");
                        // 대기번호로 사용하기 위한 전화번호 뒷자리
                        String PhoneNumber = JOptionPane.showInputDialog("전화번호 뒷 4자리를 입력하세요");
                        order.setOrderNum(Integer.parseInt(PhoneNumber));
                        payment = new Payment(order);
                        dispose();
                        new PurchaseFrame(payment);
                    } else {
                        //    Information.setText("선택된 메뉴가 없습니다.");
                    }
                });
            }
        }

            class OrderPanel extends JPanel {
                JPanel panel;
                public OrderPanel() {
                    // setSize(100, 100);

                    panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
                    updateorder();
                    setVisible(true);

                }
                public void updateorder(){
                    System.out.println("-------장바구니-------");
                    // 기존에 있던 패널을 삭제한다.
                    remove(panel);
                    panel = new JPanel();
                    panel.setBounds(20, 30, 10, 40);
                    // 현재 주문 목록에 있는 menuItem 들을 화면에 출력한다.
                    // menuItem.getName() + menuItem 의 order 수량
                    for (MenuItem menuItem : order.getMaps().keySet()) {
                        // 해당 menuItem 의 수량
                        int cnt = order.getMaps().get(menuItem);
                        System.out.print(menuItem);
                        System.out.println(" "+ cnt+" 개");
                        JLabel label = new JLabel(menuItem.getName()+" "+ cnt+" 개");

                        label.setBorder(BorderFactory.createEmptyBorder(10 , 10 , 10 , 10));
                        // 메뉴아이템의 이름과 개수를 임시 panel 에 담는다.
                        panel.add(label);
                        }
                        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
                         // 업데이트된 임시 패널(menu 아이템이 담긴) 을 orderpanel 에 추가한다.
                        add(panel);
                    System.out.println("------------------");
                    }
                }


            //현재 총 금액을 표시하기 위한 패널
            class TotalPanel extends JPanel {
                public TotalPanel() {
                    setSize(180, 120);

                    JLabel Total = new JLabel("Total");
                    add(Total);
                  // Total.setBounds(10, 15, 50, 25);

                    TotalLabel = new JLabel(Integer.toString(order.calculateTotal())+"  원");
                    add(TotalLabel);

                  // TotalLabel.setBounds(100, 15, 180, 25);
                    Border TestBorder = BorderFactory.createLineBorder(Color.ORANGE);
                    setBorder(TestBorder);
                }
            }

        public static void main(String[] args) {
            new CafeGUI();
        }
    }