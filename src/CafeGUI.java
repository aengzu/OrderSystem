import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CafeGUI extends JFrame {
    private CafeManager manager;
    private FileHandler fileHandler;

    private JTextArea displayArea;
    private JTextField customerNameField;
    private JTextField customerPhoneField;
    private JComboBox<MenuItem> menuComboBox;
    private JButton orderButton;
    private JButton payButton;

    public CafeGUI() {
        manager = new CafeManager();
        fileHandler = new FileHandler("customer.txt");

        setLayout(new BorderLayout());

        displayArea = new JTextArea();
        add(displayArea, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        customerNameField = new JTextField(10);
        customerPhoneField = new JTextField(10);
        inputPanel.add(new JLabel("이름:"));
        inputPanel.add(customerNameField);
        inputPanel.add(new JLabel("전화번호:"));
        inputPanel.add(customerPhoneField);

        menuComboBox = new JComboBox<>(manager.getMenu().toArray(new MenuItem[0]));
        inputPanel.add(menuComboBox);

        orderButton = new JButton("주문");
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = customerNameField.getText();
                String phone = customerPhoneField.getText();
                MenuItem item = (MenuItem) menuComboBox.getSelectedItem();

                Customer customer = new Customer(name, phone);
                Order order = new Order();
                order.addItem(item);
                customer.addOrder(order);

                displayArea.append("주문이 접수되었습니다.\n");
                displayArea.append("이름: " + name + ", 전화번호: " + phone + ", 주문 아이템: " + item.getName() + "\n");

                fileHandler.saveCustomerInfo(customer);
            }
        });
        inputPanel.add(orderButton);

        payButton = new JButton("결제");
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Customer customer = fileHandler.loadCustomerInfo();
                if (customer != null) {
                    Payment payment = new Payment(customer.getOrders().get(0));
                    payment.payByCreditCard();

                    displayArea.append("결제가 완료되었습니다.\n");
                } else {
                    displayArea.append("결제할 주문이 없습니다.\n");
                }
            }
        });
        inputPanel.add(payButton);

        add(inputPanel, BorderLayout.SOUTH);

        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new CafeGUI();
    }
}
