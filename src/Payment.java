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
            String line = item.getKey().getName() + "," + item.getValue() + "," + (item.getKey().getPrice() * item.getValue());
            fileHandler.writeFile(PAYMENT_FILE_NAME, line + "\n");
        }
    }
    // 모든 판매의 총 가격을 계산하는 메소드
    public int getTotalSales() {
        String rawString = fileHandler.readFile(PAYMENT_FILE_NAME);
        String[] strings = rawString.split("\n");
        int totalSales = 0;
        for (String str : strings) {
            String[] parts = str.split(",");
            int totalPrice = Integer.parseInt(parts[2]);
            totalSales += totalPrice;
        }
        return totalSales;
    }

    public Order getOrder(){
        return this.order;
    }
}
