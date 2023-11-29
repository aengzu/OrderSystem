import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Menu {
    private List<MenuItem> items;
    private List<MenuItem> coffeeItems;
    private List<MenuItem> latteItems;
    private List<MenuItem> SmoothieItems;
    private List<MenuItem> teaItems;

    // 생성자
    public Menu() {
        this.items = new ArrayList<>();

        items.add(new MenuItem("아메리카노", 1500, 100));
        items.add(new MenuItem("헤이즐넛아메리카노", 2700, 100));
        items.add(new MenuItem("바닐라라떼", 3400, 100));
        items.add(new MenuItem("카페라떼", 2900, 100));
        items.add(new MenuItem("카푸치노", 2900, 100));
        items.add(new MenuItem("녹차라떼", 3500, 100));
        items.add(new MenuItem("카라멜마끼아또", 3700, 100));
        items.add(new MenuItem("에스프레소", 2000, 100));
        items.add(new MenuItem("카페모카", 4000, 100));
        items.add(new MenuItem("아이스티", 3000, 100));
        items.add(new MenuItem("레몬에이드", 3500, 100));
        items.add(new MenuItem("딸기스무디", 4000, 100));
        items.add(new MenuItem("블루베리스무디", 4000, 100));
        items.add(new MenuItem("망고스무디", 4000, 100));
        items.add(new MenuItem("밀크티", 3700, 100));
        items.add(new MenuItem("딸기라떼", 3700, 100));
        items.add(new MenuItem("딸기바나나주스", 4000, 100));
        items.add(new MenuItem("얼그레이", 2500, 100));
        items.add(new MenuItem("캐모마일", 2500, 100));
        items.add(new MenuItem("유자차", 3300, 100));
        items.add(new MenuItem("허니자몽블랙티", 3700, 100));
        items.add(new MenuItem("흑당밀크티", 3500, 100));

        //이준협 수정
        coffeeItems = new ArrayList<>();
        latteItems = new ArrayList<>();
        SmoothieItems = new ArrayList<>();
        teaItems = new ArrayList<>();

        // 커피 아이템 추가
        coffeeItems.add(new MenuItem("아메리카노", 1500, 100));
        coffeeItems.add(new MenuItem("헤이즐넛아메리카노", 2700, 100));
        coffeeItems.add(new MenuItem("카푸치노", 2900, 100));
        coffeeItems.add(new MenuItem("에스프레소", 2000, 100));
        coffeeItems.add(new MenuItem("카페모카", 4000, 100));

        // 라떼 아이템 추가
        latteItems.add(new MenuItem("바닐라라떼", 3400, 100));
        latteItems.add(new MenuItem("카페라떼", 2900, 100));
        latteItems.add(new MenuItem("녹차라떼", 3500, 100));
        latteItems.add(new MenuItem("딸기라떼", 3700, 100));

        // 에이드/스무디 아이템 추가
        SmoothieItems.add(new MenuItem("레몬에이드", 3500, 100));
        SmoothieItems.add(new MenuItem("딸기스무디", 4000, 100));
        SmoothieItems.add(new MenuItem("블루베리스무디", 4000, 100));
        SmoothieItems.add(new MenuItem("망고스무디", 4000, 100));
        SmoothieItems.add(new MenuItem("딸기바나나주스", 4000, 100));

        // 티 아이템 추가
        teaItems.add(new MenuItem("아이스티", 3000, 100));
        teaItems.add(new MenuItem("밀크티", 3700, 100));
        teaItems.add(new MenuItem("얼그레이", 2500, 100));
        teaItems.add(new MenuItem("캐모마일", 2500, 100));
        teaItems.add(new MenuItem("유자차", 3300, 100));
        teaItems.add(new MenuItem("허니자몽블랙티", 3700, 100));
        teaItems.add(new MenuItem("흑당밀크티", 3500, 100));
    }
    // getter 메서드
    public List<MenuItem> getItems() {
        return this.items;
    }

    public List<MenuItem> getCoffeeItems() {
        return coffeeItems;
    }

    public List<MenuItem> getLatteItems() {
        return latteItems;
    }

    public List<MenuItem> getAdeSmoothieItems() {
        return SmoothieItems;
    }

    public List<MenuItem> getTeaItems() {
        return teaItems;
    }

}