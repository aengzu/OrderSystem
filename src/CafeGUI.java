import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CafeGUI extends JFrame{
    JRadioButton Category1,Category2,Category3,Category4,Category5;
    JButton purchaseButton;
    JButton ManagerButton;
    CategoryPanel CategoryPanel;
    CoffeePanel CP;
    BuyPanel BP;
    PurchasePanel PP;
    JLabel totalPrice;

    CardLayout CL=new CardLayout();
    Container cPane;
    MainPanel MP;

    JLabel purchasingLabel;
    PurchasingPanel PurchasingPanel;
    purchasing p=new purchasing();
    Complete C;
    int Number=222;
    int Price=0;

    TestPanel tp;//Test
    public CafeGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,600);

        add(MP=new MainPanel());
        add(PP=new PurchasePanel());
        add(PurchasingPanel=new PurchasingPanel());
        add(C=new Complete());

        cPane=getContentPane();
        setLayout(CL);

        setVisible(true);
    }
    //모든 패널들을 담아놓은 패널
    class MainPanel extends JPanel{
        public MainPanel() {
            setLayout(null);
            setSize(1000,600);
            CategoryPanel=new CategoryPanel();
            add(CategoryPanel);
            CategoryPanel.setLocation(50,50);

            add(CP=new CoffeePanel());

            add(tp=new TestPanel());//Test

            add(BP=new BuyPanel());

            add(ManagerButton=new JButton("Manager"));
            ManagerButton.setBounds(50,10,100,25);

            add(PP=new PurchasePanel());
        }
    }
    //카테고리 선택을 위한 RadioButton이 담긴 패널
    class CategoryPanel extends JPanel implements ActionListener {
        public CategoryPanel() {
            setSize(600,100);
            setLayout(null);
            ButtonGroup bg=new ButtonGroup();
            Category1=new JRadioButton();
            Category2=new JRadioButton();
            Category3=new JRadioButton();
            Category4=new JRadioButton();
            Category5=new JRadioButton();
            bg.add(Category1);
            bg.add(Category2);
            bg.add(Category3);
            bg.add(Category4);
            bg.add(Category5);
            add(Category1);
            add(Category2);
            add(Category3);
            add(Category4);
            add(Category5);
            Category1.addActionListener(this);//Test
            Category2.addActionListener(this);//Test
            Category1.setBounds(50,-25,70,80);
            Category2.setBounds(150,0,50,25);
            Category3.setBounds(250,0,50,25);
            Category4.setBounds(350,0,50,25);
            Category5.setBounds(450,0,50,25);

//			ImageIcon icon1=new ImageIcon("C:\\Users\\oossy\\OneDrive\\바탕 화면\\커피.png");
//  		Category1.setIcon(icon1);

            Category1.setSelected(true);
        }
        //카테고리 변경
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==Category1) {
                CP.setVisible(true);
                tp.setVisible(false);
            }else if(e.getSource()==Category2) {
                CP.setVisible(false);
                tp.setVisible(true);
            }
        }
    }
    //커피류 메뉴를 담기위한 패널. 다른 메뉴를 위한 패널 추가 필요
    class CoffeePanel extends JPanel{
        public CoffeePanel() {
            setSize(600,350);
            setLocation(50,150);
            setLayout(new GridLayout(2,4,10,10));
            add(new Item("아메리카노",2000));
            add(new Item("카푸치노",3000));
            add(new Item("A",1500));
            add(new Item("A",4000));
            add(new Item("A",5000));
            add(new Item("A",0));
            add(new Item("A",0));
            add(new Item("A",0));
        }
    }
    //리스너 작동 테스트를 위해 임시로 만든 패널. 추후 삭제
    class TestPanel extends JPanel{//Test
        public TestPanel() {
            setSize(600,350);
            setLocation(50,150);
            setVisible(false);
            JButton button=new JButton("Test");
            add(button);
            button.setBounds(300,300,10,10);
        }
    }
    //장바구니 패널, 기능 구현 필요함
    class BuyPanel extends JPanel{
        public BuyPanel() {
            setSize(200,500);
            setLocation(710,25);
            setLayout(null);

            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            JLabel label=new JLabel("장바구니");//Border, Label 중 선택
            label.setFont(new Font("Dialog",Font.PLAIN,40));
            label.setBounds(20,10,180,50);
            add(label);
            totalPanel TP;
            add(TP=new totalPanel());
            TP.setLocation(10,350);

            purchaseButton=new JButton("결제하기");
            purchaseButton.setBounds(10,430,180,50);
            purchaseButton.addActionListener(e-> {
                if(Price!=0){
                    CL.next(cPane);
                }else{
                    System.out.println("선택된 메뉴가 없습니다.");
                }
            });
            add(purchaseButton);
        }
        //현재 총 금액을 표시하기 위한 패널
        class totalPanel extends JPanel{
            public totalPanel() {
                setSize(180,50);
                setLayout(null);

                JLabel Total=new JLabel("Total");
                add(Total);
                Total.setBounds(10,15,50,25);
                add(totalPrice=new JLabel(Price+"원"));
                totalPrice.setBounds(100,15,180,25);
                Border TestBorder=BorderFactory.createLineBorder(Color.ORANGE);
                setBorder(TestBorder);
            }
        }

    }
    //결제수단 선택 화면
    class PurchasePanel extends JPanel implements ActionListener{
        JButton Card;
        JButton Cash;
        JButton purchaseButton_last;
        public PurchasePanel() {
            setSize(900,500);
            setLocation(50,20);
            setLayout(null);

            JLabel label=new JLabel("결제수단");
            label.setBounds(390,20,200,30);
            label.setFont(new Font("Dialog",Font.PLAIN,30));
            add(label);

            JButton Cancel=new JButton("Cancel");
            Cancel.setBounds(800,10,80,30);
            Cancel.addActionListener(e->CL.previous(cPane));
            add(Cancel);

            Card=new JButton("Card");
            Cash=new JButton("Cash");
            Card.setBounds(100,100,300,300);
            Cash.setBounds(500,100,300,300);
            Card.addActionListener(this);
            Cash.addActionListener(this);
            add(Card);
            add(Cash);

            JButton purchaseButton_last=new JButton("결제하기");
            purchaseButton_last.addActionListener(e->{
                if(Card.getBackground()==Color.ORANGE||Cash.getBackground()==Color.ORANGE){
                    CL.next(cPane);
                    p.start();
                }else{
                    System.out.println("결제 수단을 선택해주세요.");
                }
            });
            add(purchaseButton_last);
            purchaseButton_last.setBounds(360,430,180,50);


            setVisible(false);
        }
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==Card){
                Card.setBackground(Color.ORANGE);
                Cash.setBackground(null);
            }else if(e.getSource()==Cash){
                Card.setBackground(null);
                Cash.setBackground(Color.ORANGE);
            }
        }
    }
    //결제 로딩 화면
    class PurchasingPanel extends JPanel{
        public PurchasingPanel() {
            setLayout(null);
            setSize(900,500);
            setLocation(50,20);

            add(purchasingLabel=new JLabel("결제 중"));
            purchasingLabel.setFont(new Font("Dialog",Font.PLAIN,50));
            purchasingLabel.setBounds(350,300,250,50);
        }

    }
    //화면을 움직이기 위해 임시로 만든 쓰레드. 필요할 경우 수정
    class purchasing extends Thread{
        public void run() {
            for(int i=0;i<2;i++) {
                purchasingLabel.setText("결제 중.");
                try {Thread.sleep(500);}catch(InterruptedException e) {}
                purchasingLabel.setText("결제 중..");
                try {Thread.sleep(500);}catch(InterruptedException e) {}
                purchasingLabel.setText("결제 중...");
                try {Thread.sleep(500);}catch(InterruptedException e) {}
            }
            CL.next(cPane);
        }
    }
    //결제 완료 화면
    class Complete extends JPanel{
        public Complete() {
            setSize(900,500);
            setLocation(50,20);
            setLayout(null);

            JLabel complete=new JLabel("결제 완료!");
            complete.setFont(new Font("Dialog", Font.BOLD,40));
            complete.setBounds(400,30,250,50);
            add(complete);

            JLabel WaitingNum=new JLabel("대기 번호:");
            WaitingNum.setFont(new Font("Dialog",Font.PLAIN,40));
            WaitingNum.setBounds(250,300,250,50);
            add(WaitingNum);

            JLabel NumberLabel=new JLabel();
            NumberLabel.setText(Number+"번");
            NumberLabel.setFont(new Font("Dialog",Font.BOLD,50));
            NumberLabel.setForeground(Color.RED);
            NumberLabel.setBounds(500,300,500,50);
            add(NumberLabel);

        }
    }
    //메뉴 품목 클래스
    public class Item extends JPanel implements ActionListener{
        String name;
        int price;
        int amount=0;
        JButton minus,plus;
        JTextField Amount;
        JLabel ItemName,ItemPrice;
        public Item(String name,int price){
            this.name=name;
            this.price=price;

            setLayout(null);
            setSize(140,150);
            ItemName=new JLabel(this.name);
            add(ItemName);
            ItemName.setBounds(40,0,140,30);
            ItemPrice=new JLabel(this.price+"");
            add(ItemPrice);
            ItemPrice.setBounds(30,50,50,30);

            minus=new JButton("-");
            add(minus);
            minus.addActionListener(this);
            minus.setBounds(5,110,45,30);

            plus=new JButton("+");
            add(plus);
            plus.addActionListener(this);
            plus.setBounds(95,110,45,30);

            Amount=new JTextField(amount+"",50);
            add(Amount);
            Amount.setBounds(53,110,40,30);

            setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        //메뉴 추가, 삭제 버튼 리스너
        public void actionPerformed(ActionEvent e){
            if(e.getSource()==minus){
                if(amount>0){
                    amount--;
                    Amount.setText(amount+"");
                    Price-=this.price;
                    totalPrice.setText(Price+"원");
                }
            }else if(e.getSource()==plus){
                amount++;
                Amount.setText(amount+"");
                Price+=this.price;
                totalPrice.setText(Price+"원");
            }
        }
    }
    public static void main(String[] args) {
        new CafeGUI();
    }

}