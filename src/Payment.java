public class Payment {
    private Order order; // 결제할 주문

    // 생성자
    public Payment(Order order) {
        this.order = order;
    }

    // 신용카드로 결제하는 메서드
    public void payByCreditCard() {
        int totalAmount = order.calculateTotal();
        System.out.println("신용카드로 " + totalAmount + "원을 결제하였습니다.");
    }

    // 현금으로 결제하는 메서드
    public void payByCash() {
        int totalAmount = order.calculateTotal();
        System.out.println("현금으로 " + totalAmount + "원을 결제하였습니다.");
    }

    public Order getOrder(){
        return this.order;
    }
}
