package motores;

public class PostgreMotorSQL extends MotorSQL {

    public PostgreMotorSQL() {
        this.url = "jdbc:postgresql://omenaca-david-dam-rec-1.cmywbzf3xqma.us-east-1.rds.amazonaws.com:5432/postgres";
        this.user = "postgres";
        this.password = "12345678";
    }
}