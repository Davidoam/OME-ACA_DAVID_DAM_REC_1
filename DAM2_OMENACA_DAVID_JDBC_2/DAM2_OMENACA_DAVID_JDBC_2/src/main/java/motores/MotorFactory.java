package motores;

public class MotorFactory {

    public static final int POSTGRE = 1;

    public static MotorSQL create(int tipoMotor) {

        MotorSQL motor = null;

        switch (tipoMotor) {
            case POSTGRE:
                motor = new PostgreMotorSQL();
                break;

            default:
                System.out.println("Motor no soportado.");
                break;
        }

        return motor;
    }
}
