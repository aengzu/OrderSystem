import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private String filePath;

    // 생성자
    public FileHandler(String filePath) {
        this.filePath = filePath;
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
}