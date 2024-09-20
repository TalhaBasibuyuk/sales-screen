import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class OrderCompletion {

    public static int completeOrder(String fullName, String phoneNumber, String emailAddress, String item_description, String price_paid){
        return orderCompleter(fullName, phoneNumber, emailAddress, item_description, price_paid);
    }

    private static int orderCompleter(String name, String phone, String mail, String items, String price){

        try {
            String jsonInputString = String.format(
                    "{\"customer\": {\"fullName\": \"%s\", \"phoneNumber\": \"%s\", \"emailAddress\": \"%s\"}, \"order\": {\"item_description\": \"%s\", \"price_paid\": \"%s\"}}",
                    name, phone, mail, items, price
            );

            String apiUrl = "http://localhost:8080/orders/post";

            HttpURLConnection apiConnection = fetchApiResponse(apiUrl);

            if (apiConnection != null) {
                apiConnection.setRequestMethod("POST");
                apiConnection.setRequestProperty("Content-Type", "application/json; utf-8");
                apiConnection.setDoOutput(true);

                try (OutputStream os = apiConnection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                String response = readApiResponse(apiConnection);
                return Integer.parseInt(response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }

    private static String readApiResponse(HttpURLConnection apiConnection) {
        try {

            StringBuilder resultsJson = new StringBuilder();

            Scanner scanner = new Scanner(apiConnection.getInputStream());

            while (scanner.hasNext()){
                resultsJson.append(scanner.nextLine());
            }

            scanner.close();

            return  resultsJson.toString();

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private static HttpURLConnection fetchApiResponse(String urlString){
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");

            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
