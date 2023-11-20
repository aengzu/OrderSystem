import java.io.*;

public class FileHandler {
    private String filePath;

    // 생성자
    public FileHandler(String filePath) {
        this.filePath = filePath;
    }

    // Customer 정보를 파일에 저장하는 메서드
    public void saveCustomerInfo(Customer customer) {
        try {
            FileWriter writer = new FileWriter(filePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            // Customer의 정보를 문자열로 변환
            String customerInfo = customer.getName() + "," + customer.getPhoneNum() + "\n";

            // 파일에 쓰기
            bufferedWriter.write(customerInfo);

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 파일에서 Customer 정보를 불러오는 메서드
    public Customer loadCustomerInfo() {
        try {
            FileReader reader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line = bufferedReader.readLine();

            if (line != null) {
                // 문자열을 쉼표로 분리하여 Customer 객체 생성
                String[] parts = line.split(",");
                return new Customer(parts[0], parts[1]);
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
