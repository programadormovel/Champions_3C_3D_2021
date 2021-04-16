package br.com.itb.pra3.champions_3c_3d_2021;

public class Time {

    private int _id;
    private String nome_time;
    private String pais_time;
    private int status;

    public Time(int _id, String nome_time, String pais_time, int status) {
        this._id = _id;
        this.nome_time = nome_time;
        this.pais_time = pais_time;
        this.status = status;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getNome_time() {
        return nome_time;
    }

    public void setNome_time(String nome_time) {
        this.nome_time = nome_time;
    }

    public String getPais_time() {
        return pais_time;
    }

    public void setPais_time(String pais_time) {
        this.pais_time = pais_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
