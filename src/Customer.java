import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name; // 고객의 이름
    private String phoneNum; // 고객의 연락처
    private List<Order> orders; // 고객의 주문 내역
    private int pointStack; // 고객의 스탬프

    // 생성자
    public Customer(String name, String phoneNum) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.orders = new ArrayList<>();
        this.pointStack = pointStack;
    }

    // getter & setter 메서드들
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    // 주문
    public void addOrder(Order order) {
        this.orders.add(order);
    }
}
