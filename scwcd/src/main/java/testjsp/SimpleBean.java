package testjsp;

public class SimpleBean {
    private String name;
    private String email;
    private String errMsg;
    private String subject;
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public String getSubject() {
        return subject;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
