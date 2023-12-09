import java.io.IOException;
import java.util.Map;

public class Payment {
    private Order order; // 결제할 주문
    private final String PAYMENT_FILE_NAME = "Sale.txt";
    FileHandler fileHandler = new FileHandler(PAYMENT_FILE_NAME);
    // 생성자
    public Payment(Order order) {
        this.order = order;
    }

    // 신용카드로 결제하는 메서드
    public void payByCreditCard() {
        int totalAmount = order.calculateTotal();
        writeSale();
        System.out.println("신용카드로 " + totalAmount + "원을 결제하였습니다.");
    }

    // 현금으로 결제하는 메서드
    public void payByCash() {
        int totalAmount = order.calculateTotal();
        writeSale();
        System.out.println("현금으로 " + totalAmount + "원을 결제하였습니다.");
    }
    
    // 매출 작성 메서드
    private void writeSale() {
        Map<MenuItem, Integer> items = order.getMaps();
        for (Map.Entry<MenuItem, Integer> item : items.entrySet()) {
            String line = item.getKey().getName() + "," + item.getValue();
            fileHandler.writeFile(PAYMENT_FILE_NAME, line + "\n");
        }
    }
    private int getTotalAmount(){
        //TODO
        // 1. 파일이 있으면 제품 이름, 판매수량 불러와서 거기에 숫자 추가하고 저장.
        // 2. 파일이 없으면 파일을 생성하고 제품 이름, 판매수량 추가
        int totalAmount = order.calculateTotal();
        totalAmount += order.calculateTotal();
        return totalAmount;
    }

    public Order getOrder(){
        return this.order;
    }
}
