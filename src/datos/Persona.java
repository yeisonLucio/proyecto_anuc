package datos;

import java.io.FileInputStream;
import java.util.Date;

public class Persona {
    private int Cod_afiliado;
    private String Nombre;
    private String Apellido;
    private int Cedula;
    private int Teléfono;
    private double Sisben;
    private String Email;
    private Date Fecha_nacimiento;
    private String Genero;
    private String Estudios;
    private String Tierra;
    private String SectorPro;
    private FileInputStream fis;
    private int longitudbytes;
    private int estado;
    
    
    public FileInputStream getFis() {
        return fis;
    }

    public void setFis(FileInputStream fis) {
        this.fis = fis;
    }

    public int getLongitudbytes() {
        return longitudbytes;
    }

    public void setLongitudbytes(int longitudbytes) {
        this.longitudbytes = longitudbytes;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
       
    public int getCod_afiliado() {
        return Cod_afiliado;
    }

    public void setCod_afiliado(int Cod_afiliado) {
        this.Cod_afiliado = Cod_afiliado;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }

    public int getCedula() {
        return Cedula;
    }

    public void setCedula(int Cedula) {
        this.Cedula = Cedula;
    }

    public int getTeléfono() {
        return Teléfono;
    }

    public void setTeléfono(int Teléfono) {
        this.Teléfono = Teléfono;
    }

    public double getSisben() {
        return Sisben;
    }

    public void setSisben(double Sisben) {
        this.Sisben = Sisben;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public Date getFecha_nacimiento() {
        return Fecha_nacimiento;
    }

    public void setFecha_nacimiento(Date Fecha_nacimiento) {
        this.Fecha_nacimiento = Fecha_nacimiento;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String Genero) {
        this.Genero = Genero;
    }

    public String getEstudios() {
        return Estudios;
    }

    public void setEstudios(String Estudios) {
        this.Estudios = Estudios;
    }

    public String getTierra() {
        return Tierra;
    }

    public void setTierra(String Tierra) {
        this.Tierra = Tierra;
    }

    public String getSectorPro() {
        return SectorPro;
    }

    public void setSectorPro(String SectorPro) {
        this.SectorPro = SectorPro;
    }

}
