package http;

import com.fasterxml.jackson.databind.ObjectMapper;
import http.client.AccuweatherClient;
import http.service.AccuweatherService;
import http.utils.PgConnectionUtils;
import lombok.SneakyThrows;
import okhttp3.OkHttpClient;

import java.sql.SQLException;

public class App {

    @SneakyThrows
    public static void main(final String[] args) {
        Class.forName("org.postgresql.Driver");
        OkHttpClient okHttpClient = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();

        AccuweatherClient accuweatherClient = new AccuweatherClient(okHttpClient, objectMapper);
        AccuweatherService accuweatherService = new AccuweatherService(accuweatherClient, null);

        initDb();
    }

    public static void initDb() {
        try (var connection = PgConnectionUtils.getConnection()) {
            var statement = connection.createStatement();
            String createTemperatureHistory = """
                    CREATE TABLE IF NOT EXISTS temperatureHistory (
                        id serial primary key,
                        city varchar(40) not null,
                        temperature decimal not null,
                        creare_date_time timestamp default now()
                    )
                    """;
            statement.execute(createTemperatureHistory);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
