import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<MenuItem> items; // 주문 아이템 리스트
    private String status; // 주문 상태

    private int orderNum; // 대기 번호

    // 생성자
    public Order() {
        this.items = new ArrayList<>();
        this.status = "주문 대기 중";
    }

    // getter & setter 메서드들
    public List<MenuItem> getItems() {
        return this.items;
    }
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void setOrderNum() {
        this.orderNum = orderNum;
    }
    public int getOrderNum() {
        return this.orderNum;
    }


    // 주문 아이템을 추가하는 메서드
    public void addItem(MenuItem item) {
        this.items.add(item);
    }

    // 주문 아이템을 제거하는 메서드
    public void removeItem(MenuItem item) {
        this.items.remove(item);
    }

    // 주문의 총 금액을 계산하는 메서드
    public int calculateTotal() {
        int total = 0;
        for (MenuItem item : items) {
            total += item.getPrice();
        }
        return total;
    }
}
