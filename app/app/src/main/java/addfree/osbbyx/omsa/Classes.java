package addfree.osbbyx.omsa;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class Classes {

    String nombre, telefono, edad,id;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    String email = user.getEmail().toString();
    int ad;


    public Classes(String id,String email, String nombre, String telefono, String edad, int ad) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.telefono = telefono;
        this.edad = edad;
        this.ad = ad;
    }

    public Classes(String email, String nombre, String telefono, String edad, int ad) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.telefono = telefono;
        this.edad = edad;
        this.ad = ad;
    }
    public String getId(){
        return id;
    }

    public String getEmaill(){
        return email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEdad() {
        return edad;
    }
    public int getAd(){
        return ad;
    }
}
