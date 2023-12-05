import java.util.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private Map<MenuItem, Integer> items; // 주문한 메뉴 아이템과 그 수량
    private String status; // 주문 상태

    private int orderNum; // 대기 번호

    // 생성자
    public Order() {
        this.items = new HashMap<>();
        this.status = "주문 대기 중";
    }

    // getter & setter 메서드들
    @Deprecated
    public List<MenuItem> getItems() {
        return (List<MenuItem>) this.items;
    }


    public Map<MenuItem, Integer> getMaps(){
        return this.items;
    }
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
    public int getOrderNum() {
        return this.orderNum;
    }


    // 주문 아이템을 추가하는 메서드
    public void addItem(MenuItem item, int count) {
        items.put(item, count);
    }
    public void setItem(MenuItem item, int count){
        items.replace(item, count);
        if (count==0){
            items.remove(item);
        }

    }
    // 주문 아이템을 제거하는 메서드
    public void removeItem(MenuItem item) {
        this.items.remove(item);
    }

    // 전체 주문의 총 가격을 계산하는 메소드
    public int calculateTotal() {
        int total = 0;
        for (Map.Entry<MenuItem, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    // 주문에 포함된 메뉴 아이템의 수량을 반환하는 메소드
    public int getCount(MenuItem item) {
        return items.getOrDefault(item, 0);
    }
}