import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PurchaseFrame extends JFrame {
    private CardLayout cardlayout = new CardLayout();                  // cardlayout : 패널 변경을 위한 카드 레이아웃
    private JButton CardButton, CashButton;             // CardButton, CashButton : 카드 버튼, 현금 버튼
    private JButton PurchaseButton2, ReturnButton;  // PurchaseButton3 : 결제하기 버튼1,2 / 뒤로가기 버튼
    private JPanel purchase_panel, purchasing_panel, complete_panel;
    private Container cPane;
    private JLabel purchasingLabel;
    private int orderNum;
    private PurchaseType purchaseType = PurchaseType.NONE;

    private enum PurchaseType{
        CASH,CARD,NONE;
    }
    private Payment payment;



    public PurchaseFrame(Payment payment) {
        this.orderNum = payment.getOrder().getOrderNum();
        this.payment = payment;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(purchase_panel = new PurchasePanel());
        add(purchasing_panel = new PurchasingPanel());
        add(complete_panel = new CompletePanel());
        cPane = getContentPane();

        setSize(900, 600);
        setLayout(cardlayout);
        setVisible(true);

    }

class PurchasePanel extends JPanel {
    public PurchasePanel() {

        setLocation(50, 20);
        setLayout(null);

        //결제 수단을 선택하지 않고 결제하기를 눌렀을 경우 화면 왼쪽 아래에 경고 문구를 띄워줌
        JLabel dialog = new JLabel("결제 수단을 클릭하셔야 합니다.");
        dialog.setBounds(60, 520, 300, 30);

        JLabel label = new JLabel("결제수단");
        label.setBounds(390, 20, 200, 30);
        label.setFont(new Font("Dialog", Font.PLAIN, 30));
        add(label);

        //취소 버튼. 누르면 전 화면으로 돌아감
        JButton CancelButton = new JButton("Cancel");
        CancelButton.setBounds(800, 10, 80, 30);
        CancelButton.addActionListener(e -> {
            dispose();
            new CafeGUI();
        });
        add(CancelButton);

        //Card, Cash 버튼 각각 추가.
        CardButton = new JButton("Card");
        CashButton = new JButton("Cash");
        CardButton.setBounds(100, 100, 300, 300);
        CashButton.setBounds(500, 100, 300, 300);
        CardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == CardButton) {
                    CardButton.setBackground(Color.ORANGE);
                    CashButton.setBackground(null);
                    purchaseType = PurchaseType.CARD;
                } else if (e.getSource() == CashButton) {
                    CardButton.setBackground(null);
                    CashButton.setBackground(Color.ORANGE);
                    purchaseType = PurchaseType.NONE;
                }
            }
        });
        CashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == CardButton) {
                    CardButton.setBackground(Color.ORANGE);
                    CashButton.setBackground(null);
                    purchaseType = PurchaseType.CASH;
                } else if (e.getSource() == CashButton) {
                    CardButton.setBackground(null);
                    CashButton.setBackground(Color.ORANGE);
                    purchaseType = PurchaseType.NONE;
                }
            }
        });
        add(CardButton);
        add(CashButton);

        //결제 버튼 : 클릭시 결제중으로 이동
        PurchaseButton2 = new JButton("결제하기");
        PurchaseButton2.addActionListener(e -> {
            if (CardButton.getBackground() == Color.ORANGE || CashButton.getBackground() == Color.ORANGE) {
                CardButton.setBackground(null);
                CashButton.setBackground(null);
                cardlayout.next(cPane);
                PurchasingThread p= new PurchasingThread();
                p.start();
            } else {
                add(dialog);
            }
        });
        add(PurchaseButton2);
        PurchaseButton2.setBounds(360, 430, 180, 50);

        setVisible(true);
    }
    }

//결제 로딩 화면
class PurchasingPanel extends JPanel{
    public PurchasingPanel() {
        setLayout(null);
        setSize(900, 500);
        setLocation(50, 20);

        add(purchasingLabel = new JLabel("결제 중..."));
        purchasingLabel.setFont(new Font("Dialog", Font.PLAIN, 50));
        purchasingLabel.setBounds(350, 300, 250, 50);
    }
    //결제 대기 시간을 구현하고 화면을 움직이는 쓰레드
    }

//결제 완료 화면
class CompletePanel extends JPanel {
    public CompletePanel() {

        setSize(900, 500);
        setLocation(50, 20);
        setLayout(null);

        //결제 완료 문구
        JLabel complete = new JLabel("결제 완료!");
        complete.setFont(new Font("Dialog", Font.BOLD, 40));
        complete.setBounds(400, 30, 250, 50);
        add(complete);

        //대기 번호 문구
        JLabel WaitingNum = new JLabel("주문 번호:");
        WaitingNum.setFont(new Font("Dialog", Font.PLAIN, 40));
        WaitingNum.setBounds(250, 300, 250, 50);
        add(WaitingNum);

        //대기 번호 표시
        JLabel NumberLabel = new JLabel();
        NumberLabel.setText(orderNum+ " 번");
        NumberLabel.setFont(new Font("Dialog", Font.BOLD, 50));
        NumberLabel.setForeground(Color.RED);
        NumberLabel.setBounds(500, 300, 500, 50);
        add(NumberLabel);

        //첫 화면으로 돌아가는 버튼
        ReturnButton = new JButton("처음으로");
        add(ReturnButton);
        ReturnButton.setBounds(400, 400, 100, 40);
        ReturnButton.addActionListener(e -> {
                dispose();
                new CafeGUI();
      });
        ReturnButton.setVisible(false);
    }
}
    class PurchasingThread extends Thread {
        public void run() {
            for(int i=0;i<2;i++){
                purchasingLabel.setText("결제 중.");
                try{Thread.sleep(500);}catch(InterruptedException e){}
                purchasingLabel.setText("결제 중..");
                try{Thread.sleep(500);}catch(InterruptedException e){}
                purchasingLabel.setText("결제 중...");
                try{Thread.sleep(500);}catch(InterruptedException e){}
            }
            if(purchaseType == PurchaseType.CARD){
                payment.payByCreditCard();
            }
            else {
                payment.payByCash();
            }
            cardlayout.next(cPane);
            ReturnButton.setVisible(true);

        }

    }
}