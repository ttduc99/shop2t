package model;

public class Loaisp {
  public int Id;
  public String Tenloaisp;
       public String Haloaisp;

    public Loaisp(int id, String tenloaisp,String haloaisp) {
        Id = id;
        Tenloaisp = tenloaisp;
        Haloaisp = haloaisp;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenloaisp() {
        return Tenloaisp;
    }

    public void setTenloaisp(String tenloaisp) {
        Tenloaisp = tenloaisp;
    }

    public String getHaloaisp() {
        return Haloaisp;
    }

    public void setHaloaisp(String haloaisp) {
        Haloaisp = haloaisp;
    }
}
