

public class ConfigReader {
    private final static String CORE_CONFIGURATION_DB_TYPE = "DB-TYPE";

    /**
     * Method description
     * This methode is made to parse data from file of properties;
     * we return DBType (enum) by getting it constant PROPERTIES using String CORE_CONFIGURATION_DB_TYPE;
     * if we are not able to get it from properties - we throw IllegalArgumentException
     * ("Wrong DB type specified in file application.properties!");
     */
    public DbType parseConfig() {
        try {
            return DbType.valueOf(PropertyUtils.PROPERTIES.getProperty(CORE_CONFIGURATION_DB_TYPE));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Wrong DB type specified in file application.properties!");
        }
    }
}
