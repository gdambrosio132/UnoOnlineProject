public class Message implements java.io.Serializable{

    private String name, response;

    public Message(String name, String response) {
        this.name = name;
        this.response = response;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String toString(){
        return this.name + ": " + this.response;
    }

}
