package model;

import java.io.Serializable;

public class Sanpham implements Serializable {
    public int ID;
    public String Tensp;
    public int Gia;
    public String Motasp;
    public String Hinhanh;
    public int IDsp;

    public Sanpham(int ID, String tensp, int gia, String motasp, String hinhanh, int IDsp) {
        this.ID = ID;
        Tensp = tensp;
        Gia = gia;
        Motasp = motasp;
        Hinhanh = hinhanh;
        this.IDsp = IDsp;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTensp() {
        return Tensp;
    }

    public void setTensp(String tensp) {
        Tensp = tensp;
    }

    public int getGia() {
        return Gia;
    }

    public void setGia(int gia) {
        Gia = gia;
    }

    public String getMotasp() {
        return Motasp;
    }

    public void setMotasp(String motasp) {
        Motasp = motasp;
    }

    public String getHinhanh() {
        return Hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        Hinhanh = hinhanh;
    }

    public int getIDsp() {
        return IDsp;
    }

    public void setIDsp(int IDsp) {
        this.IDsp = IDsp;
    }
}
