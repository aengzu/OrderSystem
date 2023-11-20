public class MenuItem {
    private String name; // 아이템의 이름
    private int price; // 아이템의 가격
    private int inventory; // 아이템의 재고량

    // 생성자
    public MenuItem(String name, int price, int inventory) {
        this.name = name;
        this.price = price;
        this.inventory = inventory;
    }

    // getter & setter 메서드들
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getInventory() {
        return this.inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    // 재고를 줄이는 메서드
    public void decreaseInventory(int amount) {
        if (inventory >= amount) {
            inventory -= amount;
        } else {
            System.out.println("재고가 부족합니다.");
        }
    }

    // 재고를 늘리는 메서드
    public void increaseInventory(int amount) {
        inventory += amount;
    }

    public void setQuantity(int i) {
        inventory = i;
    }
}
