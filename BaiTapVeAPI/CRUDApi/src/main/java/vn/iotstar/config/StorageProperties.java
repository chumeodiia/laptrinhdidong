package vn.iotstar.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("storage")
public class StorageProperties {
    private String location = "uploads";  // mặc định

    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}
