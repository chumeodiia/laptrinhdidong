package vn.iotstar.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private boolean success;
    private String message;
    private Object data;

    public Response() {}

    public Response(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    // GET + SET
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
}