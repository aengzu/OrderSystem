import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<MenuItem> items;


    // 생성자
    public Menu() {
        this.items = new ArrayList<>();


        // 커피 아이템 추가
        addItem(new MenuItem("아메리카노", 1500, 100));
        addItem(new MenuItem("헤이즐넛아메리카노", 2700, 100));
        addItem(new MenuItem("카푸치노", 2900, 100));
        addItem(new MenuItem("에스프레소", 2000, 100));
        addItem(new MenuItem("카페모카", 4000, 100));

        // 라떼 아이템 추가
        addItem(new MenuItem("바닐라라떼", 3400, 100));
        addItem(new MenuItem("카페라떼", 2900, 100));
        addItem(new MenuItem("녹차라떼", 3500, 100));
        addItem(new MenuItem("딸기라떼", 3700, 100));

        // 에이드/스무디 아이템 추가
        addItem(new MenuItem("레몬에이드", 3500, 100));
        addItem(new MenuItem("딸기스무디", 4000, 100));
        addItem(new MenuItem("블루베리스무디", 4000, 100));
        addItem(new MenuItem("망고스무디", 4000, 100));
        addItem(new MenuItem("딸기바나나주스", 4000, 100));

        // 티 아이템 추가
        addItem(new MenuItem("아이스티", 3000, 100));
        addItem(new MenuItem("밀크티", 3700, 100));
        addItem(new MenuItem("얼그레이", 2500, 100));
        addItem(new MenuItem("캐모마일", 2500, 100));
        addItem(new MenuItem("유자차", 3300, 100));
        addItem(new MenuItem("허니자몽블랙티", 3700, 100));
        addItem(new MenuItem("흑당밀크티", 3500, 100));
    }


    private void addItem(MenuItem item) {
        items.add(item);
    }

    // getter 메서드
    public List<MenuItem> getItems() {
        return this.items;
    }

}