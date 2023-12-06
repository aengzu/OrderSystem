import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    @Deprecated
    private String filePath;

    // 생성자
    @Deprecated
    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    public void writeFile(String filePath, String message){
        try (FileWriter writer = new FileWriter(filePath, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            // 파일에 쓰기
            bufferedWriter.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String readFile(String filePath){
        try (FileReader reader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            String line;

            //StringBuffer vs StringBuilder
            // StringBuffer는 멀티 스레드 환경, StringBuilder는 단일 스레드
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                // 문자열을 쉼표로 분리하여 Customer 객체 생성
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    // Customer 정보를 파일에 저장
    public void saveCustomerInfo(Customer customer) {
        try (FileWriter writer = new FileWriter(filePath, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            // Customer의 정보를 문자열로 변환
            String customerInfo = customer.getName() + "," + customer.getPhoneNum() + "\n";

            // 파일에 쓰기
            bufferedWriter.write(customerInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 파일에서 모든 고객 정보를 불러오는 메서드
    @Deprecated
    public List<Customer> loadAllCustomerInfo() {
        List<Customer> customers = new ArrayList<>();
        try (FileReader reader = new FileReader(filePath);
             BufferedReader bufferedReader = new BufferedReader(reader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // 문자열을 쉼표로 분리하여 Customer 객체 생성
                String[] parts = line.split(",");
                customers.add(new Customer(parts[0], parts[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }

    // 파일에서 재고 정보를 읽어서 List<MenuItem> 객체를 반환
    @Deprecated
    public List<MenuItem> loadInventory() {
        List<MenuItem> items = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                int price = Integer.parseInt(parts[1]);
                int inventory = Integer.parseInt(parts[2]);
                items.add(new MenuItem(name, price, inventory));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return items;
    }

    // List<MenuItem> 객체의 재고 정보를 파일에 저장
    @Deprecated
    public void saveInventory(List<MenuItem> items) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for (MenuItem item : items) {
                pw.println(item.getName() + "," + item.getPrice() + "," + item.getInventory());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}