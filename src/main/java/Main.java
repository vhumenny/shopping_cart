import config.ConfigReader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("Main method run.");
        ConfigReader configReader = new ConfigReader();
        DataSourceManager dataSourceManager = new DataSourceManager(configReader);
        dataSourceManager.start();
    }
}
