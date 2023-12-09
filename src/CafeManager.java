import java.util.ArrayList;
import java.util.List;

public class CafeManager {
    private List<MenuItem> menu; // 메뉴
    private List<Order> orders; // 주문 내역

    // 생성자
    public CafeManager() {
        this.menu = new ArrayList<>();
        this.orders = new ArrayList<>();
    }

    // 메뉴 아이템을 품절 처리하는 메소드
    public void markAsSoldOut(MenuItem item) {
        if (menu.contains(item)) {
            item.setQuantity(0);
        }
    }

    // 일일 매출을 확인하는 메소드
    public int checkDailySales() {
        int totalSales = 0;
        for (Order order : orders) {
            totalSales += order.calculateTotal();
        }
        return totalSales;
    }

    // 메뉴 아이템별 판매 수량을 반환하는 메소드
    public int getSalesCount(MenuItem item) {
        int count = 0;
        for (Order order : orders) {
            count += order.getCount(item);
        }
        return count;
    }


    // getter 메소드
    public List<MenuItem> getMenu() {
        return this.menu;
    }
}