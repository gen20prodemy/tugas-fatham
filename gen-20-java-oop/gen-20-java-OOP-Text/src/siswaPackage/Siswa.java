package siswaPackage;

public class Siswa {
    private int id;
    private String name;

    public Siswa(int id) {
        this.id = id;
    }

    public Siswa(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return id + "," + name;
    }
}
