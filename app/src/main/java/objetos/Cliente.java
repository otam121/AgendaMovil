package objetos;

/**
 * Created by JuanSalinas on 09/09/2017.
 */

public class Cliente {
    String adeudo;
    String fechaCargo;
    String fechaPago;
    String ultimoCargo;
    String ultimoPago;

    public Cliente() {

    }

    public Cliente(String adeudo, String fechaCargo, String fechaPago, String ultimoCargo, String ultimoPago) {
        this.adeudo = adeudo;
        this.fechaCargo = fechaCargo;
        this.fechaPago = fechaPago;
        this.ultimoCargo = ultimoCargo;
        this.ultimoPago = ultimoPago;
    }

    public String getAdeudo() {
        return adeudo;
    }

    public void setAdeudo(String adeudo) {
        this.adeudo = adeudo;
    }

    public String getFechaCargo() {
        return fechaCargo;
    }

    public void setFechaCargo(String fechaCargo) {
        this.fechaCargo = fechaCargo;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getUltimoCargo() {
        return ultimoCargo;
    }

    public void setUltimoCargo(String ultimoCargo) {
        this.ultimoCargo = ultimoCargo;
    }

    public String getUltimoPago() {
        return ultimoPago;
    }

    public void setUltimoPago(String ultimoPago) {
        this.ultimoPago = ultimoPago;
    }
}
